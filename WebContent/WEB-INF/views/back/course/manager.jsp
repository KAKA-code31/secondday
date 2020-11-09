<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>课程信息管理</title>
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
		<i class="Hui-iconfont" style="color: #000;">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span>信息管理
		<span class="c-gray en">&gt;</span> 课程信息
		<a class="btn btn-success radius r"
			style="line-height: 1.6em; margin-top: 3px"
			href="javascript:location.replace(location.href);" title="刷新">
			<i class="Hui-iconfont">&#xe68f;</i>
		</a>
	</nav>
	<div class="page-container">
	<div class="cl pd-5 bg-1 bk-gray mt-20" > 
		<span class="l">
			<shiro:hasPermission name="/back/course/add">
				<a class="btn btn-primary radius" data-title="添加" id="add" href="javascript:;"><i class="Hui-iconfont">&#xe600;</i> 添加</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="/back/course/deleteIDs">
				<a href="javascript:;" id="delete" class="btn btn-danger radius"><i class="Hui-iconfont">&#xe6e2;</i> 删除</a> 
			</shiro:hasPermission>
		</span>
		</div>
		<br>
		<div class="mt_right" style="float:right;">
			<form  action="${staticPath }/back/course/manager" id="pageForm" style="float:right;" method="post">
				<input name="courseNumber" value="${course.courseNumber }"  class="mt_select mt_input" type="text"  placeholder="课程编号"/>
				<input name="name" value="${course.name }"  class="mt_select mt_input" type="text"  placeholder="课程名称"/>
			   	<button class="mt_button" id="submitButton"><i class="Hui-iconfont">&#xe665;</i></button>
			  	<div class="clearfix"></div>
			</form>
		</div>
		<br>
		<div class="mt-20">
		<form id="courseForm">
			<table class="table table-border table-bordered table-bg table-hover table-sort">
				<thead>
					<tr class="text-c">
						<th style="width:60px;"><input type="checkbox" onclick='chkall("courseForm",this)' /></th>
						<th>课程编号</th>
						<th>课程名</th>
						<th>课程简介</th>
						<th>操作</th>
					</tr>
				</thead>

				<c:if test="${courseList.size()==0}">
					<tbody>
						<tr>
							<td colspan="4">没有课程信息</td>
						</tr>
					</tbody>
				</c:if>
				<c:if test="${courseList.size()!=0}">
					<tbody>
						<c:forEach var="course" items="${courseList}">
							<tr>
							
								<td><input value="${course.encryptId}" class="ids" type="checkbox"/></td>
								<td>${course.courseNumber}</td>	
								<td>${course.name}</td>	
								<td>${course.description}</td>							
								<td>
									<shiro:hasPermission name="/back/course/edit">
										<a href="javascript:void(0);" idVal="${course.encryptId}" class="tablelink edit">修改</a>
									</shiro:hasPermission>
									<shiro:hasPermission name="/back/course/delete">
										<a href="javascript:void(0);" idVal="${course.encryptId}" class="tablelink deleteOne">删除</a>
									</shiro:hasPermission>
									<shiro:hasPermission name="/back/course/details">
										<a href="javascript:void(0);" idVal="${course.encryptId}" class="tablelink details">查看</a>
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
	<script type="text/javascript" src="${staticPath}/static/ckeditor/ckeditor.js"></script>
	<script type="text/javascript">
	
		function jumpUrl(){
			top.layer.closeAll();
			var i = ${currentPage};
			var t = ${totalPage};
			GoToPage(i,t);
			window.location.href = "${staticPath}/back/course/manager";
		}
		
		//批量删除
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
					$.ajax({ type:'post', url:rootPath+"/back/course/deleteIDs",  
					    data:{"ids":id}, cache:false, dataType:'json',success:function(data){
					    	if (data.success==true) { top.layer.alert("删除成功！", {icon: 1},function(){ jumpUrl(); });
					    	} else { top.layer.alert(data.msg, {icon: 2},function(){ jumpUrl(); }); }
					    },
					    error:function(){ top.layer.alert(data.msg, {icon: 5},function(){ jumpUrl(); }); }
					});
			});
		});
		/*添加*/
		$("#add").click(function() {
			top.layer.open({
				title: false,
				type: 2,shadeClose: true,shade: 0.3,area: ['800px', '50%'],
				content: rootPath+'/back/course/addUI',btn: ['保存','取消'],
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
				type:'post',url:rootPath+"/back/course/add",data:addFormData,  
			    cache:false,dataType:'json', success:function(data){
			    	if(data.success==true){top.layer.alert("保存成功！", {icon: 1},function(){jumpUrl();});
			    	}else{top.layer.alert(data.msg, {icon: 2},function(){jumpUrl();});}
			    }, error:function(){top.layer.alert(data.msg, {icon: 5},function(){jumpUrl();});}
			});
		}
		//修改
		$(".edit").click(function() {
			var id = $(this).attr("idVal");
			top.layer.open({
				title: false,
				type: 2,shadeClose: true,shade: 0.3,area: ['800px', '50%'],
				content: rootPath+'/back/course/editUI/'+id,
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
		function subEditForm(addFormData){
			top.layer.closeAll();
			$.ajax({  
			    type:'post',url:rootPath+"/back/course/edit",data:addFormData,  
			    cache:false,dataType:'json',  
			    success:function(data){
			    	if(data.success==true){top.layer.alert("修改成功！", {icon: 1},function(){jumpUrl();});
			    	}else{top.layer.alert(data.msg, {icon: 2},function(){jumpUrl();});}
			    },
			    error:function(){
			    	top.layer.alert(data.msg, {icon: 5},function(){jumpUrl();});
			    }
			});
		}
		
		$(".deleteOne").click(function(){
			var id = $(this).attr("idVal");
			top.layer.confirm('将会执行删除，是否继续？', {
				btn: ['是','否'] //按钮
			},function(){
				$.ajax({ type:'get', url:rootPath+"/back/course/delete/"+id,  
				    cache:false,dataType:'json',success:function(data){
				    	if(data.success==true){ top.layer.alert("删除成功！", {icon: 1},function(){jumpUrl();});
				    	}else{ top.layer.alert(data.msg, {icon: 2},function(){jumpUrl();}); }
				    },
				    error:function(){ top.layer.alert("删除失败，系统内部错误!", {icon: 5},function(){jumpUrl();}); }
				});
			})
		});
				
		//查看
		$(".details").click(function() {
			var id = $(this).attr("idVal");
			top.layer.open({
				title : false,
				type : 2,shadeClose : true,shade : 0.3,area : [ '800px', '50%' ],
				content : rootPath + '/back/course/detailsUI/' + id,
				btn : [ '返回' ],
				yes : function(index, layero) {
						jumpUrl();
				}
			});
		});
		
		$('.tablelist tbody tr:odd').addClass('odd');
	</script>
</body>
</html>
