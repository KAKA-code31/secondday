<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/commons/basejs.jsp"%>
<title>首页</title>
</head>
<body>
	<%-- 首页暂时不做<br/>
	<a href="${staticPath }/login/admin">教师登陆</a><br/>
	<a href="${staticPath }/login/examation">测试</a><br/> --%>
	<% 
	response.sendRedirect("login/admin");
	%>
</body>
</html>