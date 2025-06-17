<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>BGroup在庫一覧</title>
<%@ include file="cssInclude.jsp"%>
</head>
<body>
	<%@ include file="adminHeader.jsp"%>
	<%@ include file="topButton.jsp"%>
	<section>
		<h2>登録商品一覧</h2>

		<!-- エラーメッセージの表示 -->
		<c:if test="${not empty errormessage}">
			<div class="error-message">${errormessage}</div>
		</c:if>

		<!-- メッセージの表示 -->
		<c:if test="${not empty message}">
			<div class="message">${message}</div>
		</c:if>
		<div class="container">
			<!-- 商品情報テーブル -->
			<table class="admin-ichiran">
				<tr>
					<th>商品ID</th>
					<th>商品名称</th>
					<th>商品内容</th>
					<th>商品画像</th>
					<th>販売単価</th>
					<th>在庫</th>
					<th>-</th>
				</tr>
				<!-- allShohinInfoのリストを表示 -->
				<c:forEach var="shohin" items="${allShohinInfo}">
					<tr>
						<td><c:out value="${shohin.shohinId}" /></td>
						<td><c:out value="${shohin.shohinMei}" /></td>
						<td><c:out value="${shohin.detail}" /></td>
						<td><c:out value="${shohin.shohinImgPath}" /></td>
						<td><c:out value="${shohin.hanbaiTanka}" /></td>
						<td><c:out value="${shohin.zaiko}" /></td>
						<td><a href="AdminUpdateServlet?shohinId=${shohin.shohinId}"
							class="zaikoUpdate">変更・削除へ</a></td>
					</tr>
				</c:forEach>
			</table>

			<ul>
				<li>
					<form action="AdminInsertServlet" method="get">
						<input type="submit" value="新規登録へ">
					</form>
				</li>
			</ul>
		</div>
		<ul>
			<li><a href="AdminMenuServlet">管理者メニューへ</a></li>
		</ul>

	</section>
	<%@ include file="footer.jsp"%>
</body>
</html>
