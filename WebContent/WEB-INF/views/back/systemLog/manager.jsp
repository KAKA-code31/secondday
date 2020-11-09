<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>操作日志信息</title>
<%@ include file="/commons/basejs.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<link rel="stylesheet" type="text/css" href="${staticPath }/static/h-ui/css/H-ui.min.css" />
<link rel="stylesheet" type="text/css" href="${staticPath }/static/h-ui.admin/css/H-ui.admin.css" />
<link rel="stylesheet" type="text/css" href="${staticPath }/static/lib/Hui-iconfont/1.0.8/iconfont.css" />
<link rel="stylesheet" type="text/css" href="${staticPath }/static/lib/icheck/icheck.css" />
<link rel="stylesheet" type="text/css" href="${staticPath }/static/h-ui.admin/skin/default/skin.css" id="skin" />
<link rel="stylesheet" type="text/css" href="${staticPath }/static/h-ui.admin/css/style.css" />
<link rel="stylesheet" type="text/css" href="${staticPath }/static/css/style.css" />
<style type="text/css">
	.table-sort tr td{
	    text-align: center;
	}
	.mt_right {
    	width: 100%;
	}
</style>
</head>
<body>
	<nav class="breadcrumb">
		<i class="Hui-iconfont" style="color: #000;">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span>系统管理
		<span class="c-gray en">&gt;</span> 操作日志
		<a class="btn btn-success radius r"
			style="line-height: 1.6em; margin-top: 3px"
			href="javascript:location.replace(location.href);" title="刷新">
			<i class="Hui-iconfont">&#xe68f;</i>
		</a>
	</nav>
	<div class="page-container">
		<div class="mt_right" style="float:right;">
			<form action="${staticPath}/back/systemLog/manager" id="pageForm" style="float:right;" method="post">
				<input 	name="optContent" value="${systemLog.optContent }"  class="mt_select mt_input" type="text"  placeholder="操作内容"/>
			   	<button class="mt_button" id="submitButton"><i class="Hui-iconfont">&#xe665;</i></button>
			  	<div class="clearfix"></div>
			</form>
		</div>
		<br>
		<div class="mt-20">
		<form id="systemlogForm">
			<table class="table table-border table-bordered table-bg table-hover table-sort">
				<thead>
				<tr class="text-c">
						<th>操作人</th>
						<th>操作内容</th>
						<th>操作地址</th>
						<th>操作时间</th>
						<th>操作</th>
					</tr>
				</thead>
	
				<c:if test="${systemLogList.size()==0}">
					<tbody>
						<tr>
							<td colspan="5">没有日志信息</td>
						</tr>
					</tbody>
				</c:if>
				<c:if test="${systemLogList.size()!=0}">
					<tbody>
						<c:forEach var="systemLog" items="${systemLogList}">
							<tr>
								<td>${systemLog.loginName}</td>
								<td>${systemLog.optContentFront()}</td>
								<td>${systemLog.optIp}</td>
								<td><fmt:formatDate value="${systemLog.optTime}"
										type="both" dateStyle="default" timeStyle="default" /></td>
								<td>
									<shiro:hasPermission name="/back/systemLog/detail">
										<a href="javascript:void(0);" idVal="${systemLog.encryptId}" class="detail tablelink">查看</a>
									</shiro:hasPermission>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</c:if>
	
			</table>
		</form>
		<jsp:include page="../../common/page.jsp"></jsp:include>
		</div>
	</div>
	<script type="text/javascript">
		$(".detail").click(function() {
			var id = $(this).attr("idVal");
			top.layer.open({
				title: false,
				type: 2,shadeClose: true,shade: 0.3,area: ['600px', '400px'],
				content: rootPath+'/back/systemLog/detail/'+id
			});
		});
		$('.tablelist tbody tr:odd').addClass('odd');
	</script>
</body>
</html>