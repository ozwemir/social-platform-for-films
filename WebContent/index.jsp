<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Log In</title>
<link type="text/css" rel="stylesheet" href="css/index.css">
<body>
	<div id="wrapper">
		<div id="container">
			<div>
				<b>Log In</b><br> <br>
			</div>
			<form action="MainController" method="POST">
				<input type="hidden" name="command" value="LOG-IN">
				<div>
					<label>E-Mail:</label>
				</div>
				<div>
					<input type="text" name="email">
				</div>
				<div>
					<label> Password: </label>
				</div>
				<div>
					<div>
						<input type="password" name="password">
					</div>
					<div>
						<c:if test="${not empty message}">
							<label id="warning-label"> Invalid user or password </label>
						</c:if>
					</div>
				</div>
				<div>
					<input type="submit" value="Log In">
				</div>
			</form>
			<div>
				<a href="add-new-user.jsp">Sign Up</a>
			</div>
		</div>
	</div>
</body>
</html>