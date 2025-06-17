<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>BGroup管理者メニュー画面</title>
<%@ include file="cssInclude.jsp" %>
</head>
<body>
<%@ include file="adminHeader.jsp" %>
<section>
	<h2>管理者メニュー画面</h2>
	
		<!-- エラーメッセージの表示 -->
	<c:if test="${not empty errormessage}">
		<div class="error-message">${errormessage}</div>
	</c:if>

	<!-- メッセージの表示 -->
	<c:if test="${not empty message}">
		<div class="message">${message}</div>
	</c:if>
	
<body>
<ul>
    <li>
        <form action="AdminIchiranServlet" method="post">
            <input type="submit" value="登録商品一覧">
        </form>
    </li>
<!-- 
    <li>
        <form action="???Servlet" method="get">
            <input type="submit" value="全売り上げ履歴">
        </form>
    </li>
       
    <li>
        <form action="????Servlet" method="get">
            <input type="submit" value="商品毎売上履歴">
        </form>
    </li>
 -->        
</ul>

	<form action="LogoutServlet" method="post">
		<!-- 送信ボタン -->
			<ul><li>
			<button type="submit" name="action" value="ログアウト">ログアウト</button>
			</li></ul>
	</form>
</section>
<%@ include file="footer.jsp" %>
</body>
</html>
