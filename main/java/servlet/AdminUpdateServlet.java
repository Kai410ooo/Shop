package servlet;

import java.io.File;
import java.io.IOException;

import dao.AdminDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import model.Constants;
import model.Shohin;

@WebServlet("/AdminUpdateServlet")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 1,  // 1 MB
    maxFileSize = 1024 * 1024 * 10,       // 10 MB
    maxRequestSize = 1024 * 1024 * 15     // 15 MB
)
public class AdminUpdateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("AdminUpdateServlet：doGet");

        // 商品IDを取得して、商品情報をデータベースから取得する
        String shohinIdStr = request.getParameter("shohinId");
        int shohinId = Integer.parseInt(shohinIdStr);

        // AdminDAOを使って商品情報をデータベースから取得
        AdminDAO dao = new AdminDAO();
        Shohin shohin = dao.findShohinByShohinId(shohinId); // 商品情報を取得するメソッド

        // 商品情報をセッションに格納
        HttpSession session = request.getSession();
        session.setAttribute("shohin", shohin);

        // エラーメッセージを初期化
        request.setAttribute("errormessage", null);

        // 商品情報の表示画面にフォワード
        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/adminUpdateOrInsert.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("AdminUpdateServlet：doPost");
        request.setCharacterEncoding("UTF-8");

        // 送信されたすべてのパラメータを確認
        System.out.println("Parameters: " + request.getParameterMap());

        // 商品情報をフォームから取得
        String shohinidstr = request.getParameter("shohinId");
        String shohinMei = request.getParameter("shohinMei");
        String detail = request.getParameter("detail");
        String hanbaiTankaStr = request.getParameter("hanbaiTanka");
        String zaikoStr = request.getParameter("zaiko");
        String shohinImgPath = request.getParameter("shohinImgPath");
        // 商品情報をフォームから商品画像ファイルを取得
        Part shohinImgPart = request.getPart("shohinImgPathFile");

        // 商品idを取得（nullチェック）
        int shohinid = 0;
        if (shohinidstr != null && !shohinidstr.isEmpty()) {
            try {
                shohinid = Integer.parseInt(shohinidstr);
            } catch (NumberFormatException e) {
                request.setAttribute("errormessage", "商品IDの形式が正しくありません。");
                request.getRequestDispatcher("WEB-INF/jsp/adminUpdateOrInsert.jsp").forward(request, response);
                return;
            }
        }

        // 商品情報のバリデーション（エラーチェック）
        if (shohinMei == null || detail == null || hanbaiTankaStr == null || zaikoStr == null) {
            request.setAttribute("errormessage", "すべての項目を入力してください。");
            request.getRequestDispatcher("WEB-INF/jsp/adminUpdateOrInsert.jsp").forward(request, response);
            return;
        }

        // 数値の変換（nullチェック）
        int hanbaiTanka = 0;
        if (hanbaiTankaStr != null && !hanbaiTankaStr.isEmpty()) {
            try {
                hanbaiTanka = Integer.parseInt(hanbaiTankaStr);
            } catch (NumberFormatException e) {
                request.setAttribute("errormessage", "販売単価の形式が正しくありません。");
                request.getRequestDispatcher("WEB-INF/jsp/adminUpdateOrInsert.jsp").forward(request, response);
                return;
            }
        }
        // Zaikoがnullまたは空の場合
        int zaiko = 0;
        if (zaikoStr != null && !zaikoStr.isEmpty()) {
            try {
                zaiko = Integer.parseInt(zaikoStr);
            } catch (NumberFormatException e) {
                request.setAttribute("errormessage", "在庫数の形式が正しくありません。");
                request.getRequestDispatcher("WEB-INF/jsp/adminUpdateOrInsert.jsp").forward(request, response);
                return;
            }
        }
        
        // shohinImgPathがnullまたは空の場合
        if (shohinImgPath == null || shohinImgPath.trim().isEmpty()) {
            // 画像パスが未入力の場合の処理（例: エラーメッセージを設定するなど）
            request.setAttribute("errormessage", "商品画像のパスが未入力です。");
            request.getRequestDispatcher("WEB-INF/jsp/adminUpdateOrInsert.jsp").forward(request, response);
            return;
        }

        //変更時は画像はnullでもOK
