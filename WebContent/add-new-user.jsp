<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Sign in</title>
<link type="text/css" rel="stylesheet" href="css/add-new-user.css">
</head>
<body>
	<div id="wrapper">
		<div id="header">Sign in</div>
		<div id="container">
			<form action="MainController" method="POST">
				<input type="hidden" name="command" value="ADD-USER">
				<div>
					<label>User name</label>
					<div>
						<input type="text" name="userName">
					</div>
				</div>
				<div>
					<label>E-mail</label>
					<div>
						<input type="text" name="email">
					</div>
				</div>
				<div>
					<label>Password</label>
					<div>
						<input type="password" name="password">
					</div>
				</div>
				<div>
					<input type="submit" value="Sign In">
				</div>
			</form>
		</div>
	</div>
</body>
</html>