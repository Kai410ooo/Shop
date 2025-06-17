package servlet;

import java.io.IOException;

import dao.CartDAO;
import dao.CustomerDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Account;

@WebServlet("/CustomerServlet")
public class CustomerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		String userId = (String) session.getAttribute("id");
		
		Account temporaryAccount = (Account) session.getAttribute("temporaryAccount");
		
		Account account;
        if (temporaryAccount != null) {
            account = temporaryAccount;
        } else {
            CustomerDAO dao = new CustomerDAO();
            account = dao.findById(userId);
        }
        session.setAttribute("account", account);
		request.setAttribute("account", account);
//		System.out.println("CustomerServlet:sessionScope内ユーザID=" + account.getUserId());

		RequestDispatcher dispatcher = 
				request.getRequestDispatcher("WEB-INF/jsp/customer.jsp");
		dispatcher.forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	
		HttpSession session = request.getSession(false);
		String userId = (String) session.getAttribute("id");
	
		int shohinId = Integer.parseInt(request.getParameter("shohinId"));
	
		// ここで確認するため、shohinId と userId をログに出力
		System.out.println("UserId: " + userId + ", ShohinId: " + shohinId);
    
		CartDAO cartDao = new CartDAO();
	
		if (cartDao.check(userId, shohinId)) {
	
			Account temporaryAccount = (Account) session.getAttribute("temporaryAccount");
	
			Account account;
			if (temporaryAccount != null) {
				account = temporaryAccount;
			} else {
				CustomerDAO dao = new CustomerDAO();
				account = dao.findById(userId);
			}
			session.setAttribute("account", account);
			request.setAttribute("account", account);
			//	System.out.println("CustomerServlet:sessionScope内ユーザID=" + account.getUserId());
		
			RequestDispatcher dispatcher = 
					request.getRequestDispatcher("WEB-INF/jsp/customer.jsp");
			dispatcher.forward(request, response);
		} else {
			request.setAttribute("errormessage", "在庫数量を超えています。");
			RequestDispatcher dispatcher = 
					request.getRequestDispatcher("WEB-INF/jsp/cart.jsp");
			dispatcher.forward(request, response);
		}
	}
}

