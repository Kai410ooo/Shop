package model;

import java.sql.Timestamp;

public class Rireki {
	private int order_id;
	private int shohin_id;
	private String shohin_mei;
	private String detail;
	private int buy_price;
	private int suryo;
	private int total;
	private Timestamp buy_day;
	private String user_name;
	private int postnumber;
	private String address;
	private String tell;
	private String mail;
	private String pay_id;
	private String user_id;
	private int order_group_id;

	public Rireki(int order_id, int shohin_id, String shohin_mei, String detail, int buy_price, int suryo, int total,
			Timestamp buy_day, String user_name, int postnumber, String address, String tell, String mail,
			String pay_id, String user_id, int order_group_id) {
		this.order_id = order_id;
		this.shohin_id = shohin_id;
		this.shohin_mei = shohin_mei;
		this.detail = detail;
		this.buy_price = buy_price;
		this.suryo = suryo;
		this.total = total;
		this.buy_day = buy_day;
		this.user_name = user_name;
		this.postnumber = postnumber;
		this.address = address;
		this.tell = tell;
		this.mail = mail;
		this.pay_id = pay_id;
		this.user_id = user_id;
		this.order_group_id = order_group_id;
	}

	public int getOrderId() {
		return order_id;
	}
	
	public int setOrderId(int order_id) {
		return this.order_id = order_id;
	}

	public int getId() {
		return shohin_id;
	}

	public String getName() {
		return shohin_mei;
	}

	public String getDetail() {
		return detail;
	}

	public int getBuyPrice() {
		return buy_price;
	}

	public int getSuryo() {
		return suryo;
	}

	public int getTotal() {
		return total;
	}

	public Timestamp getBuyDay() {
		return buy_day;
	}

	public String getUserName() {
		return user_name;
	}

	public int getPostNumber() {
		return postnumber;
	}

	public String getAddress() {
		return address;
	}

	public String getTell() {
		return tell;
	}

	public String getMail() {
		return mail;
	}

	public String getPayId() {
		return pay_id;
	}

	public String getUserId() {
		return user_id;
	}

	public int getOrderGroupId() {
		return order_group_id;
	}

}
