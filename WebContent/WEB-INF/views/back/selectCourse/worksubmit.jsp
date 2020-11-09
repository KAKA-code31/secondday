<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ include file="/commons/global.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="/commons/basejs.jsp"%>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
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
<title>提交</title>
</head>
<body>
<nav class="breadcrumb">
	<i class="Hui-iconfont" style="color: #000;">&#xe67f;</i> 首页 
	<span class="c-gray en">&gt;</span>作业提交<font color="red"> (注意：实验报告、源码、截图都以word形式提交，且以“学号-姓名-课程名-实验名”命名提交)</font>
</nav>
<article class="page-container">
	<iframe id="result_win" name="result_win"></iframe><br>
	<form action="${staticPath }/back/report/submit1" class="form form-horizontal"  target="result_win" id="importForm" method="POST" enctype="multipart/form-data">
		<%-- <input type = "hidden" value="${experiment.classId }" name ="classId" id="classId"/>
		<input type = "hidden" value="${experiment.etId }" name ="etId" id="etId"/> --%>
		<input type = "hidden" value="" name ="teacherId" id="teacherId"/>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">课程名称：</label>
			<div class="formControls col-xs-3 col-sm-3"> 
				<select class="select"  id="courseId" name ="courseId" style="width:400px">
					<option value="">--请选择--</option>
					<c:forEach var="course" items="${courses}">
						<option value="${course.courseId}">${course.courseName}(${course.year}第${course.term}学期})</option>
					</c:forEach>
				</select>
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">实验名称：</label>
			<div class="formControls col-xs-3 col-sm-3"> 
				<select class="select"  id="experimentId" name ="experimentId">
					<option value="">--请选择--</option>
				</select>
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">实验报告（word）：</label>
			<div class="formControls col-xs-8 col-sm-9"> <span class="btn-upload form-group">
					<input class="input-text upload-url" type="text" id="uploadfile" readonly nullmsg="请添加实验报告！" style="width:200px">
					<a href="javascript:void();" class="btn btn-primary radius upload-btn"><i class="Hui-iconfont">&#xe642;</i> 浏览文件</a>
					<input type="file" name="attachs" class="input-file" id="file_upload1">
				</span> 
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">图片（word）：</label>
			<div class="formControls col-xs-8 col-sm-9"> <span class="btn-upload form-group">
					<input class="input-text upload-url" type="text" id="uploadfile" readonly nullmsg="请添加图片！" style="width:200px">
					<a href="javascript:void();" class="btn btn-primary radius upload-btn"><i class="Hui-iconfont">&#xe642;</i> 浏览文件</a>
					<input type="file" name="attachs" class="input-file" id="file_upload2">
				</span> 
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3">源码（word）：</label>
			<div class="formControls col-xs-8 col-sm-9"> <span class="btn-upload form-group">
					<input class="input-text upload-url" type="text" id="uploadfile" readonly nullmsg="请添加源码！" style="width:200px">
					<a href="javascript:void();" class="btn btn-primary radius upload-btn"><i class="Hui-iconfont">&#xe642;</i> 浏览文件</a>
					<input type="file" name="attachs" class="input-file" id="file_upload3">
				</span> 
			</div>
		</div>
		<div class="row cl">
			<div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-3">
				<input class="btn btn-primary radius" type="button" id="sub" value="&nbsp;&nbsp;上传&nbsp;&nbsp;">
			</div>
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
		/* var id = $("#courseId").val(); */
		top.layer.closeAll();
	}
	$("#result_win").hide();
	$("#courseId").change(function(){
		var courseId = document.getElementById("courseId").value;
		$.ajax({
			url:"${staticPath }/back/report/getExperiment",
			type:"post",
			dataType : "json",
			data:{courseId:courseId},
			success:function(data){
				var types=data.obj;
				if(types!=null && types.length>0){
					$("#experimentId").empty();
					for(var i=0;i<types.length;i++){
						$("#experimentId").append(
							"<option selected='selected' value='"+types[i].experimentId+"'>"
						+types[i].experimentName+"</option>");
						console.log('访问地址：' + types[i].experimentId + "," + types[i].teacherId);
						$("#teacherId").val(types[i].teacherId);
					}
					
				}else{
					$("#experimentId").empty();//清空
					$("#experimentId").append(
						"<option value=''>--实验名称--</option>"
					);
				}
			}
		});
	});
	$("#sub").click(function(){
		if($("#courseId").val()=="")
		{
			top.layer.alert("请选择课程名称！！！", {icon: 0},function(index){
				top.layer.close(index);
			});
			return false;
		}
		if($("#id").val()=="")
		{
			top.layer.alert("请选择实验名称！！！", {icon: 0},function(index){
				top.layer.close(index);
			});
			return false;
		}
		if($("#file_upload1").val()=="")
		{
			top.layer.alert("请选择需要提交的实验报告！！！", {icon: 0},function(index){
				top.layer.close(index);
			});
			return false;
		}
/* 		if($("#file_upload2").val()=="")
		{
			top.layer.alert("请选择需要提交的实验截图！！！", {icon: 0},function(index){
				top.layer.close(index);
			});
			return false;
		}
		if($("#file_upload3").val()=="")
		{
			top.layer.alert("请选择需要提交的源码！！！", {icon: 0},function(index){
				top.layer.close(index);
			});
			return false;
		} */
		document.getElementById("result_win").contentWindow.document.body.innerText = "";
		top.layer.confirm('将要提交报告，是否继续？', {
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
				    		top.layer.alert("保存成功！", {icon: 1},function(index){
				 				top.layer.close(index);
				 				location.reload();
				    		});
				    	}else{
				    		top.layer.alert(info.msg, {icon: 2},function(index){
				    			top.layer.close(index);
				    			location.reload();
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
		var filetypes = [ ".doc",".docx" ];
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
</script> 
</body>
</html>