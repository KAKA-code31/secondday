﻿<%@ page language="java" contentType="text/html; charset=UTF-8"
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
<link href="${staticPath}/static/h-ui/css/H-ui.min.css" rel="stylesheet" type="text/css" />
<link href="${staticPath}/static/h-ui.admin/css/H-ui.login.css" rel="stylesheet" type="text/css" />
<link href="${staticPath}/static/h-ui.admin/css/style.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="${staticPath }/static/lib/icheck/icheck.css" />
<script type="text/javascript" src="${staticPath }/static/lib/icheck/jquery.icheck.min.js"></script> 
<link href="${staticPath}/static/lib/Hui-iconfont/1.0.8/iconfont.css" rel="stylesheet" type="text/css" />
<title>实验报告管理系统</title>
<meta name="author" content="合肥正鼎软件科技有限公司" />
<meta name="keywords" content="实验报告管理系统">
<meta name="description" content="实验报告管理系统">
<style type="text/css">
	input[type="radio"]{
		width:16px;height:16px;
	}
	label{font-size:16px;}

	.qrCode{
		width: 120px;
		height: 120px;
		z-index: 10000;
		position: fixed;
		display: block;
		bottom:340px;
		right: 9px;
		background:#FFF;
	}
	.qrCode img{
		width: 120px;
		height: 120px;
	}
	.lpCode{
		z-index: 10000;
		position: fixed;
		display: block;
		bottom:100px;
		right: 9px;
		background:#FFF;
	}
	.lpCode img{
		width: 120px;
		height: 120px;
	}
		
</style>
</head>
<body>
<input type="hidden" id="TenantId" name="TenantId" value="" />
<div class="header"></div>
<div class="loginWraper">
  <div id="loginform" class="loginBox">
    <form class="form form-horizontal"  method="post" id="loginForm" onsubmit="return checkCode();">
    <div class="form form-horizontal">
      <div class="row cl">
        <label class="form-label col-xs-3"><i class="Hui-iconfont">&#xe60d;</i></label>
        <div class="formControls col-xs-8">
         	<input type="text" id="username" name="username" placeholder="用户名" class="input-text size-L"/>
        </div>
      </div>
      <div class="row cl">
        <label class="form-label col-xs-3"><i class="Hui-iconfont">&#xe60e;</i></label>
        <div class="formControls col-xs-8">
          <input type="password" id="password" name="password" placeholder="初始密码 111333" class="input-text size-L"/>
        </div>
      </div>
      <div class="row cl">
        <div class="formControls col-xs-8 col-xs-offset-3">
          <input class="input-text size-L" type="text" placeholder="验证码" id="J_codetext" style="width:150px;">
                    <canvas class="input-text size-L yzmCanvas" style="float: right;width: 150px;margin-right: 40px;" id="myCanvas" onclick="createCode()" >对不起，您的浏览器不支持canvas，请下载最新版浏览器!</canvas>
      </div>
      <!-- <div class="row cl">
        <div class="formControls col-xs-8 col-xs-offset-3">
          <label for="online">
            <input type="checkbox" name="online" id="online" value="">
            使我保持登录状态</label>
        </div> -->
	</div>
	<div class="row cl">
		<div class="formControls col-xs-8 col-xs-offset-4">
			<div class="radio-box" style="margin-left:10px;">
				<input name="type" type="radio" value="1" checked/>
				<label for="type-1">教师</label>
			</div>
			<div class="radio-box">
				<input name="type" type="radio" value="2"/>
				<label for="type-2">学生</label>
			</div>
		</div>
		<label class="formControls col-xs-8  col-xs-offset-4 msg" style="color:red;"></label>
	</div>
	<div class="row cl">
	<div class="formControls col-xs-8 col-xs-offset-3">
          <input style="margin-left:56px;" name="" type="button" id="btnSubmit" class="btn btn-success radius size-L" value="&nbsp;登&nbsp;&nbsp;&nbsp;&nbsp;录&nbsp;">
          <input style="margin-left:56px;" name="" type="reset" class="btn btn-default radius size-L" value="&nbsp;取&nbsp;&nbsp;&nbsp;&nbsp;消&nbsp;">
        </div>
      </div>
    </form>
  </div>
