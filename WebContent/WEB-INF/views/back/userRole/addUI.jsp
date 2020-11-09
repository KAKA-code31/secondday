<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/commons/basejs.jsp"%>
<title>添加角色</title>
<link href="${staticPath}/static/css/style.css" rel="stylesheet" type="text/css" />
<link href="${staticPath}/static/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<style type="text/css">
.roleline{padding-right: 20px;display: inline-block;line-height: 34px;}
</style>
</head>
<body style="min-width: 600px;">
	<div class="place">
		<span>位置：</span>
		<ul class="placeul">
			<li><a href="javascript:void(0);">首页</a></li>
			<li><a href="javascript:void(0);">系统管理</a></li>
			<li><a href="javascript:void(0);">系统管理员</a></li>
			<li><a href="javascript:void(0);">管理员添加</a></li>
		</ul>
	</div>
	<form id="addForm">
		<div class="formbody">
			<div class="formtitle">
				<span>管理员信息</span>
			</div>
			<ul class="forminfo">
				<li><label>部门名称</label>
					<select name="depart" id="depart" class="dfinput">
						<option value="">-请选择部门-</option>
						<c:forEach items="${departs }" var="depart">
							<option value="${depart.id }">${depart.yxmc }</option>
						</c:forEach>
					</select>
				</li>
				<li><label>管理员名称</label>
					<select name="userId" class="dfinput" id="userId">
						<option value="">--请选择--</option>
					</select>
				</li>
				<%-- <li><label>分配角色</label>
					<c:forEach items="${roles }" var="role">
						<span class="roleline"><input name="roleIds" type="checkbox" value="${role.id }"> ${role.name }</span>
					</c:forEach>
				</li> --%>
				<li><label>管理员类型</label>
					<span class="roleline"><input name="roleType" type="radio" checked="checked" value="1"> 班主任</span>
					<span class="roleline"><input name="roleType" type="radio" value="2"> 院管理员</span>
					<span class="roleline"><input name="roleType" type="radio" value="3"> 校管理员</span>
				</li>
			</ul>
		</div>
	</form>
</body>
<script type="text/javascript" src="${staticPath}/static/plugins/validate/jquery.validate.min.js"></script>
<script type="text/javascript" src="${staticPath}/static/plugins/validate/localization/messages_zh.min.js"></script>
<script type="text/javascript">
	$(function() {
		$("#depart").change(function(){
			var departId = $("#depart").val();
			$.ajax({  
			    type:'post',      
			    url:"${staticPath}/teacher/findTeacherNotAdmin",
			    data:{"id":departId},
			    cache:false,  
			    dataType:'json',  
			    success:function(data){
			    	var types = data.obj;
			    	if(types!=null && types.length>0){
			    		$("#userId").empty();//清空
			    		for(var i=0;i<types.length;i++){
			    			$("#userId").append("<option value='"+types[i].id+"'>"+types[i].xm+"</option>"); 
			    		}
			    	}else{
			    		$("#userId").empty();//清空
			    		$("#userId").append("<option value=''>--请选择--</option>"); 
			    	}
			    }
			});
		});
		$("#addForm").validate({
			rules : { depart : { required : true},userId : { required : true}},
			messages : { depart : { required : "请选择部门" },userId : { required : "请选择教师" }},
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