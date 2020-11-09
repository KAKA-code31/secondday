<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>添加课程项</title>
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
<link href="${staticPath}/static/plugins/zTree/css/zTreeStyle/zTreeStyle.css" rel="stylesheet" type="text/css" />
<style type="text/css">
.ztree li span.button.add {
	margin-left: 2px;
	margin-right: -1px;
	background-position: -144px 0;
	vertical-align: top;
	*vertical-align: middle
}
</style>
<script type="text/javascript" src="${staticPath}/static/plugins/zTree/js/jquery.ztree.core-3.5.js"></script>  
<script type="text/javascript" src="${staticPath}/static/plugins/zTree/js/jquery.ztree.excheck-3.5.js"></script>  
<script type="text/javascript" src="${staticPath}/static/plugins/zTree/js/jquery.ztree.exedit-3.5.js"></script> 

<script>  
var dragId;var zTree_Menu;  
var setting = {  
        view: {  
            addHoverDom: addHoverDom,  
            removeHoverDom: removeHoverDom,  
            selectedMulti: false  
            /* showLine: false，
            showIcon: false */   
        },  
        edit: {  
            enable: true,  
            showRemoveBtn: setRemoveBtn,  
            showRenameBtn:setRenameBtn,
            removeTitle:"删除分类",  
            renameTitle:"编辑分类",  
            drag: {  
                  prev: true,  
                  next: true,  
                  inner: false  
            },  
            editNameSelectAll: true  
        },  
        data: {  
            simpleData: {  
                enable: true  
            }  
        },  
        callback: {  
        	beforeEditName: beforeEditName,  
            beforeRemove: beforeRemove,  
            beforeRename: beforeRename,  
           /* 	onRemove: onRemove, */  
           	setRemoveBtn:setRemoveBtn,
            onRename: onRename,
            /* onClick:function zTreeOnClick(event, treeId, treeNode){
                var ids=[];
                ids=getChildren(ids,treeNode);
                for(var i=0; i < ids.length; i++){
                    console.log("节点id:"+ids[i]);
                }	   
            } */
            onClick:zTreeOnClick
        }  
    };
	/* function getChildren(ids,treeNode){
		ids.push(treeNode.id);
	 	if (treeNode.level == 0){
			for(var obj in treeNode.children){
				getChildren(ids,treeNode.children[obj]);
			}
	    }
	 return ids;
	} */

	/* function addBtnProperty(nodes){  
	    $.each(nodes,function(i,node){  
            if(node.level == 0){  
          		 node.noEditBtn=true;  
             }  
	    })  
	    return nodes;  
	} */
	function zTreeOnClick(event, treeId, treeNode,ids){
   	  	var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
   	    var node = treeObj.getNodes();
   	   /*  ids.value=node; */
   	   /*  alert(ids); */
   	  	var ids = document.getElementById("treeIds");
   	    var nodes = treeObj.transformToArray(node);//将node转化为数组
   	 	var na = nodes.map(function(v){return v.id;});//获取数组中的id属性
   	 	var nas = na.join(",");//将数组转为字符串
   	 	ids.value = nas;//对treeIds赋值
   	   /*  for(var i=0; i < nodes.length; i++)
   	    	ids.value = nodes[i].id; */
   	    	/* console.log(nodes[i].id); */
    }
	    //父级分类去除删除功能  
    function setRemoveBtn(treeId, treeNode) {  
       return (!treeNode.isParent && treeNode.level!=0);  
    }   
	    
	function setRenameBtn(treeId, treeNode) { 
	    return treeNode.level!=0;
	}
      //用于捕获分类编辑按钮的 click 事件，并且根据返回值确定是否允许进入名称编辑状态  
     function beforeEditName(treeId, treeNode) {  
            var zTree = $.fn.zTree.getZTreeObj("treeDemo");  
            zTree.selectNode(treeNode);  
            return true;  
     }  
      //移除分类前执行  
     function beforeRemove(treeId, treeNode) {  
            var zTree = $.fn.zTree.getZTreeObj("treeDemo");  
            zTree.selectNode(treeNode);  
            var confirmFlag = confirm("确认删除分类[ " + treeNode.name + " ]吗？" )  
            var confirmVal = false;  
            if(confirmFlag){  
                 var data = {id:treeNode.id};  
                $.ajax({  
                     async: false,  
                     type: "post",  
                     data:data,  
                     url: rootPath + "/back/report/deleteJudge",  
                     success: function(json){  
                            if(json == "success" ){  
                                confirmVal = true;  
                           } else{  
                        	   alert('删除失败！');
                           }  
                     },  
                     error: function(){  
                           alert('删除失败！');  
                     }  
                });  
           }  
            return confirmVal;  
     }  
       //执行删除操作后提示  
    /*  function onRemove(e, treeId, treeNode) {  
           alert('删除成功！');  
     }    */
      //用于捕获分类编辑名称结束（Input 失去焦点 或 按下 Enter 键）之后，更新分类名称数据之前的事件回调函数  
     function beforeRename(treeId, treeNode, newName) {  
            if (newName.length == 0) {  
        		 alert('请输入分类名称！');  
               /*   var zTree = $.fn.zTree.getZTreeObj("treeDemo");  
                 setTimeout( function(){zTree.editName(treeNode)}, 10);  */ 
                 return false;  
          	 }  
            if(newName.length > 15){  
       			 alert('分类名称过长！');  
               /*  var zTree = $.fn.zTree.getZTreeObj("treeDemo");  
                setTimeout( function(){zTree.editName(treeNode)}, 10);  */ 
                return false;  
           }  
           native_name = treeNode.name;  
           return true;  
     }  
      //执行编辑操作  
     function onRename(e, treeId, treeNode) {  
            if(native_name == treeNode.name){  
                 return;  
            }  
            var data = {id:treeNode.id,level_id:treeNode.level,parentId:treeNode.pId,name:treeNode.name};  
            $.ajax({  
                async: false,  
                type: "post",  
                data:data,  
                url: rootPath + "/back/report/updateJudgeName",  
                /* success : function(json){  
                      if(json == "success" ){  
               alert('操作成功!');  
                     } else{  
               alert('操作失败，请稍后再试！');  
                     }  
                },   */
                error : function()    {  
            	 alert('编辑失败！');  
                }  
           });  
     }  
       
      //添加子分类  
     /* function addHoverDom(treeId, treeNode) {  
    	 var sObj = $("#" + treeNode.tId + "_span");  
         if (treeNode.editNameFlag || $("#addBtn_" +treeNode.tId).length>0 || treeNode.level == 3) return;  
         var addStr = "<span class='button add' id='addBtn_" + treeNode.tId + "' title='添加分类' onfocus='this.blur();'></span>";  
         sObj.after(addStr);  
         var btn = $("#addBtn_" +treeNode.tId);  
         if (btn) btn.bind("click" , function(){  
              var zTree = $.fn.zTree.getZTreeObj("treeDemo");  
              var treeNodes;  
             $.ajax({  
                  async: false,  
                  type: "post",  
                  url: rootPath + "/back/report/saveJudge", 
                  success : function(libraryId){  
                         if(libraryId != "" ){  
                             treeNodes = zTree.addNodes(treeNode, {id:(libraryId), pId:treeNode.id, name:"请输入名称" });  
                        }  
                         if (treeNodes) {  
                             zTree.editName(treeNodes[0]);  
                        }  
                  },  
                  error : function(){  
                        alert('亲，网络有点不给力呀！');  
                  }  
             });  
              return false;  
        });  
     }  */ 
     function addHoverDom(treeId, treeNode) {
    	/* console.log("treeNode:"+JSON.stringify(treeNode)); */
 		var sObj = $("#" + treeNode.tId + "_span");
 		if (treeNode.editNameFlag || $("#addBtn_" + treeNode.tId).length > 0)
 			return;
 		var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
 				+ "' title='add node' onfocus='this.blur();'></span>";
 		sObj.after(addStr);
 		var btn = $("#addBtn_" + treeNode.tId);
 		if (btn)
 			btn.bind("click", function() {
 				var Ppname = prompt("请输入评阅具体内容");
 				if (Ppname == null) {
 					return;
 				} else if (Ppname == "") {
 					alert("内容不能为空");
 				} else {
 					var zTree = $.fn.zTree.getZTreeObj("treeDemo");
 					/* var param ="parentId="+ treeNode.id + "&name=" + Ppname; */
 					$.ajax({  
 		                  async: false,  
 		                  type: "post",  
 		                  data:{name:Ppname,parentId:treeNode.id},
 		                  url: rootPath + "/back/report/saveJudge", 
 		                  success : function(data){  
 		                	/* alert("data:"+JSON.stringify(data)); */
 		                	 if ($.trim(data) != null) {
									var treenode = $.trim(data);
									zTree.addNodes(treeNode, {
										id :data,
										pId : treeNode.id,
										name : Ppname
									}, true);
								}  
 		                  },  
 		                  error : function(){  
 		                        alert('保存失败！');  
 		                  }  
 		             });
 				}

 			});
 	};
       
      //鼠标移开按钮消失  
     function removeHoverDom(treeId, treeNode) {  
           $( "#addBtn_"+treeNode.tId).unbind().remove();  
     };  
       
      //添加按钮点击事件  
     function addClick(){  
           $( "#addParent").bind("click" , {isParent:true}, add);  
     }  
  
      //移除分类  
     function remove(e) {  
            var zTree = $.fn.zTree.getZTreeObj("treeDemo"),  
           nodes = zTree.getSelectedNodes(),  
           treeNode = nodes[0];  
            if (nodes.length == 0) {  
                 alert( "请先选择一个分类!" );  
                 return;  
           }  
            var callbackFlag = $("#callbackTrigger" ).attr("checked");  
           zTree.removeNode(treeNode, callbackFlag);  
     };  
       
      //展开全部分类  
     function expandAllFlag(){  
           zTree_Menu.expandAll( true);  
     }  
      //合并全部分类  
     function combineAllFlag(){  
           zTree_Menu.expandAll( false);  
     }  
       
      //加载ztree  
     function onloadZTree(){  
            var ztreeNodes;  
           $.ajax( {  
                async : true, //是否异步  
                cache : false, //是否使用缓存  
                type : 'post', //请求方式,post  
                dataType : "json", //数据传输格式  
                url : rootPath + "/back/report/findAllJudge",   //请求链接  
                error : function() {  
                     alert('系统错误！');  
                },  
                success : function(data) {  
                     ztreeNodes = eval( "["+data+"]" ); //将string类型转换成json对象  
                     $.fn.zTree.init($( "#treeDemo"), setting, ztreeNodes);  
                     zTree_Menu = $.fn.zTree.getZTreeObj("treeDemo" );  
                     /* $( "#selectAll").bind("click" , selectAll);  */ 
                     expandAllFlag();  
                     addClick();  
                }  
           });  
     }  
      //初始化操作  
     $(document).ready( function(){  
           onloadZTree();  
     });  
