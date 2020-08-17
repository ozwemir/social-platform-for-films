<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

	<div id="wrapper">
		<div id="content">
			<form action="MainController" method="POST">
				<div>
					<input type="hidden" name="command" value="ADD-CONTENT"> <input
						type="hidden" name="userId" value="${THEUSER.id}"> <input
						type="hidden" name="userName" value="${THEUSER.userName}">
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
	</div>
</body>
</html>