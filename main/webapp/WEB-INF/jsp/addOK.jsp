<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>BGroup追加完了</title>
<%@ include file="cssInclude.jsp" %>
</head>
<body>
<%@ include file="header.jsp" %>
<section>
	<h4><br>商品をカートに追加しました。</h4>

	<ul><li><form action="CartServlet" method="get">
		<input type="submit" value="カートを見る">
	</form></li></ul>
	<p>
		<ul><li><a href="ShohinServlet">買い物を続ける</a></li></ul>
	</p>
</section>
<%@ include file="footer.jsp" %>
</body>
</html>