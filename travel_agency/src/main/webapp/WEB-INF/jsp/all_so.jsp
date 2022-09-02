<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<fmt:setLocale value="${sessionScope.locale}" />
<fmt:setBundle basename="localization.local" var="loc"></fmt:setBundle>

<fmt:message bundle="${loc}" key="all_so_page.so_title" var="so_title" />
<fmt:message bundle="${loc}" key="all_so_page.so_description"
	var="so_description" />
<fmt:message bundle="${loc}" key="all_so_page.so_discount"
	var="so_discount" />
<fmt:message bundle="${loc}" key="all_so_page.open_tours"
	var="open_tours" />
<fmt:message bundle="${loc}" key="all_so_page.open_tours_with_so"
	var="open_tours_with_so" />
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<header>
		<jsp:include page="header.jsp" />
	</header>

	<div class="main-div-tours">

		<table border="1">
			<tr>
				<td>${so_title}</td>
				<td>${so_description}</td>
				<td>${so_discount}</td>
			</tr>
			<c:forEach items="${sessionScope.allSO}" var="SO">
				<tr>
					<td>${SO.title}</td>
					<td>${SO.description}</td>
					<td>${SO.discount}</td>
					<td><form action="Controller" method="post">
							<input type="hidden" name="command" value="get_SO_tours" /> <input
								type="hidden" name="SOId" value="${SO.id}" /><input
								class="button-discount" type="submit" value="${open_tours}" />
						</form></td>
				</tr>
			</c:forEach>
		</table>

		<button class="button-discount"
			onclick="window.location.href = 'Controller?command=get_so_tours'">${open_tours_with_so}</button>
	</div>
	<footer>
		<jsp:include page="footer.jsp" />
	</footer>
</body>
</html>