</script>
</head>
<body style="min-width: 600px;">
	<nav class="breadcrumb">
		<i class="Hui-iconfont" style="color: #000;">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span>课程实验
		<span class="c-gray en">&gt;</span> 课程管理
		<span class="c-gray en">&gt;</span> 课程添加<font color="red">（注意：评阅内容编辑最后一级以“名称-分数”形式命名）</font>
	</nav>
	<article class="page-container">
	<form class="form form-horizontal" id="addForm">
			<div class="page-container_tittle">
				<i class="firm_det_tit_line"></i>课程信息
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-2">
					<span class="c-red">*</span>课程编号
				</label>
				<div class="formControls col-xs-8 col-sm-9">
					<input type="text" class="input-text"  placeholder="" name="courseCode" id="courseCode"/>
					<input type="text" name="code" id="code" style="display:none; color:#F00;width:100%;" />
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-2">
					<span class="c-red">*</span>课程名
				</label>
				<div class="formControls col-xs-8 col-sm-9">
					<input type="text" class="input-text"  placeholder="" name="courseName" id ="courseName"/>
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-2">
					<span class="c-red">*</span>课程简介
				</label>
				<div class="formControls col-xs-8 col-sm-9">
					<textarea class="textarea"  placeholder="0-500字数" name="cdescription"></textarea>
				</div>
				<input type = "hidden" id = "treeIds" name = "treeIds" >
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-2">教学进程：</label>
				<div class="formControls col-xs-8 col-sm-9">
				<div class="cl pd-5 bg-1 bk-gray mt-20">
					<span class="l"> <a href="javascript:;" id="add" class="btn btn-primary radius"><i class="Hui-iconfont">&#xe600;</i> 添加</a> </span>
				</div>
					<table class="table table-border table-bordered table-bg table-hover table-sort" id = "course">
						<thead>
							<tr class="text-c">
								<th>实验编号</th>
								<th>实验名称</th>
								<th>实验简介</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody>
							<tr  id="add_info">
								<td>
									<input name="experimentCode" class="input-text" type="text" placeholder="实验编号"/>
								</td>
								<td>
									<input id="experimentName" class="input-text" name="experimentName" type="text" placeholder="实验名称"/>
								</td>
								<td>
									<input id="description" class="input-text" name="description" type="text" placeholder="0-500字数"/>
								</td>
								<td>
									 <center><a href="javascript:;" onclick='deleteCurrentRow(this);'><i class="icon Hui-iconfont" style="color: #000;">&#xe609;</i></a></center>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-4">
					<a style="color:red" href="javascript:void(0)" onMouseOut="hideImg()" onmouseover="showImg()"><h5>移到此处查看评阅编辑示例</h5></a>
				</label>
				<div class="formControls col-xs-8 col-sm-8">
					<div id="wxImg" style="display:none;height:50px;back-ground:#f00;position:absolute;"><img src = "${staticPath }/static/img/caozuo.gif"></div> 
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-2">
					评阅内容编辑：
				</label>
				<div class="formControls col-xs-8 col-sm-9">
					<div id="treeDemo" class="ztree"></div>
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
	src="${staticPath}/static/plugins/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript"
	src="${staticPath}/static/plugins/bootstrap/js/bootstrap-datepicker.js"></script>
