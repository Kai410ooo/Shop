<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="model.Cart"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>BGroup購入内容の確認</title>
<%@ include file="cssInclude.jsp" %>
</head>
<body>
<%@ include file="header.jsp" %>
<section>
		<h2>購入内容の確認</h2>
	<div class="container">
		<table class="order">
			<tr>
				<th>商品名</th>
				<th>画像</th>
				<th>単価</th>
				<th>数量</th>
				<th>小計</th>
			</tr>
			<%
			List<Cart> cartItems = (List<Cart>) request.getAttribute("cartItems");
			int totalAmount = 0;
			for (Cart item : cartItems) {
				int subTotal = item.getBuyPrice() * item.getSuryo();
				totalAmount += subTotal;
			%>
			<tr>
				<td><%=item.getShohinMei()%></td>
				<td><img src="<%=item.getImgPath() %>" alt="商品画像" class="order-img"></td>
				<td><fmt:formatNumber value="<%=item.getBuyPrice()%>" type="number" groupingUsed="true"/>円</td>
				<td><%=item.getSuryo()%></td>
				<td><fmt:formatNumber value="<%=subTotal%>" type="number" groupingUsed="true"/>円</td>
			</tr>
			<%
			}
			%>
			<tr>
				<td colspan="4" style="text-align: right;"><strong>総合計:</strong></td>
				<td><strong><fmt:formatNumber value="<%=totalAmount%>" type="number" groupingUsed="true"/>円</strong></td>
			</tr>
		</table>
	</div>
	<div>
		<ul><li><form action="OrderServlet" method="post">
			<input type="submit" value="注文確定">
		</form></li></ul>
		<ul><li><a href="CustomerServlet">注文者情報へ</a></li></ul>
		<ul><li><a href="CartServlet">カートへ</a></li></ul>
		<ul><li><a href="MenuServlet">メニューへ</a></li></ul>
	</div>
</section>
<%@ include file="footer.jsp" %>
</body>
</html>
