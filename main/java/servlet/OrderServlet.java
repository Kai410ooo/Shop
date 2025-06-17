package servlet;

import java.io.IOException;
import java.util.List;

import dao.CartDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Cart;

@WebServlet("/OrderServlet")
public class OrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		HttpSession session = request.getSession(false);
		String userId = (String) session.getAttribute("id");
		
		// データベースからカートアイテムを取得
		CartDAO cartDAO = new CartDAO();
		List<Cart> cartItems = cartDAO.findAll(userId);

		// 取得したカートアイテムをリクエスト属性に設定
		request.setAttribute("cartItems", cartItems);

		// order.jsp にフォワード
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/order.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		RequestDispatcher dispatcher = request.getRequestDispatcher("OrderCompServlet");
		dispatcher.forward(request, response);
	}
}
