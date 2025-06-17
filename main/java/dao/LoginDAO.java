package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Login;

public class LoginDAO {
	private final String JDBC_URL = "jdbc:postgresql://localhost:5432/bshop";
	private final String DB_USER = "postgres";
	private final String DB_PASS = "sql";

	public String extractPassById(Login login) {
		String searchid = login.getId(); // ユーザーID を取得
		String searchpass = null; // パスワードの初期化
		System.out.println("LoginDAO：searchid=" + searchid);

		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			//			throw new IllegalStateException(
			//					"JDBCドライバを読み込めませんでした");
			System.out.println("PostgreSQL JDBCドライバが見つかりません: " + e.getMessage());
			e.printStackTrace();
		}

		try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
			// USER_ID に基づいてパスワードを取得するためのSELECT文
			String sql = "SELECT user_pass FROM users WHERE user_id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);

			// searchidをバインドパラメータとして設定
			stmt.setString(1, searchid);

			ResultSet rs = stmt.executeQuery(); // クエリを実行して結果を取得

			// 結果があれば1件のみ処理
			if (rs.next()) {
				searchpass = rs.getString("user_pass"); // パスワードを取得
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("LoginDAO：searchpass=" + searchpass);
		// パスワードを返す（存在しない場合はnull）
		return searchpass;
	}

}