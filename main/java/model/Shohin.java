package model;

public class Shohin {

	private int shohinId;
	private String shohinMei;
	private int hanbaiTanka;
	private int zaiko;
	private String detail;
	private String shohinImgPath;

	public Shohin() {
	}

	public Shohin(int shohinId, String shohinMei, String detail,
			int hanbaiTanka, int zaiko) {
		this.shohinId = shohinId;
		this.shohinMei = shohinMei;
		this.detail = detail;
		this.hanbaiTanka = hanbaiTanka;
		this.zaiko = zaiko;
	}
	
	public Shohin(int shohinId, String shohinMei, String detail,
			int hanbaiTanka, int zaiko, String shohinImgPath) {
		this.shohinId = shohinId;
		this.shohinMei = shohinMei;
		this.detail = detail;
		this.hanbaiTanka = hanbaiTanka;
		this.zaiko = zaiko;
		this.shohinImgPath = shohinImgPath;
	}

	public void setShohinId(int shohinId) {
		this.shohinId = shohinId;
	}

	public int getShohinId() {
		return shohinId;
	}

	public void setShohinMei(String shohinMei) {
		this.shohinMei = shohinMei;
	}

	public String getShohinMei() {
		return shohinMei;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getDetail() {
		return detail;
	}

	public void setHanbaiTanka(int hanbaiTanka) {
		this.hanbaiTanka = hanbaiTanka;
	}

	public int getHanbaiTanka() {
		return hanbaiTanka;
	}

	public void setZaiko(int zaiko) {
		this.zaiko = zaiko;
	}

	public int getZaiko() {
		return zaiko;
	}

	public void setShohinImgPath(String shohinImgPath) {
		this.shohinImgPath = shohinImgPath;
	}

	public String getShohinImgPath() {
		return shohinImgPath;
	}
}
