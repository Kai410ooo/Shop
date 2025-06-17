package servlet;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Account;
import model.AccountRegisterLogic;

/**
 * Servlet implementation class AccountAddServlet
 */
@WebServlet("/AccountDeleteServlet")
public class AccountDeleteServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("AccountDeleteServlet：doGet");
//        request.setAttribute("errormessage", null);

        // フォームに初期表示する値があれば設定（新規登録の場合）
        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/userinfo.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("AccountDeleteServlet：doPost");
    	String paymentMethod = request.getParameter("paymentMethod");

    	// その他のフォームパラメータを取得
    	String userId = request.getParameter("user_id");
    	String password = request.getParameter("password");
    	String name = request.getParameter("name");
    	String zipcodeStr = request.getParameter("zipcode");
    	String address = request.getParameter("address");
    	String phone = request.getParameter("phone");
    	String email = request.getParameter("email");

        // コンソールに出力（デバッグ用）
        System.out.println("paymentMethod: " + paymentMethod);
        System.out.println("userId: " + userId);
        System.out.println("password: " + password);
        System.out.println("name: " + name);
        System.out.println("zipcodeStr: " + zipcodeStr);
        System.out.println("address: " + address);
        System.out.println("phone: " + phone);
        System.out.println("email: " + email);
    	
        // 支払方法IDを選択肢から取得
        int payId = Integer.parseInt(paymentMethod);

        // 郵便番号を整数に変換
        int zipcode = 0;
        try {
            zipcode = Integer.parseInt(zipcodeStr.replace("-", ""));  // 例: "123-4567" -> 1234567
        } catch (NumberFormatException e) {
            request.setAttribute("errormessage", "郵便番号の形式が正しくありません。");
            request.getRequestDispatcher("WEB-INF/jsp/userinfo.jsp").forward(request, response);
            return;
        }

        // コンソールに出力（デバッグ用）
        System.out.println("zipcode: " + zipcode);
        System.out.println("payId: " + payId);

        // Accountオブジェクトを作成し、フォームの入力内容をセット
        Account newAccount = new Account(userId, password, name, email, zipcode, address, phone, payId, null);
        
        AccountRegisterLogic userLogic = new AccountRegisterLogic();
        boolean result = userLogic.execute(newAccount);  // アカウント登録
        
        System.out.print("新規登録結果"+result);
        
        // セッションにアカウント情報を保存
        HttpSession session = request.getSession();
        session.setAttribute("newAccount", newAccount);

        // 登録完了メッセージを設定
        request.setAttribute("message", "アカウント情報が正常に登録されました。");

        // 結果画面に転送
        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/login.jsp");
        dispatcher.forward(request, response);
    }
}
