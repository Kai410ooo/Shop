package servlet;

import java.io.IOException;
import java.util.List;

import dao.CustomerDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Account;

@WebServlet("/AlterServlet")
public class AlterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
        Account account = (Account) session.getAttribute("account");
        
        CustomerDAO dao = new CustomerDAO();
        List<String[]> paymentMethods = dao.getPaymentMethods();
        
        request.setAttribute("account", account);
        request.setAttribute("paymentMethods", paymentMethods);
        RequestDispatcher dispatcher = 
                request.getRequestDispatcher("WEB-INF/jsp/alter.jsp");
        dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		String userId = request.getParameter("user_id");
        String userName = request.getParameter("name");
        int postnumber = Integer.parseInt(request.getParameter("postnumber"));
        String address = request.getParameter("address");
        String tell = request.getParameter("tell");
        String mail = request.getParameter("mail");
        int payId = Integer.parseInt(request.getParameter("payId"));

        CustomerDAO dao = new CustomerDAO();
        String payName = dao.getPaymentMethodNameById(payId);

        Account account = new Account(userId, userName, postnumber, address, tell, mail, payId, payName);
        
        HttpSession session = request.getSession();
        session.setAttribute("temporaryAccount", account);
        
        response.sendRedirect("CustomerServlet");
	}
}
