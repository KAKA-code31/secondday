<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>修改学生所在班级</title>
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
		<span class="c-gray en">&gt;</span> 学生管理
		<span class="c-gray en">&gt;</span> 学生班级修改
	</nav>
	<article class="page-container">
		<form id="editClassForm" class="form form-horizontal">
			<div class="page-container_tittle">
				<i class="firm_det_tit_line"></i>学生信息
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-2">
					<span class="c-red">*</span>学号
				</label>
				<div class="formControls col-xs-8 col-sm-9">
					<input name="userId" type="text" class="input-text" id="studentUserId" value="${studentVo.userId}" readonly/>
					<input name="encryptId" type="text" style="display: none"
					class="input-text" id="studentId" value="${studentVo.encryptId}"/>
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-2">
					<span class="c-red">*</span>姓名
				</label>
				<div class="formControls col-xs-8 col-sm-9">
					<input  name="name" type="text" id="studentName" class="input-text" value="${studentVo.name}" readonly/>
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-2">
					<span class="c-red">*</span>院系
				</label>
				<div class="formControls col-xs-8 col-sm-9">
				<select class="select"  id="departmentId" name="departmentId">
					<option value="">--所属院系--</option>
					<c:forEach var="delist" items="${departmentList}">
						<option value="${delist.id}" ${delist.id==studentVo.getDepartment().getId()?'selected':''}>
						${delist.name}
						</option>
					</c:forEach>	
				</select>
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-2">
					<span class="c-red">*</span>专业
				</label>
				<div class="formControls col-xs-8 col-sm-9">
				<select class="select" name="majorId" id="majorId">
					<option value="">--所属专业--</option>
				</select>
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-2">
					<span class="c-red">*</span>班级
				</label>
				<div class="formControls col-xs-8 col-sm-9">
				<select class="select" name="classId" id="classId">
					<option value="">--所属班级--</option>
				</select>
				</div>
			</div>
	</form>
	</article>
</body>
<script type="text/javascript" src="${path }/lib/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript" src="${staticPath }/static/lib/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript" src="${staticPath }/static/lib/layer/2.4/layer.js"></script> 
<script type="text/javascript" src="${staticPath }/static/lib/icheck/jquery.icheck.min.js"></script> 
<script type="text/javascript" src="${staticPath}/static/plugins/validate/jquery.validate.min.js"></script>
<script type="text/javascript" src="${staticPath}/static/plugins/validate/localization/messages_zh.min.js"></script>
<script type="text/javascript">
	function getFormData() {
		if ($("#editClassForm").valid()) {
			var data = $("#editClassForm").serialize();
			data= decodeURIComponent(data,true);
			return data;
		} else {
			return 0;
		}
	}
/* var departmentEncryptId = "${studentVo.department.encryptId}" ;
if(departmentEncryptId != null && departmentEncryptId != ""){
	changeDepartmentEncryptId(departmentEncryptId);
} */
/* 院系--专业联动 */
var departmentId ="${studentVo.getDepartment().getId()}";
if(departmentId != null && departmentId!= ""){
	changeInfo(departmentId);
}
$("#departmentId").change(function(){
	departmentId=$("#departmentId").val();
	changeInfo(departmentId);
});
function changeInfo(departmentId){
	var majorId = "${studentVo.getMajor().getId()}";
	$("#majorId").empty();
	$("#majorId").append('<option value="">---请选择---</option>');
	$.ajax({
		url : "${staticPath }/back/studentClass/seletMajor",
		type: "post",
		data: {"departmentId" : departmentId},
		dataType: "json",
		success:function(data){
			var types=data.obj;
			if(types!=null && types.length>0){
				for(var i=0;i<types.length;i++){
					$("#majorId").append(
							'<option value="' + types[i].id + '" ' + (majorId ==types[i].id   ? 'selected=selected':'') + '>' + types[i].name + '</option>');
				}
			}
		}
		
	});
}
/* 专业--班级联动 */
var majorId = "${studentVo.getMajor().getId()}";
if(majorId != null && majorId!= ""){
	changeMajorInfo(majorId);
}
$("#majorId").change(function(){
	majorId=$("#majorId").val();
	changeMajorInfo(majorId);
});
function changeMajorInfo(majorId){
	var classId = "${studentVo.getStudentClass().getId()}";
	$("#classId").empty();
	$("#classId").append('<option value="">---请选择---</option>');
	$.ajax({
		url : "${staticPath }/back/studentClass/seletClass",
		type: "post",
		data: {"majorId" : majorId},
		dataType: "json",
		success:function(data){
			var types=data.obj;
			if(types!=null && types.length>0){
				for(var i=0;i<types.length;i++){
					$("#classId").append(
							'<option value="' + types[i].id + '" ' + (classId ==types[i].id   ? 'selected=selected':'') + '>' + types[i].name + '</option>');
				}
			}
			classId=$("#classId").val();
			
		}
	});
}			
</script>

</html>