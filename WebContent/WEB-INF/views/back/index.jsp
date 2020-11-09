<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
<link rel="Bookmark" href="${staticPath}/static/favicon.ico" >
<link rel="Shortcut Icon" href="${staticPath}/static/favicon.ico" />
<!--[if lt IE 9]>
<script type="text/javascript" src="lib/html5shiv.js"></script>
<script type="text/javascript" src="lib/respond.min.js"></script>
<![endif]-->
<link rel="stylesheet" type="text/css" href="${staticPath}/static/h-ui/css/H-ui.min.css" />
<link rel="stylesheet" type="text/css" href="${staticPath}/static/h-ui.admin/css/H-ui.admin.css" />
<link rel="stylesheet" type="text/css" href="${staticPath}/static/lib/Hui-iconfont/1.0.8/iconfont.css" />
<link rel="stylesheet" type="text/css" href="${staticPath}/static/h-ui.admin/skin/default/skin.css" id="skin" />
<link rel="stylesheet" type="text/css" href="${staticPath}/static/h-ui.admin/css/style.css" />
<title>实验报告管理系统</title>
<meta name="keywords" content="实验报告管理系统">
<meta name="description" content="实验报告管理系统">
 <style type="text/css">  
	    .menu_dropdown a:hover {
		    text-decoration: none;
	    }
	</style>
</head>
<body>
<header class="navbar-wrapper">
	<div class="navbar navbar-fixed-top">
		<div class="container-fluid cl"> 
		<a class="logo navbar-logo f-l mr-10 hidden-xs"href="javascript:void(0);">实验报告管理系统</a> 
		<a aria-hidden="false" class="nav-toggle Hui-iconfont visible-xs" href="javascript:;">&#xe667;</a>
		<nav id="Hui-userbar" class="nav navbar-nav navbar-userbar hidden-xs">
			<ul class="cl">
				<li class="dropDown dropDown_hover">
					<a href="#" class="dropDown_A">${user.name}<i class="Hui-iconfont">&#xe6d5;</i></a>
					<ul class="dropDown-menu menu radius box-shadow">
						<c:if test="${user.adminType == 0}">
							<li><a id="self" data-title="个人信息"
										data-href="${staticPath }/back/report/studentInfo/${user.userId}">个人信息</a></li>
						</c:if>
						<c:if test="${user.adminType == 1}">
							<li><a id="self" data-title="个人信息"
										data-href="${staticPath }/back/report/teacherInfo/${user.userId}">个人信息</a></li>
						</c:if>
						<li><a href="${staticPath}/loginOut">切换账户</a></li>
						<li><a href="javascript:void(0);" id="quit">退出</a></li>
				</ul>
			</li>
 				<li id="Hui-msg"> <a href="javascript:void(0);" id="downloadTemplet" title = "用户使用手册下载"><span class="badge badge-danger"></span><i class="Hui-iconfont" style="font-size:18px">&#xe68a;</i></a> </li>
				<li id="Hui-skin" class="dropDown right dropDown_hover"> <a href="javascript:;" class="dropDown_A" title="换肤"><i class="Hui-iconfont" style="font-size:18px">&#xe62a;</i></a>
					<ul class="dropDown-menu menu radius box-shadow">
						<li><a href="javascript:;" data-val="default" title="默认（黑色）">默认（黑色）</a></li>
						<li><a href="javascript:;" data-val="blue" title="蓝色">蓝色</a></li>
						<li><a href="javascript:;" data-val="green" title="绿色">绿色</a></li>
						<li><a href="javascript:;" data-val="red" title="红色">红色</a></li>
						<li><a href="javascript:;" data-val="yellow" title="黄色">黄色</a></li>
						<li><a href="javascript:;" data-val="orange" title="橙色">橙色</a></li>
					</ul>
				</li>
			</ul>
		</nav>
	</div>
