<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>编辑课程项</title>
<%@ include file="/commons/basejs.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<link rel="stylesheet" type="text/css" href="${staticPath }/static/lib/webuploader/0.1.5/webuploader.css"/>
<link rel="stylesheet" type="text/css" href="${staticPath }/static/h-ui/css/H-ui.min.css" />
<link rel="stylesheet" type="text/css" href="${staticPath }/static/h-ui.admin/css/H-ui.admin.css" />
<link rel="stylesheet" type="text/css" href="${staticPath }/static/lib/Hui-iconfont/1.0.8/iconfont.css" />
<link rel="stylesheet" type="text/css" href="${staticPath }/static/lib/icheck/icheck.css" />
<link rel="stylesheet" type="text/css" href="${staticPath }/static/h-ui.admin/skin/default/skin.css" id="skin" />
<link rel="stylesheet" type="text/css" href="${staticPath }/static/h-ui.admin/css/style.css" />
<link rel="stylesheet" type="text/css" href="${staticPath }/static/css/style.css" />
<link rel="stylesheet" type="text/css" href="${staticPath }/static/css/practice.css"/>
</head>
<body style="min-width: 600px;">
	<nav class="breadcrumb">
		<i class="Hui-iconfont" style="color: #000;">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span>信息管理
		<span class="c-gray en">&gt;</span> 课程管理
		<span class="c-gray en">&gt;</span> 课程项修改
	</nav>
	<article class="page-container">
	<form id="editForm" class="form form-horizontal">
			<div class="page-container_tittle">
				<i class="firm_det_tit_line"></i>课程信息
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-2">
					<span class="c-red">*</span>课程编号
				</label>
				<div class="formControls col-xs-8 col-sm-9">
					<input name="encryptId" value="${course.encryptId }"  type="hidden"/>
					<input type="text" class="input-text"  placeholder="" name="courseNumber" value="${course.courseNumber }" >
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-2">
					<span class="c-red">*</span>课程名
				</label>
				<div class="formControls col-xs-8 col-sm-9">
					<input type="text" class="input-text"  placeholder="" name="name" value="${course.name }" >
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-2">
					<span class="c-red">*</span>课程简介
				</label>
				<div class="formControls col-xs-8 col-sm-9">
					<input type="text" class="input-text"  placeholder="" name="description" value="${course.description }" >
				</div>
			</div>
	</form>
	</article>
</body>
<script type="text/javascript" src="${staticPath}/static/plugins/validate/jquery.validate.min.js"></script>
<script type="text/javascript" src="${staticPath}/static/plugins/validate/localization/messages_zh.min.js"></script>
<script type="text/javascript" src="${staticPath}/static/plugins/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${staticPath}/static/plugins/bootstrap/js/bootstrap-datepicker.js"></script>
<script type="text/javascript">

		$("#editForm").validate({
			rules : {
				'name' : {
					required : true
				},
				
			},
			messages : {
				'name' : {
					required : "请输入课程名称"
				},
			}
		});

	function getFormData() {
		if ($("#editForm").valid()) {
			var data = $("#editForm").serialize();
			return data;
		} else {
			return 0;
		}
	}
</script>
</html>