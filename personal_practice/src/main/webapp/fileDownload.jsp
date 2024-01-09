<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

</head>
<body>
<jsp:useBean id="fileDownload" class="com.ppj.oct.file.FileDownload"/>
<c:set var="req" value="<%=request %>"/>
<c:set var="res" value="<%=response %>"/>
<% String path = application.getRealPath("fileUpDown"); %>
<c:set var="realPath" value="<%=path %>"/>

${fileDownload.fileDown(realPath,req,res) }
</body>
</html>