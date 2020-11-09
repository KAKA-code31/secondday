/**
 * @param currentPage
 * @param url
 * @param boxWarper
 * @param listWraper
 */
function droploadList(currentPage, url, boxWarper, listWraper){
	var dropload =  $(boxWarper).dropload({
		scrollArea : window,
		domDown : {
            domClass   : 'dropload-down',
            domRefresh : '<div class="dropload-refresh">↑上拉加载更多</div>',
            domLoad    : '<div class="dropload-load"><span class="loading"></span>加载中...</div>',
            domNoData  : '<div class="dropload-noData">暂无更多数据</div>'
        },
       loadUpFn : function(me){
            currentPage = 1;
            $.ajax({
                type: 'POST',
                url: url,
                data: {currentPage: currentPage},
                dataType: 'json',
                success: function(result){
                	if(result.success){
	                	var list = result.msg;
	                    $(listWraper).html(list);
	                    initEvent();
	                        // 每次数据加载完，必须重置
	                        me.resetload();
	                        me.unlock();
	                    if(list != ''){
	                    	// 无数据
	                    	me.noData();
	                        }else{
   	                        me.noData(false);
	                        }
                    }else{
                    	// 即使加载出错，也得重置
	                    me.resetload();
                    }
                },
                error: function(xhr, type){
                    // 即使加载出错，也得重置
                    me.resetload();
                }
            });
        },
        loadDownFn : function(me){
            currentPage ++;
            $.ajax({
                type: 'POST',
                url: url,
                data: {currentPage: currentPage},
                dataType: 'json',
                success: function(result){
                    if(result.success){
	                	var list = result.msg;
	                    $(listWraper).append(list);
	                    initEvent();
	                    if(list == ''){
	                      		// 锁定
	                    	me.lock('down');
	                    	// 无数据
	                    	me.noData();
	                        }else{
   	                        me.unlock();
	                        }
	                        // 每次数据加载完，必须重置
	                        me.resetload();
                    }else{
                    	currentPage --;
                    	// 即使加载出错，也得重置
	                    me.resetload();
                    }
                },
                error: function(xhr, type){
                	currentPage --;
                    // 即使加载出错，也得重置
                    me.resetload();
                }
            });
        }
	});
};

$(function(){
	var protocol = window.location.protocol,
	host = window.location.host;
	$.getJSON(protocol+'//'+host + '/mobile/mobileWebkey.do?url=' + encodeURIComponent(location.href.split('#')[0]), function (res) {
		wx.config({
	        debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
	        appId: res.appid, // 必填，公众号的唯一标识
            timestamp: res.timestamp, // 必填，生成签名的时间戳
            nonceStr: res.nonceStr, // 必填，生成签名的随机串
            signature: res.signature, // 必填，签名，见附录1
			// appId: '${appId}', // 必填，公众号的唯一标识
			// timestamp: "${timestamp}", // 必填，生成签名的时间戳
			// nonceStr: '${nonce}', // 必填，生成签名的随机串
			// signature: '${signature}',// 必填，签名，见附录1
	        jsApiList: [
		     	'openLocation',
		     	'getLocation',
		     	'chooseImage',
		     	'previewImage',
		     	'uploadImage',
		     	'showOptionMenu',
		     	'closeWindow',
		     	'hideOptionMenu'
	     	] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
	    });
	});
});