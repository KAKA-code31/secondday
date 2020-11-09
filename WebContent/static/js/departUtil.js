/**
 * 院系、专业、班级 三级联动效果js。
 * 使用时需要在Controller 的方法中增加enDepartId，enMajorId，enClassId这三个string形参
 * 在该方法中加获取departs列表的方法
 * 需要在表单中，把这三个对象的select的Id设置成enDepartId，enMajorId，enClassId
 * 并将 <input type="hidden" majorId="${enMajorId }" classId="${enClassId }" id="value_content" />
 * 加入表单中
 */
var zyId;
var bjId;
function getMajors() {
	var departmentId = $("#enDepartId").val();
	if(departmentId==null || departmentId==""){
		$("#enMajorId").empty();//清空
		$("#enMajorId").append("<option value=''>--暂无专业--</option>");
		$("#enClassId").empty();//清空
		$("#enClassId").append("<option value=''>--暂无班级--</option>");
		return 0;
	}
	$.ajax({
		type : 'post',
		url : rootPath+"/back/major/findMajorBydepartJson",
		data : {"departId" : departmentId},
		cache : false,
		dataType : 'json',
		success : function(data) {
			var types = data.obj;
			if (types != null && types.length > 0) {
				$("#enMajorId").empty();//清空
				$("#enMajorId").append("<option value=''>-请选择专业-</option>");
				for (var i = 0; i < types.length; i++) {
					if(types[i].encryptId == zyId){
						$("#enMajorId").append("<option value='"+types[i].encryptId+"' selected='selected'>"+ types[i].name+ "</option>");
					}else{
						$("#enMajorId").append("<option value='"+types[i].encryptId+"'>"+ types[i].name+ "</option>");
					}
				}
				getStudentClass();
			} else {
				$("#enMajorId").empty();//清空
				$("#enMajorId").append("<option value=''>--暂无专业--</option>");
			}
		}
	});
}
function getStudentClass(){
	var majorId = $("#enMajorId").val();
	if(majorId==null || majorId==""){
		$("#enClassId").empty();//清空
		$("#enClassId").append("<option value=''>--暂无班级--</option>");
		return 0;
	}
	$.ajax({  
	    type:'post',      
	    url:rootPath+"/back/studentClass/findClassByMajorJson",
	    data:{"majorId":majorId},
	    cache:false,  
	    dataType:'json',  
	    success:function(data){
	    	var types = data.obj;
	    	if(types!=null && types.length>0){
	    		$("#enClassId").empty();//清空
	    		$("#enClassId").append("<option value=''>-请选择班级-</option>");
	    		for(var i=0;i<types.length;i++){
					if(bjId==types[i].encryptId){
						$("#enClassId").append("<option value='"+types[i].encryptId+"' selected='selected'>"+ types[i].name+ "</option>");
					}else{
						$("#enClassId").append("<option value='"+types[i].encryptId+"'>"+ types[i].name+ "</option>");
					}
	    		}
	    	}else{
	    		$("#enClassId").empty();//清空
	    		$("#enClassId").append("<option value=''>--暂无班级--</option>"); 
	    	}
	    }
	});
}
$("#enDepartId").change(function(){
	getMajors();
});
$("#enMajorId").change(function(){
	getStudentClass();
});
$(function(){
	zyId = $("#value_content").attr("majorId");
	bjId = $("#value_content").attr("classId");
	getMajors();
	getStudentClass();
});