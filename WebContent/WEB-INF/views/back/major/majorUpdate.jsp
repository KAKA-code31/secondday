<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>修改专业信息</title>
<%@ include file="/commons/basejs.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<!--[if lt IE 9]>
<script type="text/javascript" src="${path }/lib/html5.js"></script>
<script type="text/javascript" src="${path }/lib/respond.min.js"></script>
<script type="text/javascript" src="${path }/lib/PIE_IE678.js"></script>
<![endif]-->
<link rel="stylesheet" type="text/css" href="${staticPath }/static/h-ui/css/H-ui.min.css" />
<link rel="stylesheet" type="text/css" href="${staticPath }/static/h-ui.admin/css/H-ui.admin.css" />
<link rel="stylesheet" type="text/css" href="${staticPath }/static/lib/Hui-iconfont/1.0.8/iconfont.css" />
<link rel="stylesheet" type="text/css" href="${staticPath }/static/lib/icheck/icheck.css" />
<link rel="stylesheet" type="text/css" href="${staticPath }/static/h-ui.admin/skin/default/skin.css" id="skin" />
<link rel="stylesheet" type="text/css" href="${staticPath }/static/h-ui.admin/css/style.css" />
<link rel="stylesheet" type="text/css" href="${staticPath }/static/css/style.css" />
<link rel="stylesheet" type="text/css" href="${staticPath }/static/css/practice.css"/>
<!--[if IE 6]>
<script type="text/javascript" src="http://lib.h-ui.net/DD_belatedPNG_0.0.8a-min.js" ></script>
<script>DD_belatedPNG.fix('*');</script>
<![endif]-->
</head>
<body>
<nav class="breadcrumb">
	<i class="Hui-iconfont" style="color: #000;">&#xe67f;</i> 首页 
	<span class="c-gray en">&gt;</span>信息管理
	<span class="c-gray en">&gt;</span>专业管理
	<span class="c-gray en">&gt;</span>修改专业
</nav>
<article class="page-container">
	<form class="form form-horizontal" method="post" action="${back}/major/majorUpdate" id="majorForm">
		<input type="hidden" name="id" value="${major.id }"/>
		<div class="page-container_tittle">
			<i class="firm_det_tit_line"></i>专业信息
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>院系名称：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<select class="select"
							name="department.id">
								<option value="">--所属院系--</option>
								<c:forEach var="list" items="${departmentList}">
									<option value="${list.id}"
										${list.id==major.department.id?'selected="selected"':'' }>${list.name}</option>
								</c:forEach>
				</select>
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>专业编码：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text"  placeholder="" id="code" name="code" value="${major.code}">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>专业名称：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text"  placeholder="" id="name" name="name" value="${major.name}">
			</div>
		</div>
	</form>
</article>

<!--_footer 作为公共模版分离出去-->
<script type="text/javascript" src="${path }/lib/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript" src="${staticPath }/static/lib/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript" src="${staticPath }/static/lib/layer/2.4/layer.js"></script> 
<script type="text/javascript" src="${staticPath }/static/lib/icheck/jquery.icheck.min.js"></script> 
<script type="text/javascript" src="${staticPath}/static/plugins/validate/jquery.validate.min.js"></script>
<script type="text/javascript" src="${staticPath}/static/plugins/validate/localization/messages_zh.min.js"></script>
<!--/_footer /作为公共模版分离出去-->

<!--请在下方写此页面业务相关的脚本-->
<script type="text/javascript">
$(function() {
	$("#majorForm").validate({
		rules : {
			code : {
				required : true
			},
			name : {
				required : true
			},
		},
		messages : {
			code : {
				required : "请输入编号"
			},
			name : {
				required : "请输入名称"
			},
		},
	});
});
function getFormData() {
	if ($("#majorForm").valid()) {
		var data = $("#majorForm").serialize();
		return data;
	} else {
		return 0;
	}
}
$(function(){
	
	$("#cancel").click(function(){
		parent.layer.close(parent.index);
	});
	
});
</script>
<!--/请在上方写此页面业务相关的脚本-->
</body>
</html>