<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>添加教师项</title>
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
		<span class="c-gray en">&gt;</span> 教师管理
		<span class="c-gray en">&gt;</span> 教师项添加
	</nav>
	<article class="page-container">
	<form id="addForm" class="form form-horizontal">
			<div class="page-container_tittle">
				<i class="firm_det_tit_line"></i>教师信息
			</div>
			
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-2">
					<span class="c-red">*</span>管理员账号
				</label>
				<div class="formControls col-xs-8 col-sm-9">
					<input type="hidden" class="input-text"  placeholder="" name="userId" value="${user.encryptId }" >
					<input type="text" class="input-text"  placeholder="" value="${user.userId }" readonly="readonly" >
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-2">
					<span class="c-red">*</span>管理员名称
				</label>
				<div class="formControls col-xs-8 col-sm-9">
					<input type="text" class="input-text"  placeholder="" value="${user.name }" readonly="readonly" >
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-2">
					<span class="c-red">*</span>分配角色
				</label>
				<div class="formControls col-xs-8 col-sm-9">
					<c:forEach items="${user.roles }" var="role">
						<c:if test="${role.name!='学生' && role.name!='督导'}">
							<span class="roleline"><label><input name="roleIds" ${role.status==1?'checked="checked"':'' } type="checkbox" placeholder="" value="${role.encryptId() }"> ${role.name }</label></span>
						</c:if>
					</c:forEach>
				</div>
			</div>		
	</form>
	</article>
</body>
<script type="text/javascript" src="${staticPath}/static/plugins/validate/jquery.validate.min.js"></script>
<script type="text/javascript" src="${staticPath}/static/plugins/validate/localization/messages_zh.min.js"></script>
<script type="text/javascript">
	function getFormData() {
		/* var roleids = $("input[name='roleIds']:checked").length;
		if(roleids == 0){ var ro = top.layer.alert("管理员至少拥有一个角色！",function(){top.layer.close(ro);});return 0; } */
		var data = $("#addForm").serialize();
		return data;
	}
</script>
</html>