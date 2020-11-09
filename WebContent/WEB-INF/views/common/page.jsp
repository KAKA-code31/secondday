<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="dataTables_info">
	<!-- 显示<span> 1 </span>到<span> 10</span> ，共<span> 1 </span>条 -->
	共<span> ${totalCount } </span>条记录，当前显示第&nbsp;<span> ${currentPage} </span>页
</div>
<div class="dataTables_paginate " id="DataTables_Table_0_paginate">
	<a href="javascript:GoToPage(1,${totalPage})" class="paginate_button previous ${currentPage == 1 ? 'disabled' : '' }">首页</a>
	<a href="javascript:GoToPage(${currentPage-1},${totalPage})" class="paginate_button previous ${currentPage == 1 ? 'disabled' : '' }">上一页</a>
	<span>
		<c:if test="${totalCount == 0 }">
			<a class="paginate_button ${i==currentPage?'current':''}" href="javascript:GoToPage(1,${totalPage})">1</a>
		</c:if>
		<c:if test="${totalPage <= 5 }">
			<c:forEach begin="1" end="${totalPage}" var="i">
				<a class="paginate_button ${i==currentPage?'current':''}" href="javascript:GoToPage(${i},${totalPage})">${i}</a>
			</c:forEach>
		</c:if>
		<c:if test="${totalPage > 5 }">
			<c:choose>
				<c:when test="${currentPage <= 3 }">
					<c:forEach begin="1" end="5" var="i">
						<a class="paginate_button ${i==currentPage?'current':''}" href="javascript:GoToPage(${i},${totalPage})">${i}</a>
					</c:forEach>
				</c:when>
				<c:when test="${totalPage - currentPage < 3 }">
					<c:forEach begin="${totalPage-4 }" end="${totalPage }" var="i">
						<a class="paginate_button ${i==currentPage?'current':''}" href="javascript:GoToPage(${i},${totalPage})">${i}</a>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<c:forEach begin="${currentPage-2 }" end="${currentPage+2 }" var="i">
						<a class="paginate_button ${i==currentPage?'current':''}" href="javascript:GoToPage(${i},${totalPage})">${i}</a>
					</c:forEach>
				</c:otherwise>
			</c:choose>
		</c:if>
	</span>
	<a href="javascript:GoToPage(${currentPage+1},${totalPage})" class="paginate_button next ${currentPage == totalPage ? 'disabled' : '' }">下一页</a>
	<a href="javascript:GoToPage(${totalPage},${totalPage})" class="paginate_button next ${currentPage == totalPage ? 'disabled' : '' }">尾页</a>
</div>
<div class="page_goto">
	<input type="text" id="gotoPage" class="form-control gotoPageInput" /><button id="btnGotoPage" onclick="btnGotoPage()" class="btn gotoPageBtn">Go</button>
</div>
<div class="clearfix"></div>
<script type="text/javascript">
	function btnGotoPage(){
		var page = $("#gotoPage").val();
		var toalPage = "${totalPage}";
		if(isNaN(Number(page))){
			top.layer.alert("请输入数字！");
			$("#gotoPage").val("");
			return false;
		}
		if (Number(page) <= 0) {
			top.layer.alert("请输入正确的页码！");
			$("#gotoPage").val("");
			return false;
		}
		if (Number(page) > Number(toalPage)) {
			top.layer.alert("请输入正确的页码！");
			$("#gotoPage").val("");
			return false;
		}
		GoToPage(Number(page), Number(toalPage));
	}
	function GoToPage(currentPage, totalPage) {
		if(totalPage == 0){
			return false;
		}
		if (currentPage <= 0) {
			currentPage = 1;
		}
		if (currentPage >= totalPage) {
			currentPage = totalPage;
		}

		$("#pageForm")
		.append(
				'<input type="hidden" name="currentPage" value="'+ currentPage +'" >');
		$("#pageForm").submit();

	}
</script>