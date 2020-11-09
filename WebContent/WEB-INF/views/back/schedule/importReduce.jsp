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
<title>解压</title>
</head>
<body>
<article class="page-container">
	<iframe id="result_win" name="result_win"></iframe><br>
	<form action="${staticPath }/back/schedule/importReduce" class="form form-horizontal"  target="result_win" id="importForm" method="POST" enctype="multipart/form-data">
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">选择院系：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<span class="select-box" style="width:150px;">
					<select class="select" name="departName" size="1">
						<c:forEach items="${list }" var="department">
							<option value="${department.name }">${department.name }</option>
						</c:forEach>
					</select>
				</span> 
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">附件：</label>
			<div class="formControls col-xs-8 col-sm-9"> <span class="btn-upload form-group">
					<input class="input-text upload-url" type="text" id="uploadfile" readonly nullmsg="请添加附件！" style="width:200px">
					<a href="javascript:void();" class="btn btn-primary radius upload-btn"><i class="Hui-iconfont">&#xe642;</i> 浏览文件</a>
					<input type="file" name="file" class="input-file" id="file_upload">
				</span> 
			</div>
		</div>
		<div class="row cl">
			<div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-3">
				<input class="btn btn-primary radius" type="button" id="sub" value="&nbsp;&nbsp;上传&nbsp;&nbsp;">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"></label>
			<label class="form-label col-xs-4 col-sm-3" style="color:red;">仅支持ZIP 、RAR 文件</label>
		</div>
	</form>
</article>

<!--_footer 作为公共模版分离出去-->
 <script src="${staticPath}/static/js/jquery-1.7.2.js"></script>
<script type="text/javascript" src="${staticPath}/static/lib/layer/2.4/layer.js"></script>
<script type="text/javascript" src="${staticPath}/static/h-ui/js/H-ui.min.js"></script> 
<script type="text/javascript" src="${staticPath}/static/h-ui.admin/js/H-ui.admin.js"></script> <!--/_footer 作为公共模版分离出去-->

<!--请在下方写此页面业务相关的脚本--> 
<script type="text/javascript" src="${staticPath}/static/lib/My97DatePicker/4.8/WdatePicker.js"></script>
<script type="text/javascript">
	function jumpUrl(){
		var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
		top.layer.closeAll();
		parent.location.href=rootPath+'/back/scheduleManager/manager';
		parent.layer.close(index);
	}
	$("#result_win").hide();
	$("#sub").click(function(){
		if($("#file_upload").val()=="")
		{
			top.layer.alert("请选择需要压缩的文件！！！", {icon: 0},function(index){
				top.layer.close(index);
			});
			return false;
		}
		document.getElementById("result_win").contentWindow.document.body.innerText = "";
		top.layer.confirm('将要上传课程安排信息，是否继续？', {
		 btn: ['是','否'] //按钮
		 }, 
		 function(con){
		 	top.layer.close(con);
		    var index = top.layer.load(0, {shade: [0.1,'#000']}); 
			$("#importForm").submit();
		  	var timer = setInterval(function(){    //开启定时器
		  		var jsonStr = $("#result_win").contents().find("body").html();
		  		if(jsonStr != "" && jsonStr != null){
		  			var info = JSON.parse(jsonStr); 
			        if(info!="" && info!=null){
				 		top.layer.close(index);
				 		clearInterval(timer);    //清除定时器
				 		if(info.success==true){
						 	top.layer.alert(info.msg, {icon: 1},function(){
								jumpUrl();
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
		var filetypes = [ ".zip",".rar" ];
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
</script> 
<!--/请在上方写此页面业务相关的脚本-->
</body>
</html>