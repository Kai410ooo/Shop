package servlet;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class Login
 */
@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {
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
		System.out.println("LogoutServlet：doget");
		// ログインセッションを無効化
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate(); // セッションの無効化
		}
		request.setAttribute("message", "ログアウトしました。");
		// ログアウト後、login.jspにリダイレクト
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/login.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("LogoutServlet：dopost");
		request.setCharacterEncoding("UTF-8");
		// ログインセッションを無効化
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate(); // セッションの無効化
		}
		request.setAttribute("message", "ログアウトしました。");
		// ログアウト後、login.jspにリダイレクト
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/login.jsp");
		dispatcher.forward(request, response);

	}

}