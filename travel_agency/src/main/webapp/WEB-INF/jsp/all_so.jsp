<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<fmt:setLocale value="${sessionScope.locale}" />
<fmt:setBundle basename="localization.local" var="loc"></fmt:setBundle>
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
					<form action="Controller" method="post">
						<input type="hidden" name="command" value="get_SO_tours" /> <input
							type="hidden" name="SOId" value="${SO.id}" />
							<td>${SO.food}</td>
							<td>${SO.placement}</td>
							<td>${SO.realPrice}</td>
							<td><input class="button-discount" type="submit"
								value="${open_tours}" /></td>					
					</form>
					</tr>
					
				</c:forEach>
			</table>

	<footer>
		<jsp:include page="footer.jsp" />
	</footer>
</body>
</html>