<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ include file="/commons/global.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="/commons/basejs.jsp"%>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<link rel="stylesheet" type="text/css" href="${staticPath}/static/h-ui/css/H-ui.min.css" />
<link rel="stylesheet" type="text/css" href="${staticPath}/static/h-ui.admin/css/H-ui.admin.css" />
<link rel="stylesheet" type="text/css" href="${staticPath}/static/lib/Hui-iconfont/1.0.8/iconfont.css" />
<link rel="stylesheet" type="text/css" href="${staticPath}/static/h-ui.admin/skin/default/skin.css" id="skin" />
<link rel="stylesheet" type="text/css" href="${staticPath}/static/h-ui.admin/css/style.css" />
<link href="${staticPath}/static/css/page.css" rel="stylesheet" type="text/css" />
<title>资讯列表</title>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont" style="color: #000;">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 资讯管理 <span class="c-gray en">&gt;</span> 资讯列表 <a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="page-container">
	<form id="pageForm" action="${staticPath }/back/schedule/manager" method="post">
		<div class="text-c">
			<input type="text" name="courseName" id="" value="${ schedule.courseName}" placeholder="课程 " style="width:150px" class="input-text">
			<input type="text" name="className" id="" placeholder="班级" value="${ schedule.className}" style="width:150px" class="input-text">
			<input type="text" name="teacherName" id="" placeholder="教师 " value="${ schedule.teacherName}" style="width:150px" class="input-text">
			<input type="text" name="departName" id="" placeholder="所属系部 " value="${ schedule.departName}" style="width:150px" class="input-text">
			</span>
			<%-- <input type="text"  placeholder="开始时间" value="${ schedule.startTime}"  onfocus="WdatePicker({ maxDate:'#F{$dp.$D(\'logmax\')||\'%y-%M-%d\'}' })" id="logmin" class="input-text Wdate" style="width:120px;">
			-
			<input type="text" placeholder="结束时间" value="${ schedule.endTime}" onfocus="WdatePicker({ minDate:'#F{$dp.$D(\'logmin\')}',maxDate:'%y-%M-%d' })" id="logmax" class="input-text Wdate" style="width:120px;"> --%>
			<button name="" id="" class="btn btn-success" type="submit"><i class="Hui-iconfont">&#xe665;</i> 搜索</button>
		</div>
	</form>
	<div class="cl pd-5 bg-1 bk-gray mt-20"> 
		<span class="l">
			<shiro:hasPermission name="/back/schedule/add">
				<a class="btn btn-primary radius" data-title="添加" id="add" href="javascript:void(0);"><i class="Hui-iconfont">&#xe600;</i> 添加</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="/back/schedule/importReduce">
				<a href="javascript:void(0);" onclick="import_excel('导入压缩文件','${staticPath}/back/schedule/importReduceUI','800','400')" class="btn btn-success radius"><i class="Hui-iconfont">&#xe645;</i> 导入</a>
			</shiro:hasPermission> 
			<shiro:hasPermission name="/back/schedule/deleteIDs">
				<a href="javascript:void(0);" id="delete" class="btn btn-danger radius"><i class="Hui-iconfont">&#xe6e2;</i> 删除</a>
			</shiro:hasPermission> 
			<input type="file" hidden="hidden" id="file">
		</span>
	</div>
	<div class="mt-20">
		<table class="table table-border table-bordered table-bg table-hover table-sort table-responsive">
			<thead>
				<tr class="text-c">
					<th width="25"><input type="checkbox" name="" value=""></th>
					<th width="200">课程名称</th>
					<th width="80">教室名称</th>
					<th width="120">班级名称</th>
					<th width="80">教师名称</th>
					<th width="60">教学班</th>
					<th width="120">所属院系</th>
					<th width="100">开始周次</th>
					<th width="100">结束周次</th>
					<th width="75">周几</th>
					<th width="60">节次</th>
					<th width="125">学年</th>
					<th width="140">开始时间</th>
					<th width="140">结束时间</th>
					<th width="120">操作</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${page.size() == 0 }">
					<tr>
						<td colspan="15">没有课程安排信息</td>
					</tr>
				</c:if>
				<c:if test="${page.size()!=0}">
					<c:forEach items="${page }" var="page">
						<tr class="text-c">
							<td><input value="${page.encryptId}" class="ids" type="checkbox"/></td>
							<td>${page.courseName }</td>
							<td class="text-l">${page.classroomName}</td>
							<td class="text-l">${page.className}</td>
							<td class="text-l">${page.teacherName}</td>
							<td class="text-l">${page.teacherClass}</td>
							<td class="text-l">${page.departName}</td>
							<td class="text-l">${page.weekStart}</td>
							<td class="text-l">${page.weekEnd}</td>
							<td class="text-l">${page.weekDay}</td>
							<td class="text-l">${page.section}</td>
							<td class="text-l">${page.yearTerm}</td>
							<td class="text-l">${page.startTime}</td>
							<td class="text-l">${page.endTime}</td>
							<td class="f-14 td-manage">
								<a style="text-decoration:none" class="ml-5" onClick="article_edit('资讯编辑','article-add.html','10001')" href="javascript:;" title="编辑"><i class="Hui-iconfont">&#xe6df;</i></a>
								<shiro:hasPermission name="/back/schedule/delete">
									<a style="text-decoration:none" class="deleteOne" class="ml-5" idVal="${page.encryptId}" href="javascript:void(0);" title="删除"><i class="Hui-iconfont">&#xe6e2;</i></a>
									<!-- <a style="text-decoration:none" class="deleteOne" class="ml-5" onClick="schedule_del(this,'${page.encryptId}')" href="javascript:void(0);" title="删除"><i class="Hui-iconfont">&#xe6e2;</i></a>-->
								</shiro:hasPermission> 
							</td>
						</tr>
					</c:forEach>
				</c:if>
			</tbody>
		</table>
		<jsp:include page="../../common/page.jsp"></jsp:include>
	</div>
