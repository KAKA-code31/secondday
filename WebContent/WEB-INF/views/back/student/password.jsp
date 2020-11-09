<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>修改密码</title>
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
<link href="${staticPath}/static/plugins/bootstrap/css/datepicker3.css"	rel="stylesheet" type="text/css" />
</head>
<body style="min-width: 600px;">
	<nav class="breadcrumb">
		<i class="Hui-iconfont" style="color: #000;">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span>个人设置
		<span class="c-gray en">&gt;</span> 密码管理
		<span class="c-gray en">&gt;</span> 密码修改
	</nav>
	<article class="page-container">
		<form class="form form-horizontal" id="editForm">
			<div class="page-container_tittle">
				<i class="firm_det_tit_line"></i>密码修改
			</div>
			<article class="page-container">
		<form id="editForm" class="form form-horizontal">
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-2">
					<span class="c-red">*</span>原密码
				</label>
				<div class="formControls col-xs-8 col-sm-9">
					<input name="oldPwd" type="password" class="input-text" id="oldPwd"/>
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-2">
					<span class="c-red">*</span>新密码
				</label>
				<div class="formControls col-xs-8 col-sm-9">
					<input  name="newPwd" type="password" id="newPwd" class="input-text"/>
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-2">
					<span class="c-red">*</span>确认新密码
				</label>
				<div class="formControls col-xs-8 col-sm-9">
					<input  name="newPwd2" type="password" id="newPwd2" class="input-text"/>
				</div>
			</div>
			<div class="row cl">
			<div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-3">
				<input class="btn btn-primary radius" type="button" id="sub" value="&nbsp;&nbsp;修改&nbsp;&nbsp;">
			</div>
		</div>
	</form>
	</article>
		</form>
	</article>
</body>
	<script type="text/javascript" 
		src="${staticPath}/static/plugins/validate/jquery.validate.min.js"></script>
	<script type="text/javascript" 
		src="${staticPath}/static/plugins/validate/localization/messages_zh.min.js"></script>
	<script type="text/javascript"
		src="${staticPath}/static/plugins/validate/localization/messages_zh.min.js"></script>
	<script type="text/javascript">
	$(document).ready(function(){
		$("#editForm").validate({
			rules : {
				"oldPwd" : {
					required : true,
					remote : rootPath + "/back/student/checkoldPwd"
				},
				"newPwd" : {
					required : true,
					minlength : 6
				},
				"newPwd2" : {
					required : true,
					equalTo : "#newPwd"
				}
			},
			messages : {
				"oldPwd" : {
					required : "请输入原密码",
					remote : "原密码输入不正确"
				},
				"newPwd" : {
					required : "请输入新密码",
					minlength : "新的密码长度不少于六个字符"
				},
				"newPwd2" : {
					required : "请输入确认密码",
					equalTo : "两次输入的密码不一致"
				}
			}
		});
        });
	
	$("#sub").click(function(){
		if($("#editForm").valid()){
		var npd = $("#newPwd").val();
		top.layer.confirm('将会执行修改，是否继续？', {
			btn: ['是','否'] //按钮
		},function(){
			$.ajax({  
			    type:'post',      
			    url: rootPath + "/back/student/password",
			    data:{newPwd:npd},
			    cache:false,  
			    dataType:'json',  
			    success:function(data){
			    	if(data.success==true){
			    		top.layer.alert("修改成功！", {icon: 1},function(){
			    			jumpUrl();
			    		});
			    	}else{
			    		top.layer.alert(data.msg, {icon: 2},function(){
			    			top.layer.closeAll();
			    			window.location.href = rootPath + "/back/student/password";
			    		});
			    	}
			    },
			    error:function(){
			    	top.layer.alert("修改失败，系统内部错误!", {icon: 5},function(){
			    		jumpUrl();
			    	});
			    }
			});
		})
	}
	});
	function jumpUrl(){
		top.layer.closeAll();
		window.location.href =  "${staticPath}/login/admin";
	}
	</script>
</html>
