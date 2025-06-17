package servlet;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import dao.RirekiDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Rireki;

@WebServlet("/RirekiServlet")
public class RirekiServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("id");
		String userName = (String) session.getAttribute("userName");

		if (userId == null) {
			response.sendRedirect("LoginServlet");
			return;
		}

		RirekiDAO dao = new RirekiDAO();
		List<Rireki> rirekis = dao.printRirekiByUserId(userId);
		request.setAttribute("rirekis", rirekis);
		request.setAttribute("userName", userName);
		request.setAttribute("userId", userId);

		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/rireki.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("id");
		String userName = (String) session.getAttribute("userName");

		if (userId == null) {
			response.sendRedirect("LoginServlet");
			return;
		}

		// 購入履歴をデータベースに保存
		int orderId = Integer.parseInt(request.getParameter("order_id"));
		int shohinId = Integer.parseInt(request.getParameter("shohin_id"));
		String shohinMei = request.getParameter("shohin_mei");
		String detail = request.getParameter("detail");
		int buyPrice = Integer.parseInt(request.getParameter("buy_price"));
		int suryo = Integer.parseInt(request.getParameter("suryo"));
		int total = Integer.parseInt(request.getParameter("total"));
		Timestamp buyDay = new Timestamp(System.currentTimeMillis());
		int postNumber = Integer.parseInt(request.getParameter("postnumber"));
		String address = request.getParameter("address");
		String tell = request.getParameter("tell");
		String mail = request.getParameter("mail");
		String payId = request.getParameter("pay_id");
		int orderGroupId = Integer.parseInt(request.getParameter("order_group_id"));

		Rireki rireki = new Rireki(orderId, shohinId, shohinMei, detail, buyPrice, suryo, total, buyDay, userName,
				postNumber, address, tell, mail, payId, userId, orderGroupId);
		RirekiDAO dao = new RirekiDAO();
		dao.addRireki(rireki);

		// 保存後、購入履歴ページにリダイレクト
		response.sendRedirect("RirekiServlet");
	}
}
