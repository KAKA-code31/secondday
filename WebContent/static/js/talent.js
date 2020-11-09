function chkall(input1, input2) {
		var objForm = document.forms[input1];
		var objLen = objForm.length;
		for (var iCount = 0; iCount < objLen; iCount++) {
			if (input2.checked == true) {
				if (objForm.elements[iCount].type == "checkbox") {
					objForm.elements[iCount].checked = true;
				}
			} else {
				if (objForm.elements[iCount].type == "checkbox") {
					objForm.elements[iCount].checked = false;
				}
			}
		}
	}