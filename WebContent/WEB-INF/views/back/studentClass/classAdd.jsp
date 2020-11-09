<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>修改专业信息</title>
<%@ include file="/commons/basejs.jsp"%>
<%@ page import="java.util.Calendar"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<!--[if lt IE 9]>
<script type="text/javascript" src="${path }/lib/html5.js"></script>
<script type="text/javascript" src="${path }/lib/respond.min.js"></script>
<script type="text/javascript" src="${path }/lib/PIE_IE678.js"></script>
<![endif]-->
<link rel="stylesheet" type="text/css" href="${staticPath }/static/h-ui/css/H-ui.min.css" />
<link rel="stylesheet" type="text/css" href="${staticPath }/static/h-ui.admin/css/H-ui.admin.css" />
<link rel="stylesheet" type="text/css" href="${staticPath }/static/lib/Hui-iconfont/1.0.8/iconfont.css" />
<link rel="stylesheet" type="text/css" href="${staticPath }/static/lib/icheck/icheck.css" />
<link rel="stylesheet" type="text/css" href="${staticPath }/static/h-ui.admin/skin/default/skin.css" id="skin" />
<link rel="stylesheet" type="text/css" href="${staticPath }/static/h-ui.admin/css/style.css" />
<link rel="stylesheet" type="text/css" href="${staticPath }/static/css/style.css" />
<link rel="stylesheet" type="text/css" href="${staticPath }/static/css/practice.css"/>
<!--[if IE 6]>
<script type="text/javascript" src="http://lib.h-ui.net/DD_belatedPNG_0.0.8a-min.js" ></script>
<script>DD_belatedPNG.fix('*');</script>
<![endif]-->
</head>
<body>
<nav class="breadcrumb">
	<i class="Hui-iconfont" style="color: #000;">&#xe67f;</i> 首页 
	<span class="c-gray en">&gt;</span>信息管理
	<span class="c-gray en">&gt;</span>班级管理
	<span class="c-gray en">&gt;</span>添加班级
</nav>
<article class="page-container">
	<form class="form form-horizontal" method="post" action="${staticPath}/back/studentClass/studentClassAdd" id="classForm">
		<input type="hidden" name="id" value="${studentClass.id }"/>
		<div class="page-container_tittle">
			<i class="firm_det_tit_line"></i>添加班级
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>所属学校：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<select class="select"  id="schoolId" name="schoolId">
								<option value="">--所属学校--</option>
								<c:forEach var="delist" items="${schoolList}">
									<option value="${delist.id}">${delist.name}</option>
								</c:forEach>
				</select>
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>所属院系：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<select class="select"  id="departmentId" name="departmentId">
								<option value="">--所属院系--</option>
				</select>
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>所属专业：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<select class="select"  name="majorId" id="majorId">
								<option value="">--所属专业--</option>
				</select>
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>年级：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<select class="select" name="year" id="year" >  
						<option value="">--请选择--</option>  
						<% Calendar cal = Calendar.getInstance();  
						int year = cal.get(Calendar.YEAR);  
						for(int i=0; i<=4; i++){   
						    String str = String.valueOf(year);  
						    if(str.equals(request.getParameter("year"))){  
						%>         
						        <option selected="selected"><%=str%></option>  
						<%} else{%>  
						    <option><%=str%></option>  
						<%}  year = year - 1;  
						}%>    
					</select> 
				
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>班级编码：</label>
			<div class="formControls col-xs-8 col-sm-9">
				
				<input type= "text" class="input-text" name= "code" id= "code" placeholder="班级编码" />
				<input type="text" style="display:none; color :#F00 ;width:100%;"  id="msg" name="msg" disabled>
				
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>班级名称：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text"  placeholder="" id="name" name="name"  >
				<input type="text" style="display:none; color :#F00 ;width:100%;"  id="nmsg" name="nmsg" disabled>
			</div>
		</div>
	</form>
</article>

<!--_footer 作为公共模版分离出去-->
<script type="text/javascript" src="${path }/lib/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript" src="${staticPath }/static/lib/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript" src="${staticPath }/static/lib/layer/2.4/layer.js"></script> 
<script type="text/javascript" src="${staticPath }/static/lib/icheck/jquery.icheck.min.js"></script> 
<script type="text/javascript" src="${staticPath}/static/plugins/validate/jquery.validate.min.js"></script>
<script type="text/javascript" src="${staticPath}/static/plugins/validate/localization/messages_zh.min.js"></script>
<!--/_footer /作为公共模版分离出去-->

