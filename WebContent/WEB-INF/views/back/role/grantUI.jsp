<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/commons/global.jsp"%>
<%@ include file="/commons/basejs.jsp"%>
<link href="${staticPath}/static/plugins/zTree/css/zTreeStyle/zTreeStyle.css" rel="stylesheet" type="text/css" />
<div>
	<form id="privilegeForm">
		<input type="hidden" name="id" value="${id }">
		<input type="hidden" name="privilegeIds" id="privilegeIds" value="${privilegeIds }" />
	</form>
	<ul id="privilegeTree" class="ztree">
	</ul>
</div>
<script src="${staticPath}/static/js/jquery-1.7.2.js"></script>
<script src="${staticPath}/static/plugins/zTree/js/jquery.ztree.core.min.js"></script>
<script src="${staticPath}/static/plugins/zTree/js/jquery.ztree.excheck.min.js"></script>
<script type="text/javascript">
	var zNodes=null;
	var setting = {
		data: {simpleData: {enable: true}},
		async: {
			enable: true,
			type: "post",
			dataType: "json",
			url: rootPath+"/back/privilege/findAllTree"
		},
		check: {
			enable: true,
			chkboxType:{ "Y" : "ps", "N" : "ps" }
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
		var privilegeIds = "";
		for (var i = 0, l = checkNodes.length; i < l; i++) {
			var nodeId = checkNodes[i].idEncrypt;
			privilegeIds += nodeId + ",";
		}
		if (privilegeIds.length != 0) {
			privilegeIds = privilegeIds.substring(0,
					privilegeIds.length - 1);
		}
		$("#privilegeIds").val(privilegeIds);
	}
	function onAsyncError(event, treeId, treeNode, XMLHttpRequest, textStatus, errorThrown) {
		var zTree = $.fn.zTree.getZTreeObj("privilegeTree");
		//top.layer.closeAll();
		top.layer.alert("权限项加载失败，请稍后重试！");
		treeNode.icon = "";
		zTree.updateNode(treeNode);
	}
	function onAsyncSuccess(event, treeId, treeNode, msg) {
		var zTree = $.fn.zTree.getZTreeObj("privilegeTree")
		var str_privilegeIds;
		$.ajax({  
		    type:'post',      
		    url:rootPath+"/back/role/findPrivilegeIdListByRoleId",
		    data:{"id":'${id}'},
		    cache:false,  
		    dataType:'json',  
		    success:function(data){
		    	str_privilegeIds = data.obj;
		    	function zTreemodify(obj) {
					for (var x = 0; x < str_privilegeIds.length; x++) {
						if (obj.idEncrypt == str_privilegeIds[x]) {
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
	}
	$(document).ready(function(){
		$.fn.zTree.init($("#privilegeTree"), setting);
	});
	function getFormData(){
		var formData=$("#privilegeIds").val();
		return formData;
	}
</script>