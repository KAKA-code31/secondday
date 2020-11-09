	/* 院系--专业联动 */
	var departmentId ="${departmentId}";
	if(departmentId != null && departmentId!= ""){
		changeInfo(departmentId);
	}
	$("#departmentId").change(function(){
		departmentId=$("#departmentId").val();
		changeInfo(departmentId);
	});
	function changeInfo(departmentId){
		var majorId = "${majorId}";
		$("#majorId").empty();
		$("#majorId").append('<option value="">---请选择---</option>');
	
		$.ajax({
			url : "${staticPath }/back/studentClass/seletMajor",
			type: "post",
			data: {"departmentId" : departmentId},
			dataType: "json",
			success:function(data){
				var types=data.obj;
				if(types!=null && types.length>0){
					for(var i=0;i<types.length;i++){
						$("#majorId").append(
								'<option value="' + types[i].id + '" ' + (majorId ==types[i].id   ? 'selected=selected':'') + '>' + types[i].name + '</option>');
					}
				}
			}
			
		});
	}
	/* 专业--班级联动 */
	var majorId = "${majorId}";
	var classId = "${classId}";
	if(majorId != null && majorId!= ""){
		changeMajorInfo(majorId);
	}
	$("#majorId").change(function(){
		majorId=$("#majorId").val();
		changeMajorInfo(majorId);
	});
	function changeMajorInfo(majorId){
		var classId = "${classId}";
		$("#classId").empty();
		$("#classId").append('<option value="">---请选择---</option>');
		$.ajax({
			url : "${staticPath }/back/studentClass/seletClass",
			type: "post",
			data: {"majorId" : majorId},
			dataType: "json",
			success:function(data){
				var types=data.obj;
				if(types!=null && types.length>0){
					for(var i=0;i<types.length;i++){
						$("#classId").append(
								'<option value="' + types[i].id + '" ' + (classId ==types[i].id   ? 'selected=selected':'') + '>' + types[i].name + '</option>');
					}
				}
				classId=$("#classId").val();
				
			}
		});
	}