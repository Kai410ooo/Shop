package model;

public class Cart {

	private int cartId;
	private int shohinId;
	private String shohinMei;
	private int buyPrice;
	private int suryo;
	private int total;
	private int zaiko;
	private String userId;
	private String imgPath;
	
	public Cart() {}
	
	public Cart(int cartId, int shohinId, String shohinMei, int buyPrice, 
						int suryo, int total, int zaiko, String userId, String imgPath) {
		this.cartId = cartId;
		this.shohinId = shohinId;
		this.shohinMei = shohinMei;
		this.buyPrice = buyPrice;
		this.suryo = suryo;
		this.total = total;
		this.zaiko = zaiko;
		this.userId = userId;
		this.imgPath = imgPath;
	}
	
	public void setCartId(int cartId) {
		this.cartId = cartId;
	}
	
	public int getCartId() {
		return cartId;
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
	
	public void setBuyPrice(int buyPrice) {
		this.buyPrice = buyPrice;
	}
	
	public int getBuyPrice() {
		return buyPrice;
	}
	
	public void setSuryo(int suryo) {
		this.suryo = suryo;
	}
	
	public int getSuryo() {
		return suryo;
	}
	
	public void setTotal(int total) {
		this.total = total;
	}
	
	public int getTotal() {
		return total;
	}
	
	public void setZaiko(int zaiko) {
		this.zaiko = zaiko;
	}
	
	public int getZaiko() {
		return zaiko;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getUserId() {
		return userId;
	}
	
	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}
	
	public String getImgPath() {
		return imgPath;
	}
}