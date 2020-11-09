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
	<i class="Hui-iconfont" style="color: #000;">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span>报告批阅
	<span class="c-gray en">&gt;</span> 报告查重
	</nav>
	<div class="page-container">
		<center><span style="color:green"><h3>${msg }</h3></span></center>
		<div class="mt-20">
			<table
				class="table table-border table-bordered table-bg table-hover table-sort">
				<thead>
					<tr class="text-c">
						<th>报告名称</th>
						<th>重复率</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
					<c:if
						test="${TotalNameList == null or TotalNameList.size() == 0 }">
						<tr>
							<td colspan="3">没有记录</td>
						</tr>
					</c:if>
					<c:if
						test="${TotalNameList != null and TotalNameList.size() > 0 }">
						<c:forEach items="${TotalNameList }" var="totalName" varStatus="loop" >
							<tr class="text-c">
								<td>${totalName }</td>
								<td>${TotalSimilaity[loop.count-1]}</td>
								<td class="f-14 td-manage">
									<%-- <a href="${staticPath}/back/report/experimentSearch/${selId[loop.count-1]}"
												class="tablelink experimentSearch" title="查看"><i class="icon Hui-iconfont" style="color: #000;">&#xe665;</i></a> --%>
									<a href="javascript:void(0);" idVal="${selId[loop.count-1]}" class="tablelink lookScource" title="查看文件">
												<i class="icon Hui-iconfont" style="color: #000;">&#xe695;</i></a>
								</td>
							</tr>
						</c:forEach>
					</c:if>
				</tbody>
			</table>
		</div>
	</div>
</body>
 <script src="${staticPath}/static/js/jquery-1.7.2.js"></script>
<script type="text/javascript" src="${staticPath}/static/lib/layer/2.4/layer.js"></script>
<script type="text/javascript" src="${staticPath}/static/h-ui/js/H-ui.min.js"></script> 
<script type="text/javascript" src="${staticPath}/static/h-ui.admin/js/H-ui.admin.js"></script> <!--/_footer 作为公共模版分离出去-->

<!--请在下方写此页面业务相关的脚本--> 
<script type="text/javascript">
$(".lookScource").click(function() {
		var id = $(this).attr("idVal");
		top.layer.open({
			title: false,
			type: 2, shadeClose: true, shade: 0.3, area: ['800px', '90%'],
			content: rootPath+'/back/report/lookReport/'+ id, 
			btn: ['取消']
		});
	});
</script>
</html>