</div>
</header>
<aside class="Hui-aside">
	<div class="menu_dropdown bk_2">
		<dl id="menu-system">
			<shiro:hasPermission name="/xxgl">
				<dt><i class="Hui-iconfont">&#xe61d;</i> 信息管理<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
				<dd>
					<ul>
						<shiro:hasPermission name="/back/school/manager">
							<li><cite></cite><a href="javascript:void(0);" data-title="学校管理" class="back_link" data-href="${staticPath}/back/school/manager">学校管理</a><i></i></li>
						</shiro:hasPermission>
						<shiro:hasPermission name="/back/department/manager">
							<li><cite></cite><a data-href="${staticPath}/back/department/manager" data-title="院系管理" class="back_link" href="javascript:void(0)">院系管理</a><i></i></li>
						</shiro:hasPermission>
						<shiro:hasPermission name="/back/major/manager">
							<li><cite></cite><a data-href="${staticPath}/back/major/manager" data-title="专业管理" class="back_link" href="javascript:void(0)">专业管理</a><i></i></li>
						</shiro:hasPermission>
						<shiro:hasPermission name="/back/studentClass/manager">
							<li><cite></cite><a href="javascript:void(0);" data-title="班级管理" class="back_link" data-href="${staticPath}/back/studentClass/manager">班级管理 </a><i></i></li>
						</shiro:hasPermission>
						<shiro:hasPermission name="/back/teacher/manager">
							<li><a data-href="${staticPath }/back/teacher/manager" data-title="教师管理" href="javascript:void(0)" class="back_link">教师管理</a></li>
						</shiro:hasPermission>
						<shiro:hasPermission name="/back/student/manager">
							<li><cite></cite><a href="javascript:void(0);" data-title="学生管理" class="back_link" data-href="${staticPath}/back/student/manager">学生管理</a><i></i></li>
						</shiro:hasPermission>
					</ul>
				</dd>
			</shiro:hasPermission>
			<shiro:hasPermission name="/bgtj">
				<dt><i class="Hui-iconfont">&#xe642;</i> 作业管理<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
				<dd>
					<ul>
						<shiro:hasPermission name="/back/report/submitList">
							<li><cite></cite><a href="javascript:void(0);" data-title="作业提交记录" class="back_link" data-href="${staticPath}/back/report/submitList">作业提交记录</a><i></i></li>
						</shiro:hasPermission>
					</ul>
					<ul>
						<shiro:hasPermission name="/back/report/historyrecord">
							<li><cite></cite><a href="javascript:void(0);" data-title="历史提交" class="back_link" data-href="${staticPath}/back/report/historyrecord">历史提交</a><i></i></li>
						</shiro:hasPermission>
					</ul>
					<ul>
						<shiro:hasPermission name="/back/report/worksubmit">
							<li><cite></cite><a href="javascript:void(0);" data-title="提交作业" class="back_link" data-href="${staticPath}/back/report/worksubmit">提交作业</a><i></i></li>
						</shiro:hasPermission>
					</ul>
				</dd>
			</shiro:hasPermission>
		
			<shiro:hasPermission name="/back/report/lookScore">
				<dt><i class="Hui-iconfont">&#xe62e;</i> <a href="javascript:void(0);" data-title="查看成绩" class="back_link" data-href="${staticPath}/back/report/lookScore">查看成绩</a></dt>
			</shiro:hasPermission>
			<shiro:hasPermission name="/kcsy">
				<dt><i class="Hui-iconfont">&#xe720;</i> 课程管理<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
				<dd>
					<ul>
						<shiro:hasPermission name="/back/report/courseManage">
							<li><cite></cite><a href="javascript:void(0);" data-title="课程添加" class="back_link" data-href="${staticPath}/back/report/courseManage">课程添加</a><i></i></li>
						</shiro:hasPermission>
					</ul>
					<ul>
						<shiro:hasPermission name="/back/report/selectClass">
							<li><cite></cite><a href="javascript:void(0);" data-title="关联学生" class="back_link" data-href="${staticPath}/back/report/selectClass">关联学生</a><i></i></li>
						</shiro:hasPermission>
					</ul>
				</dd>
			</shiro:hasPermission>
			<shiro:hasPermission name="/bgtj1">
				<dt><i class="Hui-iconfont">&#xe642;</i> <a href="javascript:void(0);" data-title="作业管理" class="back_link" data-href="${staticPath}/back/report/submitList1">作业管理</a></dt>
				<dd></dd>
			</shiro:hasPermission>
			<shiro:hasPermission name="/cjgl">
				<dt><i class="Hui-iconfont">&#xe69e;</i> <a href="javascript:void(0);" data-title="成绩管理" class="back_link" data-href="${staticPath}/back/report/scoreManagement">成绩管理</a></dt>
				<dd></dd>
			</shiro:hasPermission>
			<shiro:hasPermission name="/xtgl">
				<dt><i class="Hui-iconfont">&#xe62e;</i> 系统管理<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
				<dd>
					<ul>
						<shiro:hasPermission name="/back/role/manager">
							<li><cite></cite><a href="javascript:void(0);" data-title="角色管理" class="back_link" data-href="${staticPath}/back/role/manager">角色管理</a><i></i></li>
						</shiro:hasPermission>
						<shiro:hasPermission name="/back/privilege/manager">
							<li><cite></cite><a data-href="${staticPath}/back/privilege/manager" data-title="权限项管理" class="back_link" href="javascript:void(0)">权限项管理</a><i></i></li>
						</shiro:hasPermission>
						<shiro:hasPermission name="/back/userRole/manager">
							<li><cite></cite><a data-href="${staticPath}/back/userRole/manager" data-title="系统管理员" class="back_link" href="javascript:void(0)">系统管理员</a><i></i></li>
						</shiro:hasPermission>
						<shiro:hasPermission name="/back/systemLog/manager">
							<li><cite></cite><a href="javascript:void(0);" data-title="操作日志" class="back_link" data-href="${staticPath}/back/systemLog/manager">操作日志 </a><i></i></li>
						</shiro:hasPermission>
					</ul>
				</dd>
			</shiro:hasPermission>
		</dl> 
	</div>