//        // shohinImgPartがnullまたはサイズが0の場合
//        if (shohinImgPart == null || shohinImgPart.getSize() == 0) {
//            // 画像ファイルが選択されていない場合の処理（例: エラーメッセージを設定するなど）
//            request.setAttribute("errormessage", "商品画像が未選択です。");
//            request.getRequestDispatcher("WEB-INF/jsp/adminUpdateOrInsert.jsp").forward(request, response);
//            return;
//        }


        // 商品画像のファイルパス処理と保存
        // 原則、変更する前の商品画像パスのままにすること。
        // ただし、画像に変更があった場合は以下、サーバに画像を保存するの処理を通す
        String imagePath = shohinImgPath;
        System.out.println("shohinImgPath: " + shohinImgPath);
        System.out.println("shohinImgPart.getSize()選択されている画像サイズ："+shohinImgPart.getSize());
        
        // サーバーに画像を保存        
        if (shohinImgPart != null && shohinImgPart.getSize() > 0) {
            // サーバーに画像を保存するディレクトリを決定
            String uploadDir = getServletContext().getRealPath(Constants.SHOHINIMAGE_FOLDERNAME); // Constantsクラスから定数を呼び出す
            File uploadDirFile = new File(uploadDir);
            if (!uploadDirFile.exists()) {
                uploadDirFile.mkdir(); // フォルダがなければ作成
            }

            // ファイル名を取得して保存
            String fileName = getFileName(shohinImgPart);
            imagePath = Constants.SHOHINIMAGE_FOLDERNAME + "/" + fileName; // ここもConstantsクラスから定数を使って相対パスを作成

            // ファイルを保存
            shohinImgPart.write(uploadDir + File.separator + fileName);
//            System.out.println("uploadDirFile.getAbsolutePath(絶対パス)" + uploadDirFile.getAbsolutePath());
//            shohinImgPart.write(uploadDirFile.getAbsolutePath());

            // 画像の相対パスをコンソールに出力
            System.out.println("商品画像の処理if文中imagePath: " + imagePath);
            System.out.println("商品画像の処理if文中filename: " + fileName);

        }

        
        //デバッグ用
        System.out.println("shohinidstr: " + shohinidstr);
        System.out.println("shohinMei: " + shohinMei);
        System.out.println("detail: " + detail);
        System.out.println("hanbaiTanka: " + hanbaiTankaStr);
        System.out.println("zaikoStr: " + zaikoStr);
        // 画像の相対パスをコンソールに出力
        System.out.println("imagePath: " + imagePath);
        System.out.println("Constants.SHOHINIMAGE_FOLDERNAME: " + Constants.SHOHINIMAGE_FOLDERNAME);

        // 商品情報をデータベースに保存
        AdminDAO shohinupdatedao = new AdminDAO();
        Shohin updateshohin = new Shohin(shohinid, shohinMei, detail, hanbaiTanka, zaiko, imagePath);

        boolean isInserted = shohinupdatedao.updateShohin(updateshohin);

        // 結果メッセージの表示
        if (isInserted) {
            request.setAttribute("message", "商品が正常に登録されました。");
        } else {
            request.setAttribute("errormessage", "商品登録に失敗しました。");
        }
        
	    // セッションから "shohin" 属性を削除
	    HttpSession session = request.getSession();
	    session.removeAttribute("shohin");

        RequestDispatcher dispatcher = request.getRequestDispatcher("AdminIchiranServlet");
        dispatcher.forward(request, response);
        
        
    }

    // ファイル名を取得するメソッド
    private String getFileName(Part part) {
        String contentDisposition = part.getHeader("content-disposition");
        for (String cd : contentDisposition.split(";")) {
            if (cd.trim().startsWith("filename")) {
                String filename = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
                return filename;
            }
        }
        return null;
    }
}
