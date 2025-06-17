package servlet;

import java.io.IOException;
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

@WebServlet("/RirekiInfoServlet")
public class RirekiInfoServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 商品詳細画面への遷移処理
		String userId = request.getParameter("userId");
		String orderGroupIdParam = request.getParameter("orderGroupId");
		Integer orderGroupId = null;

		try {
			orderGroupId = Integer.parseInt(orderGroupIdParam);
		} catch (NumberFormatException e) {
			request.setAttribute("errorMessage", "無効な注文IDです。");
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/rirekiinfo.jsp");
			dispatcher.forward(request, response);
			return;
		}

		RirekiDAO dao = new RirekiDAO();
		List<Rireki> orderDetails = dao.getRirekiByOrderGroupId(userId, orderGroupId);

		if (orderDetails != null && !orderDetails.isEmpty()) {
			request.setAttribute("orderDetails", orderDetails);
		} else {
			request.setAttribute("errorMessage", "注文情報が見つかりませんでした。");
		}

		HttpSession session = request.getSession();
		session.setAttribute("detailSession", "newDetailSession"); // セッションキー作成
		session.setAttribute("userId", userId);
		session.setAttribute("orderGroupId", orderGroupId);

		System.out
				.println("RirekiInfoServlet (doGet): detailSession created = " + session.getAttribute("detailSession"));

		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/rirekiinfo.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 購入履歴画面への遷移処理
		HttpSession session = request.getSession();

		// セッションキー削除
		if (session.getAttribute("detailSession") != null) {
			System.out.println("RirekiInfoServlet (doPost): Removing detailSession");
			session.removeAttribute("detailSession");
		}

		// 購入履歴画面にリダイレクト
		response.sendRedirect(request.getContextPath() + "/RirekiServlet");
	}
}
