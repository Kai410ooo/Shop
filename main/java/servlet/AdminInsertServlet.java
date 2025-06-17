package servlet;

import java.io.IOException;

import dao.AdminDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import model.Constants;
import model.Shohin;

@WebServlet("/AdminInsertServlet")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 1,  // 1 MB
    maxFileSize = 1024 * 1024 * 10,       // 10 MB
    maxRequestSize = 1024 * 1024 * 15     // 15 MB
)
public class AdminInsertServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("AdminInsertServlet：doGet");
        request.setAttribute("errormessage", null);
        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/adminUpdateOrInsert.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("AdminInsertServlet：doPost");
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

        // shohinImgPartがnullまたはサイズが0の場合
        if (shohinImgPart == null || shohinImgPart.getSize() == 0) {
            // 画像ファイルが選択されていない場合の処理（例: エラーメッセージを設定するなど）
            request.setAttribute("errormessage", "商品画像が未選択です。");
            request.getRequestDispatcher("WEB-INF/jsp/adminUpdateOrInsert.jsp").forward(request, response);
            return;
        }

        // 取得したフォームの値をコンソールに出力（デバッグ用）
        System.out.println("shohinid: " + shohinid);
        System.out.println("shohinMei: " + shohinMei);
        System.out.println("detail: " + detail);
        System.out.println("hanbaiTanka: " + hanbaiTanka);
        System.out.println("zaiko: " + zaiko);
        System.out.println("shohinImgPath: " + shohinImgPath);
        System.out.println("shohinImgPart.getSize(): " + shohinImgPart.getSize());  
        
        // 商品画像のファイルパス=SHOHINIMAGE_FOLDERNAME("shohin_image") + "/" +取得したファイル名
        String imagePath = Constants.SHOHINIMAGE_FOLDERNAME + "/" + shohinImgPath;
        
//        if (shohinImgPart != null && shohinImgPart.getSize() > 0) {
//            // サーバーに画像を保存するディレクトリを決定
//            String uploadDir = getServletContext().getRealPath(Constants.SHOHINIMAGE_FOLDERNAME); // Constantsクラスから定数を呼び出す
//            File uploadDirFile = new File(uploadDir);
//            if (!uploadDirFile.exists()) {
//                uploadDirFile.mkdir(); // フォルダがなければ作成
//            }
//
//            // ファイル名を取得して保存
//            String fileName = getFileName(shohinImgPart);
//            imagePath = Constants.SHOHINIMAGE_FOLDERNAME + "/" + fileName; // ここもConstantsクラスから定数を使って相対パスを作成
//
//            // ファイルを保存
//            shohinImgPart.write(uploadDir + File.separator + fileName);
//            System.out.println("uploadDirFile.getAbsolutePath(絶対パス)" + uploadDirFile.getAbsolutePath());
//            shohinImgPart.write(uploadDirFile.getAbsolutePath());
//
//            // 画像の相対パスをコンソールに出力
//            System.out.println("商品画像の処理if文中imagePath: " + imagePath);
//            System.out.println("商品画像の処理if文中filename: " + fileName);
//
//        }

        // 商品情報をデータベースに保存
        AdminDAO shohinadddao = new AdminDAO();
        Shohin addshohin = new Shohin(shohinid, shohinMei, detail, hanbaiTanka, zaiko, imagePath);

        boolean isInserted = shohinadddao.RegisterShohin(addshohin);

        // 結果メッセージの表示
        if (isInserted) {
            request.setAttribute("message", "商品が正常に登録されました。");
        } else {
            request.setAttribute("errormessage", "商品登録に失敗しました。");
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("AdminIchiranServlet");
        dispatcher.forward(request, response);
    }

    // ファイル名を取得するメソッド
//    private String getFileName(Part part) {
//        String contentDisposition = part.getHeader("content-disposition");
//        for (String cd : contentDisposition.split(";")) {
//            if (cd.trim().startsWith("filename")) {
//                String filename = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
//                return filename;
//            }
//        }
//        return null;
//    }
}
