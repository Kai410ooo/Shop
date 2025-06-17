<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>BGroupログイン画面</title>
<%@ include file="cssInclude.jsp" %>
</head>
<body class="login-body">
<%@ include file="loginHeader.jsp" %>
<div class="login-container">
	<h2 class="login-h2">ログイン画面</h2>
	<!-- エラーメッセージの表示 -->
	<c:if test="${not empty errormessage}">
		<div class="error-message">${errormessage}</div>
	</c:if>

	<!-- メッセージの表示 -->
	<c:if test="${not empty message}">
		<div class="message">${message}</div>
	</c:if>
	<form action="LoginServlet" method="post">
		<p class="login-p"><strong>ユーザID</strong><br><input type="text" name="id"></p>
		<p class="login-p"><strong>パスワード</strong><br><input type="password" name="pass"></p>
		<ul><li><input type="submit" value="ログイン"></li></ul>
	</form>
<ul><li>
	<form action="UserinfoServlet" method="post">
		<input type="submit" value="ユーザ登録">
	</form>
</li></ul>
</div>
<%@ include file="footer.jsp" %>
</body>
</html>