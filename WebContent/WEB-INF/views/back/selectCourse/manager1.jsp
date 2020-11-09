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
	<i class="Hui-iconfont" style="color: #000;">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span>作业管理
	 <a class="btn btn-success radius r"
			style="line-height: 1.6em; margin-top: 3px"
			href="javascript:location.replace(location.href);" title="刷新">
			<i class="Hui-iconfont">&#xe68f;</i>
	</a>
	</nav>
	<div class="page-container">
		<div class="cl pd-5 bg-1 bk-gray mt-20">
			<span class="l"> 
				<a class="btn btn-primary radius" title="导出" class="exportAll" onclick="exportAll();"
					href="javascript:;"  ><i class="icon Hui-iconfont" style="color: #000;">&#xe644;</i>导出</a>
			</span>
		</div>
		<br>
		<div class="mt_right" style="float: right;">
			<form action="${staticPath}/back/report/submitList1" id="pageForm" style="float: right;" method="post">
				<select class="mt_select"  id="courseId" name ="courseId">
					<option value="">--请选择课程--</option>
					<c:forEach var="course" items="${courses}">
						<option value="${course.courseId}" ${course.courseId==courseId?"selected":"" }>${course.courseName}</option>
					</c:forEach>
				</select>
				<select class="mt_select"  id="experimentId" name ="experimentId">
					<option value="">--请选择实验--</option>
				</select>
					<select class="mt_select"    id="departId" name="departId">
						<option value="">--请选择院系--</option>
						<c:forEach var="delist" items="${departmentList}">
							<option value="${delist.id}" ${delist.id==departId?"selected":"" }>${delist.name}</option>
						</c:forEach>
					</select>
					<select class="mt_select"  name="majorId" id="majorId">
		 				<option value="">--请选择专业--</option>
					</select>
					<select class="mt_select"  name="classId" id="classId">
					 	<option value="">--请选择班级--</option>
					</select>
					<input name="studentCode" value="${experimentVo.studentCode}" class="mt_select mt_input" type="text" placeholder="学号" />
					<input name="year" value="${experimentVo.year}" class="mt_select mt_input" type="text" placeholder="学年" />
					<input name="term" value="${experimentVo.term}" class="mt_select mt_input" type="text" placeholder="学期" />
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
						<th>课程名称</th>
						<th>实验名称</th>
						<th>班级</th>
						<th>学生编号</th>
						<th>学生姓名</th>
						<th>学年学期</th>
						<th>查看状态</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
					<c:if
						test="${selectCourseList == null or selectCourseList.size() == 0 }">
						<tr>
							<td colspan="8">没有记录</td>
						</tr>
					</c:if>
					<c:if
						test="${selectCourseList != null and selectCourseList.size() > 0 }">
						<c:forEach items="${selectCourseList }" var="selectCourse">
							<tr class="text-c">
								<td><input type="checkbox" value="${selectCourse.id }"
									class="ids" name=""></td>
								<td>${selectCourse.courseName }</td>
								<td>${selectCourse.experimentName }</td>
								<td>${selectCourse.className }</td>
								<td>${selectCourse.studentCode }</td>
								<td>${selectCourse.studentName }</td>
								<td>${selectCourse.year }年第${selectCourse.term }学期</td>
								<c:if test="${selectCourse.checkflag==0}">
									<td style="color:#F00">未查看</td>
								</c:if>
								<c:if test="${selectCourse.checkflag==1}">
									<td style="color:#00CC00">已查看</td>
								</c:if>
								<td class="f-14 td-manage">
									<%-- <shiro:hasPermission name="/back/report/search">
										<a href="${staticPath}/back/report/search/${selectCourse.courseId}"
											class="tablelink search" title="查看"><i class="icon Hui-iconfont" style="color: #000;">&#xe665;</i></a>
									</shiro:hasPermission>  --%>
									<c:if test="${selectCourse.score >= 0}">
											<a href="javascript:void(0);" idVal="${selectCourse.id}" class="tablelink experimentSearch" title="查看">
												<i class="icon Hui-iconfont" style="color: #000;">&#xe665;</i></a>
									</c:if>
									<c:if test="${selectCourse.score >= 0}">
											<a href="javascript:void(0);" idVal="${selectCourse.id}" class="tablelink submitAgain" title="重做">
												<i class="icon Hui-iconfont" style="color: #000;">&#xe66c;</i></a>
									</c:if>
									<c:if test="${selectCourse.score==null and selectCourse.status==1}">
											<a href="javascript:void(0);" idVal="${selectCourse.id}" class="tablelink check" title="批阅">
												<i class="icon Hui-iconfont" style="color: #000;">&#xe6e1;</i></a>
									</c:if>
								</td>
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
		function jumpUrl() {
			top.layer.closeAll();
			window.location.href = "${staticPath}/back/report/submitList1";
		}
		
		var index;
		$("#add").click(function() {
				top.layer.open({
					title: false,
					type: 2,shadeClose: true,shade: 0.3,area: ['800px', '50%'],
					content : rootPath + "/back/department/departmentAddUI",btn: ['保存','取消'],
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
						url:rootPath+"/back/department/departmentAdd",
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
																url : "${staticPath }/back/department/departmentDeleteIDs",
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
		
		/*修改*/
		$(".departmentUpdate").click(
				function() {
					var id = $(this).attr("idVal");
					top.layer.open({
						title : false,
						type : 2,
						shadeClose : true,
						shade : 0.3,
						area : [ '800px', '50%' ],
						content : rootPath
								+ '/back/department/departmentUpdateUI/' + id,
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
				url : rootPath + "/back/department/departmentUpdate",
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
		$(".departmentDelete").click(function() {
			var id = $(this).attr("idVal");
			top.layer.confirm('将会执行删除，是否继续？', {
				btn : [ '是', '否' ]
			//按钮
			}, function() {
				$.ajax({
					type : 'get',
					url : rootPath + "/back/department/departmentDelete/" + id,
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
						top.layer.alert("删除失败，系统内部错误!", {
							icon : 5
						}, function() {
							jumpUrl();
						});
					}
				});
			});
		});
		$(".submitAgain").click(function(){
			var id = $(this).attr("idVal");
			top.layer.confirm('将会让学生重新更新报告，是否继续？', {
				btn: ['是','否'] //按钮
			},function(){
				$.ajax({ type:'get', url:rootPath+"/back/report/setRedo/"+id,  
				    cache:false,dataType:'json',success:function(data){
				    	if(data.success==true){ top.layer.alert("设置重做成功！", {icon: 1},function(){jumpUrl();});
				    	}else{ top.layer.alert(data.msg, {icon: 2},function(){jumpUrl();}); }
				    },
				    error:function(){ top.layer.alert("设置重做失败，系统内部错误!", {icon: 5},function(){jumpUrl();}); }
				});
			})
		});		
		$(".check").click(function() {
			var id = $(this).attr("idVal");
			top.layer.open({
				title: false,
				type: 2, shadeClose: true, shade: 0.3, area: ['800px', '90%'],
				content: rootPath+'/back/report/rcUI/'+ id, 
				btn: ['取消'],
				 end: function () {
				        location.reload();
				      }
			});
		});
		$(".experimentSearch").click(function() {
			var id = $(this).attr("idVal");
			top.layer.open({
				title: false,
				type: 2, shadeClose: true, shade: 0.3, area: ['800px', '90%'],
				content: rootPath+'/back/report/scoreDetail/'+ id, 
				btn: ['取消'],
				 end: function () {
				        location.reload();
				      }
			});
		});
		var courseId ="${courseId}";
		if(courseId != null && courseId!= ""){
			changeCourseInfo(courseId);
		}
		$("#courseId").change(function(){
			courseId=$("#courseId").val();
			changeCourseInfo(courseId);
		});			
		
		function changeCourseInfo(courseId){
			var experimentId = "${experimentId}";
			$("#experimentId").empty();
			$("#experimentId").append('<option value="">---请选择---</option>');		
			$.ajax({
				url:"${staticPath }/back/report/getExperiment1",
				type:"post",
				dataType : "json",
				data:{courseId:courseId},
				success:function(data){
					var types=data.obj;
					if(types!=null && types.length>0){
						//$("#experimentId").empty();
						for(var i=0;i<types.length;i++){
							$("#experimentId").append(
								/* "<option selected='selected' value='"+types[i].experimentId+"'>"
							+types[i].experimentName+"</option>"); */
							'<option value="' + types[i].experimentId + '" ' + (experimentId ==types[i].experimentId   ? 'selected=selected':'') + '>' + types[i].experimentName + '</option>');
						}
					}
					experimentId=$("experimentId").val();
				}
			});			
		}	
		
		/* 院系--专业联动 */
		var departmentId ="${departId}";
		if(departmentId != null && departmentId!= ""){
			changeInfo(departmentId);
		}
		$("#departId").change(function(){
			departmentId=$("#departId").val();
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

		function exportAll(){
			var cid =$("#courseId").val();
			var classId = $("#classId").val();
			var experimentId = $("#experimentId").val();
			if(classId==null||classId==""){
				layer.alert("请先选择班级！");
				return ;
			}
			if(experimentId==null||experimentId==""){
				layer.alert("请选择实验名称！");
				return ;
			}
			top.layer.confirm('将会导出该班级所有学生该门课程下该门实验所有报告，是否继续？', {
				btn: ['是','否'] //按钮
			},function(){
				 window.location.href = '${staticPath}/back/report/exportOne/'+cid+'/'+classId+'/'+experimentId;
				 top.layer.closeAll();
				}
			)
		}
	</script>
</body>
</html>