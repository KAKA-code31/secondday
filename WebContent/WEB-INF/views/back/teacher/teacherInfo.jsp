<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>修改教师信息</title>
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
<body>
	<div class="cl pd-40" style="background-color: #5bacb6; position: relative;">
		<div class="change_password"
			style="color: #fff; position: absolute; top: 40px; left: 310px; cursor: pointer;">✎修改密码</div>
		<div class="editIds"
			style="color: #fff; position: absolute; top: 65px; left: 310px; cursor: pointer;">✎修改个人信息</div>
		<dl style="margin-left: 40px; color: #fff">
			<dt>
				<span class="f-18">${teacher.name}</span>
			</dt>
		</dl>
	</div>
	<div class="pd-20" >

		<table class="table">
			<tbody>
				<tr>
					<th class="text-r" width="80">编号：</th>
					<td>${teacher.userId }</td>
				</tr>
				<tr>
					<th class="text-r" width="80">姓名：</th>
					<td>${teacher.name }</td>
				</tr>
				<tr>
					<th class="text-r" width="80">性别：</th>
					<td>${teacher.sex == 1?"男":"女"}</td>
				</tr>
				<tr>
					<th class="text-r" width="80">手机号：</th>
					<td>${teacher.phoneno}</td>
				</tr>
				<tr>
					<th class="text-r" width="80">邮箱：</th>
					<td>${teacher.email}</td>
				</tr>
			</tbody>
		</table>

	</div>
</body>

<script type="text/javascript" src="${staticPath}/static/plugins/validate/jquery.validate.min.js"></script>
<script type="text/javascript" src="${staticPath}/static/plugins/validate/localization/messages_zh.min.js"></script>
<script type="text/javascript" src="${staticPath}/static/plugins/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${staticPath}/static/plugins/bootstrap/js/bootstrap-datepicker.js"></script>
<script type="text/javascript">
	function jumpUrl() {
		top.layer.closeAll();
		document.location.reload();
	}
	 $(".editIds").click(function() {
		 var id = $(this).attr("idVal");
		top.layer.open({
			title: false,
			type: 2,shadeClose: true,shade: 0.3,area: ['1000px', '90%'],
			content: rootPath+'/back/teacher/ppUI/'+id,
			btn: ['修改','取消'],yes:function(index, layero) {
				//提交iframe中的表单
				var frameName="layui-layer-iframe"+index;//获得layer层的名字
				var formdata = parent.frames[frameName].getFormData();
				if(formdata!=0){
					subEditForm(formdata);
				}
			}
		});
	});
	 
	function subEditForm(addFormData){
		 top.layer.closeAll(); 
		$.ajax({  
		    type:'post',      
		    url:rootPath+"/back/teacher/ppIds",  
		    data:addFormData,  
		    cache:false,  
		    dataType:'json',  
		    success:function(data){
		    	if(data.success==true){
		    		top.layer.alert("修改成功！", {icon: 1},function(){
		    			jumpUrl();
		    		});
		    	}else{
		    		top.layer.alert("修改失败！", {icon: 2},function(){
		    			jumpUrl();
		    		});
		    	}
		    },
		    error:function(){
		    	top.layer.alert("修改失败，系统内部错误!", {icon: 5},function(){
		    		jumpUrl();
		    	});
		    }
		});
	}
	 $(".change_password").click(function() {
		 var id = $(this).attr("idVal");
		top.layer.open({
			title: false,
			type: 2,shadeClose: true,shade: 0.3,area: ['1000px', '90%'],
			content: rootPath+'/back/teacher/passwordUI/'+id,
			btn: ['取消'],
			end: function () {
		        location.reload();
		      }
		});
	});
	 
	function subEditFormOfPassword(addFormData){
		 top.layer.closeAll(); 
		$.ajax({  
		    type:'post',      
		    url:rootPath+"/back/teacher/password",  
		    data:addFormData,  
		    cache:false,  
		    dataType:'json',  
		    success:function(data){
		    	if(data.success==true){
		    		top.layer.alert("修改成功！", {icon: 1},function(){
		    			jumpUrl();
		    		});
		    	}else{
		    		top.layer.alert("修改失败！", {icon: 2},function(){
		    			jumpUrl();
		    		});
		    	}
		    },
		    error:function(){
		    	top.layer.alert("修改失败，系统内部错误!", {icon: 5},function(){
		    		jumpUrl();
		    	});
		    }
		});
	}
	</script>
</html>
