<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>添加教师项</title>
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
		<i class="Hui-iconfont" style="color: #000;">&#xe67f;</i> 首页 
		<span class="c-gray en">&gt;</span>信息管理
		<span class="c-gray en">&gt;</span>教师信息
		<span class="c-gray en">&gt;</span>教师添加
	</nav>
<article class="page-container">
	<form class="form form-horizontal" id="addForm">
		<div class="page-container_tittle">
				<i class="firm_det_tit_line"></i>添加教师
		</div>
		<div class="row cl">
		<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>教工号：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="" id="studentUserId" name="userId">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>姓名：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text"  placeholder="" id="studentName" name="name">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>性别：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<select name="sex" id="sex" class="select">
							<option value="">请选择</option>
							<option value="1">男</option>
							<option value="2">女</option>
				</select>
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>学校：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<select name="schoolId" class="select" id="school">
					<option value="">--选择学校--</option>
					<c:forEach var="school" items="${schools}">
						<option value="${school.id}"
							${school.encryptId==studentVo.school.encryptId?'selected="selected"':'' }>${school.name}</option>
					</c:forEach>
				</select>
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>院系：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<select name="departId" class="select" id="depart">
					<option value="">--选择学院--</option>
				</select>
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2">号码：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input name="phoneno" type="text" class="input-text" id="phoneno" />
			</div>
		</div>	
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2">邮箱（选填）：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input name="email" type="text" class="input-text" id="email" />
			</div>
		</div>
	</form>
</article>
</body>
<script type="text/javascript"
	src="${staticPath}/static/plugins/validate/jquery.validate.min.js"></script>
<script type="text/javascript"
	src="${staticPath}/static/plugins/validate/localization/messages_zh.min.js"></script>
<script type="text/javascript"
	src="${staticPath}/static/plugins/bootstrap/js/bootstrap-datepicker.js"></script>
<script type="text/javascript">
	$("#studentEntrancetime").datepicker({
	    format: 'yyyy',  
        weekStart: 1,  
        autoclose: true,  
        startView: 2,  maxViewMode: 2,minViewMode:2,
        forceParse: false,  
        language: 'zh-CN'  
	});
	$(function() {
		$("#addForm").validate({
			rules : {
				'userId' : {
					required : true,
					digits:true
				},
				'name' : {
					required : true
				},
				'department.encryptId' : {
					required : true
				},
				'school.encryptId' : {
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
					required : '请输入账号',
					digits:'账号格式不正确'
				},
				'name' : {
					required : '请输入教师姓名'
				},
				'department.encryptId' : {
					required : '请选择学院'
				},
				'school.encryptId' : {
					required : '请选择学校'
				},
				'sex' : {
					required : '请选择性别'
				},
				'phoneno' : {
					rangelength:"手机号11位"
				},
				'email' : {
					email : '邮箱格式不正确'
				},
			},
		});
	});
	
	jQuery.validator.addMethod("idcardno", function(value, element){
		return this.optional(element) || IdentityCodeValid(value);
	}, "请正确输入身份证号码");
	
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
        console.info("IdentityCodeValid tip="+tip);
        if(!pass) ;
        return pass;
    }
  
	function getFormData() {
		if ($("#addForm").valid()) {
			var data = $("#addForm").serialize();
			return data;
		} else {
			return 0;
		}
	}

	function jumpUrl(){
		top.layer.closeAll();
	}
	
	$("#school").change(function(){
		var school = $("#school").val();
		changeSchoolEncryptId(school);
	})
	function changeSchoolEncryptId(schoolEncryptId){
		var departId = "${studentVo.depart.encryptId}";
		$("#depart").empty();
		$("#depart").append('<option value="">---请选择院系---</option>');
		$.ajax({
			url :  rootPath+ "/back/teacher/selectDepart",
			type: "post",
			data: {"schoolId" : schoolEncryptId},
			dataType: "json",
			success: function(departList){
				$.each(departList, function(i, depart){
					$("#depart").append('<option value="' + depart.id + '" ' + (departId == depart.encryptId   ? 'selected=selected':'') + '>' + depart.name + '</option>');
				});
			}
		});
	}
	
</script>
</html>