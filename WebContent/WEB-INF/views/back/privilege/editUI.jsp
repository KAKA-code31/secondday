<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>修改权限信息</title>
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
<body style="min-width: 600px;">
	<nav class="breadcrumb">
		<i class="Hui-iconfont" style="color: #000;">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span>系统管理
		<span class="c-gray en">&gt;</span> 权限项管理
		<span class="c-gray en">&gt;</span> 权限项修改
	</nav>
	<article class="page-container">
		<form class="form form-horizontal" id="editForm">
			<div class="page-container_tittle">
				<i class="firm_det_tit_line"></i>权限信息
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-2">
					<span class="c-red">*</span>权限项名称</label>
					<input name="encryptId" value="${privilege.encryptId }"  type="hidden"/>
					<div class="formControls col-xs-8 col-sm-9">
						<input name="name" value="${privilege.name }" type="text" class="input-text" />
					</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-2">
					<span class="c-red">*</span>权限项URL</label>
					<div class="formControls col-xs-8 col-sm-9">
						<input name="url" value="${privilege.url }" type="text" class="input-text" />
					</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-2">
					权限项图标</label>
					<div class="formControls col-xs-8 col-sm-9">
						<input name="icon" value="${privilege.icon }" type="text" class="input-text" />
					</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-2">
					上级菜单
				</label>
				<div class="formControls col-xs-8 col-sm-9">
					<span class="select-box">
						<select name="parentIdStr" class="select">
							<option value="">--请选择--</option>
							<c:forEach items="${parents }" var="parent">
								<option value="${parent.encryptId }" ${parent.id == privilege.parentId ? 'selected="selected"' : ''}>${parent.name }</option>
							</c:forEach>
						</select>
					</span>
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-2">
					权限项类型
				</label>
				<div class="formControls col-xs-8 col-sm-9">
					<span class="select-box">
						<select name="privilegeType" class="select">
						<option value="0" >菜单</option>
						<option value="1" ${privilege.privilegeType == 1 ? 'selected="selected"' : ''}>按钮</option>
					</select>
					</span>
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-2">权限项简介</label>
				<div class="formControls col-xs-8 col-sm-9">
					<textarea name="description" cols="" rows="" class="textarea" dragonfly="true">${privilege.description }</textarea>
				</div>
			</div>
	</form>
</body>
<script type="text/javascript"
	src="${staticPath}/static/plugins/validate/jquery.validate.min.js"></script>
<script type="text/javascript"
	src="${staticPath}/static/plugins/validate/localization/messages_zh.min.js"></script>
<script type="text/javascript">

	$(function() {
		$("#editForm").validate({
			rules : {
				name : {
					required : true
				},
			},
			messages : {
				name : {
					required : "请输入权限项名称"
				},
			},
		});
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