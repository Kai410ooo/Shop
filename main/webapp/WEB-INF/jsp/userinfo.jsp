<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>BGroupログイン画面</title>
<%@ include file="cssInclude.jsp"%>
</head>
<body>
	<%@ include file="loginHeader.jsp"%>

		<!-- エラーメッセージの表示 -->
		<c:if test="${not empty errormessage}">
			<div class="error-message"">${errormessage}</div>
		</c:if>

		<!-- メッセージの表示 -->
		<c:if test="${not empty message}">
			<div class="message"">${message}</div>
		</c:if>

		<!-- 変更 or 新規登録のフォーム -->
		<c:choose>
			<c:when test="${not empty account.userId}">
				<form action="AccountUpdateServlet" method="post">
					<h2>会員情報変更</h2>
			</c:when>
			<c:otherwise>
				<form action="AccountAddServlet" method="post">
					<h2>ユーザ登録</h2>
			</c:otherwise>
		</c:choose>
	<div class="form-container">
		<table border="1" class="info-form">
			<tr>
				<td class="user-text">会員ＩＤ</td>
				<!-- 変更時：会員id入力はreadonly or 新規登録時、会員id入力はrequired -->
				<c:choose>
					<c:when test="${not empty account.userId}">
						<td><input type="text" name="user_id"
							value="${account.userId}" readonly></td>
					</c:when>
					<c:otherwise>
						<td><input type="text" name="user_id"
							value="${account.userId}" required></td>
					</c:otherwise>
				</c:choose>
			</tr>
			<tr>
				<td class="user-text">パスワード</td>
				<td><input type="password" name="password"
					value="${account.userPass}" required></td>
			</tr>
			<tr>
				<td class="user-text">氏名</td>
				<td><input type="text" name="name" value="${account.name}"
					required></td>
			</tr>
			<tr>
				<td class="user-text">郵便番号</td>
				<td><input type="text" name="zipcode"
					value="${account.postnumber}" pattern="\d{7}"
					placeholder="例: 1234567" required></td>
			</tr>
			<tr>
				<td class="user-text">住所</td>
				<td><input type="text" name="address"
					value="${account.address}" required></td>
			</tr>
			<tr>
				<td class="user-text">電話番号</td>
				<td><input type="tel" name="phone" value="${account.tell}"
					pattern="\d{10,11}" placeholder="例: 0312345678" required></td>
			</tr>
			<tr>
				<td class="user-text">メールアドレス</td>
				<td><input type="email" name="email" value="${account.mail}"
					required></td>
			</tr>
			<tr>
				<td class="user-text">支払方法</td>
				<td>
					<!-- 支払方法の選択肢をセッションスコープから取得 --> <select name="paymentMethod"
					required>
						<c:forEach var="shiharai" items="${sessionScope.shiharaiList}">
							<option value="${shiharai.payId}"
								${shiharai.payId == account.payId ? 'selected' : ''}>${shiharai.payName}</option>
						</c:forEach>
				</select>
				</td>
			</tr>
		</table>

		<!-- 変更 or 新規登録ボタン -->
		<c:choose>
			<c:when test="${not empty account.userId}">
				<input type="submit" value="変更する">
			</c:when>
			<c:otherwise>
				<input type="submit" value="登録する">
			</c:otherwise>
		</c:choose>

		</form>
		<!-- ここで <form> を閉じる -->
	</div>
	<ul>
		<!-- account.userId が null のときだけログイン画面のリンクを表示 -->
		<c:if test="${empty account.userId}">
			<li><a href="LoginServlet">ログインへ</a></li>
		</c:if>
		<c:if test="${not empty account.userId}">
			<li><a href="MenuServlet">メニューへ</a></li>
		</c:if>
	</ul>
	<%@ include file="footer.jsp"%>
</body>
</html>
