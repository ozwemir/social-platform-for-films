<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Home Page</title>
<link type="text/css" rel="stylesheet" href="css/home.css">
<!-- Java script part -->
<script type="text/javascript" src="jquery.js"></script>
<script>
	$(function() {
		$('#btn1').on('click', function() {
			$('#content').slideToggle(200);
		});
	});
</script>

</head>
<body>
	<%
	if (session.getAttribute("THEUSER") == null) {
		response.sendRedirect("index.jsp");
	}
	%>
	<header>
		<nav>
			<div class="header">
				<div class="btn1">
					<c:url var="tempLink" value="profile.jsp">
						<c:param name="userId" value="${THEUSER.id}"></c:param>
					</c:url>
					<a href="${tempLink}">${THEUSER.userName}</a>
				</div>
				<div>
					<button id="btn1" class="btn1">Write a review</button>
				</div>
				<div>
					<!-- Log out -->
					<form action="LogoutController" method="POST">
						<input type="submit" value="Log out">
					</form>
					<!-- Logout is ended -->
				</div>
			</div>
		</nav>
	</header>
	<main>
		<div id="wrapper">
			<div id="content-slide">
				<!-- Review part -->
				<div id="content">
					<form action="MainController" method="POST">
						<div>
							<input type="hidden" name="command" value="ADD-CONTENT">
							<input type="hidden" name="userId" value="${THEUSER.id}">
							<input type="hidden" name="userName" value="${THEUSER.userName}">
						</div>
						<div>
							<input type="text" name="title" placeholder="Movie title">
						</div>
						<div>
							<textarea name="comment" rows="15" cols="70"
								placeholder="What did you think of the movie?"></textarea>
						</div>
						<div>
							<input type="submit" value="Save">
						</div>
					</form>
				</div>
				<!-- end of the write a review part -->

				<div id="stream-content">
					<c:forEach var="content" items="${CONTENT.values()}">
						<div id="stream">
							<div>${content.userName}</div>
							<div class="stream-header">${content.header}</div>
							<div>${content.review }</div>
						</div>
					</c:forEach>
				</div>
			</div>
		</div>
	</main>
	<footer> </footer>

</body>
</html>