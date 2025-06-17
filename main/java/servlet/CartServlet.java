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

@WebServlet("/CartServlet")
public class CartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		HttpSession session = request.getSession(false);
		String userId = (String) session.getAttribute("id");
		
		CartDAO dao = new CartDAO();
		List<Cart> cartList = dao.findAll(userId);

        session.setAttribute("cartList", cartList);
		request.setAttribute("cartList", cartList);

		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/cart.jsp");
		dispatcher.forward(request, response);
	}
}