package model;

import dao.LoginDAO;

public class LoginLogic {
	public boolean execute(Login login) {

		// IDによってパスワードを抽出
		String inputpass = login.getPass();
		LoginDAO dao = new LoginDAO();
		String stragepass = dao.extractPassById(login); // パスワードが返される

		System.out.println("LoginLogic：stragepass=" + stragepass);
		System.out.println("LoginLogic：inputpass=" + inputpass);

		if (inputpass.equals(stragepass)) {
			return true;
		}
		return false;
	}
}
