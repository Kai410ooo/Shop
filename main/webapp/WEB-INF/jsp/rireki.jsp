<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.LinkedHashMap"%>
<%@ page import="java.util.Collections"%>
<%@ page import="java.util.Comparator"%>
<%@ page import="model.Rireki"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>BGroup注文履歴</title>
<%@ include file="cssInclude.jsp"%>
<!-- 
<style>
body {
	font-family: Arial, sans-serif;
	line-height: 1.6;
	background-color: #f4f4f4;
	margin: 0;
	padding: 20px;
}

h1, h2, h3 {
	color: #333;
}

table {
	border-collapse: collapse;
	width: 100%;
	margin-bottom: 20px;
	background-color: #fff;
}

table, th, td {
	border: 1px solid #ddd;
}

th, td {
	padding: 10px;
	text-align: left;
}

th {
	background-color: #f8f8f8;
	font-weight: bold;
}

tr:nth-child(even) {
	background-color: #f2f2f2;
}

a {
	color: #3498db;
	text-decoration: none;
}

a:hover {
	text-decoration: underline;
}

.container {
	max-width: 1200px;
	margin: auto;
	background: #fff;
	padding: 20px;
	border-radius: 8px;
	box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}
</style>
 -->
</head>
<body>
	<%@ include file="header.jsp"%>
	<%@ include file="topButton.jsp" %>
<section>
	<h2>注文履歴</h2>
	<h2>
		ユーザーID:
		<%=request.getAttribute("userId")%>
	</h2>
	<div class="container">
		<%
		List<Rireki> rirekis = (List<Rireki>) request.getAttribute("rirekis");
		SimpleDateFormat fullDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		SimpleDateFormat dateOnlyFormat = new SimpleDateFormat("yyyy-MM-dd");
		Map<String, Map<String, List<Rireki>>> groupedRirekis = new LinkedHashMap<>();

		for (Rireki rirekiEntry : rirekis) {
			String dateKey = fullDateFormat.format(rirekiEntry.getBuyDay());
			String userKey = rirekiEntry.getUserName();
			groupedRirekis
			.computeIfAbsent(dateKey, k -> new LinkedHashMap<>())
			.computeIfAbsent(userKey, k -> new ArrayList<>())
			.add(rirekiEntry);
		}

		List<String> sortedDateKeys = new ArrayList<>(groupedRirekis.keySet());
		Collections.sort(sortedDateKeys, Collections.reverseOrder());

		for (String dateKey : sortedDateKeys) {
			Map<String, List<Rireki>> userGroupedRirekis = groupedRirekis.get(dateKey);
			for (Map.Entry<String, List<Rireki>> userEntry : userGroupedRirekis.entrySet()) {
				String userName = userEntry.getKey();
				List<Rireki> groupedRirekiList = userEntry.getValue();
				int totalSum = 0;
		%>
		<h4>
			注文日時:
			<%=fullDateFormat.format(fullDateFormat.parse(dateKey))%>
		</h4>
		<table class="rireki">
			<tr>
				<th>注文No</th>
				<th>注文日</th>
				<th>注文者</th>
				<th>商品名</th>
				<th>単価</th>
				<th>注文数量</th>
				<th>合計金額</th>
			</tr>
			<%
			int previousOrderNo = -1;
			int rowSpan = 0;
			int totalItems = groupedRirekiList.size();
			boolean orderNoPrinted = false;

			for (int i = 0; i < totalItems; i++) {
				Rireki groupedRirekiEntry = groupedRirekiList.get(i);
				totalSum += groupedRirekiEntry.getTotal();
				int currentOrderNo = groupedRirekiEntry.getOrderGroupId();

				if (currentOrderNo != previousOrderNo) {
					previousOrderNo = currentOrderNo;
					rowSpan = 1;
					for (int j = i + 1; j < totalItems; j++) {
				if (groupedRirekiList.get(j).getOrderGroupId() == currentOrderNo) {
					rowSpan++;
				} else {
					break;
				}
					}
					orderNoPrinted = false;
				}
			%>
			<tr>
				<%
				if (!orderNoPrinted) {
				%>
				<td rowspan="<%=rowSpan%>"><a
					href="RirekiInfoServlet?userId=<%=groupedRirekiEntry.getUserId()%>&orderGroupId=<%=groupedRirekiEntry.getOrderGroupId()%>">
						<%=groupedRirekiEntry.getOrderGroupId()%>
				</a></td>
				<td rowspan="<%=rowSpan%>"><%=dateOnlyFormat.format(groupedRirekiEntry.getBuyDay())%></td>
				<td rowspan="<%=rowSpan%>"><%=groupedRirekiEntry.getUserName()%></td>
				<%
				orderNoPrinted = true;
				}
				%>
				<td><%=groupedRirekiEntry.getName()%></td>
				<td><fmt:formatNumber value="<%=groupedRirekiEntry.getBuyPrice()%>" type="number" groupingUsed="true"/>円</td>
				<td><%=groupedRirekiEntry.getSuryo()%></td>
				<td><fmt:formatNumber value="<%=groupedRirekiEntry.getTotal()%>" type="number" groupingUsed="true"/>円</td>
			</tr>
			<%
			}
			%>
			<tr>
				<td colspan="6" style="text-align: right;"><strong>総合計金額:</strong></td>
				<td><strong><fmt:formatNumber value="<%=totalSum%>" type="number" groupingUsed="true"/>円</strong></td>
			</tr>
		</table>
		<%
		}
		}
		%>
	</div>
	<ul>
		<li><a href="MenuServlet">メニューへ</a></li>
	</ul>
</section>
	<%@ include file="footer.jsp"%>
</body>

</html>
