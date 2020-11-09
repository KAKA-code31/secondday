<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>添加选课</title>
<%@ include file="/commons/basejs.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<link rel="stylesheet" type="text/css" href="${staticPath }/static/h-ui/css/H-ui.min.css" />
<link rel="stylesheet" type="text/css" href="${staticPath }/static/lib/Hui-iconfont/1.0.8/iconfont.css" />
<link rel="stylesheet" type="text/css" href="${staticPath }/static/h-ui.admin/css/H-ui.admin.css" />
<link rel="stylesheet" type="text/css" href="${staticPath }/static/lib/icheck/icheck.css" />
<link rel="stylesheet" type="text/css" href="${staticPath }/static/h-ui.admin/skin/default/skin.css" id="skin" />
<link rel="stylesheet" type="text/css" href="${staticPath }/static/h-ui.admin/css/style.css" />
<link rel="stylesheet" type="text/css" href="${staticPath }/static/css/style.css" />
<link rel="stylesheet" type="text/css" href="${staticPath }/static/css/practice.css"/>
</head>
<body  style="min-width: 600px;">
<nav class="breadcrumb">
	<i class="Hui-iconfont" style="color: #000;">&#xe67f;</i> 首页 
	<span class="c-gray en">&gt;</span>课程实验
	<span class="c-gray en">&gt;</span>学生选课
	<span class="c-gray en">&gt;</span>重修选课
</nav>
<article class="page-container">
	<form class="form form-horizontal" id="selectCourseForm">
		<div class="page-container_tittle">
			<i class="firm_det_tit_line"></i>添加选课
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>重修课程名称：</label>
			<div class="formControls col-xs-6 col-sm-5">
				<select class="select"  id="courseId" name ="courseId">
								<option value="">--重修课程名称--</option>
								<c:forEach var="courseList" items="${courseList}">
									<option value="${courseList.id}">${courseList.code}-${courseList.name}</option>
								</c:forEach>
				</select>
			</div>
		</div><br/>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>学生学号：</label>
			<div class="formControls col-xs-6 col-sm-5">
				<input name="code" class="input-text" type="text" placeholder="请输入..." /> 
			</div>
		</div><br/>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>重修院系：</label>
			<div class="formControls col-xs-6 col-sm-5">
				<select class="select"  id="departmentId" name="departmentId">
								<option value="">--重修所属院系--</option>
								<c:forEach var="delist" items="${departmentList}">
									<option value="${delist.id}">${delist.name}</option>
								</c:forEach>
				</select>
			</div>
		</div><br/>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>重修专业：</label>
			<div class="formControls col-xs-6 col-sm-5">
				<select class="select" id="majorId" name = "majorId">
								<option value="">--重修所属专业--</option>
				</select>
			</div>
		</div><br/>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>重修班级：</label>
			<div class="formControls col-xs-6 col-sm-5">
				<select class="select" id="fakeclassId" name ="fakeclassId">
								<option value="">--重修所属班级--</option>
				</select>
			</div>
		</div>
	</form>
</article>

<!--_footer 作为公共模版分离出去-->
<script type="text/javascript" src="${staticPath }/static/lib/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript" src="${staticPath }/static/lib/layer/2.4/layer.js"></script> 
<script type="text/javascript" src="${staticPath }/static/lib/icheck/jquery.icheck.min.js"></script> 
<script type="text/javascript" src="${staticPath}/static/plugins/validate/jquery.validate.min.js"></script>
<script type="text/javascript" src="${staticPath}/static/plugins/validate/localization/messages_zh.min.js"></script>
<!--/_footer /作为公共模版分离出去-->

<!--请在下方写此页面业务相关的脚本-->
<script type="text/javascript">
$("#departmentId").blur(function(){
	var departmentId = document.getElementById("departmentId").value;
	$.ajax({
		url:"${staticPath }/back/report/seletMajor",
		type:"post",
		dataType : "json",
		data:{departmentId:departmentId},
		success:function(data){
			var types=data.obj;
			if(types!=null && types.length>0){
				$("#majorId").empty();
				for(var i=0;i<types.length;i++){
					$("#majorId").append(
						"<option selected='selected' value='"+types[i].id+"'>"
					+types[i].name+"</option>");
				}
			}else{
				$("#majorId").empty();//清空
				$("#majorId").append(
					"<option value=''>--重修所属专业--</option>"
				);
			}
		}
	});
});
$("#majorId").blur(function(){
	var majorId = document.getElementById("majorId").value;
	$.ajax({
		url:"${staticPath }/back/report/selectStudentClass",
		type:"post",
		dataType : "json",
		data:{majorId:majorId},
		success:function(data){
			var types=data.obj;
			if(types!=null && types.length>0){
				$("#fakeclassId").empty();
				for(var i=0;i<types.length;i++){
					$("#fakeclassId").append(
						"<option selected='selected' value='"+types[i].id+"'>"
					+types[i].year+'级'+types[i].name+"</option>");
				}
			}else{
				$("#fakeclassId").empty();//清空
				$("#fakeclassId").append(
					"<option value=''>--重修所属班级--</option>"
				);
			}
		}
	});
});
$(function(){
 	//校验
	 $("#selectCourseForm").validate({
		rules:{
			'courseId':{
				required:true
			},
			'code':{
				required:true
			},
			'departmentId':{
				required:true
			},
			'majorId':{
				required:true
			},
			'fakeclassId':{
				required:true
			}
		},
		messages:{
			'courseId':{
				required:"请选择课程！"
			},
			'code':{
				required:"请输入学号！"
			},'departmentId':{
				required:"请选择重修院系！"
			},
			'majorId':{
				required:"请选择重修专业！"
			},
			'fakeclassId':{
				required:"请选择重修班级！"
			}
		}
	});
});
	
function getFormData() {		
	if ($("#selectCourseForm").valid()) {
		var data = $("#selectCourseForm").serialize();
		return data;
	} else {
		return 0;
	}
}
	
</script>
</body>
</html>