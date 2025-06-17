package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Cart;

public class CartDAO {

	// データベース接続に使用する情報
	private final String JDBC_URL = "jdbc:postgresql://localhost:5432/bshop";
	private final String DB_USER = "postgres";
	private final String DB_PASS = "sql";

	public CartDAO() {
		// JDBCドライバを読み込む
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした", e);
		}
	}

	public boolean create(int shohinId, String shohinMei, int hanbaiTanka, int suryo, int zaiko, String userId) {
		// データベース接続
		try (Connection conn = DriverManager.getConnection(
				JDBC_URL, DB_USER, DB_PASS)) {

			String sql = "INSERT INTO CART(SHOHIN_ID, SHOHIN_MEI, BUY_PRICE, SURYO, TOTAL, ZAIKO, USER_ID) VALUES (?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement pStmt = conn.prepareStatement(sql);

			pStmt.setInt(1, shohinId);
			pStmt.setString(2, shohinMei);
			pStmt.setInt(3, hanbaiTanka);
			pStmt.setInt(4, suryo);
			pStmt.setInt(5, hanbaiTanka * suryo);
			pStmt.setInt(6, zaiko);
			pStmt.setString(7, userId);

			int result = pStmt.executeUpdate();
			if (result != 1) {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean findById(int shohinId, String userId) {
		// データベース接続
		try (Connection conn = DriverManager.getConnection(
				JDBC_URL, DB_USER, DB_PASS)) {

			String sql = "SELECT COUNT(*) FROM CART WHERE SHOHIN_ID = ? AND USER_ID = ?";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setInt(1, shohinId);
			pStmt.setString(2, userId);
			ResultSet rs = pStmt.executeQuery();
			if (rs.next()) {
				return rs.getInt(1) > 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean add(int shohinId, int suryo, String userId) {
		boolean rowUpdated = false;
		// データベース接続
		try (Connection conn = DriverManager.getConnection(
				JDBC_URL, DB_USER, DB_PASS)) {
			String selectSql = "SELECT CART.SURYO, SHOHIN.HANBAI_TANKA FROM CART "
					+ "JOIN SHOHIN ON CART.SHOHIN_ID = SHOHIN.SHOHIN_ID "
					+ "WHERE CART.SHOHIN_ID = ? AND CART.USER_ID = ?";
			PreparedStatement pStmt1 = conn.prepareStatement(selectSql);
			pStmt1.setInt(1, shohinId);
			pStmt1.setString(2, userId);
			ResultSet rs = pStmt1.executeQuery();
			if (rs.next()) {
				int baseSuryo = rs.getInt("SURYO");
				int updateSuryo = baseSuryo + suryo;
				int hanbaiTanka = rs.getInt("HANBAI_TANKA");
				int total = updateSuryo * hanbaiTanka;

				String updateSql = "UPDATE CART SET SURYO = ?, TOTAL = ? WHERE SHOHIN_ID = ? AND USER_ID = ?";
				PreparedStatement pStmt2 = conn.prepareStatement(updateSql);
				pStmt2.setInt(1, updateSuryo);
				pStmt2.setInt(2, total);
				pStmt2.setInt(3, shohinId);
				pStmt2.setString(4, userId);

				rowUpdated = pStmt2.executeUpdate() > 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rowUpdated;
	}

	public List<Cart> findAll(String id) {
		List<Cart> cartList = new ArrayList<>();

		// データベース接続
		try (Connection conn = DriverManager.getConnection(
				JDBC_URL, DB_USER, DB_PASS)) {

			String sql = "SELECT CART.CART_ID, CART.SHOHIN_ID, CART.SHOHIN_MEI, "
					+ "SHOHIN.HANBAI_TANKA, CART.SURYO, CART.TOTAL, SHOHIN.ZAIKO, "
					+ "CART.USER_ID, SHOHIN.SHOHIN_IMGPATH "
					+ "FROM CART "
					+ "JOIN SHOHIN ON CART.SHOHIN_ID = SHOHIN.SHOHIN_ID "
					+ "WHERE CART.USER_ID = ? ORDER BY CART.CART_ID";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, id);

			ResultSet rs = pStmt.executeQuery();

			while (rs.next()) {
				int cartId = rs.getInt("CART_ID");
				int shohinId = rs.getInt("SHOHIN_ID");
				String shohinMei = rs.getString("SHOHIN_MEI");
				int hanbaiTanka = rs.getInt("HANBAI_TANKA");
				int suryo = rs.getInt("SURYO");
				int total = suryo * hanbaiTanka;
				int zaiko = rs.getInt("ZAIKO");
				String userId = rs.getString("USER_ID");
	            String imgPath = rs.getString("SHOHIN_IMGPATH");
				Cart cart = new Cart(cartId, shohinId, shohinMei, hanbaiTanka,
						suryo, total, zaiko, userId, imgPath);
				cartList.add(cart);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return cartList;
	}

	public boolean update(int cartId, int suryo, String userId) {
		boolean result = false;
		// データベース接続
		try (Connection conn = DriverManager.getConnection(
				JDBC_URL, DB_USER, DB_PASS)) {
			String selectSql = "SELECT CART.SURYO, SHOHIN.HANBAI_TANKA FROM CART "
					+ "JOIN SHOHIN ON CART.SHOHIN_ID = SHOHIN.SHOHIN_ID "
					+ "WHERE CART.CART_ID = ? AND CART.USER_ID = ?";
			PreparedStatement pStmt1 = conn.prepareStatement(selectSql);
			pStmt1.setInt(1, cartId);
			pStmt1.setString(2, userId);
			ResultSet rs = pStmt1.executeQuery();
			if (rs.next()) {
				int hanbaiTanka = rs.getInt("HANBAI_TANKA");
				int total = suryo * hanbaiTanka;

				String updateSql = "UPDATE CART SET SURYO = ?, TOTAL = ? WHERE CART_ID = ? AND USER_ID = ?";
				PreparedStatement pStmt2 = conn.prepareStatement(updateSql);
				pStmt2.setInt(1, suryo);
				pStmt2.setInt(2, total);
				pStmt2.setInt(3, cartId);
				pStmt2.setString(4, userId);

				result = pStmt2.executeUpdate() > 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return result;
	}

	public boolean delete(int cartId, String userId) {
		boolean rowDeleted = false;
		// データベース接続
		try (Connection conn = DriverManager.getConnection(
				JDBC_URL, DB_USER, DB_PASS)) {
			String sql = "DELETE FROM CART WHERE CART_ID = ? AND USER_ID = ?";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setInt(1, cartId);
			pStmt.setString(2, userId);
			rowDeleted = pStmt.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rowDeleted;
	}

	public void clearCart(String userId) {
		try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
			String sql = "DELETE FROM CART WHERE USER_ID = ?";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, userId);
			pStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public boolean check(String userId, int shohinId) {
		boolean result = false;
		// データベース接続
		try (Connection conn = DriverManager.getConnection(
				JDBC_URL, DB_USER, DB_PASS)) {
			String sql = "SELECT CART.SURYO, SHOHIN.ZAIKO FROM CART "
					+ "JOIN SHOHIN ON CART.SHOHIN_ID = SHOHIN.SHOHIN_ID WHERE CART.USER_ID = ? AND CART.SHOHIN_ID = ?";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, userId);
			pStmt.setInt(2, shohinId);
			try (ResultSet rs = pStmt.executeQuery()) {
	            if (rs.next()) {
	                int suryo = rs.getInt("SURYO");
	                int zaiko = rs.getInt("ZAIKO");
	                
	                System.out.println("SURYO: " + suryo + ", ZAIKO: " + zaiko);
	                result = suryo <= zaiko;
	            }
	        }
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

}