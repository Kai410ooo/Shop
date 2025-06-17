package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Shohin;

public class ShohinDAO {

	// データベース接続に使用する情報
	private final String JDBC_URL = "jdbc:postgresql://localhost:5432/bshop";
	private final String DB_USER = "postgres";
	private final String DB_PASS = "sql";

	public ShohinDAO() {
		// JDBCドライバを読み込む
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした", e);
		}
	}

	public List<Shohin> findAll() {
		List<Shohin> shohinList = new ArrayList<>();

		// データベース接続
		try (Connection conn = DriverManager.getConnection(
				JDBC_URL, DB_USER, DB_PASS)) {

			String sql = "SELECT SHOHIN_ID, SHOHIN_MEI, DETAIL, HANBAI_TANKA, ZAIKO, SHOHIN_IMGPATH "
					+ "FROM SHOHIN ORDER BY SHOHIN_ID";
			PreparedStatement pStmt = conn.prepareStatement(sql);

			ResultSet rs = pStmt.executeQuery();

			while (rs.next()) {
				int shohinId = rs.getInt("SHOHIN_ID");
				String shohinMei = rs.getString("SHOHIN_MEI");
				int hanbaiTanka = rs.getInt("HANBAI_TANKA");
				int zaiko = rs.getInt("ZAIKO");
				String detail = rs.getString("DETAIL");
				String shohinImgPath = rs.getString("SHOHIN_IMGPATH");
				Shohin shohin = new Shohin(shohinId, shohinMei, detail,
						hanbaiTanka, zaiko, shohinImgPath);
				shohinList.add(shohin);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return shohinList;
	}

	public Shohin findById(int shohinId) {
		Shohin shohin = null;
		// データベース接続
		try (Connection conn = DriverManager.getConnection(
				JDBC_URL, DB_USER, DB_PASS)) {

			String sql = "SELECT SHOHIN_ID, SHOHIN_MEI, DETAIL, HANBAI_TANKA, ZAIKO, SHOHIN_IMGPATH "
					+ "FROM SHOHIN WHERE SHOHIN_ID = ?";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setInt(1, shohinId);
			ResultSet rs = pStmt.executeQuery();
			while (rs.next()) {
				String shohinMei = rs.getString("SHOHIN_MEI");
				int hanbaiTanka = rs.getInt("HANBAI_TANKA");
				int zaiko = rs.getInt("ZAIKO");
				String detail = rs.getString("DETAIL");
				String shohinImgPath = rs.getString("SHOHIN_IMGPATH");
				shohin = new Shohin(shohinId, shohinMei, detail, hanbaiTanka, zaiko, shohinImgPath);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return shohin;
	}
}