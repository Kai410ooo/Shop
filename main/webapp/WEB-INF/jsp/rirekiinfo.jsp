<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
<title>BGroup注文履歴詳細</title>
<%@ include file="cssInclude.jsp"%>
</head>
<body>
	<%@ include file="header.jsp"%>
<section>
<c:choose>
<c:when test="${not empty errormessage}">
		<!-- エラーメッセージの表示 -->
	<br><br><br><br><div class="error-message">${errormessage}</div><br><br>
</c:when>
<c:otherwise>
	<h2>注文者情報</h2>
	<c:if test="${not empty errorMessage}">
		<p style="color: red;">${errorMessage}</p>
	</c:if>
	<c:if test="${not empty orderDetails}">
		<c:set var="firstOrder" value="${orderDetails[0]}" />
		<p>注文者名: ${firstOrder.userName}</p>
		<p>注文日:<fmt:formatDate value="${firstOrder.buyDay}" pattern="yyyy-MM-dd HH:mm" /></p>
		<p>住所: ${firstOrder.address}</p>
		<p>電話番号: ${firstOrder.tell}</p>
		<p>メール: ${firstOrder.mail}</p>
		<h2>注文商品</h2>
		<div class="container">
		
			<table class="rireki-info">
				<tr>
					<th>商品名</th>
					<th>数量</th>
					<th>価格</th>
				</tr>
				<c:set var="totalSum" value="0" />
				<c:forEach var="order" items="${orderDetails}">
					<tr>
						<td><a href="ShohindetailServlet?shohinId=${order.id}">${order.name}
						</a></td>
						<td>${order.suryo}</td>
						<td><fmt:formatNumber value="${order.buyPrice}" type="number" groupingUsed="true"/>円</td>
					</tr>
					<c:set var="totalSum" value="${totalSum + order.total}" />
				</c:forEach>
				<tr>
					<td colspan="2" style="text-align: right;"><strong>総合計金額:</strong></td>
					<td><strong><fmt:formatNumber value="${totalSum}" type="number" groupingUsed="true"/>円</strong></td>
				</tr>
			</table>
		</div>
	</c:if>
</c:otherwise>
</c:choose>
	<ul><li>
		<form action="RirekiInfoServlet" method="post">
			<button type="submit">注文履歴に戻る</button>
		</form>
	</li></ul>
</section>
	<%@ include file="footer.jsp"%>
</body>
</html>

