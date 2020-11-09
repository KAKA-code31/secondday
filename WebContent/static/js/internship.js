function praseDataTableQueryData(d){
	var param = {};
    var columns = new Array();
    param.draw = d.draw;
    param.start = d.start;
    param.length = d.length;
    param.searchValue = d.search.value;
    param.searchRegex = d.search.regex;
    $.each(d.columns, function(i, column){
 	   var dataColumn = {};
 	   dataColumn.data = column.data;
 	   dataColumn.name = column.name;
 	   dataColumn.searchable = column.searchable;
 	   dataColumn.orderable = column.orderable;
 	   dataColumn.searchValue = column.search.value;
 	   dataColumn.searchRegex = column.search.regex;
 	   columns.push(dataColumn);
    });
    param.columnsString = JSON.stringify(columns);
    param.ordersString = JSON.stringify(d.order);
    return param;
}