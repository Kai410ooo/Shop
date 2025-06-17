package servlet;

import java.io.IOException;
import java.util.List;

import dao.ShohinDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Shohin;

@WebServlet("/ShohinServlet")
public class ShohinServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		ShohinDAO dao = new ShohinDAO();
		List<Shohin> shohinList = dao.findAll();

		request.setAttribute("shohinList", shohinList);

		// 購入履歴画面への遷移処理
		HttpSession session = request.getSession();

		// セッションキー削除
		if (session.getAttribute("detailSession") != null) {
			System.out.println("RirekiInfoServlet (doPost): Removing detailSession");
			session.removeAttribute("detailSession");
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/shohin.jsp");
		dispatcher.forward(request, response);

	}
}