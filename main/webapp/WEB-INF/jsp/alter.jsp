<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>BGroup注文者情報変更</title>
<%@ include file="cssInclude.jsp" %>
</head>
<body>
<%@ include file="header.jsp" %>
<section>
<h2>注文者情報変更</h2>
<div class="form-container">
<form action="AlterServlet" method="post">
<table border="1">
<tr>
    <td class="user-text">氏名</td>
    <td><input type="text" name="name" value="${account.name }" required></td>
</tr>
<tr>
    <td class="user-text">郵便番号</td>
    <td><input type="number" name="postnumber" value="${account.postnumber }" required></td>
</tr>
<tr>
    <td class="user-text">住所</td>
    <td><input type="text" name="address" value="${account.address }" required></td>
</tr>
<tr>
    <td class="user-text">電話番号</td>
    <td><input type="text" name="tell" value="${account.tell }" required></td>
</tr>
<tr>
    <td class="user-text">メールアドレス</td>
    <td><input type="text" name="mail" value="${account.mail }" required></td>
</tr>
<tr>
    <td class="user-text">支払方法</td>
	<td><select name="payId">
        <c:forEach var="method" items="${paymentMethods}">
            <option value="${method[0]}" ${method[0] == account.payId ? 'selected' : ''}>${method[1]}</option>
        </c:forEach>
    </select></td>
</tr>
    </table>
    <input type="submit" value="変更">
</form>
</div>
<ul><li><a href="CustomerServlet">戻る</a></li></ul>
</section>
	<%@ include file="footer.jsp" %>
</body>
</html>