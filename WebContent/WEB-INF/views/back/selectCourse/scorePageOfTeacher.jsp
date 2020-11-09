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
.design {
    background-color: green;
}
</style>
</head>
<body>
	<nav class="breadcrumb">
	<i class="Hui-iconfont" style="color: #000;">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span>成绩查看
	</nav>
	<div class="page-container">
	<div class="cl pd-5 bg-1 bk-gray mt-20">
			<span class="l"> 
				<a class="btn btn-primary radius" title="导出所有实验成绩" class="exportAll" onclick="exportAll();"
					href="javascript:;"  ><i class="icon Hui-iconfont" style="color: #000;">&#xe644;</i>导出所有实验成绩</a>
				<a class="btn btn-primary radius" title="导出一次成绩" class="exportAll" onclick="exportOne();"
					href="javascript:;"  ><i class="icon Hui-iconfont" style="color: #000;">&#xe644;</i>导出一次成绩</a>
				<a class="btn btn-primary radius design" title="导出一次成绩" class="exportAll" onclick="exportDesign();"
					href="javascript:;"  ><i class="icon Hui-iconfont" style="color: #000;">&#xe644;</i>导出课程设计成绩</a>
			</span>
		</div>
		<br>
		<div class="mt_right" style="float: right;">
			<form action="${staticPath}/back/report/scoreManagement" id="pageForm" style="float: right;" method="post">
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
						<th>班级名称</th>
						<th>学号</th>
						<th>姓名</th>
						<th>学年学期</th>
						<th style="width:50px;">批阅状态</th>
						<th style="width:50px;">重复率</th>
						<th style="width:50px;">成绩</th>
						<th>评语</th>
					</tr>
				</thead>
				<tbody>
					<c:if
						test="${selectCourseList == null or selectCourseList.size() == 0 }">
						<tr>
							<td colspan="11">没有记录</td>
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
								<td style="width:50px;">${selectCourse.score == null ?"未评阅":"已评阅"}</td>
								<c:if test="${selectCourse.repetition != null}">
									<td style="width:50px;">${selectCourse.repetition}%</td>
								</c:if>
								<c:if test="${selectCourse.repetition == null}">
									<td style="width:50px;">无</td>
								</c:if>
								<td style="width:50px;">${selectCourse.score}</td>
								<td title="${selectCourse.judgeContent}">${selectCourse.judgeContent}</td>
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
			window.location.href = "${staticPath}/back/report/scorePage";
		}
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
			if(cid==null||cid==""){
				layer.alert("请先选择课程！");
				return ;
			}
			if(classId==null||classId==""){
				layer.alert("请先选择班级！");
				return ;
			}
			top.layer.confirm('将会导出该班级所有学生该门课程的所有实验成绩，是否继续？', {
				btn: ['是','否'] //按钮
			},function(){
				 window.location.href = '${staticPath}/back/report/score/exportAll/'+cid+'/'+classId;
				 top.layer.closeAll();
				}
			)
		}
		function exportOne(){
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
			top.layer.confirm('将会导出该班级所有学生该门课程的一次实验成绩，是否继续？', {
				btn: ['是','否'] //按钮
			},function(){
				 window.location.href = '${staticPath}/back/report/score/exportOne/'+cid+'/'+classId+'/'+experimentId;
				 top.layer.closeAll();
				}
			)
		}		
		function exportDesign(){
			var cid =$("#courseId").val();
			var classId = $("#classId").val();
			if(classId==null||classId==""){
				layer.alert("请先选择班级！");
				return ;
			}
			if(cid==null||cid==""){
				layer.alert("请选择课程名称！");
				return ;
			}
			top.layer.confirm('将会导出该班级所有学生该课程设计的成绩，是否继续？', {
				btn: ['是','否'] //按钮
			},function(){
				 window.location.href = '${staticPath}/back/report/score/exportDesign/'+cid+'/'+classId;
				 top.layer.closeAll();
				}
			)			
		}
	</script>
</body>
</html>