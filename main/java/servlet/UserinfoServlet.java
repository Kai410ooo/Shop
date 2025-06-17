package servlet;

import java.io.IOException;
import java.util.List;

import dao.AccountDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Account;
import model.Shiharai;

/**
 * Servlet implementation class Login
 */
@WebServlet("/UserinfoServlet")
public class UserinfoServlet extends HttpServlet {
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
		System.out.println("UserinfoServlet：doget");

		// HttpSession を取得
		HttpSession session = request.getSession();

		// "id" の値を取得
		String id = (String) session.getAttribute("id");
		System.out.println("user_id：" + id);

		// リクエストパラメータを取得
		String username = request.getParameter("username");
		String purchaseDate = request.getParameter("purchaseDate");

		// AccountDAOインスタンスを作成
		AccountDAO dao = new AccountDAO();

		// username と purchaseDate をもとにユーザー情報を取得
		Account account = dao.findAccountFromRireki(username, purchaseDate);

		// 取得した Account 情報をリクエストスコープに格納
		request.setAttribute("account", account);

		// セッションスコープにユーザー情報を保存
		session.setAttribute("account", account);

		// userinfo.jspにリクエストを転送
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/userinfo.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("UserinfoServlet：doPost");

		// HttpSession を取得
		HttpSession session = request.getSession();

		// "id" の値を取得
		String id = (String) session.getAttribute("id");
		System.out.println("user_id：" + id);

		// AccountDAOインスタンスを作成
		AccountDAO dao = new AccountDAO();

		// userId をもとにユーザー情報を取得
		Account account = dao.findAccountByUserId(id);

		// 取得した Account 情報をセッションスコープに格納
		if (account != null) {
			session.setAttribute("account", account);
		} else {
			System.out.println("アカウント情報が見つかりませんでした。");
		}

		// Shiharaiテーブルからすべての pay_id, pay_name を取得
		List<Shiharai> shiharaiList = dao.findAllShiharai();

		// セッションスコープに shiharaiList を格納
		session.setAttribute("shiharaiList", shiharaiList);

		// リクエストエンコーディングを設定
		request.setCharacterEncoding("UTF-8");

		// userinfo.jsp にフォワード
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/userinfo.jsp");
		dispatcher.forward(request, response);
	}

}
