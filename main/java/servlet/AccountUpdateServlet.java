package servlet;

import java.io.IOException;

import dao.AccountDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Account;

/**
 * Servlet implementation class AccountAddServlet
 */
@WebServlet("/AccountUpdateServlet")
public class AccountUpdateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("AccountUpdateServlet：doGet");

    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("AccountUpdateServlet：doPost");

        // フォームからデータを取得
        String userId = request.getParameter("user_id");
        String password = request.getParameter("password");
        String name = request.getParameter("name");
        String zipcodeStr = request.getParameter("zipcode");
        String address = request.getParameter("address");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String paymentMethod = request.getParameter("paymentMethod");

        // デバッグ用ログ出力
        System.out.println("userId: " + userId);
        System.out.println("password: " + password);
        System.out.println("name: " + name);
        System.out.println("zipcodeStr: " + zipcodeStr);
        System.out.println("address: " + address);
        System.out.println("phone: " + phone);
        System.out.println("email: " + email);
        System.out.println("paymentMethod: " + paymentMethod);

        // 支払方法IDを整数に変換
        int payId = Integer.parseInt(paymentMethod);

        // 郵便番号を整数に変換
        int zipcode = 0;
        try {
            zipcode = Integer.parseInt(zipcodeStr.replace("-", ""));  // "123-4567" -> 1234567
        } catch (NumberFormatException e) {
            request.setAttribute("errormessage", "郵便番号の形式が正しくありません。");
            request.getRequestDispatcher("WEB-INF/jsp/userinfo.jsp").forward(request, response);
            return;
        }

        // 更新するアカウントオブジェクトを作成
        Account updatedAccount = new Account(userId, password, name, email, zipcode, address, phone, payId, null);

        // DAO を使用してアカウント情報を更新
        AccountDAO dao = new AccountDAO();
        boolean updateResult = dao.updateUserAccount(updatedAccount);

        // 更新結果をログに出力
        System.out.println("アカウント更新結果: " + updateResult);

        if (updateResult) {
            // セッションに更新後のアカウント情報を保存
            HttpSession session = request.getSession();
            session.setAttribute("account", updatedAccount);

            // 更新成功メッセージを設定
            request.setAttribute("message", "アカウント情報が正常に変更されました。");

            // メニュー画面に転送
            RequestDispatcher dispatcher = request.getRequestDispatcher("MenuServlet");
            dispatcher.forward(request, response);
        } else {
            // 更新失敗メッセージを設定
            request.setAttribute("errormessage", "アカウント情報の更新に失敗しました。");

            // ユーザー情報画面に転送
            RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/userinfo.jsp");
            dispatcher.forward(request, response);
        }
    }

}
