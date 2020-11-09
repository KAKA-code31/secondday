<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>添加角色信息</title>
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
		<span class="c-gray en">&gt;</span> 角色管理
		<span class="c-gray en">&gt;</span> 角色添加
	</nav>
	<article class="page-container">
		<form class="form form-horizontal" id="addForm">
			<div class="page-container_tittle">
				<i class="firm_det_tit_line"></i>角色信息
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-2">
					<span class="c-red">*</span>角色名称
				</label>
				<div class="formControls col-xs-8 col-sm-9">
					<input type="text" class="input-text"  placeholder="" name="name" >
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-2">
					<span class="c-red">*</span>角色状态
				</label>
				<div class="formControls col-xs-8 col-sm-9">
					<span class="select-box">
						<select name="status" class="select">
							<option value="1">启用</option>
							<option value="0">禁用</option>
						</select>
					</span>
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-2">角色简介</label>
				<div class="formControls col-xs-8 col-sm-9">
					<textarea name="description" cols="" rows="" class="textarea" dragonfly="true"></textarea>
				</div>
			</div>
		</form>
	</article>
</body>
<!--_footer 作为公共模版分离出去-->
<script type="text/javascript" src="${staticPath }/static/lib/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript" src="${staticPath }/static/lib/layer/2.4/layer.js"></script> 
<script type="text/javascript" src="${staticPath }/static/lib/icheck/jquery.icheck.min.js"></script> 
<script type="text/javascript" src="${staticPath }/static/lib/jquery.validation/1.14.0/jquery.validate.js"></script> 
<script type="text/javascript" src="${staticPath }/static/lib/jquery.validation/1.14.0/validate-methods.js"></script> 
<script type="text/javascript" src="${staticPath }/static/lib/jquery.validation/1.14.0/messages_zh.js"></script> 
<script type="text/javascript" src="${staticPath }/static/h-ui/js/H-ui.js"></script> 
<script type="text/javascript" src="${staticPath }/static/h-ui.admin/js/H-ui.admin.js"></script> 
<script type="text/javascript" src="${staticPath }/static/lib/webuploader/0.1.5/webuploader.js"></script> 
<!--/_footer /作为公共模版分离出去-->
<script type="text/javascript">
	$(function() {
		$("#addForm").validate({
			rules : {
				name : {
					required : true
				},
				
			},
			messages : {
				name : {
					required : "请输入角色名称"
				},
			},
		});
	});
	function getFormData() {
		if ($("#addForm").valid()) {
			var data = $("#addForm").serialize();
			return data;
		} else {
			return 0;
		}
	}
</script>
</html>