<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title></title>
<%@ include file="/commons/basejs.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport"
	content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<link rel="stylesheet" type="text/css" href="${staticPath }/static/h-ui/css/H-ui.min.css" />
<link rel="stylesheet" type="text/css" href="${staticPath }/static/h-ui.admin/css/H-ui.admin.css" />
<link rel="stylesheet" type="text/css" href="${staticPath }/static/lib/Hui-iconfont/1.0.8/iconfont.css" />
<link rel="stylesheet" type="text/css" href="${staticPath }/static/lib/icheck/icheck.css" />
<link rel="stylesheet" type="text/css" href="${staticPath }/static/h-ui.admin/skin/default/skin.css" id="skin" />
<link rel="stylesheet" type="text/css" href="${staticPath }/static/h-ui.admin/css/style.css" />
<link rel="stylesheet" type="text/css" href="${staticPath }/static/css/style.css" />

<style type="text/css">
.table-sort tr td {
	text-align: center;
}
</style>
</head>
<body>
	<nav class="breadcrumb">
	<i class="Hui-iconfont" style="color: #000;">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span>信息管理
	<span class="c-gray en">&gt;</span> 学生信息
	<a class="btn btn-success radius r"
			style="line-height: 1.6em; margin-top: 3px"
			href="javascript:location.replace(location.href);" title="刷新">
			<i class="Hui-iconfont">&#xe68f;</i>
	</a>
	</nav>
	<div class="page-container">
		<div class="cl pd-5 bg-1 bk-gray mt-20">
			<span class="l"> 
				<shiro:hasPermission name="/back/student/add">
					<a class="btn btn-primary radius" data-title="添加" id="add"
						href="javascript:;"><i class="Hui-iconfont">&#xe600;</i> 添加</a>
				</shiro:hasPermission>
				<shiro:hasPermission name="/back/student/deleteIDs">
					<a data-href="javascript:;" id="delete" class="btn btn-danger radius">
						<i class="Hui-iconfont">&#xe6e2;</i> 批量删除
					</a>
				</shiro:hasPermission>
				<shiro:hasPermission name="/back/student/import">
					<a data-href="javascript:;" id="upload" class="btn btn-success radius">
						<i class="Hui-iconfont">&#xe645;</i> 导入
					</a>
				</shiro:hasPermission>
			</span>
		</div>
		<br>
		<div class="mt_right" style="float: right;">
			<form id="pageForm" action="${staticPath }/back/student/manager" method="post"  >
				<label>院系：</label>
				<select  class="mt_select" id="enDepartId" name ="enDepartId">
					<option value="">---请选择---</option>
					<c:forEach var="depart" items="${departs}">
						<option  value="${depart.encryptId}" ${depart.encryptId == enDepartId ? "selected":""}>${depart.name}</option>
					</c:forEach>
				</select>
				<label>专业：</label>
					<select  name="enMajorId" class="mt_select" id="enMajorId">
					</select>
				<label>班级：</label>
					<select  name="enClassId" class="mt_select" id="enClassId"></select>
				<input type="hidden" majorId="${enMajorId }" classId="${enClassId }" id="value_content" />	
				<input 	name="userId" value="${student.userId}"  class="mt_select mt_input" type="text"  placeholder="请输入学号"/>
				<input 	name="name" value="${student.name}"  class="mt_select mt_input" type="text"  placeholder="请输入姓名"/>
				<select class="mt_select" name="sex">
					<option value="">--性别--</option>
					<option value="1" ${student.sex==1?'selected="selected"':'' }>男</option>
					<option value="2" ${student.sex==2?'selected="selected"':'' }>女</option>
				</select>
				<button class="mt_button" id="submitButton"><i class="Hui-iconfont">&#xe665;</i></button>
			   <div class="clearfix"></div>
			</form>
		</div>
		<br>
		<div class="mt-20">
		<form id="pageForm" action="${staticPath}/back/student/manager"
			method="post"></form>
		<form id="studentForm">
			<table
				class="table table-border table-bordered table-bg table-hover table-sort">
				<thead>
					<tr class="text-c">
					<th style="width:20px;"><input type="checkbox" onclick='chkall("studentForm",this)' /></th>
						<th>学号</th>
						<th>姓名</th>
						<th>学校</th>
						<th>院系</th>
						<th>专业</th>
						<th>班级</th>
						<th style="width:40px;">性别</th>
						<th>邮箱</th>
						<th>号码</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
				<c:if test="${studentList == null or studentList.size() == 0 }">
						<tr>
							<td colspan="10">没有记录</td>
						</tr>
					</c:if>
					<c:if test="${studentList != null and studentList.size() > 0 }">
						<c:forEach items="${studentList }" var="student">
							<tr class="text-c">
								<td><input type="checkbox" value="${student.encryptId }" class="ids" name=""></td>
								<td>${student.userId }</td>
								<td>${student.name }</td>
								<td>${student.schoolName}</td>
								<td>${student.departName }</td>
								<td>${student.majorName }</td>
								<td>${student.studentClass.name }</td>
								<td>
									<c:if test="${student.sex == 1}">男</c:if>
									<c:if test="${student.sex == 2}">女</c:if>
								</td>
								<td>${student.email }</td>
								<td>${student.phoneno }</td>
								<td class="f-14 td-manage">
									<shiro:hasPermission name="/back/student/delete">
										<a href="javascript:void(0);" idVal="${student.encryptId}" class="tablelink deleteOne" title="删除"><i class="icon Hui-iconfont" style="color: #000;">&#xe609;</i></a>
									</shiro:hasPermission>
									<shiro:hasPermission name="/back/student/edit">
										<a href="javascript:void(0);" idVal="${student.encryptId}" class="tablelink edit" title="修改"><i class="icon Hui-iconfont" style="color: #000;">&#xe6df;</i></a>
									</shiro:hasPermission>
									<shiro:hasPermission name="/back/student/edit">
										<a href="javascript:void(0);" idVal="${student.encryptId}" class="tablelink resetPassword" title="重置密码"><i class="icon Hui-iconfont" style="color: #000;">&#xe60e;</i></a>
									</shiro:hasPermission>
									<shiro:hasPermission name="/back/student/edit">
										<a href="javascript:void(0);" idVal="${student.encryptId}" class="tablelink editClass" title="调班"><i class="icon Hui-iconfont" style="color:  #000;">&#xe61d;</i></a>
									</shiro:hasPermission>
								</td>
							</tr>
						</c:forEach>
					</c:if>
				</tbody>
			</table>
			</form>
		</div>
		<jsp:include page="../../common/page.jsp" />
	</div>
