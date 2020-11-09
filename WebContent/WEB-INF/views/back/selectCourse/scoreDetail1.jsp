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
<link href="${staticPath}/static/plugins/zTree/css/zTreeStyle/zTreeStyle.css" rel="stylesheet" type="text/css" />
<title>提交</title>
</head>
<body>
<nav class="breadcrumb">
	<i class="Hui-iconfont" style="color: #000;">&#xe67f;</i> 首页 
	<span class="c-gray en">&gt;</span>报告查阅
</nav>
<article class="page-container">
	<iframe id="result_win" name="result_win"></iframe><br>
	<form  class="form form-horizontal">
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">
				报告：
			</label>
			<div class="formControls col-xs-4 col-sm-6">
				<a href="${staticPath }/back/report/downloadReport/${experiment.id}" class="tablelink downloadReport" title="下载"><i class="icon Hui-iconfont" style="color: #000;">&#xe640;</i></a>
				&nbsp;&nbsp;&nbsp;&nbsp;
				<%-- <a href="${staticPath }/back/report/lookReport/${experiment.id}" class="tablelink lookReport" title="预览"><i class="icon Hui-iconfont" style="color: #000;">&#xe695;</i></a> --%>
				<a href="javascript:void(0);" idVal="${experiment.id}" class="tablelink lookReport" title="预览">
												<i class="icon Hui-iconfont" style="color: #000;">&#xe695;</i></a>
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">
				截图：
			</label>
			<div class="formControls col-xs-4 col-sm-6">
				<a href="${staticPath }/back/report/downloadPicture/${experiment.id}" class="tablelink downloadPicture" title="截图下载"><i class="icon Hui-iconfont" style="color: #000;">&#xe640;</i></a>
				&nbsp;&nbsp;&nbsp;&nbsp;
				<a href="javascript:void(0);" idVal="${experiment.id}" class="tablelink lookPicture" title="预览">
												<i class="icon Hui-iconfont" style="color: #000;">&#xe695;</i></a>
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">
				源码：
			</label>
			<div class="formControls col-xs-4 col-sm-6">
				<a href="${staticPath }/back/report/downloadScource/${experiment.id}" class="tablelink downloadScource" title="源码下载"><i class="icon Hui-iconfont" style="color: #000;">&#xe640;</i></a>
				&nbsp;&nbsp;&nbsp;&nbsp;
				<a href="javascript:void(0);" idVal="${experiment.id}" class="tablelink lookScource" title="预览">
												<i class="icon Hui-iconfont" style="color: #000;">&#xe695;</i></a>
				<%-- <a href="${staticPath }/back/report/lookScource/${experiment.id}" class="tablelink lookScource" title="预览"><i class="icon Hui-iconfont" style="color: #000;">&#xe695;</i></a> --%>
			</div>
		</div>
		<!-- <div class="row cl">
			<div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-3">
				<input class="btn btn-primary radius" type="button" id="sub" value="&nbsp;&nbsp;上传&nbsp;&nbsp;">
			</div>
		</div> -->
	</form>
</article>

<!--_footer 作为公共模版分离出去-->
 <script src="${staticPath}/static/js/jquery-1.7.2.js"></script>
