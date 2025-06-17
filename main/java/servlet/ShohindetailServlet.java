package servlet;

import java.io.IOException;

import dao.ShohinDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Shohin;

@WebServlet("/ShohindetailServlet")
public class ShohindetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		HttpSession session = request.getSession();

		int shohinId;
		try {
			shohinId = Integer.parseInt(request.getParameter("shohinId"));
			System.out.println("shohinid1:" +shohinId);
		} catch (NumberFormatException e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid shohin ID");
			return;
		}
		
		ShohinDAO dao = new ShohinDAO();
		Shohin shohin = dao.findById(shohinId);
		
		if (shohin== null) {
			request.setAttribute("errormessage", "商品が存在しません。");
			RequestDispatcher dispatcher = 
					request.getRequestDispatcher("RirekiInfoServlet");
			dispatcher.forward(request, response);
		}

		// セッションキーに基づく戻り先分岐
		String detailSession = (String) session.getAttribute("detailSession");
		String returnUrl;

		if (detailSession != null && detailSession.equals("newDetailSession")) {
			// 履歴詳細に戻る
			String userId = (String) session.getAttribute("userId");
			Integer orderGroupId = (Integer) session.getAttribute("orderGroupId");
			returnUrl = "RirekiInfoServlet?userId=" + userId + "&orderGroupId=" + orderGroupId;
		} else {
			// 商品一覧に戻る
			returnUrl = "ShohinServlet";
		}

		// デバッグログ
		System.out.println("ShohindetailServlet: Generated returnUrl = " + returnUrl);

		// JSPに渡す
		request.setAttribute("returnUrl", returnUrl);

		session.setAttribute("shohinId", shohinId);
		session.setAttribute("shohinMei", shohin.getShohinMei());
		session.setAttribute("detail", shohin.getDetail());
		session.setAttribute("hanbaiTanka", shohin.getHanbaiTanka());
		session.setAttribute("zaiko", shohin.getZaiko());
		session.setAttribute("shohinImgPath", shohin.getShohinImgPath());

		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/shohindetail.jsp");
		dispatcher.forward(request, response);
	}
}
