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
	<i class="Hui-iconfont" style="color: #000;">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span>实验管理
	<span class="c-gray en">&gt;</span> 实验查看
	 <a class="btn btn-success radius r"
			style="line-height: 1.6em; margin-top: 3px"
			href="javascript:location.replace(location.href);" title="刷新">
			<i class="Hui-iconfont">&#xe68f;</i>
			<input type="hidden" name ="id">
	</a>
	</nav>
	<div class="page-container">
		<%-- <div class="cl pd-5 bg-1 bk-gray mt-20">
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
		</div>
		<br> --%>
		<div class="mt_right" style="float: right;">
			<form action="${staticPath}/back/report/search/${id}" id="pageForm" style="float: right;" method="get">
				<%-- <label class="mt_label">编码：</label> <input name="code"
					value="${department.code}" class="mt_select mt_input" type="text"
					placeholder="请输入..." /> <label class="mt_label">名称：</label> <input
					name="name" value="${department.name}" class="mt_select mt_input"
					type="text" placeholder="请输入关键字..." />
				<button class="mt_button" id="submitButton">
					<i class="Hui-iconfont">&#xe665;</i>
				</button> --%>
			</form>
		</div>
		<br>
		<div class="mt-20">
			<table
				class="table table-border table-bordered table-bg table-hover table-sort">
				<thead>
					<tr class="text-c">
						<th>实验编号</th>
						<th>实验名称</th>
						<th>实验描述</th>
					</tr>
				</thead>
				<tbody>
					<c:if
						test="${experimentList == null or experimentList.size() == 0 }">
						<tr>
							<td colspan="4">没有记录</td>
						</tr>
					</c:if>
					<c:if
						test="${experimentList != null and experimentList.size() > 0 }">
						<c:forEach items="${experimentList }" var="experiment">
							<tr class="text-c">
								<td>${experiment.experimentCode }</td>
								<td title="${experiment.experimentName }">${experiment.experimentName }</td>
								<td title="${experiment.description }">${experiment.description }</td>
							</tr>
						</c:forEach>
					</c:if>
				</tbody>
			</table>
		</div>
	</div>
</body>
</html>