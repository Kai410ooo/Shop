<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>BGroupメニュー画面</title>
<%@ include file="cssInclude.jsp" %>
</head>
<body>
<%@ include file="header.jsp" %>
<div class="image-slider">
    <img src="image/apple3.jpg" alt="image1" class="slider-image active">
    <img src="image/orange5.jpg" alt="image2" class="slider-image">
    <img src="image/peach2.jpg" alt="image3" class="slider-image">
    <img src="image/strawberry4.jpg" alt="image4" class="slider-image">
    <img src="image/grape3.jpg" alt="image5" class="slider-image">
</div>
<section>
<div class="slider-text">
　選び抜かれた極上のフルーツが、あなたのひとときを彩ります。<br>
　自然の恵みを余すところなく閉じ込めた、贅沢な味わい。<br>
　心を込めてお届けする最高品質のフルーツで、大切なひとときに特別なひと品を。</div>
	<h2>メニュー画面</h2>
<ul>
    <li>
        <form action="UserinfoServlet" method="post">
            <input type="submit" value="会員情報変更">
        </form>
    </li>
    
    <li>
        <form action="RirekiServlet" method="get">
            <input type="submit" value="注文履歴">
        </form>
    </li>
    
     <li>
        <form action="ShohinServlet" method="get">
            <input type="submit" value="商品を選択する">
        </form>
    </li>
    
    <li>
        <form action="CartServlet" method="get">
            <input type="submit" value="カートを見る">
        </form>
    </li>
    
</ul>
<ul><li>
	<form action="LogoutServlet" method="post">
		<!-- 送信ボタン -->
			<button type="submit" name="action" value="ログアウト">ログアウト</button>
	</form>
</li></ul>
</section>
<%@ include file="footer.jsp" %>
<script>
document.addEventListener('DOMContentLoaded', function () {
    const images = document.querySelectorAll('.slider-image');
    let currentIndex = 0;

    function changeImage() {
        // 現在の画像からactiveクラスを削除
        if (images.length > 0) {
            images[currentIndex].classList.remove('active');
            // 次の画像のインデックスを計算
            currentIndex = (currentIndex + 1) % images.length;
            // 次の画像にactiveクラスを追加
            images[currentIndex].classList.add('active');
        }
    }

    // 3.5秒ごとに画像を切り替える
    if (images.length > 0) {
        setInterval(changeImage, 3500);
    }
});
</script>
</body>
</html>
