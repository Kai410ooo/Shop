package servlet;

import java.io.IOException;

import dao.CartDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/UpdateServlet")
public class UpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		HttpSession session = request.getSession(false);
		String userId = (String) session.getAttribute("id");

		int cartId = Integer.parseInt(request.getParameter("cartId"));
		int suryo = Integer.parseInt(request.getParameter("suryo"));

		CartDAO dao = new CartDAO();
		dao.update(cartId, suryo, userId);

		response.sendRedirect("CartServlet");
	}

}
