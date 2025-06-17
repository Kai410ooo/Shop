<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>BGroup注文完了</title>
<%@ include file="cssInclude.jsp" %>
</head>
<body>
<%@ include file="loginHeader.jsp" %>
<section>
		<h2>注文完了</h2>
		<p>注文が正常に完了しました。ご利用ありがとうございます。</p>
		<form action="MenuServlet" method="get">
			<input type="submit" value="メニューに戻る">
		</form>
</section>
<%@ include file="footer.jsp" %>
</body>
</html>