</div>
<script type="text/javascript" src="${staticPath}/static/lib/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript" src="${staticPath}/static/lib/layer/2.4/layer.js"></script>
<script type="text/javascript" src="${staticPath}/static/h-ui/js/H-ui.min.js"></script> 
<script type="text/javascript" src="${staticPath}/static/h-ui.admin/js/H-ui.admin.js"></script> 
<script type="text/javascript" src="${staticPath}/static/lib/My97DatePicker/4.8/WdatePicker.js"></script> 
<script type="text/javascript">
function jumpUrl(){
	top.layer.closeAll();
	var i = ${currentPage};
	var t = ${totalPage};
	GoToPage(i,t);
}
function importExcel(){
	var file = $("#file").files;
	$("#file").click();
}

function import_add(title,url,w,h){
	layer_show(title,url,w,h);
}

/*导入*/
function import_excel(title,url,w,h){
	layer_show(title,url,w,h);
}

/*删除*/
$(".deleteOne").click(function(){
	var id = $(this).attr("idVal");
	top.layer.confirm('确认要删除吗？', {
		btn: ['是','否'] //按钮
	},function(){
		$.ajax({ type:'get', url:rootPath+"/back/schedule/delete/"+id,  
		    cache:false,dataType:'json',success:function(data){
		    	if(data.success==true){ top.layer.alert(data.msg, {icon: 1},function(){jumpUrl();});
		    	}else{ top.layer.alert(data.msg, {icon: 2},function(){jumpUrl();}); }
		    },
		    error:function(){ top.layer.alert(data.msg, {icon: 5},function(){jumpUrl();}); }
		});
	})
});

/*删除
function schedule_del(obj,id){
	layer.confirm('确认要删除吗？',function(index){
		$.ajax({
			type: 'POST',
			url: '${staticPath}/back/schedule/delete/'+id,
			dataType: 'json',
			success: function(data){
				$(obj).parents("tr").remove();
				//top.layer.alert(data.msg,{icon:2,time:1000});
				if (data.success==true) { top.layer.alert("删除成功！", {icon: 1},function(){ jumpUrl(); });
		    	} else { top.layer.alert(data.msg, {icon: 2},function(){ jumpUrl(); }); }
				
			},
			error:function(data) {
				console.log(data.msg);
			},
		});		
	});
} */


$("#delete").click(function() {
	var idChecks = $(".ids:checked");
	if (idChecks.length == 0) {
		top.layer.alert("至少选择一项！");
		return false;
	}
	top.layer.confirm('将会删除所选选课的所有信息，是否继续？', {
		btn: ['是','否'] //按钮
	},function(){
		var id = "";
		for (var i = 0; i < idChecks.length; i++) { id += idChecks[i].value + ","; }
		id = id.substring(0, id.length - 1);
		$.ajax({ type:'post', url:rootPath+"/back/schedule/deleteIDs",  
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
		title:"添加信息",
	  	type: 2,
	  	shade: 0.3,
	  	shadeClose: true,
  		area: ['800px', '600px'],
	  	fixed: false, //不固定
	  	maxmin: true,
	  	content: rootPath+'/back/schedule/addUI',
	  	btn: ['保存','取消'],
	  	yes:function(index,layero){
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
	console.info(addFormData);
	top.layer.closeAll();
	$.ajax({  
		type:'post',
		url:rootPath+"/back/schedule/add",
		data:addFormData,  
	    cache:false,
	    dataType:'json', 
	    success:function(data){
	    	if(data.success==true){
	    		top.layer.alert(data.msg, {icon: 1},function(){jumpUrl();});
	    	}else{
	    		top.layer.alert(data.msg, {icon: 2},function(){jumpUrl();});}
	    }, 
	    error:function(data){
	    	top.layer.alert(data.msg, {icon: 5},function(){jumpUrl();});
	    }
	});
}

</script>
</body>
</html>