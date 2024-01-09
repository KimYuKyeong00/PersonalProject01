<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="resources/css/signUp.css">

</head>
<body>
	    <div id="signUpHead" class="container">

	    
        <h3>회원가입 페이지</h3>
        <form action="oct.register" method="post">
	        <% String folder = application.getRealPath("UserFolders"); %>
	        <%=folder %>
			<input name = "folder" value="<%=folder %>" type="hidden">
			
            <table id="signUpT">
                <tr>
                    <td><label for="username">사용자 이름:</label></td>
                    <td><input type="text" name="squeeze_id" required></td>
                </tr>
                <tr>
                    <td><label for="password">비밀번호:</label></td>
                    <td><input type="password" name="squeeze_pw" required></td>
                </tr>
                <tr>
                    <td><label for="email">이메일:</label></td>
                    <td><input type="email" name="squeeze_email" required></td>
                </tr>
                <tr>
                    <td></td>
                    <td><input type="submit" value="가입하기"></td>
                </tr>
            </table>
        </form>
    </div>
</body>
</html>