<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>修改教师信息</title>
<%@ include file="/commons/basejs.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<link rel="stylesheet" type="text/css" href="${staticPath }/static/lib/webuploader/0.1.5/webuploader.css"/>
<link rel="stylesheet" type="text/css" href="${staticPath }/static/h-ui/css/H-ui.min.css" />
<link rel="stylesheet" type="text/css" href="${staticPath }/static/h-ui.admin/css/H-ui.admin.css" />
<link rel="stylesheet" type="text/css" href="${staticPath }/static/lib/Hui-iconfont/1.0.8/iconfont.css" />
<link rel="stylesheet" type="text/css" href="${staticPath }/static/lib/icheck/icheck.css" />
<link rel="stylesheet" type="text/css" href="${staticPath }/static/h-ui.admin/skin/default/skin.css" id="skin" />
<link rel="stylesheet" type="text/css" href="${staticPath }/static/h-ui.admin/css/style.css" />
<link rel="stylesheet" type="text/css" href="${staticPath }/static/css/style.css" />
<link rel="stylesheet" type="text/css" href="${staticPath }/static/css/practice.css"/>
</head>
<body style="min-width: 600px;">
	<nav class="breadcrumb">
		<i class="Hui-iconfont" style="color: #000;">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span>信息管理
		<span class="c-gray en">&gt;</span> 教师管理
		<span class="c-gray en">&gt;</span> 教师信息修改
	</nav>
	<article class="page-container">
		<form id="editForm" class="form form-horizontal">
			<div class="page-container_tittle">
				<i class="firm_det_tit_line"></i>教师信息
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-2">
					<span class="c-red">*</span>职工号
				</label>
				<div class="formControls col-xs-8 col-sm-9">
					<input name="userId" type="text" class="input-text" id="teacherUserId" value="${teacherVo.userId}"/>
					<input name="id" type="text" style="display: none"
					class="input-text" id="teacherId" value="${teacherVo.id}"/>
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-2">
					<span class="c-red">*</span>姓名
				</label>
				<div class="formControls col-xs-8 col-sm-9">
					<input  name="name" type="text" id="teacherName" class="input-text" value="${teacherVo.name}"/>
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-2">
					<span class="c-red">*</span>性别
				</label>
				<div class="formControls col-xs-8 col-sm-9">
					<select name="sex" class="input-text" id="sex">
						<option value="1" ${teacherVo.sex==1?"selected":''} >男</option>
						<option value="2" ${teacherVo.sex==2?"selected":''} >女</option>
					</select>
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-2">
					手机号（选填）
				</label>
				<div class="formControls col-xs-8 col-sm-9">
					<input  name="phoneno" type="text" id="phoneno" class="input-text" value="${teacherVo.phoneno}"/>
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-2">
					邮箱（选填）
				</label>
				<div class="formControls col-xs-8 col-sm-9">
					<input  name="email" type="text" id="email" class="input-text" value="${teacherVo.email}"/>
				</div>
			</div>
	</form>
	</article>
</body>
<script type="text/javascript" src="${path }/lib/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript" src="${staticPath }/static/lib/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript" src="${staticPath }/static/lib/layer/2.4/layer.js"></script> 
<script type="text/javascript" src="${staticPath }/static/lib/icheck/jquery.icheck.min.js"></script> 
<script type="text/javascript" src="${staticPath}/static/plugins/validate/jquery.validate.min.js"></script>
<script type="text/javascript" src="${staticPath}/static/plugins/validate/localization/messages_zh.min.js"></script>
<script type="text/javascript">
	$(function() {
		$("#editForm").validate({
			rules : {
				'userId' : {
					required : true,
					digits:true
				},
				'name' : {
					required : true
				},
				'sex' : {
					required : true
				},
				'phoneno' : {
					rangelength:[11,11]
				},
				'email' : {
					email:true
				},
			},
			messages : {
				'userId' : {
					required : '请输入职工号',
					digits:'职工号格式不正确，请输入十位数字组合。'
				},
				'name' : {
					required : '请输入学生姓名'
				},
				'sex' : {
					required : '请选择性别'
				},
				'phoneno' : {
					rangelength:"11字符"
				},
				'email' : {
					email : '邮箱格式不正确'
				},
			},
		});
	});
	
