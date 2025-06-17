package servlet;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import dao.CartDAO;
import dao.RirekiDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Account;
import model.Cart;
import model.Rireki;
import shopMail.ShopMail;

@WebServlet("/OrderCompServlet")
public class OrderCompServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session1 = request.getSession(false);
		String userId = (String) session1.getAttribute("id");

		// 注文がすでに完了している場合はメニューにリダイレクト
		if (session1 == null || session1.getAttribute("orderCompleted") != null) {
			// すでに注文が完了している場合は、注文ページに戻す
			response.sendRedirect("MenuServlet");
			return;
		}

		// 注文内容を履歴に登録
		Account account = (Account) session1.getAttribute("account");
		List<Cart> cartList = getListAttribute(session1, "cartList", Cart.class);

		RirekiDAO rirekiDAO = new RirekiDAO();
		List<String> insufficientStockItems = new ArrayList<>();

		for (Cart cart : cartList) {
			int currentZaiko = rirekiDAO.checkZaiko(cart.getShohinId());
			if (cart.getSuryo() > currentZaiko) {
				insufficientStockItems.add(cart.getShohinMei());
			}
		}

		if (!insufficientStockItems.isEmpty()) {
			// 在庫不足を表示
			StringBuilder errorMessage = new StringBuilder("以下の商品が在庫不足です: ");
			for (String item : insufficientStockItems) {
				errorMessage.append(item).append(" ");
			}
			request.setAttribute("errorMessage", errorMessage.toString());

			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/order.jsp");
			dispatcher.forward(request, response);
			return;
		}

		// 新しいorder_group_idを生成
		int newOrderGroupId = rirekiDAO.generateNewOrderGroupId();
		System.out.println("生成されたorder_group_id: " + newOrderGroupId); // デバッグ出力

		for (Cart cart : cartList) {
			Rireki rireki = new Rireki(
					0, // 初期値は0に設定
					cart.getShohinId(),
					cart.getShohinMei(),
					"N/A", // ここを修正。詳細がない場合はデフォルト値を設定
					cart.getBuyPrice(),
					cart.getSuryo(),
					cart.getTotal(),
					new Timestamp(System.currentTimeMillis()),
					account.getName(),
					account.getPostnumber(),
					account.getAddress(),
					account.getTell(),
					account.getMail(),
					"支払いID",
					userId,
					newOrderGroupId); // 新しいorder_group_idを設定

			// 各商品の在庫数を減らす
			rirekiDAO.updateZaiko(cart.getShohinId(), cart.getSuryo());

			// 購入内容を履歴に追加
			rirekiDAO.addRireki(rireki);
			System.out.println("最終的なRirekiオブジェクト: " + rireki); // デバッグ出力
		}

		// 注文内容をメールで送信
		int group = 2;
		String send = account.getMail();

		Timestamp buyDay = new Timestamp(System.currentTimeMillis());
		Calendar cal = Calendar.getInstance();
		cal.setTime(buyDay);
		cal.add(Calendar.DAY_OF_MONTH, 2); // 2日後に設定
		Timestamp estimatedDeliveryDate = new Timestamp(cal.getTimeInMillis());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年M月d日(E)");
		String formattedDate = sdf.format(new Date(estimatedDeliveryDate.getTime()));

		String shop = "注文受付完了：くだもの屋さん";
		String title = "ご注文ありがとうございます";
		StringBuilder main = new StringBuilder(account.getName() + "様\n\n");
		int sum = 0;
		main.append("このたびはくだもの屋さんをご利用いただき、誠にありがとうございます。\n").append("お客様のご注文を承りました。\n\n")
				.append("《お届け予定》\n")
				.append(formattedDate)
				.append("\n\n")
				.append("《お届け先》\n")
				.append(account.getName() + " 様\n")
				.append(account.getPostnumber() + "\n").append(account.getAddress() + "\n\n")
				.append("《注文情報》\n")
				.append("----------------------------------------\n")
				.append("ご注文No：")
				.append(newOrderGroupId + "\n");
		for (Cart cart : cartList) {
			main
					.append(cart.getShohinMei())
					.append(cart.getBuyPrice())
					.append("円 ")
					.append(cart.getSuryo())
					.append("個 ")
					.append(cart.getTotal())
					.append("円\n");
			sum += cart.getTotal();
		}
		main.append("注文合計：").append(sum).append("円\n").append("----------------------------------------\n\n")
				.append("《支払方法》\n")
				.append(account.getPayName() + "\n\n")
				.append("ご不明点等に関しましては「ご注文No」を明記の上、メールにお問い合わせください。")
				.append("くだもの屋さんのまたのご利用を心よりお待ちしております。\n")
				.append("============================\n").append("くだもの屋さん\n")
				.append("お問い合わせ：kudamonoyasan@example.com\n")
				.append("============================\n");
		String mainString = main.toString();
		int format = 0;

		try {
			ShopMail.send(group, send, shop, title, mainString, format);
			System.out.println("送信できました");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("送信できませんでした");
		}

		// 注文処理（注文内容を注文履歴に登録、ショッピングカートをクリア）
		CartDAO cartDAO = new CartDAO();
		cartDAO.clearCart(userId);

		// セッションからカートアイテムを削除
		HttpSession session = request.getSession();
		session.removeAttribute("cartItems");
		// 注文者情報で一時保存した情報を削除
		HttpSession session2 = request.getSession();
		session2.removeAttribute("temporaryAccount");

		// 注文完了フラグをセッションに保存（ブラウザバック対策）
		session1.setAttribute("orderCompleted", true);

		// 注文完了メッセージを表示するためのJSPにフォワード
		RequestDispatcher dispatcher = request
				.getRequestDispatcher("WEB-INF/jsp/ordercomp.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	@SuppressWarnings("unchecked")
	public static <T> List<T> getListAttribute(HttpSession session, String attributeName, Class<T> clazz) {
		Object attribute = session.getAttribute(attributeName);
		if (attribute instanceof List<?>) {
			List<?> list = (List<?>) attribute;
			for (Object item : list) {
				if (!clazz.isInstance(item)) {
					throw new ClassCastException("List contains elements of the wrong type.");
				}
			}
			return (List<T>) list;
		}
		return new ArrayList<>();
	}
}
