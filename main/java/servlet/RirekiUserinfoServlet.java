package servlet;

import java.io.IOException;

import dao.RirekiDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Rireki;

@WebServlet("/RirekiUserinfoServlet")
public class RirekiUserinfoServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String userId = request.getParameter("userId");
		String orderIdParam = request.getParameter("orderId");
		Integer orderId = null;

		try {
			orderId = Integer.parseInt(orderIdParam);
		} catch (NumberFormatException e) {
			request.setAttribute("errorMessage", "無効な注文IDです。");
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/rirekiuserinfo.jsp");
			dispatcher.forward(request, response);
			return;
		}

		RirekiDAO dao = new RirekiDAO();
		Rireki userInfo = dao.getRirekiByOrderId(userId, orderId);

		if (userInfo != null) {
			request.setAttribute("userInfo", userInfo);
		} else {
			request.setAttribute("errorMessage", "ユーザー情報が見つかりませんでした。");
		}

		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/rirekiuserinfo.jsp");
		dispatcher.forward(request, response);
	}
}
