<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>BGroup商品一覧</title>
<%@ include file="cssInclude.jsp" %>
</head>
<body>
<%@ include file="header.jsp" %>
<%@ include file="topButton.jsp" %>
<div class="image-container">
    <div class="overlay"></div>
    <img src="image/fruits.jpg" alt="image" class="image">
</div>
<section>
	<h2>商品一覧</h2>
<div class="container">
    <c:forEach var="shohin" items="${shohinList}">
        <c:choose>
            <c:when test="${shohin.zaiko > 0}">
                <a href="ShohindetailServlet?shohinId=${shohin.shohinId}" class="product-card">
            <img src="${shohin.shohinImgPath}" alt="${shohin.shohinMei}">
                    <h3>${shohin.shohinMei}</h3>
                    <p>在庫あり</p>
                </a>
            </c:when>
            <c:otherwise>
                <div class="product-card out-of-stock-card">
                <img src="${shohin.shohinImgPath}" alt="${shohin.shohinMei}">
                    <h3>${shohin.shohinMei}</h3>
                    <p class="out-of-stock">在庫切れ</p>
                </div>
            </c:otherwise>
        </c:choose>
    </c:forEach>
</div>
	<ul><li><a href="MenuServlet">メニューへ</a></li></ul>
</section>
<%@ include file="footer.jsp" %>
</body>
</html>