<script type="text/javascript" src="${staticPath }/static/js/talent.js"></script>
<script type="text/javascript" src="${staticPath }/static/js/departUtil.js"></script>
<script type="text/javascript">
/* 院系--专业联动 */
var departmentId ="${departmentId}";
if(departmentId != null && departmentId!= ""){
	changeInfo(departmentId);
}
$("#departmentId").change(function(){
	departmentId=$("#departmentId").val();
	changeInfo(departmentId);
});
function changeInfo(departmentId){
	var majorId = "${majorId}";
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
var majorId = "${majorId}";
var classId = "${classId}";
if(majorId != null && majorId!= ""){
	changeMajorInfo(majorId);
}
$("#majorId").change(function(){
	majorId=$("#majorId").val();
	changeMajorInfo(majorId);
});
function changeMajorInfo(majorId){
	var classId = "${classId}";
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
function jumpUrl(){
	top.layer.closeAll();
	var i = ${currentPage};
	var t = ${totalPage};
	GoToPage(i,t);
	window.location.href=rootPath+"/back/student/manager";
}
$(".deleteOne").click(function(){
	var id = $(this).attr("idVal");
	top.layer.confirm('将会删除该学生的所有信息，是否继续？', {
		btn: ['是','否'] //按钮
	},function(){
		$.ajax({ type:'get', url:rootPath+"/back/student/delete/"+id,  
		    cache:false,dataType:'json',success:function(data){
		    	if(data.success==true){ top.layer.alert("删除成功！", {icon: 1},function(){jumpUrl();});
		    	}else{ top.layer.alert(data.msg, {icon: 2},function(){jumpUrl();}); }
		    },
		    error:function(){ top.layer.alert("删除失败，系统内部错误!", {icon: 5},function(){jumpUrl();}); }
		});
	})
});

$(".resetPassword").click(function(){
	var id = $(this).attr("idVal");
	top.layer.confirm('将重置该学生的登录密码且不可恢复，是否继续？', {
		btn: ['是','否'] //按钮
	},function(){
		$.ajax({ type:'get', url:rootPath+"/back/student/resetUI/"+id,  
		    cache:false,dataType:'json',success:function(data){
		    	if(data.success==true){ top.layer.alert(data.msg, {icon: 1},function(){jumpUrl();});
		    	}else{ top.layer.alert(data.msg, {icon: 2},function(){jumpUrl();}); }
		    },
		    error:function(){ top.layer.alert(data.msg, {icon: 5},function(){jumpUrl();}); }
		});
	})
});

$("#delete").click(function() {
	var idChecks = $(".ids:checked");
	if (idChecks.length == 0) {
		top.layer.alert("至少选择一项！");
		return false;
	}
	top.layer.confirm('将会删除所选学生的所有信息，是否继续？', {
		btn: ['是','否'] //按钮
	},function(){
			var id = "";
			for (var i = 0; i < idChecks.length; i++) { id += idChecks[i].value + ","; }
			id = id.substring(0, id.length - 1);
			$.ajax({ type:'post', url:rootPath+"/back/student/deleteIDs",  
			    data:{"ids":id}, cache:false, dataType:'json',success:function(data){
			    	if (data.success==true) { top.layer.alert("删除成功！", {icon: 1},function(){ jumpUrl(); });
			    	} else { top.layer.alert(data.msg, {icon: 2},function(){ jumpUrl(); }); }
			    },
			    error:function(){ top.layer.alert("删除失败，系统内部错误!", {icon: 5},function(){ jumpUrl(); }); }
			});
	});
});
$("#add").click(function() {
	top.layer.open({
		title: false,
		type: 2,shadeClose: true,shade: 0.3,area: ['800px', '90%'],
		content: rootPath+'/back/student/addUI',btn: ['保存','取消'],
		yes:function(index, layero){
			//提交iframe中的表单
			var frameName="layui-layer-iframe"+index;//获得layer层的名字
			var formdata = parent.frames[frameName].getFormData();
			if(formdata!=0){
				subAddForm(formdata);
			}
		}
	});
});
function subAddForm(addFormData){
	top.layer.closeAll();
	$.ajax({  
		type:'post',url:rootPath+"/back/student/add",data:addFormData,  
	    cache:false,dataType:'json', success:function(data){
	    	if(data.success==true){top.layer.alert("保存成功！", {icon: 1},function(){jumpUrl();});
	    	}else{top.layer.alert(data.msg, {icon: 2},function(){jumpUrl();});}
	    }, error:function(){top.layer.alert("保存失败，系统内部错误!", {icon: 5},function(){jumpUrl();});}
	});
}
$(".edit").click(function() {
	var id = $(this).attr("idVal");
	top.layer.open({
		title: false,
		type: 2,shadeClose: true,shade: 0.3,area: ['800px', '90%'],
		content: rootPath+'/back/student/editUI/'+id,
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
$(".editClass").click(function() {
	var id = $(this).attr("idVal");
	top.layer.open({
		title: false,
		type: 2,shadeClose: true,shade: 0.3,area: ['800px', '90%'],
		content: rootPath+'/back/student/changClassUI/'+id,
		btn: ['修改','取消'],yes:function(index, layero) {
			//提交iframe中的表单
			var frameName="layui-layer-iframe"+index;//获得layer层的名字
			var formdata = parent.frames[frameName].getFormData();
			if(formdata!=0){
				subEditClassForm(formdata);
			}
		}
	});
});
function subEditClassForm(addFormData){
	top.layer.closeAll();

	$.ajax({  
	    type:'post',url:rootPath+"/back/student/changClass",data:addFormData,  
	    cache:false,dataType:'json',  
	    success:function(data){
	    	if(data.success==true){top.layer.alert("修改成功！", {icon: 1},function(){jumpUrl();});
	    	}else{top.layer.alert("修改失败！", {icon: 2},function(){jumpUrl();});}
	    },
	    error:function(){
	    	top.layer.alert("修改失败，系统内部错误!", {icon: 5},function(){jumpUrl();});
	    }
	});
}
function subEditForm(addFormData){
	top.layer.closeAll();

	$.ajax({  
	    type:'post',url:rootPath+"/back/student/edit",data:addFormData,  
	    cache:false,dataType:'json',  
	    success:function(data){
	    	if(data.success==true){top.layer.alert("修改成功！", {icon: 1},function(){jumpUrl();});
	    	}else{top.layer.alert("修改失败！", {icon: 2},function(){jumpUrl();});}
	    },
	    error:function(){
	    	top.layer.alert("修改失败，系统内部错误!", {icon: 5},function(){jumpUrl();});
	    }
	});
}

$('#upload').click(function(){
	 location.href= rootPath+"/back/student/studentUploadUI";
});

$('.tablelist tbody tr:odd').addClass('odd');
</script>
</body>
</html>