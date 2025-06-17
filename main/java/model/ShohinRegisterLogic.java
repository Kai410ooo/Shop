package model;

import dao.AdminDAO;

public class ShohinRegisterLogic {
	//shohinidの重複チェックはデータベース側のみでOKｌ
	/*
    public boolean isShohinIdDuplicate(String userId) {
        AccountDAO dao = new AccountDAO();
        System.out.println("isUserIdDuplicateメソッド：引数userId："+userId);
        System.out.println("isUserIdDuplicateメソッド：返り値："+dao.isUserIdDuplicate(userId));
        
        return dao.isUserIdDuplicate(userId); // 重複している場合はtrueを返す
    }
    */

    public boolean execute(Shohin shohin) {
        AdminDAO dao = new AdminDAO();
        return dao.RegisterShohin(shohin); // shohinを登録
    }
}

