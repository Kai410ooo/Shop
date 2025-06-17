<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>BGroup商品詳細</title>
<%@ include file="cssInclude.jsp"%>
</head>
<body>
	<%@ include file="header.jsp"%>
	<jsp:useBean id="shohinBean" class="model.Shohin" scope="request">
		<jsp:setProperty name="shohinBean" property="shohinId"
			value="${sessionScope.shohinId}" />
		<jsp:setProperty name="shohinBean" property="shohinMei"
			value="${sessionScope.shohinMei}" />
		<jsp:setProperty name="shohinBean" property="detail"
			value="${sessionScope.detail}" />
		<jsp:setProperty name="shohinBean" property="hanbaiTanka"
			value="${sessionScope.hanbaiTanka}" />
		<jsp:setProperty name="shohinBean" property="zaiko"
			value="${sessionScope.zaiko}" />
	</jsp:useBean>
<section>
<div class="detail">
	<h3 class="product-name">${sessionScope.shohinMei}</h3>
	<img src="${sessionScope.shohinImgPath}" alt="${sessionScope.shohinMei}" class="shohindetail-image">
	<div class="shohin-detail">
		<p class="detail-text">${fn:replace(sessionScope.detail, '$', '<br/>')}</p>
		<p class="price"><fmt:formatNumber value="${sessionScope.hanbaiTanka}" type="number" groupingUsed="true"/>円</p>
	</div>
	<form action="AddServlet" method="post">
		<input type="hidden" name="shohinId" value="${shohinBean.shohinId }">
		<input type="hidden" name="shohinMei" value="${shohinBean.shohinMei }">
		<input type="hidden" name="hanbaiTanka"
			value="${shohinBean.hanbaiTanka }">
		<c:if test="${sessionScope.zaiko > 0}">
		<input type="hidden" name="zaiko" value="${shohinBean.zaiko }">
		<label for="suryo">個数<br></label>
		<input type="number" id="suryo" name="suryo" min="1"
			max="${sessionScope.zaiko }" value="1" required><br>
		<input type="submit" name="cart" value="カートへ入れる">
		</c:if>
	</form>
</div>
	<ul>
		<li><a href="${requestScope.returnUrl}">戻る</a></li>
	</ul>
</section>
	<%@ include file="footer.jsp"%>
</body>
</html>
