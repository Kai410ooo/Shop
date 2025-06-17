package servlet;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Login;
import model.LoginLogic;

/**
 * Servlet implementation class Login
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	/*
	public Login() {
	    super();
	    // TODO Auto-generated constructor stub
	}
	*/
	

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		// ログインセッションを無効化
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate(); // セッションの無効化
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/login.jsp");
		dispatcher.forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("LoginServlet：dopost");
		request.setCharacterEncoding("UTF-8");
		String id = request.getParameter("id");
		String pass = request.getParameter("pass");

		System.out.println("id:" + id);
		System.out.println("pass:" + pass);
		// id または pass が null または空の場合、エラーメッセージをセットして login.jsp に転送
		if (id == null || id.isEmpty() || pass == null || pass.isEmpty()) {
			// エラーメッセージをリクエストにセット
			request.setAttribute("errormessage", "ID または パスワードが未入力です。");

			// login.jsp にリクエストを転送
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/login.jsp");
			dispatcher.forward(request, response);
			return;
		}

		//Loginインスタンスの生成
		Login login = new Login(id, pass);

		//ログイン処理
		LoginLogic loginLogic = new LoginLogic();
		boolean isLogin = loginLogic.execute(login);
		System.out.println("LoginServlet:isLogin=" + isLogin);

		//ログインNG
		if (!isLogin) {
			// ログイン失敗時にエラーメッセージを設定
			request.setAttribute("errormessage", "ＩＤ、またはパスワードが間違っています");

			// エラーメッセージを表示するためにlogin.jspに転送
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/login.jsp");
			dispatcher.forward(request, response);
			return;
		}
		HttpSession session = request.getSession();
		session.setAttribute("id", id); // idをセッションに保存
		System.out.println("LoginServlet:sessionScope内ユーザID=" + id);

		// id または pass が "admin" である場合、AdminMenuServletへ
		if (id.equals("admin")) {
		    request.setAttribute("message", "管理者権限のあるID、パスワードでログインしました。");

		    // login.jsp にリクエストを転送
		    RequestDispatcher dispatcher = request.getRequestDispatcher("AdminMenuServlet");
		    dispatcher.forward(request, response);
		    return;
		}else {
		request.setAttribute("message", "ログインしました。");
		RequestDispatcher dispatcher = request.getRequestDispatcher("MenuServlet");
		dispatcher.forward(request, response);
		}

	}

}