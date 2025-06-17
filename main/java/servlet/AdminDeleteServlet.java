package servlet;

import java.io.IOException;

import dao.AdminDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class AccountAddServlet
 */
@WebServlet("/AdminDeleteServlet")
public class AdminDeleteServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("AdminDeleteServlet：doGet");
//        request.setAttribute("errormessage", null);

        // フォームに初期表示する値があれば設定（新規登録の場合）
        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/userinfo.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("AdminDeleteServlet：doPost");
        request.setCharacterEncoding("UTF-8");

        // 送信されたすべてのパラメータを確認
        System.out.println("Parameters: " + request.getParameterMap());

        // 商品情報をフォームから取得
        String shohinidstr = request.getParameter("shohinId");

        System.out.println("shohinidstr: " + shohinidstr);

        // 商品IDを選択肢から取得
        int shohinid = Integer.parseInt(shohinidstr);

        // Accountオブジェクトを作成し、フォームの入力内容をセット
        AdminDAO deleteshohindao = new AdminDAO();
        
        boolean result = deleteshohindao.deleteById(shohinid);
        
        System.out.print("商品削除結果"+result);
        
        // 登録完了メッセージを設定
        request.setAttribute("message", "商品情報が正常に削除されました。");

	    // セッションから "shohin" 属性を削除
	    HttpSession session = request.getSession();
	    session.removeAttribute("shohin");
	    
        // 結果画面に転送
        RequestDispatcher dispatcher = request.getRequestDispatcher("AdminIchiranServlet");
        dispatcher.forward(request, response);

    }
}
