package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import model.Rireki;

public class RirekiDAO {
	private final String JDBC_URL = "jdbc:postgresql://localhost:5432/bshop";
	private final String DB_USER = "postgres";
	private final String DB_PASS = "sql";

	private static final String SELECT_RIREKI = "SELECT * FROM rireki WHERE user_id = ?";

	public RirekiDAO() {
	}

	protected Connection getConnection() {
		Connection connection = null;

		// JDBCドライバを読み込む
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}

		try {
			// データベース接続を確立
			connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}

	public List<Rireki> printRirekiByUserId(String uId) {
		List<Rireki> rirekis = new ArrayList<>();
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_RIREKI);) {
			preparedStatement.setString(1, uId);
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				int orderId = rs.getInt("order_id");
				int shohinId = rs.getInt("shohin_id");
				String shohinMei = rs.getString("shohin_mei");
				String detail = rs.getString("detail");
				int buyPrice = rs.getInt("buy_price");
				int suryo = rs.getInt("suryo");
				int total = rs.getInt("total");
				Timestamp buyDay = rs.getTimestamp("buy_day");
				String userName = rs.getString("user_name");
				int postNumber = rs.getInt("postnumber");
				String address = rs.getString("address");
				String tell = rs.getString("tell");
				String mail = rs.getString("mail");
				String payId = rs.getString("pay_id");
				String userId = rs.getString("user_id");
				int orderGroupId = rs.getInt("order_group_id");

				Rireki rireki = new Rireki(orderId, shohinId, shohinMei, detail, buyPrice, suryo, total, buyDay,
						userName,
						postNumber, address, tell, mail, payId, userId, orderGroupId);
				rirekis.add(rireki);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rirekis;
	}

	public void addRireki(Rireki rireki) {
		String sql = "INSERT INTO Rireki (shohin_id, shohin_mei, detail, buy_price, suryo, total, buy_day, user_name, postnumber, address, tell, mail, pay_id, user_id, order_group_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try (Connection conn = getConnection();
				PreparedStatement preparedStatement = conn.prepareStatement(sql,
						PreparedStatement.RETURN_GENERATED_KEYS)) {

			preparedStatement.setInt(1, rireki.getId());
			preparedStatement.setString(2, rireki.getName());
			preparedStatement.setString(3, rireki.getDetail());
			preparedStatement.setInt(4, rireki.getBuyPrice());
			preparedStatement.setInt(5, rireki.getSuryo());
			preparedStatement.setInt(6, rireki.getTotal());
			preparedStatement.setTimestamp(7, rireki.getBuyDay());
			preparedStatement.setString(8, rireki.getUserName());
			preparedStatement.setInt(9, rireki.getPostNumber());
			preparedStatement.setString(10, rireki.getAddress());
			preparedStatement.setString(11, rireki.getTell());
			preparedStatement.setString(12, rireki.getMail());
			preparedStatement.setString(13, rireki.getPayId());
			preparedStatement.setString(14, rireki.getUserId());
			preparedStatement.setInt(15, rireki.getOrderGroupId()); // 新しいフィールドを設定

			System.out.println("SQL文: " + preparedStatement); // デバッグ出力

			int affectedRows = preparedStatement.executeUpdate();

			if (affectedRows > 0) {
				try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
					if (generatedKeys.next()) {
						rireki.setOrderId(generatedKeys.getInt(1)); // 自動生成されたorder_idを設定
						System.out.println("生成されたorder_id: " + generatedKeys.getInt(1)); // デバッグ出力
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static final String UPDATE_ZAIKO = "UPDATE shohin SET zaiko = zaiko - ? WHERE shohin_id = ?";

	public void updateZaiko(int shohinId, int quantity) {
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ZAIKO)) {
			preparedStatement.setInt(1, quantity);
			preparedStatement.setInt(2, shohinId);

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static final String CHECK_ZAIKO = "SELECT zaiko FROM shohin WHERE shohin_id = ?";

	public int checkZaiko(int shohinId) {
		int zaiko = 0;
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(CHECK_ZAIKO)) {
			preparedStatement.setInt(1, shohinId);
			ResultSet rs = preparedStatement.executeQuery();

			if (rs.next()) {
				zaiko = rs.getInt("zaiko");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return zaiko;
	}

	public Rireki getRirekiByOrderId(String userId, int orderGroupId) {
		Rireki rireki = null;
		String sql = "SELECT order_id, shohin_id, shohin_mei, detail, buy_price, suryo, total, buy_day, user_name, postnumber, address, tell, mail, pay_id, user_id, order_group_id FROM rireki WHERE user_id = ? AND order_group_id = ?";

		try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, userId);
			stmt.setInt(2, orderGroupId);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					rireki = new Rireki(
							rs.getInt("order_id"),
							rs.getInt("shohin_id"),
							rs.getString("shohin_mei"),
							rs.getString("detail"),
							rs.getInt("buy_price"),
							rs.getInt("suryo"),
							rs.getInt("total"),
							rs.getTimestamp("buy_day"),
							rs.getString("user_name"),
							rs.getInt("postnumber"),
							rs.getString("address"),
							rs.getString("tell"),
							rs.getString("mail"),
							rs.getString("pay_id"),
							rs.getString("user_id"),
							rs.getInt("order_group_id"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rireki;
	}

	public int addRirekiAndGetOrderId(Rireki rireki) {
		int orderId = -1;
		String sql = "INSERT INTO rireki (shohin_id, shohin_mei, detail, buy_price, suryo, total, buy_day, user_name, postnumber, address, tell, mail, pay_id, user_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING order_id";

		try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, rireki.getId());
			stmt.setString(2, rireki.getName());
			stmt.setString(3, rireki.getDetail());
			stmt.setInt(4, rireki.getBuyPrice());
			stmt.setInt(5, rireki.getSuryo());
			stmt.setInt(6, rireki.getTotal());
			stmt.setTimestamp(7, rireki.getBuyDay());
			stmt.setString(8, rireki.getUserName());
			stmt.setInt(9, rireki.getPostNumber());
			stmt.setString(10, rireki.getAddress());
			stmt.setString(11, rireki.getTell());
			stmt.setString(12, rireki.getMail());
			stmt.setString(13, rireki.getPayId());
			stmt.setString(14, rireki.getUserId());
			stmt.setInt(15, rireki.getOrderGroupId());

			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					orderId = rs.getInt("order_id");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return orderId;
	}

	public int generateNewOrderGroupId() {
		String sql = "SELECT MAX(order_group_id) FROM Rireki";
		int newOrderGroupId = 0;

		try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
				PreparedStatement pstmt = conn.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()) {

			if (rs.next()) {
				newOrderGroupId = rs.getInt(1) + 1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return newOrderGroupId;
	}

	public List<Rireki> getRirekiByOrderGroupId(String userId, int orderGroupId) {
		List<Rireki> rirekis = new ArrayList<>();
		String sql = "SELECT order_id, shohin_id, shohin_mei, detail, buy_price, suryo, total, buy_day, user_name, postnumber, address, tell, mail, pay_id, user_id, order_group_id FROM rireki WHERE user_id = ? AND order_group_id = ?";

		try (Connection conn = getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, userId);
			stmt.setInt(2, orderGroupId);
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					Rireki rireki = new Rireki(
							rs.getInt("order_id"),
							rs.getInt("shohin_id"),
							rs.getString("shohin_mei"),
							rs.getString("detail"),
							rs.getInt("buy_price"),
							rs.getInt("suryo"),
							rs.getInt("total"),
							rs.getTimestamp("buy_day"),
							rs.getString("user_name"),
							rs.getInt("postnumber"),
							rs.getString("address"),
							rs.getString("tell"),
							rs.getString("mail"),
							rs.getString("pay_id"),
							rs.getString("user_id"),
							rs.getInt("order_group_id"));
					rirekis.add(rireki);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rirekis;
	}

}