</aside>
<div class="dislpayArrow hidden-xs"><a class="pngfix" href="javascript:void(0);" onClick="displaynavbar(this)"></a></div>
<section class="Hui-article-box">
	<div id="Hui-tabNav" class="Hui-tabNav hidden-xs">
		<div class="Hui-tabNav-wp">
			<ul id="min_title_list" class="acrossTab cl">
				<li class="active">
					<span title="我的桌面" data-href="welcome.html">我的桌面</span>
					<em></em></li>
		</ul>
	</div>
		<div class="Hui-tabNav-more btn-group"><a id="js-tabNav-prev" class="btn radius btn-default size-S" href="javascript:;"><i class="Hui-iconfont">&#xe6d4;</i></a><a id="js-tabNav-next" class="btn radius btn-default size-S" href="javascript:;"><i class="Hui-iconfont">&#xe6d7;</i></a></div>
</div>
	<div id="iframe_box" class="Hui-article">
		<div class="show_iframe">
			<div style="display:none" class="loading"></div>
			<iframe scrolling="yes" frameborder="0" src="${staticPath }/back/index"></iframe>
	</div>
</div>
</section>

<div class="contextMenu" id="Huiadminmenu">
	<ul>
		<li id="closethis">关闭当前 </li>
		<li id="closeall">关闭全部 </li>
</ul>
</div>
<!--_footer 作为公共模版分离出去-->
<script type="text/javascript" src="${staticPath }/static/lib/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript" src="${staticPath }/static/lib/layer/2.4/layer.js"></script>
<script type="text/javascript" src="${staticPath }/static/h-ui/js/H-ui.min.js"></script>
<script type="text/javascript" src="${staticPath }/static/h-ui.admin/js/H-ui.admin.js"></script> <!--/_footer 作为公共模版分离出去-->

<!--请在下方写此页面业务相关的脚本-->
<script type="text/javascript" src="${staticPath }/static/lib/jquery.contextmenu/jquery.contextmenu.r2.js"></script>
<script type="text/javascript">
$(function(){

});
</script> 

<!--此乃百度统计代码，请自行删除-->
<script>
$("#quit").click(function(){
	$.ajax({  
	    type:'post',      
	    url:rootPath+"/logout",  
	    cache:false,  
	    dataType:'json',  
	    success:function(data){
	    	top.layer.alert(data.msg, {icon: 1},function(){
	    		window.location.href = rootPath+"/login/admin";
    		});
	    },
	    error:function(){
	    	top.layer.alert("抱歉系统发生内部错误！", {icon: 5},function(index){
	    		top.layer.close(index);
	    		window.location.href = rootPath+"/login/admin";
    		});
	    }
	});
});
var _hmt = _hmt || [];
(function() {
	$("#self").on("click", function() {
		Hui_admin_tab(this);
	});
	$("#notice").on("click", function() {
		Hui_admin_tab(this);
	});

	var hm = document.createElement("script");
	hm.src = "//hm.baidu.com/hm.js?080836300300be57b7f34f4b3e97d911";
	var s = document.getElementsByTagName("script")[0];
	s.parentNode.insertBefore(hm, s)
})();

var _bdhmProtocol = (("https:" == document.location.protocol) ? " https://"
		: " http://");
document
		.write(unescape("%3Cscript src='"
				+ _bdhmProtocol
				+ "hm.baidu.com/h.js%3F080836300300be57b7f34f4b3e97d911' type='text/javascript'%3E%3C/script%3E"));

$(".back_link").click(function(){
	var link = $(this).attr("data-href");
	$("#mainFrame").attr("src",link);
});
$("#downloadTemplet").click(function(){
	window.location.href = rootPath+"/back/notice/download";
});
</script>
<!--/此乃百度统计代码，请自行删除-->
</body>
</html>