</div>
<div class="footer">Copyright ©2014-2017 合肥正鼎软件科技有限公司 All Rights Reserved.</div>
	<script type="text/javascript"
		src="${staticPath}/static/js/ValidateTop.js"></script>
	<script type="text/javascript" src="${staticPath}/static/js/encrypt.js"></script>
	<script type="text/javascript"
		src="${staticPath}/static/js/verificationNumbers.js"></script>
	<script type="text/javascript"
		src="${staticPath}/static/js/jquery.tips.js"></script>
	<script>
		$(function(){
			$('.skin-minimal input').iCheck({
				checkboxClass: 'icheckbox-blue',
				radioClass: 'iradio-blue',
				increaseArea: '20%'
			});
		});
		createCode();
		$("#btnSubmit") .click(function() {
			if (check()) {
				var inputCode = document.getElementById("J_codetext").value.toUpperCase();
				var codeToUp = code.toUpperCase();
				if (inputCode != codeToUp) {
					document.getElementById("J_codetext").value = "";
					$("#J_codetext").tips({
						side : 4,
						msg : '验证码错误！',
						bg : '#AE81FF',
						time : 3
					});
					createCode();
					return false;
				}
				var un = encode64($("#username").val());
				var up = encode64($("#password").val());
				$.ajax({
					type : 'POST',
					url : rootPath + "/login/admin",
					data : {
						'username' : un,
						'password' : up,
						'type':$('input:radio[name="type"]:checked').val()
					},
					dataType : "json",
					cache : false,
					success : function(data) {
						if (data.success == true) {
							window.location.href = rootPath+ "/back/home";
						} else {
							/* top.layer.alert(data.msg, {icon: 5},function(index){ top.layer.close(index); }); */
							$(".msg").text(data.msg);
						}
					},
					error : function() {
					}
				})
				return true;
			}
		})

		createCode();
		//客户端校验
		function check() {
			if ($("*[name='username']").val() == "") {
				$("*[name='username']").tips({
					side : 2,
					msg : '帐号不能为空',
					bg : '#AE81FF',
					time : 3
				});
				$("*[name='username']").focus();
				return false;
			} /* else {
			        $("#username").val(jQuery.trim($('#username').val()));
			        } */
		
			if ($("*[name='password']").val() == "") {
				$("*[name='password']").tips({
					side : 2,
					msg : '密码不能为空!',
					bg : '#AE81FF',
					time : 3
				});
				$("*[name='password']").focus();
				return false;
			}
			if ($("#J_codetext").val() == "") {
				$("#J_codetext").tips({
					side : 1,
					msg : '验证码不能为空!',
					bg : '#AE81FF',
					time : 3
				});
				$("#J_codetext").focus();
				return false;
			}
			return true;
		};
		
		document.onkeydown = function() {
			if (event.keyCode == 13) {
				if (check()) {
					var inputCode = document.getElementById("J_codetext").value
							.toUpperCase();
					var codeToUp = code.toUpperCase();
					if (inputCode != codeToUp) {
						document.getElementById("J_codetext").value = "";
						$("#J_codetext").tips({
							side : 4,
							msg : '验证码错误！',
							bg : '#AE81FF',
							time : 3
						});
						createCode();
						return false;
					}
					var un = encode64($("#username").val());
					var up = encode64($("#password").val());
					$.ajax({
						type : 'POST',
						url : rootPath + "/login/admin",
						data : {
							'username' : un,
							'password' : up,
							'type':$('input:radio[name="type"]:checked').val()
						}, 
						dataType : "json",
						cache : false,
						success : function(data) {
							if (data.success == true) {
								window.location.href = rootPath + "/back/home";
							} else {
								$(".msg").text(data.msg);
							}
						},
						error : function() {
						}
					})
					return true;
				}
			}
		}
		
	</script>
</body>
</html>