package model;

import dao.AccountDAO;

public class AccountRegisterLogic {
    public boolean isUserIdDuplicate(String userId) {
        AccountDAO dao = new AccountDAO();
        System.out.println("isUserIdDuplicateメソッド：引数userId："+userId);
        System.out.println("isUserIdDuplicateメソッド：返り値："+dao.isUserIdDuplicate(userId));
        
        return dao.isUserIdDuplicate(userId); // 重複している場合はtrueを返す
    }

    public boolean execute(Account account) {
        AccountDAO dao = new AccountDAO();
        return dao.RegisterUserAccount(account); // アカウントを登録
    }
}

