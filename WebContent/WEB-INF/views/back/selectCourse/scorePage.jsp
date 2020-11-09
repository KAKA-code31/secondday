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
	<i class="Hui-iconfont" style="color: #000;">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span>成绩查看
	</nav>
	<div class="page-container">
		 <%--<div class="cl pd-5 bg-1 bk-gray mt-20">
			<span class="l"> 
				 <shiro:hasPermission name="/back/report/selectCourseAdd">
					<a class="btn btn-primary radius" data-title="添加" id="add"
						href="javascript:;"><i class="Hui-iconfont">&#xe600;</i> 添加选课</a>
				</shiro:hasPermission> 
				<shiro:hasPermission name="/back/report/selectCourseDeleteIDs">
					<a data-href="javascript:;" id="delete"
						class="btn btn-danger radius"><i class="Hui-iconfont">&#xe6e2;</i>
						批量删除</a>
				</shiro:hasPermission> 
				
			</span>
		</div>--%>
		<br>
		<div class="mt_right" style="float: right;">
			<form action="${staticPath}/back/report/lookScore" id="pageForm" style="float: right;" method="post">
				<%-- <label class="mt_label">课程编号：</label> 
					<input name="coursecode" value="${experimentVo.courseCode}" class="mt_select mt_input" type="text" placeholder="请输入..." />  --%>
				<label class="mt_label">课程名称：</label> 
					<input name="courseName" value="${experimentVo.courseName}" class="mt_select mt_input" type="text" placeholder="请输入关键字..." />
				<label class="mt_label">学年：</label> 
					<input name="year" value="${experimentVo.year}" class="mt_select mt_input" type="text" placeholder="请输入关键字..." />
				<label class="mt_label">学期：</label> 
					<input name="term" value="${experimentVo.term}" class="mt_select mt_input" type="text" placeholder="请输入关键字..." />
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
	</script>
</body>
</html>