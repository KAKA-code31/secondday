<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>学生上传</title>
<%@ include file="/commons/basejs.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport"
	content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<link rel="stylesheet" type="text/css" href="${staticPath }/static/h-ui/css/H-ui.min.css" />
<link rel="stylesheet" type="text/css" href="${staticPath }/static/h-ui.admin/css/H-ui.admin.css" />
<link rel="stylesheet" type="text/css" href="${staticPath }/static/lib/Hui-iconfont/1.0.8/iconfont.css" />
<link rel="stylesheet" type="text/css" href="${staticPath }/static/lib/icheck/icheck.css" />
<link rel="stylesheet" type="text/css" href="${staticPath }/static/h-ui.admin/skin/default/skin.css" id="skin" />
<link rel="stylesheet" type="text/css" href="${staticPath }/static/h-ui.admin/css/style.css" />
<link rel="stylesheet" type="text/css" href="${staticPath }/static/css/style.css" />
<link href="${staticPath}/static/plugins/uploadify/uploadify.css" rel="stylesheet" type="text/css" />
<style type="text/css">
.buttonCSS{
	height:30px;
	width:70px;
    background-color:#1AAD19;
    text-align: center;
    line-height: 2.55555556;
    color: #FFFFFF;
    border-radius: 5px;
}
</style>
</head>
<body>
	<nav class="breadcrumb">
		<i class="Hui-iconfont" style="color: #000;">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span>信息管理
		<span class="c-gray en">&gt;</span> 学生信息管理
		<span class="c-gray en">&gt;</span> 学生上传
	</nav>
	<iframe id="result_win" name="result_win"></iframe><br>
    <div class="formbody">
    	<form target="result_win" action="${staticPath }/back/student/import" method="post"
				id="importForm" enctype="multipart/form-data">
				上传学生信息文件：<input type="file" name="file" id="file_upload" />
				<input class="buttonCSS" type="button" id="sub" value="上传" />
		</form>
    </div>
    <br>
    <div class='formbody'>
		<pre style="color:#000;font-size: 14px;">提示：请按照模板填写数据，新添加的所有数据的单元格格式统一设置为文本格式！！！<br/>  
		</pre>
    </div>
    <div class='formbody'>
    	<p style="color: blue;">&gt;&gt;&gt;&gt;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0);" id="downloadTemplet" style="font-size: 15px;color:blue;font-weight: bold;">模板下载</a></p>
    </div>
    
 <script src="${staticPath}/static/js/jquery-1.7.2.js"></script>
<script type="text/javascript" 
		src="${staticPath }/static/plugins/validate/jquery.validate.min.js"></script>
<script type="text/javascript">
function jumpUrl(){
	window.location.href = "${staticPath}/back/student/manager";
}
	$("#result_win").hide();
	$("#sub").click(function(){
		if($("#file_upload").val()=="")
		{
			top.layer.alert("请选择学生信息文件！！！", {icon: 0},function(index){
					top.layer.close(index);
			});
			return false;
		}
		document.getElementById("result_win").contentWindow.document.body.innerText = "";
		if ($("#importForm").valid()) {
			top.layer.confirm('将要上传学生信息，是否继续？', {
			 btn: ['是','否'] //按钮
			 }, 
			 function(con){
			 	top.layer.close(con);
			    var index = top.layer.load(0, {shade: [0.1,'#000']}); 
				$("#importForm").submit();
				  var timer = setInterval(function(){    //开启定时器
					 var info = eval("("+$("#result_win").contents().find("body").html()+")");
			         if(info!="" && info!=null){
				 		top.layer.close(index);
				 		clearInterval(timer);    //清除定时器
					 }
					 if(info.success==true){
					 	top.layer.alert("学生信息上传成功！", {icon: 1},function(index){
							top.layer.close(index);
							jumpUrl();
						});
				 	 }else{
					 	top.layer.alert(info.msg, {icon: 2},function(index){
							top.layer.close(index);
						});
					 } 
				},1000); 
		 	});
		 }else{
			return 0; 
		}
	}); 
	
	var isIE = /msie/i.test(navigator.userAgent) && !window.opera; 
	
	function fileChange(target) {
		var fileSize = 0;
		var filetypes = [ ".xls",".xlsx" ];
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
	$("#file_upload").change(function(){
		var target = $(this);
		var fi = $("#file_upload").val();
		if(fi!=""){
			fileChange(target[0]);
		}
	});
	
	$("#downloadTemplet").click(function(){
		window.location.href = rootPath+"/back/student/excelDownload";
	});
</script>
</body>
</html>