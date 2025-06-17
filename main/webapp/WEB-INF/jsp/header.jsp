<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<header>
    <h1 class="userHeader"><a href="MenuServlet">くだもの屋さん</a></h1>
		<nav>
			<a href="MenuServlet">メニュー画面</a>
			<form action="UserinfoServlet" method="post" class="nav-form">
				<button type="submit">会員情報変更</button>
			</form>
			<form action="RirekiInfoServlet" method="post" class="nav-form">
				<button type="submit">注文履歴</button>
			</form>
			<a href="ShohinServlet">商品を選択</a>
			<a href="CartServlet">カートを見る</a>
        </nav>
</header>