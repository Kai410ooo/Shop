<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>BGroup注文者情報</title>
<%@ include file="cssInclude.jsp" %>
</head>
<body>
<%@ include file="header.jsp" %>
<section>
<h2>注文者情報</h2>
<div class="customer-info">
	<p>${account.name }</p>
	<p>${account.postnumber }</p>
	<p>${account.address }</p>
	<p>${account.tell }</p>
	<p>${account.mail }</p>
	<p>${account.payName }</p>
</div>
<ul><li>
	<form action="AlterServlet" method="get">
		<input type="submit" name="change" value="変更する">
	</form>
	</li></ul>
	<ul><li>
	<form action="OrderServlet" method="get">
		<input type="submit" name="check" value="確定">

	</form>
	</li></ul>
		<ul><li><a href="CartServlet">カートへ</a></li></ul>
		<ul><li><a href="MenuServlet">メニューへ</a></li></ul>
</section>
<%@ include file="footer.jsp" %>
</body>
</html>