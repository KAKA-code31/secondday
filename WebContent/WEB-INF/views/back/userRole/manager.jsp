<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>系统管理员</title>
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
<body>
	<nav class="breadcrumb">
		<i class="Hui-iconfont" style="color: #000;">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span>系统管理
		<span class="c-gray en">&gt;</span> 系统管理员
		<a class="btn btn-success radius r"
			style="line-height: 1.6em; margin-top: 3px"
			href="javascript:location.replace(location.href);" title="刷新">
			<i class="Hui-iconfont">&#xe68f;</i>
		</a>
	</nav>
	<div class="page-container">
		<div class="cl pd-5 bg-1 bk-gray mt-20" > 
		<span class="l">
			<shiro:hasPermission name="/back/userRole/add">
				<a class="btn btn-primary radius" data-title="添加" id="add" href="javascript:;"><i class="Hui-iconfont">&#xe600;</i> 添加</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="/back/userRole/deleteIDs">
				<a href="javascript:;" id="delete" class="btn btn-danger radius"><i class="Hui-iconfont">&#xe6e2;</i> 删除</a> 
			</shiro:hasPermission>
		</span>
		</div>
		<div class="mt-20">
		<form id="pageForm" action="${staticPath }/back/userRole/manager" method="post">
		</form>
		<form id="userRoleForm">
			<table class="table table-border table-bordered table-bg table-hover table-sort">
				<thead>
					<tr class="text-c">
						<th><input type="checkbox" onclick='chkall("userRoleForm",this)' /></th>
						<th>管理员账号</th>
						<th>管理员名称</th>
						<th>操作</th>
					</tr>
				</thead>

				<c:if test="${userVoList.size()==0}">
					<tbody>
						<tr>
							<td colspan="6">没有管理员信息</td>
						</tr>
					</tbody>
				</c:if>
				<c:if test="${userVoList.size()!=0}">
					<tbody>
						<c:forEach var="userVo" items="${userVoList}">
							<tr>
								<td><input value="${userVo.encryptId}" class="ids" type="checkbox"/></td>
								<td>${userVo.userId}</td>
								<td>${userVo.name}</td>
								<td>
									<shiro:hasPermission name="/back/userRole/delete">
										<a href="javascript:void(0);" idVal="${userVo.encryptId}" class="tablelink delete">删除</a>
									</shiro:hasPermission>
									<shiro:hasPermission name="/back/userRole/grant">
										<a href="javascript:void(0);" class="grant tablelink" idVal="${userVo.encryptId}">授权</a>
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
	<script type="text/javascript" src="${pageContext.request.contextPath }/static/js/talent.js"></script>
	<script type="text/javascript">
		function jumpUrl(){
			top.layer.closeAll();
			window.location.href = "${staticPath}/back/userRole/manager";
		}
		$(".grant").click(function() {
			var id = $(this).attr("idVal");
			var ind = top.layer.open({
				title: false,
				type: 2, title: '授权页', shadeClose: true, shade: 0.3, area: ['800px', '90%'],
				content: '${staticPath}/back/userRole/userGrantUI/'+id,
				btn: ['保存','取消'], yes:function(index, layero){ //提交iframe中的表单
					var frameName="layui-layer-iframe"+index;//获得layer层的名字
					var formData=parent.frames[frameName].getFormData();
					if(formData!=0){
						$.ajax({  type:'post',  url:"${staticPath}/back/userRole/grant",  
						    data:formData, cache:false,dataType:'json', 
						    success:function(data){ top.layer.alert(data.msg,function(){jumpUrl();}); }
						});
					}
				}
			});
		});
		$("#delete").click(function() {
			var idChecks = $(".ids:checked");
			if (idChecks.length == 0) {
				top.layer.alert("至少选择一项！");
				return false;
			}
			top.layer.confirm('将会执行删除，是否继续？', {
				btn: ['是','否'] //按钮
			},function(){
					var id = "";
					for (var i = 0; i < idChecks.length; i++) { id += idChecks[i].value + ","; }
					id = id.substring(0, id.length - 1);
					$.ajax({  
					    type:'post', url:"${staticPath}/back/userRole/deleteIDs",  
					    data:{"ids":id}, cache:false, dataType:'json',  
					    success:function(data){
					    	if (data.success==true) { top.layer.alert("删除成功！", {icon: 1},function(){ jumpUrl(); });
					    	} else { top.layer.alert(data.msg, {icon: 2},function(){jumpUrl(); }); }
					    },
					    error:function(){ top.layer.alert("删除失败，系统内部错误!", {icon: 5},function(){ jumpUrl(); }); }
					});
			});
		});
		$(".delete").click(function() {
			var id = $(this).attr("idVal");
			top.layer.confirm('将会执行删除，是否继续？', { 
				btn: ['是','否'] //按钮
			},function(){
				$.ajax({  
				    type:'get', url:"${staticPath}/back/userRole/delete/"+id,  
				    cache:false, dataType:'json',  
				    success:function(data){
				    	if (data.success==true) { top.layer.alert("删除成功！", {icon: 1},function(){ jumpUrl(); });
				    	} else { top.layer.alert(data.msg, {icon: 2},function(){jumpUrl(); }); }
				    },
				    error:function(){ top.layer.alert("删除失败，系统内部错误!", {icon: 5},function(){ jumpUrl(); }); }
				});
			});
		});
		$("#add").click(function() {
			top.layer.open({
				title: false,
				type: 2, shadeClose: true, shade: 0.3, area: ['800px', '90%'],
				content: '${staticPath}/back/userRole/addUI', btn: ['保存','取消'],
				yes:function(index, layero){ //提交iframe中的表单
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
			$.ajax({ type:'post', url:"${staticPath}/back/userRole/add", data:addFormData,  
			    cache:false, dataType:'json', success:function(data){
			    	if(data.success==true){ top.layer.alert("保存成功！", {icon: 1},function(){ jumpUrl(); });
			    	} else { top.layer.alert("保存失败！", {icon: 2},function() { jumpUrl(); }); }
			    },
			    error:function(){ top.layer.alert("保存失败，系统内部错误!", {icon: 5},function(){ jumpUrl(); });
			    }
			});
		}
		$('.tablelist tbody tr:odd').addClass('odd');
	</script>
</body>

</html>
