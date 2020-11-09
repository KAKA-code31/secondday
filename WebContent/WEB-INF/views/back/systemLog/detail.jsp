<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>操作日志详情</title>
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
<body style="min-width: 300px;">
	<nav class="breadcrumb">
		<i class="Hui-iconfont" style="color: #000;">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span>系统管理
		<span class="c-gray en">&gt;</span> 操作日志
		<span class="c-gray en">&gt;</span> 日志详情
	</nav>
	<article class="page-container">
		<form id="editForm" class="form form-horizontal"> 
			<div class="page-container_tittle">
				<i class="firm_det_tit_line"></i>日志信息
			</div>
			<div class="row cl">
				<label class="form-label col-xs-2 col-sm-2">
					操作用户
				</label>
				<div class="formControls col-xs-10 col-sm-9">
					<input value="${systemLog.loginName }"  readonly="readonly" type="text" class="input-text" />
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-2 col-sm-2">
					用户IP地址
				</label>
				<div class="formControls col-xs-10 col-sm-9">
					<input value="${systemLog.optIp }"  readonly="readonly" type="text" class="input-text" />
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-2 col-sm-2">
					操作时间
				</label>
				<div class="formControls col-xs-10 col-sm-9">
					<input value="<fmt:formatDate value="${systemLog.optTime}" type="both" dateStyle="default" timeStyle="default" />"  readonly="readonly" class="input-text" />
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-2 col-sm-2">操作内容</label>
				<div class="formControls col-xs-10 col-sm-9">
					<textarea style="width: 471px;"  readonly="readonly" cols="" rows="" class="textarea" dragonfly="true">${systemLog.optContent }</textarea>
				</div>
			</div>
		</form>
	</article>
</body>
</html>