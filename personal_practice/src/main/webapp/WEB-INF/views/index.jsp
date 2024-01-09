<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="resources/jQuery.js"></script>
<link rel="stylesheet" type="text/css" href="resources/css/Index.css">
<link rel="stylesheet" type="text/css" href="resources/css/signUp.css">
<script type="text/javascript">
	$(document).ready(function(){
		
	    $("#downloadButton").hide();
		
		$("#btn").click(function(){
			$.ajax({
				url:"oct.item",
				method:"GET",
				data:{//url주소와 경로를 파라미터로 요청
					address: $("#address").val(),
				},
				success:function(result){//서버에 저장된 이미지 url, 저장된 이미지파일명목록
					console.log("요청 성공!");
					console.log(result);
					let imageURLs = result.imageURLs;
					let fileList = result.fileList;
					console.log(imageURLs);
					
					
					if(imageURLs == undefined || imageURLs.length == 0){//제대로 응답을 받지 못한경우 오류메세지 이미지출력
						if ($("#t1 img[src='resources/img/ei.JPG']").length) {
						    $("#t1 img[src='resources/img/ei.JPG']").closest('tr').remove();
						}//만약에 이미 에러 메시지가 있었다면 제거

						let tr = $("<tr></tr>");
					    let td = $("<td colspan='2'></td>");
					    let errorImage = $("<img>").attr("src", "resources/img/ei.JPG");
				
					    td.append(errorImage);
					    tr.append(td);
					    $("#t1").append(tr);
					}else{
						if ($("#t1 img[src='resources/img/ei.JPG']").length) {
						    $("#t1 img[src='resources/img/ei.JPG']").closest('tr').remove();
						}//만약에 이미 에러 메시지가 있었다면 제거
						
						if($("#login").val() == "y"){ //로그인 상태에서 저장소관련 버튼 생성
							  var button1 = $("<button>").text("선택한 이미지 저장소에 저장하기");
							  button1.addClass("btns");
							  button1.attr("id","ImageToStorage");
							  var newRow = $("<tr>");
							  var tdElement = $("<td>").attr("colspan", 3);

							  tdElement.append(button1);
							  newRow.append(tdElement);
							  $("#t1").append(newRow);
							  
							  
							  $("#ImageToStorage").click(function(){
								  var selected = $(".cBox:checked").map(function() {
							            return $(this).val();
							        }).get();
									console.log("ImageToStorage버튼 누름");
									 $.ajax({
										url:"oct.toStorage",
										method:"GET",
										data:{
											selectedOption: selected
											},
										traditional:true,
										success:function(result){
											console.log(result);
										},
										error:function(){
											alert("응답을 받아오지 못함!");
										}
									}); 
								});
						}
						
						let len = imageURLs.length;
						
						let thl = $("<tr><td><h3>전체 선택</h3></td></tr>");
						let checkAll = $("<td><input type='checkbox' id='selectAll'></td>");
						thl.append(checkAll);
						$("#t1").append(thl);
						
						for(let i=0; i<len ; i++){
							let rr = $("<tr></tr>");
							$("#t1").append(rr);
							let d1 = $("<td></td>");
							let d2 = $("<td class='buttonArea'></td>");
							let d3 = $("<td class='checkboxArea'></td>");
							let img = $("<img>").attr("src",imageURLs[i]['imgURL']);
							let download = $("<a>download</a>").attr("href","fileDownload.jsp?fileName="+fileList[i]);
							download.attr("target","_blank");
							download.attr("class","downloadAnchor")
							img.attr("class","imageTag")
							
							let cBox = $("<input></input>").attr("type","checkbox");
							cBox.addClass("cBox");
							cBox.attr("name","selected[]");
							cBox.attr("value",fileList[i]);
							d1.append(img);
							d2.append(download);
							d3.append(cBox);
							rr.append(d1);
							rr.append(d3);
							rr.append(d2);
							
		                    $("#downloadButton").show();

						}//for end
					
					}//else end
				}
			}); 
		});//btn.clcik	
		
		 $(document).on("change", "#selectAll", function() {
		        const isChecked = $(this).prop("checked");
		        $(".cBox").prop("checked", isChecked);
		    });

		    $(document).on("change", ".cBox", function() {
		        const allChecked = $(".cBox").length === $(".cBox:checked").length;
		        $("#selectAll").prop("checked", allChecked);
		    });
		
		$("#downloadButton").click(function(){
			var selected = $(".cBox:checked").map(function() {
	            return $(this).val();
	        }).get();
			
			console.log(selected);
			
			$.ajax({
				url:"oct.zip",
				method:"GET",
				data: { selectedOption: selected },
				traditional: true,
				success:function(result){
					$("#hiddenLink").attr("href","zipDownload.jsp");
					document.getElementById("hiddenLink").click();
				}
				
			});
		});//downloadButton
		
		
		
		
	});//document.ready
	
	 window.onbeforeunload = function() {
	    // 세션 종료를 위한 서버 요청
	    $.ajax({
	    	type:"GET",
	    	url:"oct.out",
	    	success:function(response){},
	    	error:function(error){}
	    });
	}; 
</script>
</head>
<body>
<table id="headerOfPage" >
	<tr>
		<td colspan="3"><a href="oct.home"><img id="titleImage" src="resources/img/pageTitle.JPG"></a></td>
	</tr>
	<tr>
		<td class="addressArea"><input id="address" placeholder="URL을 입력하세요."></td>
		<td><button id = "btn">squeeze</button></td>
		<td><button id="downloadButton">.zip download</button></td>
	</tr>
	<tr>
		<td colspan="2">
			<img id="warning" src="resources/img/warn.JPG">
		</td>
		<td>
			<jsp:include page="${loginPage }">
				<jsp:param name="parameterName" value="parameterValue" />
			</jsp:include>
		</td>
	</tr>
	<tr>
		<td colspan="3">
			<jsp:include page="${subPage }">
				<jsp:param name="parameterName" value="parameterValue" />
			</jsp:include>
		</td>
	</tr>
</table>


<a id="hiddenLink" style="display: none;" target="_blank"></a>

<input id = "login" type="hidden" value="${login }">
<table id="t1" border="1"></table>

</body>
</html>