<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>make this title personal</title>
<link type="text/css" rel="stylesheet" href="css/profile.css">
</head>
<body>

	<%
		// this code is generated for back arrow relogin issue
	response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
	response.setHeader("Pragma", "no-cache");
	response.setHeader("Expires", "0");

	//if home.jsp url is typed and the session is null then redirect to index.jsp
	if (session.getAttribute("THEUSER") == null) {
		response.sendRedirect("index.jsp");
	}
	%>
	<div id="stream-content">
		<div class="stream-header start">My posts</div>
		<c:forEach var="content" items="${CONTENT.values()}">
			<c:if test="${THEUSER.id==content.userId}">
				<div id="stream">
					<div class="stream-header">${content.header}</div>
					<div>${content.review }</div>
				</div>
			</c:if>
		</c:forEach>
	</div>
	<footer>
		<div>
			<div id ="footer"><jsp:include page="footer.jsp"></jsp:include></div>
		</div>
	</footer>

</body>
</html>