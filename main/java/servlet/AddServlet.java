package servlet;

import java.io.IOException;

import dao.CartDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/AddServlet")
public class AddServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		HttpSession session = request.getSession(false);
		String userId = (String) session.getAttribute("id");
		
		String shohinId = request.getParameter("shohinId");
		String shohinMei = request.getParameter("shohinMei");
		String hanbaiTanka = request.getParameter("hanbaiTanka");
		String suryo = request.getParameter("suryo");
		String zaiko = request.getParameter("zaiko");

		CartDAO dao = new CartDAO();
		if (dao.findById(Integer.parseInt(shohinId), userId)) {
			dao.add(Integer.parseInt(shohinId), Integer.parseInt(suryo), userId);
		} else {
			dao.create(Integer.parseInt(shohinId), shohinMei, Integer.parseInt(hanbaiTanka), Integer.parseInt(suryo),
					Integer.parseInt(zaiko), userId);
		}

		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/addOK.jsp");
		dispatcher.forward(request, response);
	}

}