/* 	jQuery.validator.addMethod("idcardno", function(value, element){
		return this.optional(element) || IdentityCodeValid(value);
	}, "请正确输入身份证号码"); */
	
	function IdentityCodeValid(code) { 
        var city={11:"北京",12:"天津",13:"河北",14:"山西",15:"内蒙古",21:"辽宁",22:"吉林",23:"黑龙江 ",31:"上海",32:"江苏",33:"浙江",34:"安徽",35:"福建",36:"江西",37:"山东",41:"河南",42:"湖北 ",43:"湖南",44:"广东",45:"广西",46:"海南",50:"重庆",51:"四川",52:"贵州",53:"云南",54:"西藏 ",61:"陕西",62:"甘肃",63:"青海",64:"宁夏",65:"新疆",71:"台湾",81:"香港",82:"澳门",91:"国外 "};
        var tip = "";
        var pass= true;
        
        if(!code || !/^\d{6}(18|19|20)?\d{2}(0[1-9]|1[12])(0[1-9]|[12]\d|3[01])\d{3}(\d|X)$/i.test(code)){
            tip = "身份证号格式错误";
            pass = false;
        }
        
       else if(!city[code.substr(0,2)]){
            tip = "地址编码错误";
            pass = false;
        }
        else{
            //18位身份证需要验证最后一位校验位
            if(code.length == 18){
                code = code.split('');
                //∑(ai×Wi)(mod 11)
                //加权因子
                var factor = [ 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 ];
                //校验位
                var parity = [ 1, 0, 'X', 9, 8, 7, 6, 5, 4, 3, 2 ];
                var sum = 0;
                var ai = 0;
                var wi = 0;
                for (var i = 0; i < 17; i++)
                {
                    ai = code[i];
                    wi = factor[i];
                    sum += ai * wi;
                }
                var last = parity[sum % 11];
                if(parity[sum % 11] != code[17]){
                    tip = "校验位错误";
                    pass =false;
                }
            }
        }
        if(!pass) ;
        return pass;
    }
	
	function getFormData() {
		if ($("#editForm").valid()) {
			var data = $("#editForm").serialize();
			data= decodeURIComponent(data,true);
			return data;
		} else {
			return 0;
		}
	}
var departmentEncryptId = "${studentVo.department.encryptId}" ;
if(departmentEncryptId != null && departmentEncryptId != ""){
	changeDepartmentEncryptId(departmentEncryptId);
}
$("#depart").change(function(){
	var depart = $("#depart").val();
	changeDepartmentEncryptId(depart);
})
function changeDepartmentEncryptId(departmentEncryptId){
	var majorId = "${studentVo.major.encryptId}";
	$("#major").empty();
	$("#major").append('<option value="">---请选择专业---</option>');
	$.ajax({
		url :  rootPath+ "/back/student/selectMajor/",
		type: "post",
		data: {"departId" : departmentEncryptId},
		dataType: "json",
		success: function(majorList){
			$.each(majorList, function(i, major){
				$("#major").append('<option value="' + major.encryptId + '" ' + (majorId == major.encryptId   ? 'selected=selected':'') + '>' + major.name + '</option>');
			});
		}
	});
}
var majorEncryptId = "${studentVo.major.encryptId}";

if(majorEncryptId!=null && majorEncryptId!=''){
	changeStudentClassEncryptId(majorEncryptId);
}

$("#major").change(function(){
	var major = $("#major").val();
	changeStudentClassEncryptId(major);
});

function changeStudentClassEncryptId(studentClassEncryptId){
	var studentClassId = "${studentVo.studentClass.encryptId}";
	$("#studentClass").empty();
	$("#studentClass").append('<option value="">---请选择班级---</option>');
	$.ajax({url : rootPath+ "/back/student/selectClass/",
		type : "post",
		dataType : "json",
		data : {
			majorId : studentClassEncryptId
		},
		success : function(classList) {
			$.each(classList, function(i, studentClass){
				$("#studentClass").append('<option value="' + studentClass.encryptId + '" ' + (studentClassId == studentClass.encryptId   ? 'selected=selected':'') + '>' + studentClass.name + '</option>');
			});
		}
	});
}

	$("#studentUserId").blur(function() {
		var userId = document.getElementById("studentUserId").value;
		$.ajax({
			url : rootPath + "/back/student/selectStudent/",
			async : false,
			type : "post",
			dataType : "json",
			data : {
				userId:userId
			},
			success : function(data) {
				if(data.msg!=null && data.msg!=""){
				 }
				},
			error : function() {
				top.layer.alert("该学生学号已存在!", {
					icon : 5
				}, function() {
					jumpUrl();
				});
			}
			});
		});
	
</script>

</html>