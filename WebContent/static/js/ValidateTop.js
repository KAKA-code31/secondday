/**
 * 防止页面被iframe嵌套js,页面加载，若发现当前页面不是顶层页面，则会跳转
 */
$(function(){
	if(top.location!==self.location){
		top.location.href=self.location.href;
		self.location.href = '';
	}
})
