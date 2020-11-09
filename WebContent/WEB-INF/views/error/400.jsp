<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/commons/basejs.jsp"%>
<title>质量监控管理系统</title>
<link href="${staticPath}/static/css/style.css" rel="stylesheet"
	type="text/css" />
<script src="${staticPath}/static/js/jquery-1.7.2.js"></script>
<script language="javascript">
	$(function() {
		$('.syserror').css({
			'position' : 'absolute',
			'left' : ($(window).width() - 490) / 2
		});
		$(window).resize(function() {
			$('.syserror').css({
				'position' : 'absolute',
				'left' : ($(window).width() - 490) / 2
			});
		})
	});
</script>
</head>
<body style="background: #FFF;min-width: 600px;">
	<div class="place">
		<span>位置：</span>
		<ul class="placeul">
			<li><a href="#">首页</a></li>
			<li><a href="#">400错误提示</a></li>
		</ul>
	</div>
	<div class="decrypterror">
		<h2>系统解析表单数据失败，请稍后重试！</h2>
		<div class="reindex">
			<a href="${staticPath}/back/home" target="_parent">返回首页</a>
		</div>
	</div>
</body>
</html>