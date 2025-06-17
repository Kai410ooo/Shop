package model;

public class Account {
	// 会員ID
	private String user_id;

	// パスワード
	private String user_pass;

	// 氏名
	private String name;

	// 郵便番号
	private int postnumber;

	// 住所
	private String address;

	// 電話番号
	private String tell;

	// メールアドレス
	private String mail;

	// 支払方法ID
	private int pay_id;

	// 支払方法名
	private String pay_name;

	public Account() {
	};

	// コンストラクタ
	public Account(String user_id, String user_pass, String name, String mail,
			int postnumber, String address, String tell, int pay_id, String pay_name) {
		this.user_id = user_id;
		this.user_pass = user_pass;
		this.name = name;
		this.mail = mail;
		this.postnumber = postnumber;
		this.address = address;
		this.tell = tell;
		this.pay_id = pay_id;
		this.pay_name = pay_name;
	}

	// 2.27追加(西市）
	public Account(String user_id, String name, int postnumber, String address, String tell, String mail, int pay_id,
			String pay_name) {
		this.user_id = user_id;
		this.name = name;
		this.mail = mail;
		this.postnumber = postnumber;
		this.address = address;
		this.tell = tell;
		this.pay_id = pay_id;
		this.pay_name = pay_name;
	}

	// ゲッターメソッド
	public String getUserId() {
		return user_id;
	}

	public String getUserPass() {
		return user_pass;
	}

	public String getMail() {
		return mail;
	}

	public String getName() {
		return name;
	}

	public int getPostnumber() {
		return postnumber;
	}

	public String getAddress() {
		return address;
	}

	public String getTell() {
		return tell;
	}

	public int getPayId() {
		return pay_id;
	}

	public String getPayName() {
		return pay_name;
	}

	// セッターメソッド
	public void setUserId(String user_id) {
		this.user_id = user_id;
	}

	public void setUserPass(String user_pass) {
		this.user_pass = user_pass;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPostnumber(int postnumber) {
		this.postnumber = postnumber;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setTell(String tell) {
		this.tell = tell;
	}

	public void setPayId(int pay_id) {
		this.pay_id = pay_id;
	}

	// 新しく追加されたセッターメソッド
	public void setPayName(String pay_name) {
		this.pay_name = pay_name;
	}

	// toString メソッドのオーバーライド
	@Override
	public String toString() {
		return "Account{" +
				"user_id='" + user_id + '\'' +
				", user_pass='" + user_pass + '\'' +
				", name='" + name + '\'' +
				", postnumber=" + postnumber +
				", address='" + address + '\'' +
				", tell=" + tell +
				", mail='" + mail + '\'' +
				", pay_id=" + pay_id +
				", pay_name='" + pay_name + '\'' +
				'}';
	}
}