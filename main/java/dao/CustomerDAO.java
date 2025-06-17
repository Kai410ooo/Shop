package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Account;

public class CustomerDAO {

	// データベース接続に使用する情報
	private final String JDBC_URL = "jdbc:postgresql://localhost:5432/bshop";
	private final String DB_USER = "postgres";
	private final String DB_PASS = "sql";

	public CustomerDAO() {
		// JDBCドライバを読み込む
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした", e);
		}
	}
	
	public Account findById(String userId) {
		Account account = null;
		// データベース接続
		try (Connection conn = DriverManager.getConnection(
				JDBC_URL, DB_USER, DB_PASS)) {

			String sql = "SELECT U.USER_ID, U.USER_NAME, U.POSTNUMBER, U.ADDRESS, U.TELL, "
					+ "U.MAIL, U.PAY_ID, S.PAY_NAME FROM USERS U "
					+ "LEFT JOIN SHIHARAI S ON U.PAY_ID = S.PAY_ID "
					+ "WHERE USER_ID = ?";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, userId);
			ResultSet rs = pStmt.executeQuery();
			while (rs.next()) {
				String user_id = rs.getString("USER_ID");
				String userName = rs.getString("USER_NAME");
				int postnumber = rs.getInt("POSTNUMBER");
				String address = rs.getString("ADDRESS");
				String tell = rs.getString("TELL");
				String mail = rs.getString("MAIL");
				int payId = rs.getInt("PAY_ID");
				String payName = rs.getString("PAY_NAME");
				account= new Account(user_id, userName, postnumber, address, tell, mail, payId, payName);

				System.out.println("DEBUG: " + account);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return account;
	}
	
	public List<String[]> getPaymentMethods() {
        List<String[]> paymentMethods = new ArrayList<>();
        // データベース接続
        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
            String sql = "SELECT PAY_ID, PAY_NAME FROM SHIHARAI";
            PreparedStatement pStmt = conn.prepareStatement(sql);
            ResultSet rs = pStmt.executeQuery();
            while (rs.next()) {
                int payId = rs.getInt("PAY_ID");
                String payName = rs.getString("PAY_NAME");
                paymentMethods.add(new String[]{String.valueOf(payId), payName});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return paymentMethods;
    }
	
	public String getPaymentMethodNameById(int payId) {
        String payName = null;
        // データベース接続
        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
            String sql = "SELECT PAY_NAME FROM SHIHARAI WHERE PAY_ID = ?";
            PreparedStatement pStmt = conn.prepareStatement(sql);
            pStmt.setInt(1, payId);
            ResultSet rs = pStmt.executeQuery();
            if (rs.next()) {
                payName = rs.getString("PAY_NAME");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return payName;
    }
}
