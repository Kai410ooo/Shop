<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>BGroupカート</title>
<%@ include file="cssInclude.jsp" %>
</head>
<body>
<%@ include file="header.jsp" %>
<%@ include file="topButton.jsp" %>
<section>
<h2>カート</h2>

<div class="container">
    <c:set var="totalSum" value="0" />
    <table class="cart">
        <tr>
            <th>商品名</th>
            <th>画像</th>
            <th>単価</th>
            <th>数量</th>
            <th>小計</th>
            <th>削除</th>
        </tr>
        <c:forEach var="cart" items="${cartList }">
            <tr>
                <form action="UpdateServlet" method="post">
                    <td>${cart.shohinMei}</td>
                    <td><img src="${cart.imgPath}" alt="商品画像" class="cart-img"></td>
                    <td><fmt:formatNumber value="${cart.buyPrice}" type="number" groupingUsed="true"/>円</td>
                    <td>
                        <input type="number" name="suryo" min="1" max="${cart.zaiko}" 
                        value="${cart.suryo}" required class="small-input">個
                        <input type="hidden" name="cartId" value="${cart.cartId}">
                        <button type="submit" class="action-button">変更</button>
                    </td>
                    <td><fmt:formatNumber value="${cart.total}" type="number" groupingUsed="true"/>円</td>
                </form>
                <form action="DeleteServlet" method="post">
                    <td>
                        <input type="hidden" name="cartId" value="${cart.cartId}">
                        <button type="submit" class="action-button">削除</button>
                    </td>
                </form>
            </tr>
            <c:set var="totalSum" value="${totalSum + cart.total}" />
        </c:forEach>
        <tr>
        	<td colspan="4" style="text-align: right;"><strong>総合計:</strong></td>
            <td colspan="2"><strong><fmt:formatNumber value="${totalSum}" type="number" groupingUsed="true"/>円</strong></td>
        </tr>
    </table>
</div>

		<!-- エラーメッセージの表示 -->
		<c:if test="${not empty errormessage}">
			<div class="error-message"">${errormessage}</div>
		</c:if>

 <c:if test="${not empty cartList}">
 	<ul><li><form action="CustomerServlet" method="post">
  		<input type="submit" value="購入する">
  		<c:forEach var="cart" items="${cartList}">
			<input type="hidden" name="shohinId" value="${cart.shohinId}">
		</c:forEach>
	</form></li></ul>
</c:if>
    <ul><li><a href="ShohinServlet">商品の選択へ</a></li></ul>
    <ul><li><a href="MenuServlet">メニューへ</a></li></ul>
    <br><ul><a href="CartAllDeleteServlet">カートの中身を空にする</a></ul>
</section>
<%@ include file="footer.jsp" %>
</body>
</html>
