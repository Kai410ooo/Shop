package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Account;
import model.Shiharai;

public class AccountDAO {
	private final String JDBC_URL = "jdbc:postgresql://localhost:5432/bshop";
	private final String DB_USER = "postgres";
	private final String DB_PASS = "sql";

	public Account findAccountByUserId(String userId) {
		Account account = null; // 結果を格納する変数

		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("PostgreSQL JDBCドライバが見つかりません: " + e.getMessage());
			e.printStackTrace();
		}

		String sql = "SELECT u.user_id, u.user_pass, u.user_name, u.postnumber, u.address, u.tell, u.mail, u.pay_id, s.pay_name "
				+
				"FROM Users u " +
				"LEFT JOIN Shiharai s ON u.pay_id = s.pay_id " +
				"WHERE u.user_id = ?";

		try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, userId); // クエリに userId をセット

			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) { // データが存在する場合
					account = new Account();
					account.setUserId(rs.getString("user_id"));
					account.setUserPass(rs.getString("user_pass"));
					account.setName(rs.getString("user_name"));
					account.setPostnumber(rs.getInt("postnumber"));
					account.setAddress(rs.getString("address"));
					account.setTell(rs.getString("tell"));
					account.setMail(rs.getString("mail"));
					account.setPayId(rs.getInt("pay_id"));
					account.setPayName(rs.getString("pay_name"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return account; // 該当するユーザーがいなければ null を返す
	}

	public boolean isUserIdDuplicate(String userId) {
		boolean isDuplicate = false;

		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("PostgreSQL JDBCドライバが見つかりません: " + e.getMessage());
			e.printStackTrace();
		}

		try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
			// user_idを使って、usersテーブル内で重複があるか確認
			String sql = "SELECT COUNT(*) FROM users WHERE user_id = ?";

			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, userId); // user_idをセット

			// SQLを実行して、結果を取得
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					int count = rs.getInt(1);
					// 重複があればcountが1以上になる
					if (count > 0) {
						isDuplicate = true;
					}
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		// 重複の有無を返す
		return isDuplicate;
	}

	public boolean updateUserAccount(Account correctdata) {
		String userId = correctdata.getUserId(); // userIdを取得
		boolean isUpdated = false;

		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("PostgreSQL JDBCドライバが見つかりません: " + e.getMessage());
			e.printStackTrace();
		}

		try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
			// user_idに基づいて、usersテーブル内の該当するユーザー情報を更新
			String sql = "UPDATE users SET user_pass = ?, user_name = ?, postnumber = ?, address = ?, tell = ?, mail = ?, pay_id = ? "
					+
					"WHERE user_id = ?";

			PreparedStatement stmt = conn.prepareStatement(sql);

			// correctdata の内容をパラメータとして設定
			stmt.setString(1, correctdata.getUserPass()); // パスワード
			stmt.setString(2, correctdata.getName()); // 氏名
			stmt.setInt(3, correctdata.getPostnumber()); // 郵便番号
			stmt.setString(4, correctdata.getAddress()); // 住所
			stmt.setString(5, correctdata.getTell()); // 電話番号
			stmt.setString(6, correctdata.getMail()); // メールアドレス
			stmt.setInt(7, correctdata.getPayId()); // 支払方法
			stmt.setString(8, userId); // user_idで検索

			// SQLを実行して更新
			int rowsAffected = stmt.executeUpdate();

			// 更新が成功した場合、1行以上が影響を受ける
			if (rowsAffected > 0) {
				isUpdated = true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		// 更新成功かどうかを返す
		return isUpdated;
	}

	public boolean deleteById(String userId) {
		boolean isDeleted = false;

		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("PostgreSQL JDBCドライバが見つかりません: " + e.getMessage());
			e.printStackTrace();
		}

		try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
			// user_idに基づいてユーザーを削除
			String sql = "DELETE FROM users WHERE user_id = ?";

			PreparedStatement stmt = conn.prepareStatement(sql);

			// userIdをバインドパラメータとして設定
			stmt.setString(1, userId);

			// SQLを実行して削除する
			int rowsAffected = stmt.executeUpdate();

			// 削除が成功した場合、1行以上が影響を受ける
			if (rowsAffected > 0) {
				isDeleted = true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		// 削除成功かどうかを返す
		return isDeleted;
	}

	public boolean RegisterUserAccount(Account addaccount) {
		try {
			// JDBCドライバの読み込み
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("PostgreSQL JDBCドライバが見つかりません: " + e.getMessage());
			e.printStackTrace();
			return false;
		}

		// SQL文の作成 (UsersテーブルへのINSERT)
		String sql = "INSERT INTO users (user_id, user_pass, user_name, postnumber, address, tell, mail, pay_id) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

		try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
				PreparedStatement pStmt = conn.prepareStatement(sql)) {

			// Accountオブジェクトから値を取得して、PreparedStatementにセット
			pStmt.setString(1, addaccount.getUserId()); // 会員ID
			pStmt.setString(2, addaccount.getUserPass()); // パスワード
			pStmt.setString(3, addaccount.getName()); // 氏名
			pStmt.setInt(4, addaccount.getPostnumber()); // 郵便番号
			pStmt.setString(5, addaccount.getAddress()); // 住所
			pStmt.setString(6, addaccount.getTell()); // 電話番号
			pStmt.setString(7, addaccount.getMail()); // メールアドレス
			pStmt.setInt(8, addaccount.getPayId()); // 支払方法

			// SQL文を実行し、挿入操作を行う
			int result = pStmt.executeUpdate(); // 1行挿入を実行

			return result > 0; // 挿入が成功した場合はtrueを返す
		} catch (SQLException e) {
			e.printStackTrace();
			return false; // エラーが発生した場合はfalseを返す
		}
	}

	// Shiharaiテーブルから全てのpay_id, pay_nameを取得するメソッド
	public List<Shiharai> findAllShiharai() {
		List<Shiharai> shiharaiList = new ArrayList<>();

		try {
			// JDBCドライバの読み込み
			Class.forName("org.postgresql.Driver");

			// データベース接続
			try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
				// SQLクエリを作成（Shiharaiテーブルから全てのデータを取得）
				String sql = "SELECT pay_id, pay_name FROM Shiharai";

				// PreparedStatementを作成してSQLを実行
				try (PreparedStatement stmt = conn.prepareStatement(sql);
						ResultSet rs = stmt.executeQuery()) {

					// 結果セットを処理
					while (rs.next()) {
						int id = rs.getInt("pay_id");
						String name = rs.getString("pay_name");

						// Shiharaiオブジェクトを作成
						Shiharai shiharai = new Shiharai(id, name);

						// リストに追加
						shiharaiList.add(shiharai);
					}
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}

		} catch (ClassNotFoundException e) {
			System.out.println("PostgreSQL JDBCドライバが見つかりません: " + e.getMessage());
			e.printStackTrace();
		}

		// Shiharaiのリストを返す
		return shiharaiList;
	}

	public Account findAccountFromRireki(String username, String purchaseDate) {
		Account account = null;

		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("PostgreSQL JDBCドライバが見つかりません: " + e.getMessage());
			e.printStackTrace();
		}

		String sql = "SELECT u.user_id, u.user_pass, u.user_name, u.postnumber, u.address, u.tell, u.mail, u.pay_id, s.pay_name "
				+
				"FROM Users u " +
				"LEFT JOIN Shiharai s ON u.pay_id = s.pay_id " +
				"WHERE u.user_name = ? AND u.purchase_date = ?";

		try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, username);
			stmt.setString(2, purchaseDate);

			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					account = new Account();
					account.setUserId(rs.getString("user_id"));
					account.setUserPass(rs.getString("user_pass"));
					account.setName(rs.getString("user_name"));
					account.setPostnumber(rs.getInt("postnumber"));
					account.setAddress(rs.getString("address"));
					account.setTell(rs.getString("tell"));
					account.setMail(rs.getString("mail"));
					account.setPayId(rs.getInt("pay_id"));
					account.setPayName(rs.getString("pay_name"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return account;
	}


}