package model;

public class Shiharai {
    private int payId;  // 支払いID (主キー)
    private String payName;  // 支払い方法名

    // コンストラクタ（引数なし）
    public Shiharai() {}

    // コンストラクタ（フィールドを指定）
    public Shiharai(int payId, String payName) {
        this.payId = payId;
        this.payName = payName;
    }

    // GetterとSetter

    public int getPayId() {
        return payId;
    }

    public void setPayId(int payId) {
        this.payId = payId;
    }

    public String getPayName() {
        return payName;
    }

    public void setPayName(String payName) {
        this.payName = payName;
    }

    // toStringメソッド（オブジェクトの文字列表示用）
    @Override
    public String toString() {
        return "Shiharai [payId=" + payId + ", payName=" + payName + "]";
    }
}
