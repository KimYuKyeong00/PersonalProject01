<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

</head>
<body>
<table id="loginT" border="1">
	<tr>
		<td>
			<div> <h2>Login</h2> </div>
			<div>&nbsp;</div>
			<form action="oct.login" method="post">
			    <label for="username">ID</label><br>
			    <input type="text" name="squeeze_id" required><br><br>
			    <label for="password">Password</label><br>
			    <input type="password"  name="squeeze_pw" required><br>
			    <div>&nbsp;</div>
			    <input type="submit" value="로그인">
			</form>
			<form action="oct.signUp">
			<button>회원가입</button>
			</form>
			
		</td>
	</tr>
</table>
</body>
</html>