<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
	$(document).ready(function(){
		
		$("#storageButton").click(function(){
			$.ajax({
				url:"oct.storage",
				method:"POST",
				data:{
					folderDirectory:$("#folderDirectory").val()
				},
				success:function(data){
					console.log(data);
				},
				error:function(){
					alert("오류발생");
				}	
			});
		});
	});
</script>
</head>
<body>

<h2> 환영합니다 ${user.getSqueeze_id() }</h2>
<table id="userT">
  <tr>
    <td colspan="2">
        <button id="storageButton">저장소</button>
    </td>
  </tr>
  <tr>
  	<td>
  	  <form action="oct.logout" method="POST">
		<button>로그아웃</button>  	  
  	  </form>
  	</td>
  	<td>
      <form action="oct.signOut" method="POST">
        <button>탈퇴</button>
      </form>
    </td>
  </tr>
</table>
</body>
</html>