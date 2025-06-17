package servlet;

import java.io.IOException;
import java.util.List;

import dao.AdminDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Shohin;

/**
 * Servlet implementation class Login
 */
@WebServlet("/AdminIchiranServlet")
public class AdminIchiranServlet extends HttpServlet {
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
		System.out.println("AdminIchiranServlet：doget");
		// セッションに値を格納
		/*    	
		HttpSession session = request.getSession();
		session.setAttribute("name", "ユーザー名");
		*/
		// menu.jspにリクエストを転送
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/adminIchiran.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	    System.out.println("AdminIchiranServlet：doPost");
	    request.setCharacterEncoding("UTF-8");
	    
	    

	    // AdminDAOをインスタンス化
	    AdminDAO dao = new AdminDAO();

	    // Shohinデータベースの全てのデータを取得
	    List<Shohin> allShohinInfo = dao.findAllShohin();

	    // allShohinInfoを全てコンソールに表示するfor文
	    for (Shohin shohin : allShohinInfo) {
	        System.out.println("商品ID: " + shohin.getShohinId());
	        System.out.println("商品名: " + shohin.getShohinMei());
	        System.out.println("詳細: " + shohin.getDetail());
	        System.out.println("販売単価: " + shohin.getHanbaiTanka());
	        System.out.println("在庫: " + shohin.getZaiko());
	        System.out.println("商品画像パス: " + shohin.getShohinImgPath());
	        System.out.println("----------------------------");
	    }

	    // 取得したShohinデータをセッションスコープに格納
	    HttpSession session = request.getSession();
	    session.removeAttribute("shohin");
	    session.setAttribute("allShohinInfo", allShohinInfo);

	    // JSPへフォワードして結果を表示
	    RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/adminIchiran.jsp");
	    dispatcher.forward(request, response);
	}


}
