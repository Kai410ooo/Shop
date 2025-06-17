package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.DuplicateKeyException;
import model.Shohin;

public class AdminDAO {
	private final String JDBC_URL = "jdbc:postgresql://localhost:5432/bshop";
	private final String DB_USER = "postgres";
	private final String DB_PASS = "sql";

	public Shohin findShohinByShohinId(int shohinId) {
	    Shohin shohin = null; // 結果を格納する変数

	    try {
	        // PostgreSQL JDBCドライバの読み込み
	        Class.forName("org.postgresql.Driver");
	    } catch (ClassNotFoundException e) {
	        System.out.println("PostgreSQL JDBCドライバが見つかりません: " + e.getMessage());
	        e.printStackTrace();
	    }

	    // 商品情報を取得するためのSQLクエリ
	    String sql = "SELECT shohin_id, shohin_mei, detail, hanbai_tanka, zaiko, shohin_imgpath " +
	                 "FROM Shohin " +
	                 "WHERE shohin_id = ?";

	    // データベース接続とクエリ実行
	    try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
	         PreparedStatement stmt = conn.prepareStatement(sql)) {

	        stmt.setInt(1, shohinId); // クエリに shohinId をセット（int 型として）

	        // クエリを実行して結果を取得
	        try (ResultSet rs = stmt.executeQuery()) {
	            if (rs.next()) { // データが存在する場合
	                // Shohin オブジェクトを作成し、結果をセット
	                shohin = new Shohin();
	                shohin.setShohinId(rs.getInt("shohin_id"));  // 商品ID
	                shohin.setShohinMei(rs.getString("shohin_mei"));  // 商品名
	                shohin.setDetail(rs.getString("detail"));  // 商品詳細
	                shohin.setHanbaiTanka(rs.getInt("hanbai_tanka"));  // 販売単価
	                shohin.setZaiko(rs.getInt("zaiko"));  // 在庫数
	                shohin.setShohinImgPath(rs.getString("shohin_imgpath"));  // 商品画像パス
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return shohin; // 該当する商品がいなければ null を返す
	}


	 // ユーザーIDが重複しているかを確認するメソッド
    public boolean isShohinIdDuplicate(String shohinId) {
        System.out.println("Dao内isShohinIdDuplicateメソッド：shohinId："+shohinId);
	    try {
	        Class.forName("org.postgresql.Driver");
	    } catch (ClassNotFoundException e) {
	        System.out.println("PostgreSQL JDBCドライバが見つかりません: " + e.getMessage());
	        e.printStackTrace();
	    }
        // データベースからユーザーIDがすでに存在するか確認
        String sql = "SELECT COUNT(*) FROM users WHERE user_id = ?";
        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, shohinId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // 1件以上存在すれば重複している
            }
        } catch (SQLException e) {
            // 重複キーエラーを検出した場合、DuplicateKeyExceptionをスロー
            if (e.getMessage().contains("duplicate key value violates unique constraint")) {
                throw new DuplicateKeyException("IDが重複しています。別のIDを選んでください。");
            }
            e.printStackTrace();
        }

        return false;
    }

	public boolean updateShohin(Shohin correctdata) {
	    int shohinId = correctdata.getShohinId(); // shohinIdを取得
	    boolean isUpdated = false;

	    try {
	        Class.forName("org.postgresql.Driver");
	    } catch (ClassNotFoundException e) {
	        System.out.println("PostgreSQL JDBCドライバが見つかりません: " + e.getMessage());
	        e.printStackTrace();
	    }

	    try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
	        // 商品情報を更新するSQL文
	        String sql = "UPDATE shohin SET shohin_mei = ?, detail = ?, hanbai_tanka = ?, zaiko = ?, shohin_imgpath = ? WHERE shohin_id = ?";

	        PreparedStatement stmt = conn.prepareStatement(sql);

	        // correctdata の内容をパラメータとして設定
	        stmt.setString(1, correctdata.getShohinMei()); // 商品名
	        stmt.setString(2, correctdata.getDetail()); // 詳細
	        stmt.setInt(3, correctdata.getHanbaiTanka()); // 販売単価
	        stmt.setInt(4, correctdata.getZaiko()); // 在庫
	        stmt.setString(5, correctdata.getShohinImgPath()); // 商品画像パス
	        stmt.setInt(6, shohinId); // 商品ID

	        // SQLを実行して更新
	        int rowsAffected = stmt.executeUpdate();

	        // 更新が成功した場合、1行以上が影響を受ける
	        if (rowsAffected > 0) {
	            isUpdated = true;
	        }

	    } catch (SQLException e) {
	        // 重複キーエラーを検出した場合、DuplicateKeyExceptionをスロー
	        if (e.getMessage().contains("duplicate key value violates unique constraint")) {
	            throw new DuplicateKeyException("IDが重複しています。別のIDを選んでください。");
	        }
	        e.printStackTrace();
	    }

