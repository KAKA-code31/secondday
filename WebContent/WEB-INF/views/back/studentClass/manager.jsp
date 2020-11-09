<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title></title>
<%@ include file="/commons/basejs.jsp"%>
<%@ page import="java.util.Calendar"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport"
	content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<link rel="stylesheet" type="text/css"
	href="${staticPath }/static/h-ui/css/H-ui.min.css" />
<link rel="stylesheet" type="text/css"
	href="${staticPath }/static/h-ui.admin/css/H-ui.admin.css" />
<link rel="stylesheet" type="text/css"
	href="${staticPath }/static/lib/Hui-iconfont/1.0.8/iconfont.css" />
<link rel="stylesheet" type="text/css"
	href="${staticPath }/static/lib/icheck/icheck.css" />
<link rel="stylesheet" type="text/css"
	href="${staticPath }/static/h-ui.admin/skin/default/skin.css" id="skin" />
<link rel="stylesheet" type="text/css"
	href="${staticPath }/static/h-ui.admin/css/style.css" />
<link rel="stylesheet" type="text/css"
	href="${staticPath }/static/css/style.css" />
<style type="text/css">
.table-sort tr td {
	text-align: center;
}
</style>
</head>
<body>
	<nav class="breadcrumb">
	<i class="Hui-iconfont" style="color: #000;">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span>信息管理
	<span class="c-gray en">&gt;</span> 班级信息
	<a class="btn btn-success radius r"
			style="line-height: 1.6em; margin-top: 3px"
			href="javascript:location.replace(location.href);" title="刷新">
			<i class="Hui-iconfont">&#xe68f;</i>
	</a>
	</nav>
	<div class="page-container">
		<div class="cl pd-5 bg-1 bk-gray mt-20">
			<span class="l"> <shiro:hasPermission
					name="/back/studentClass/studentClassAdd">
					<a class="btn btn-primary radius" data-title="添加" id="add"
						href="javascript:;"><i class="Hui-iconfont">&#xe600;</i> 添加班级</a>
				</shiro:hasPermission> <shiro:hasPermission name="/back/studentClass/studentClassDeleteIDs">
					<a data-href="javascript:;" id="delete"
						class="btn btn-danger radius"><i class="Hui-iconfont">&#xe6e2;</i>
						批量删除</a>
				</shiro:hasPermission>
			</span>
		</div>
		<br>
		<div class="mt_right" style="float: right;">
			<form action="${staticPath}/back/studentClass/manager" id="pageForm"
				style="float: right;" method="post">
				
				<label class="mt_label"></span>入学年份：</label>
					<select class="mt_select" name="grade" id="grade" >  
						<option value="">--请选择--</option>  
						<% Calendar cal = Calendar.getInstance();  
						int year = cal.get(Calendar.YEAR);  
						for(int i=0; i<=4; i++){   
						    String str = String.valueOf(year);  
						    if(str.equals(request.getParameter("grade"))){  
						%>         
						        <option selected="selected"><%=str%></option>  
						<%} else{%>  
						    <option><%=str%></option>  
						<%}  year = year - 1;  
						}%>    
					</select> 
					<label class="mt_label"></span>学校：</label>
					<select class="mt_select"    id="schoolId" name="schoolId">
						<option value="">--请选择--</option>
						<c:forEach var="delist" items="${schoolList}">
									<option value="${delist.id}" ${delist.id==schoolId?"selected":"" } >${delist.name}</option>
						</c:forEach>
					</select>
				<label class="mt_label"></span>院系：</label>
					<select class="mt_select"    id="departmentId" name="departmentId">
						<option value="">--请选择--</option>
					</select>
				<label class="mt_label"></span>专业：</label>
					<select class="mt_select"  name="majorId" id="majorId">
					<option value="">--请选择--</option>
					</select>
				<!-- <label class="mt_label"></span>班级：</label>
					<select class="mt_select"  name="classId" id="classId">
					<option value="">--请选择--</option>
					</select> -->
				<button class="mt_button" id="submitButton">
					<i class="Hui-iconfont">&#xe665;</i>
				</button>
			</form>
		</div>
		<br>
		<div class="mt-20">
			<table
				class="table table-border table-bordered table-bg table-hover table-sort">
				<thead>
					<tr class="text-c">
						<th style="width:60px;"><input type="checkbox" name="" value=""></th>
						<th>班级编码</th>
						<th>班级名称</th>
						<th>入学年份</th>
						<th>学校名称</th>
						<th>院系名称</th>
						<th>专业名称</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
					<c:if
						test="${classList == null or classList.size() == 0 }">
						<tr>
							<td colspan="8">没有记录</td>
						</tr>
					</c:if>
					<c:if
						test="${classList != null and classList.size() > 0 }">
						<c:forEach items="${classList }" var="studentClassVo">
							<tr class="text-c">
								<td><input type="checkbox" value="${studentClassVo.id }"
									class="ids" name=""></td>
								<td>${studentClassVo.code }</td>
								<td>${studentClassVo.name}</td>
								<td>${studentClassVo.year }</td>
								<td>${studentClassVo.school.name}</td>
								<td>${studentClassVo.department.name}</td>
								<td>${studentClassVo.major.name}</td>
								<td class="f-14 td-manage"><shiro:hasPermission
										name="/back/studentClass/studentClassUpdate">
										<a href="javascript:void(0);" idVal="${studentClassVo.encryptId}"
											class="tablelink studentClassUpdate" title="修改"><i class="Hui-iconfont" style="color: #000;">&#xe6df;</i></a>
									</shiro:hasPermission> <shiro:hasPermission name="/back/studentClass/studentClassDelete">
									&nbsp;&nbsp;
										<a href="javascript:void(0);" idVal="${studentClassVo.encryptId}"
											class="tablelink studentClassDelete" title="删除"><i class="Hui-iconfont" style="color: red;">&#xe6e2;</i></a>
									</shiro:hasPermission></td>
							</tr>
						</c:forEach>
					</c:if>
				</tbody>
			</table>
			<jsp:include page="../../common/page.jsp" />
		</div>
	</div>
	<script type="text/javascript"
		src="${staticPath }/static/lib/jquery/1.9.1/jquery.min.js"></script>
	<script type="text/javascript"
		src="${staticPath }/static/lib/layer/2.4/layer.js"></script>
	<script type="text/javascript"
		src="${staticPath }/static/lib/My97DatePicker/4.8/WdatePicker.js"></script>
	<script type="text/javascript"
		src="${staticPath }/static/lib/datatables/1.10.0/jquery.dataTables.min.js"></script>
	<script type="text/javascript"
		src="${staticPath }/static/h-ui/js/H-ui.js"></script>
	<script type="text/javascript"
		src="${staticPath }/static/h-ui.admin/js/H-ui.admin.js"></script>
	<script type="text/javascript">
	/* 学校--院系联动 */
	var schoolId ="${schoolId}";
	if(schoolId != null && schoolId!= ""){
		changeInfo(schoolId);
	}
	$("#schoolId").change(function(){
		schoolId=$("#schoolId").val();
		changeInfo(schoolId);
	});
	function changeInfo(schoolId){
		var departmentId = "${departmentId}";
		$("#departmentId").empty();
		$("#departmentId").append('<option value="">---请选择---</option>');
	
		$.ajax({
			url : "${staticPath }/back/studentClass/seletDepartment",
			type: "post",
			data: {"schoolId" : schoolId},
			dataType: "json",
			success:function(data){
				var types=data.obj;
				if(types!=null && types.length>0){
					for(var i=0;i<types.length;i++){
						$("#departmentId").append(
								'<option value="' + types[i].id + '" ' + (departmentId ==types[i].id   ? 'selected=selected':'') + '>' + types[i].name + '</option>');
					}
				}
			}
			
		});
	}
	/********************************/
	
	/* 院系--专业联动 */
	var departmentId ="${departmentId}";
	var majorId = "${majorId}";
	if(departmentId != null && departmentId!= ""){
		changeDepartmentInfo(departmentId);
	}
	$("#departmentId").change(function(){
		departmentId=$("#departmentId").val();
		changeDepartmentInfo(departmentId);
	});
	function changeDepartmentInfo(departmentId){
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
	/***********************************/
	/* 专业--班级联动 */
/* 	var majorId = "${majorId}";
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
	} */
	
		 function jumpUrl() {
			top.layer.closeAll();
			window.location.href = "${staticPath}/back/studentClass/manager";
		} 
		var index;
		$(function() {
			$("#add").click(function() {
				top.layer.open({
					title: false,
					type: 2,shadeClose: true,shade: 0.3,area: ['800px', '50%'],
					content : rootPath + "/back/studentClass/studentClassAddUI",btn: ['保存','取消'],
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
						type:'post',
						url:rootPath+"/back/studentClass/studentClassAdd",
						data:addFormData,  
					    cache:false,
					    dataType:'json', 
					    success:function(data){
					    	if(data.success==true){
					    		top.layer.alert("保存成功！", {icon: 1},function(){jumpUrl();});
					    	}else{
					    		top.layer.alert(data.msg, {icon: 2},function(){jumpUrl();});}
					    }, 
					    error:function(){
					    	top.layer.alert("保存失败，系统内部错误!", {icon: 5},function(){jumpUrl();});
					    }
					});
				}
			/*批量删除  */
			$("#delete")
					.click(
							function() {
								var idChecks = $(".ids:checked");
								if (idChecks.length == 0) {
									top.layer.alert("请选择需要删除的记录！");
									return false;
								}
								top.layer
										.confirm(
												'您确认删除选中的记录吗?',
												{
													icon : 3,
													title : '提示'
												},
												function() {

													var id = "";
													var ids = new Array();
													for (var i = 0; i < idChecks.length; i++) {
														id += idChecks[i].value
																+ ",";
														ids[i] = idChecks[i].value;
													}
													$
															.ajax({
																url : "${staticPath }/back/studentClass/studentClassDeleteIDs",
																type : "post",
																data : "ids="
																		+ ids,
																dataType : "json",
																success : function(
																		data) {
																	if (data.success == true) {
																		top.layer
																				.alert(
																						data.msg,
																						{
																							icon : 1
																						},
																						function() {
																							jumpUrl();
																						});
																	} else {
																		top.layer
																				.open(
																						{
																							title : '删除',
																							content : "删除失败！有关联信息！",
																							icon : 2
																						},
																						function(
																								index) {
																							top.layer
																									.close(index);
																						});
																	}
																},
																error : function() {
																	top.layer
																			.alert(
																					"删除失败，系统内部错误!",
																					{
																						icon : 5
																					},
																					function() {
																						jumpUrl();
																					});
																}
															});
												});

							});
		});
		/* 查看 */
		function studentClassDetail(title, url, id) {
			index = layer.open({
				type : 2,
				title : title,
				content : url + "?id=" + id
			});
			layer.full(index);
		}
		/*修改*/
		$(".studentClassUpdate").click(function() {
					debugger;
					var id = $(this).attr("idVal");
					top.layer.open({
						title : false,
						type : 2,
						shadeClose : true,
						shade : 0.3,
						area : [ '800px', '50%' ],
						content : rootPath
								+ '/back/studentClass/studentClassUpdateUI/' + id,
						btn : [ '修改', '取消' ],
						yes : function(index, layero) { //提交iframe中的表单
							var frameName = "layui-layer-iframe" + index;//获得layer层的名字
							var formdata = parent.frames[frameName]
									.getFormData();
							if (formdata != 0) {
								subEditForm(formdata);
							}
						}
					});
				});
		function subEditForm(addFormData) {
			top.layer.closeAll();
			$.ajax({
				type : 'post',
				url : rootPath + "/back/studentClass/studentClassUpdate",
				data : addFormData,
				cache : false,
				dataType : 'json',
				success : function(data) {
					if (data.success == true) {
						top.layer.alert(data.msg, {
							icon : 1
						}, function() {
							jumpUrl();
						});
					} else {
						top.layer.alert(data.msg, {
							icon : 2
						}, function() {
							jumpUrl();
						});
					}
				},
				error : function() {
					top.layer.alert("修改失败，系统内部错误!", {
						icon : 5
					}, function() {
						jumpUrl();
					});
				}
			});
		}

		/* 删除 */
		$(".studentClassDelete").click(function() {
			var id = $(this).attr("idVal");
			top.layer.confirm('将会执行删除，是否继续？', {
				btn : [ '是', '否' ]
			//按钮
			}, function() {
				$.ajax({
					type : 'get',
					url : rootPath + "/back/studentClass/studentClassDelete/" + id,
					cache : false,
					dataType : 'json',
					success : function(data) {
						if (data.success == true) {
							top.layer.alert(data.msg, {
								icon : 1
							}, function() {
								jumpUrl();
							});
						} else {
							top.layer.open(
									{
										title : '删除',
										content : "删除失败！有关联信息！",
										icon : 2
									},
									function(
											index) {
										top.layer
												.close(index);
									});
						}
					},
					error : function() {
						top.layer.alert("删除失败，系统内部错误!", {
							icon : 5
						}, function() {
							jumpUrl();
						});
					}
				});
			});
		});
		$("#submitButton").click(function(){
			 $("#pageForm").submit();
		 });
	</script>
</body>
</html>