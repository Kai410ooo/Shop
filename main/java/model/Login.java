package model;

import java.io.Serializable;

public class Login implements Serializable {
	private String id;
	private String pass;

	// デフォルトコンストラクタ
	public Login() {

	}

	// 引数付きコンストラクタ
	public Login(String id, String pass) {
		this.id = id;
		this.pass = pass;
	}

	// ゲッター
	public String getPass() {
		return pass;
	}

	public String getId() {
		return id;
	}

	// セッター
	public void setId(String id) {
		this.id = id;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}
}