<!--请在下方写此页面业务相关的脚本-->
<script type="text/javascript">
/* 学校--院系联动 */
var schoolId ="${schoolId}";
if(schoolId != null && schoolId!= ""){
	changeInfo(schoolId);
}
$("#schoolId").change(function(){
	schoolId=$("#schoolId").val();
	changeInfo(schoolId);
});
function changeInfo(schoolId){
	var departmentId = "${departmentId}";
	$("#departmentId").empty();
	$("#departmentId").append('<option value="">---请选择---</option>');

	$.ajax({
		url : "${staticPath }/back/studentClass/seletDepartment",
		type: "post",
		data: {"schoolId" : schoolId},
		dataType: "json",
		success:function(data){
			var types=data.obj;
			if(types!=null && types.length>0){
				for(var i=0;i<types.length;i++){
					$("#departmentId").append(
							'<option value="' + types[i].id + '" ' + (departmentId ==types[i].id   ? 'selected=selected':'') + '>' + types[i].name + '</option>');
				}
			}
		}
		
	});
}
$(function() {
$("#departmentId").change(function(){
	var departmentId = document.getElementById("departmentId").value;
	$.ajax({
		url:"${staticPath }/back/studentClass/seletMajor",
		type:"post",
		dataType : "json",
		data:{departmentId:departmentId},
		success:function(data){
			var types=data.obj;
			if(types!=null && types.length>0){
				$("#majorId").empty();
				$("#majorId").append(
						"<option value=''>--所属专业--</option>"
					);
				for(var i=0;i<types.length;i++){
					$("#majorId").append(
						"<option  value='"+types[i].id+"'>"
					+types[i].name+"</option>");
				}
			}else{
				$("#majorId").empty();//清空
				$("#majorId").append(
					"<option value=''>--所属专业--</option>"
				);
			}
		}
	});
});

/* $("#code").change(function(){
	var code = document.getElementById("code").value;

        // 异步验证班级编码是否重复
        
             $.ajax({
            	 url:"${staticPath }/back/studentClass/manager",
         		type:"post",
         		dataType : "json",
         		data:{
         			departmentId:departmentId,
         			majorId:majorId,
         			grade:year	
         		},
                success: function(message){
                    $("#message").html(message);
                }
            }); 
        }
}); */



$("#classForm").validate({
	rules : {
		code : {
			required : true
		},
		name : {
			required : true
		},
		year : {
			required : true
		},
		majorId:{
			required : true
		},
		departmentId : {
			required : true
		},
		schoolId : {
			required : true
		},
	},
	messages : {
		code : {
			required : "请输入编号"
		},
		name : {
			required : "请输入名称"
		},
		year : {
			required : "请选择年级"
		},
		majorId:{
			required : "请选择专业"
		},
		departmentId:{
			required : "请选择院系"
		},
		schoolId:{
			required : "请选择学校"
		},
	},
});
});
function getFormData() {
	if ($("#classForm").valid()) {
		var data = $("#classForm").serialize();
		return data;
	} else {
		return 0;
	}
}
/*************************************/
$("#code").blur(function(){
	var code = document.getElementById("code").value;
	  $.ajax({
		  url:"${staticPath }/back/studentClass/seletClassByCode",
		  async: false,
		  type: "post",
		  dataType: "json",
		  data:{
			  classCode : code
		  },
		  success : function(data){
			  if (data.msg != ""){
				  $("#msg") .val(data.msg);
				  $("#msg").css( "display","block" );
				  $("#code").val("");
			  }else{
				  $("#msg").val("");
				  $("#msg").css('display','none');
			  }
		  }
	  });
});
$("#name").blur(function(){
	var name = document.getElementById("name").value;
	  $.ajax({
		  url:"${staticPath }/back/studentClass/seletClassByName",
		  async: false,
		  type: "post",
		  dataType: "json",
		  data:{
			  className : name
		  },
		  success : function(data){
			  if (data.msg != ""){
				  $("#nmsg") .val(data.msg);
				  $("#nmsg").css( "display","block" );
				  $("#name").val("");
			  }else{
				  $("#nmsg").val("");
				  $("#nmsg").css('display','none');
			  }
		  }
	  });
});


/**************************************/


function jumpUrl(){
	top.layer.closeAll();
}

/* $(function(){
 	var classForm;
	$("#classForm").click(function(){
		if(!classForm.form()){
			return false;
		}else{
			classAdd();
		}
	}); 
	classForm=

}); */
</script>
<!--/请在上方写此页面业务相关的脚本-->
</body>
</html>