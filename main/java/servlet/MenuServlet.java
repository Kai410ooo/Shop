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
@WebServlet("/MenuServlet")
public class MenuServlet extends HttpServlet {
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
		System.out.println("MenuServlet：doget");
		// セッションに値を格納
		/*    	
		HttpSession session = request.getSession();
		session.setAttribute("name", "ユーザー名");
		*/
        HttpSession session = request.getSession(false);
        if (session != null) {
            // 注文が完了したフラグを削除
            session.removeAttribute("orderCompleted");
        }
		
		// menu.jspにリクエストを転送
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/menu.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("MenuServlet：dopost");
		request.setCharacterEncoding("UTF-8");
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/menu.jsp");
		dispatcher.forward(request, response);

	}

}