<script type="text/javascript" src="${staticPath}/static/lib/layer/2.4/layer.js"></script>
<script type="text/javascript" src="${staticPath}/static/h-ui/js/H-ui.min.js"></script> 
<script type="text/javascript" src="${staticPath}/static/h-ui.admin/js/H-ui.admin.js"></script> <!--/_footer 作为公共模版分离出去-->
<script src="${staticPath}/static/plugins/zTree/js/jquery.ztree.core.min.js"></script>
<script src="${staticPath}/static/plugins/zTree/js/jquery.ztree.excheck.min.js"></script>
<script type="text/javascript">
	function jumpUrl(){
		/* var id = $("#courseId").val(); */
		top.layer.closeAll();
	}
	$("#result_win").hide();
	$(".cnki").click(function(){
		var type =  $("input[name='type']:checked").val();
		var id = $(this).attr("idVal");
		top.layer.open({
			title: false,
			type: 2, shadeClose: true, shade: 0.3, area: ['800px', '90%'],
			content: rootPath+'/back/report/cnki/'+ id +'/'+ type, 
			btn: ['取消']
		});
	}); 
	$("#sub").click(function(){
		if($("#score").val()=="")
		{
			top.layer.alert("请填写分数！！！", {icon: 0},function(index){
				top.layer.close(index);
			});
			return false;
		}
		document.getElementById("result_win").contentWindow.document.body.innerText = "";
		top.layer.confirm('将要提交，是否继续？', {
		 btn: ['是','否'] //按钮
		 }, 
		 function(con){
		 	top.layer.close(con);
		    var index = top.layer.load(0, {shade: [0.1,'#000']}); 
			$("#scoreForm").submit();
			
		  	var timer = setInterval(function(){    //开启定时器
		  		var jsonStr = $("#result_win").contents().find("body").html();
		  		if(jsonStr != "" && jsonStr != null){
		  			var info = JSON.parse(jsonStr); 
			        if(info!="" && info!=null){
				 		top.layer.close(index);
				 		clearInterval(timer);    //清除定时器
				 		if(info.success==true){
						 	top.layer.alert(info.msg, {icon: 1},function(){
						 		top.layer.closeAll();
							 	/* parent.$('.btn btn-success radius r').click(); */
							});
					 	 }else{
						 	top.layer.alert(info.msg, {icon: 2},function(){
								top.layer.close(index);
							});
						 }
					 }
		  		}
			},1000); 
	 	});
	}); 
	
	var isIE = /msie/i.test(navigator.userAgent) && !window.opera; 
	
	function fileChange(target) {
		var fileSize = 0;
		var filetypes = [ ".png",".jpg",".doc",".docx" ];
		var filepath = target.value;
		var filemaxsize = 1024 * 10;//10M 
		if (filepath) {
			var isnext = false;
			var fileend = filepath.substring(filepath.indexOf("."));
			if (filetypes && filetypes.length > 0) {
				for (var i = 0; i < filetypes.length; i++) {
					if (filetypes[i] == fileend) {
						isnext = true;
						break;
					}
				}
			}
			if (!isnext) {
				top.layer.alert("不接受此文件类型！",function(index){
					top.layer.close(index);
				});
				target.value = "";
				return false;
			}
		} else {
			return false;
		}
		if (isIE && !target.files) {
			var filePath = target.value;
			var fileSystem = new ActiveXObject("Scripting.FileSystemObject");
			if (!fileSystem.FileExists(filePath)) {
				top.layer.alert("附件不存在，请重新输入！",function(index){
					top.layer.close(index);
				});
				return false;
			}
			var file = fileSystem.GetFile(filePath);
			fileSize = file.Size;
		} else {
			fileSize = target.files[0].size;
		}

		var size = fileSize / 1024;
		if (size > filemaxsize) {
			top.layer.alert("附件大小不能大于" + filemaxsize / 1024 + "M！",function(index){
				top.layer.close(index);
			});
			target.value = "";
			return false;
		}
		if (size <= 0) {
			top.layer.alert("附件大小不能为0M！",function(index){
				top.layer.close(index);
			});
			target.value = "";
			return false;
		}
	}
	$("#file_upload1").change(function(){
		var target = $(this);
		var fi = $("#file_upload1").val();
		if(fi!=""){
			fileChange(target[0]);
		}
	});
	$("#file_upload2").change(function(){
		var target = $(this);
		var fi = $("#file_upload2").val();
		if(fi!=""){
			fileChange(target[0]);
		}
	});
	$("#file_upload3").change(function(){
		var target = $(this);
		var fi = $("#file_upload3").val();
		if(fi!=""){
			fileChange(target[0]);
		}
	});
	$(".lookScource").click(function() {
		var id = $(this).attr("idVal");
		top.layer.open({
			title: false,
			type: 2, shadeClose: true, shade: 0.3, area: ['800px', '90%'],
			content: rootPath+'/back/report/lookScource/'+ id, 
			btn: ['取消']
		});
	});
	$(".lookReport").click(function() {
		var id = $(this).attr("idVal");
		top.layer.open({
			title: false,
			type: 2, shadeClose: true, shade: 0.3, area: ['800px', '90%'],
			content: rootPath+'/back/report/lookReport/'+ id, 
			btn: ['取消']
		});
	});
	$(".lookPicture").click(function() {
		var id = $(this).attr("idVal");
		top.layer.open({
			title: false,
			type: 2, shadeClose: true, shade: 0.3, area: ['800px', '90%'],
			content: rootPath+'/back/report/lookPicture/'+ id, 
			btn: ['取消']
		});
	});
	
	var zNodes=null;
	var id = $("#id").val();
	var setting = {
		async: {
			enable: true,
			type: "get",
			/* autoParam:["id"], */
			dataType: "json",
			url: rootPath+"/back/report/findAllTree/"+id
		},
		check: {
			enable: true,
			 chkStyle: "radio",
			 chkDisabled:true
		},
		callback : {
			onCheck : onCheck,
			onAsyncError : onAsyncError,
			onAsyncSuccess : onAsyncSuccess
		}
	};
	function onCheck(e, treeId, treeNode) {
		var zTree = $.fn.zTree.getZTreeObj("privilegeTree");
		checkNodes = zTree.getCheckedNodes(true);
		var judges = "";
		for (var i = 0, l = checkNodes.length; i < l; i++) {
			var nodeId = checkNodes[i].id;
			judges += nodeId + ",";
		}
		if (judges.length != 0) {
			judges = judges.substring(0,
					judges.length - 1);
		}
		$("#judges").val(judges);
	}
	function onAsyncError(event, treeId, treeNode, XMLHttpRequest, textStatus, errorThrown) {
		var zTree = $.fn.zTree.getZTreeObj("privilegeTree");
		//top.layer.closeAll();
		top.layer.alert("加载失败，请稍后重试！");
		zTree.updateNode(treeNode);
	}
	function onAsyncSuccess(event, treeId, treeNode, msg) {
		var zTree = $.fn.zTree.getZTreeObj("privilegeTree")
		var str_judges;
		var id = $("#id").val();
		$.ajax({  
		    type:'post',      
		    url:rootPath+"/back/report/findJudgeIdListBySelectcourseId",
		    data:{"id":id},
		    cache:false,  
		    dataType:'json',  
		    success:function(data){
		    	str_judges = data.obj;
		    	function zTreemodify(obj) {
					for (var x = 0; x < str_judges.length; x++) {
						if (obj.id == str_judges[x]) {
							zTree.checkNode(obj, true, false);
						}
					}
					var children = obj.children;
					if (children == null || children.length == 0){
						delete obj.children;
					}else{
						for (var i = 0; i < children.length; i++) {
							zTreemodify(children[i]);
						}
					}
					return obj;
				};
				
				$.each(zTree.getNodes(), function(i, n) {
					zTreemodify(n);
				});
		    }
		});
		zTree.expandAll(true);
	}
	$(document).ready(function(){
		$.fn.zTree.init($("#privilegeTree"), setting);
	});
	function getFormData(){
		var formData=$("#judges").val();
		return formData;
	}
</script> 
</body>
</html>