	    // 更新成功かどうかを返す
	    return isUpdated;
	}


	public boolean deleteById(int shohinId) {
	    boolean isDeleted = false;

	    try {
	        Class.forName("org.postgresql.Driver");
	    } catch (ClassNotFoundException e) {
	        System.out.println("PostgreSQL JDBCドライバが見つかりません: " + e.getMessage());
	        e.printStackTrace();
	    }

	    try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
	        // shohin_idに基づいて商品を削除
	        String sql = "DELETE FROM shohin WHERE shohin_id = ?";

	        PreparedStatement stmt = conn.prepareStatement(sql);

	        // 引数として受け取ったshohinIdをバインドパラメータとして設定
	        stmt.setInt(1, shohinId);  // shohinIdをintとして設定

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



	public boolean RegisterShohin(Shohin addshohin) {
	    try {
	        // JDBCドライバの読み込み
	        Class.forName("org.postgresql.Driver");
	    } catch (ClassNotFoundException e) {
	        System.out.println("PostgreSQL JDBCドライバが見つかりません: " + e.getMessage());
	        e.printStackTrace();
	        return false;
	    }

	    // SQL文の作成 (ShohinテーブルへのINSERT)
	    String sql = "INSERT INTO shohin (shohin_mei, detail, hanbai_tanka, zaiko, shohin_imgpath) "
	               + "VALUES (?, ?, ?, ?, ?)";

	    try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
	         PreparedStatement pStmt = conn.prepareStatement(sql)) {

	        // Shohinオブジェクトから値を取得して、PreparedStatementにセット
	        pStmt.setString(1, addshohin.getShohinMei()); // 商品名
	        pStmt.setString(2, addshohin.getDetail());    // 詳細
	        pStmt.setInt(3, addshohin.getHanbaiTanka());  // 販売単価
	        pStmt.setInt(4, addshohin.getZaiko());        // 在庫数
	        pStmt.setString(5, addshohin.getShohinImgPath()); // 商品画像のパス

	        // SQL文を実行し、挿入操作を行う
	        int result = pStmt.executeUpdate(); // 1行挿入を実行

	        return result > 0; // 挿入が成功した場合はtrueを返す
	    } catch (SQLException e) {
	        // 重複キーエラーを検出した場合、DuplicateKeyExceptionをスロー
	        if (e.getMessage().contains("duplicate key value violates unique constraint")) {
	            throw new DuplicateKeyException("IDが重複しています。別のIDを選んでください。");
	        }
	        e.printStackTrace();
	    }
	    return false;
	}

	
    // Shohinテーブルからすべてのデータを取得するメソッド
    public List<Shohin> findAllShohin() {
	    try {
	        // JDBCドライバの読み込み
	        Class.forName("org.postgresql.Driver");
	    } catch (ClassNotFoundException e) {
	        System.out.println("PostgreSQL JDBCドライバが見つかりません: " + e.getMessage());
	        e.printStackTrace();
	    }
        List<Shohin> shohinList = new ArrayList<>();
        String sql = "SELECT shohin_id, shohin_mei, detail, hanbai_tanka, zaiko, shohin_imgpath FROM Shohin Order By shohin_id";

	    try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
		         PreparedStatement pStmt = conn.prepareStatement(sql)) {
            // SQL実行
            ResultSet rs = pStmt.executeQuery();
            
            // 結果セットからデータを取り出して、Shohinオブジェクトに格納
            while (rs.next()) {
                int shohinId = rs.getInt("shohin_id");
                String shohinMei = rs.getString("shohin_mei");
                String detail = rs.getString("detail");
                int hanbaiTanka = rs.getInt("hanbai_tanka");
                int zaiko = rs.getInt("zaiko");
                String shohinImgPath = rs.getString("shohin_imgpath");

                // Shohinオブジェクトを生成し、リストに追加
                Shohin shohin = new Shohin(shohinId, shohinMei, detail, hanbaiTanka, zaiko, shohinImgPath);
                shohinList.add(shohin);
            }
        } catch (SQLException e) {
            e.printStackTrace();  // 例外処理を追加する場合
        }
        return shohinList;
    }
	
}