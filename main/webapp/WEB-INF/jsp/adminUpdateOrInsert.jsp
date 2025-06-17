<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <%@ include file="cssInclude.jsp" %>
    <!-- 新規登録 or 変更登録のタイトルを切り替え -->
    <c:choose>
        <c:when test="${not empty shohin.shohinId}">
            <title>BGroup商品情報変更</title>
        </c:when>
        <c:otherwise>
            <title>BGroup商品情報登録</title>
        </c:otherwise>
    </c:choose>
    
    <script type="text/javascript">
        // ファイルが選択されたら、そのファイル名を表示
        function showFileName(input) {
            var fileName = input.files[0] ? input.files[0].name : ''; // 選択されたファイルの名前を取得
            document.getElementById('shohinImgPath').value = fileName; // テキストボックスにファイル名を設定
        }
    </script>
</head>
<body>
<%@ include file="adminHeader.jsp" %>
<section>    
    <!-- 新規登録 or 変更登録のタイトルを切り替え -->
    <c:choose>
        <c:when test="${not empty shohin.shohinId}">
            <h2>商品変更画面</h2>
        </c:when>
        <c:otherwise>
            <h2>商品登録画面</h2>
        </c:otherwise>
    </c:choose>

<div class="form-container">
    <!-- エラーメッセージの表示 -->
    <c:if test="${not empty errormessage}">
        <div class="error-message">
            ${errormessage}
        </div>
    </c:if>

    <!-- メッセージの表示 -->
    <c:if test="${not empty message}">
        <div class="message">
            ${message}
        </div>
    </c:if>

    <!-- 遷移先をAdminUpdateServlet or AdminInsertServletで切り替え -->
    <c:choose>
        <c:when test="${not empty shohin.shohinId}">
            <form action="AdminUpdateServlet" method="post" enctype="multipart/form-data">
        </c:when>
        <c:otherwise>
            <form action="AdminInsertServlet" method="post" enctype="multipart/form-data">
        </c:otherwise>
    </c:choose>

    <!-- 商品IDは隠しフィールドとして送信しない -->
    <input type="hidden" name="shohinId" value="${shohin.shohinId}">

    <!-- 商品情報入力フォーム -->
	<table border="1" class="info-form">
        <tr>
            <td>商品ID</td>
            <c:choose>
                <c:when test="${not empty shohin.shohinId}">
                    <td><input type="text" name="shohinId" value="${shohin.shohinId}" readonly></td>
                </c:when>
                <c:otherwise>
                    <td><input type="text" name="shohinId" value="(新規)" readonly></td>
                </c:otherwise>
            </c:choose>
        </tr>

        <tr>
            <td>商品名</td>
            <td><input type="text" name="shohinMei" value="${shohin.shohinMei}" required></td>
        </tr>

        <tr>
            <td>商品詳細</td>
            <td><input type="text" name="detail" value="${shohin.detail}" required></td>
        </tr>

        <tr>
            <td>販売単価</td>
            <td><input type="number" name="hanbaiTanka" value="${shohin.hanbaiTanka}" required min="0"></td>
        </tr>

        <tr>
            <td>在庫数</td>
            <td><input type="number" name="zaiko" value="${shohin.zaiko}" required min="0"></td>
        </tr>

        <!-- 商品画像選択 -->
        <tr>
            <td>商品画像</td>
            <td>

    <!-- 商品idなし、登録する：required or 商品idあり、変更する：requiredなし　で切り替え -->
    <c:choose>
        <c:when test="${not empty shohin.shohinId}">
                <input type="file" name="shohinImgPathFile" onchange="showFileName(this)">
        </c:when>
        <c:otherwise>
                <input type="file" name="shohinImgPathFile" onchange="showFileName(this)"required>
        </c:otherwise>
    </c:choose>                
             
                <!-- ファイル名を表示するテキストボックス -->
                <input type="text" id="shohinImgPath" name="shohinImgPath" value="${shohin.shohinImgPath}" readonly>
            </td>
        </tr>
    </table>

    <!-- 登録する or 変更する　で切り替え -->
    <c:choose>
        <c:when test="${not empty shohin.shohinId}">
            <input type="submit" value="変更する">
        </c:when>
        <c:otherwise>
            <input type="submit" value="登録する">
        </c:otherwise>
    </c:choose>
    
    </form>

    <c:choose>
        <c:when test="${not empty shohin.shohinId}">
            <form action="AdminDeleteServlet" method="post">
                <!-- 商品IDを隠しフィールドとして送信 -->
                <input type="hidden" name="shohinId" value="${shohin.shohinId}">
                <ul><li><button type="submit">削除する</button></li></ul>
            </form>
        </c:when>
    </c:choose>
</div>
    <ul>
    <li>
        <form action="AdminIchiranServlet" method="post">
            <input type="submit" value="登録商品一覧へ">
        </form>
    </li>
    </ul>
    <ul>
        <li><a href="AdminMenuServlet">メニューへ</a></li>
    </ul>
</section>
<%@ include file="footer.jsp" %>
</body>
</html>