<script src="${staticPath}/static/plugins/zTree/js/jquery.ztree.core.min.js"></script>
<script src="${staticPath}/static/plugins/zTree/js/jquery.ztree.excheck.min.js"></script>
<script src="${staticPath}/static/plugins/zTree/js/jquery.ztree.exedit.min.js"></script>
<script type="text/javascript">

function showImg(){ 
	document.getElementById("wxImg").style.display='block'; 
	} 
	function hideImg(){ 
	document.getElementById("wxImg").style.display='none'; 
	} 
		$("#addForm").validate({
			rules : {
				'courseCode' : {
					required : true
				},
				'courseName' : {
					required : true
				},
				'cdescription' : {
					rangelength:[0,500]
				},
				'experimentCode' : {
					required : true
				},
				'experimentName' : {
					required : true
				},
				'description' : {
					rangelength:[0,500]
				}
			},
			messages : {
				'courseCode' : {
					required : "请输入课程编号"
				},
				'courseName' : {
					required : "请输入课程名"
				},
				'cdescription' : {
					rangelength:"课程简介0-500字符之间"
				},
				'experimentCode' : {
					required : ""
				},
				'experimentName' : {
					required : ""
				},
				'description' : {
					rangelength:"实验简介0-500字符之间"
				}
			},
		});

	function getFormData() {
		if ($("#addForm").valid()) {
			var data = $("#addForm").serialize();
			return data;
		} else {
			return 0;
		}
	}
	$("#add").click(function() {
		document.getElementById("course").appendChild(document.getElementById("add_info").cloneNode(true));
		$('#course tr:last').find('td input').val(''); 
  	});
	function deleteCurrentRow(obj){
            var tr=obj.parentNode.parentNode.parentNode;
            var tbody=tr.parentNode;
			if(tbody.rows.length>1) {
	            tbody.removeChild(tr);
			}
    }
	//检查
	 $("#courseCode").blur(function() {
			$.ajax({
				url : rootPath + "/back/report/selectCourseCode",
				async : false,
				type : "post",
				dataType : "json",
				data : {
				    "courseCode" : $("#courseCode").val() 
				},
				success : function(data) {
					if (data.msg != "") {
						$("#code").val(data.msg);
						$("#code").css('display','block');
						$("#courseCode").val("");
					} else {
						$("#code").val("");
						$("#code").css('display','none');
					}
				}
			});
		});  
</script>
</html>