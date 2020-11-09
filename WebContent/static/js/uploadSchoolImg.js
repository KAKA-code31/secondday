// 文件上传
jQuery(function() {
    var $ = jQuery,
        $list = $('#thelist'),
        //$btn = $('#ctlBtn'),
        $del = $(".del"),
        state = 'pending',
        $protocol = window.location.protocol,
        $host = window.location.host,
        $project = "/internship",
        //$project = "/irobot",
        ratio = window.devicePixelRatio || 1,

        // 缩略图大小
        thumbnailWidth = 150 * ratio,
        thumbnailHeight = 100 * ratio,
        uploader;

    uploader = WebUploader.create({

    	// 自动上传。
        auto: true,
        
        // 开起分片上传。
        //chunked: true,
        //chunksize:1048576,
       // chunkRetry: 5,
        //sendAsBinary: true,
    	
        //
        name: "file", 
        fileNumLimit: 1,
        fileSizeLimit: 314572800,
        fileSingleSizeLimit: 104857600,

        // swf文件路径
        swf: '${pageContext.request.contextPath}/static/lib/webuploader/0.1.5/Uploader.swf',

        // 文件接收服务端。
        //server: $protocol+'//'+$host + '${pageContext.request.contextPath}/back/upload/uploadSchoolImg.do',
        // back == > basejs.jsp中的变量
        server: back + '/upload/uploadSchoolImg.do',

        // 选择文件的按钮。可选。
        // 内部根据当前运行是创建，可能是input元素，也可能是flash.
        pick: '#picker',
        
        // 只允许选择文件，可选。
        accept: {
            title: 'intoTypes',
            extensions: 'rar,zip,doc,xls,docx,xlsx,pdf,txt,ppt,pptx,gif,jpg,jpeg,bmp,png,oft,msg',
            mimeTypes: '.rar,.zip,.doc,.xls,.docx,.xlsx,.pdf,.txt,.ppt,.pptx,image/*,.oft,.msg'
        }
    });

    // 当有文件添加进来的时候
    uploader.on( 'fileQueued', function( file ) {
    	var $li = $(
                '<div id="' + file.id + '" class="file-item thumbnail">' +
                    '<img>' +
                    '<div class="info">' + file.name + '</div>' +
                    '<input name="logoUrl" type="hidden" value="' + file.name + '">' + 
                '</div>'
                ),
            $img = $li.find('img');
    	$list.append($li);
       /* $list.append( '<div id="' + file.id + '" class="item">' +
            '<label class="info">' + file.name + '</label>' +
            '<p class="state">等待上传...</p>' +
            '<input name="photo" type="hidden" value="' +file.name + '">' +
        '</div>' );*/
        $del = $(".del");

        $del.on('click', function(){
        	removeFile(file);
        });
        
        // 创建缩略图
        uploader.makeThumb( file, function( error, src ) {
            if ( error ) {
                $img.replaceWith('<span>不能预览</span>');
                return;
            }

            $img.attr( 'src', src );
        }, thumbnailWidth, thumbnailHeight );
    });

    // 文件上传过程中创建进度条实时显示。
    uploader.on( 'uploadProgress', function( file, percentage ) {
        var $li = $( '#'+file.id ),
            $percent = $li.find('.progress .progress-bar');

        // 避免重复创建
        if ( !$percent.length ) {
            $percent = $('<div class="progress progress-striped active">' +
              '<div class="progress-bar" role="progressbar" style="width: 100%">' +
              '</div>' +
            '</div>').appendTo( $li ).find('.progress-bar');
        }

        $li.find('p.state').text('正在上传...');

        $percent.css( 'width', percentage * 100 + '%' );
    });

    uploader.on( 'uploadSuccess', function( file, response ) {
    	$( '#'+file.id ).find('div.info').text(response.fileName);
    	$( '#'+file.id ).find('input').val(response.fileName);
        $( '#'+file.id ).find('p.state').text('上传成功');
    });

    uploader.on( 'uploadError', function( file, reason ) {
        $( '#'+file.id ).find('p.state').text('上传失败');
    });

    uploader.on( 'uploadComplete', function( file ) {
        $( '#'+file.id ).find('.progress').fadeOut();
    });

    uploader.on( 'all', function( type ) {
        if ( type === 'startUpload' ) {
            state = 'uploading';
        } else if ( type === 'stopUpload' ) {
            state = 'paused';
        } else if ( type === 'uploadFinished' ) {
            state = 'done';
        }

        /*if ( state === 'uploading' ) {
            $btn.text('暂停上传');
        } else {
            $btn.text('开始上传');
        }*/
    });

    /*$btn.on( 'click', function() {
        if ( state === 'uploading' ) {
            uploader.stop();
        } else {
            uploader.upload();
        }
    });*/
    
    /*uploader.on('error', function(handler){
    	//alert(handler);
    });*/
    
    // 负责view的销毁
    function removeFile(file) {
    	/*alert("location:"+window.location);
    	alert("href:"+window.location.href);
    	alert("protocol:"+window.location.protocol);
    	alert("host&port:"+window.location.host);
    	alert("port:"+window.location.port);
    	alert("hostname:"+window.location.hostname);*/
    	var url = window.location.protocol+ '//'+window.location.host + $project + '/upload/deleteFile.htm';
	    var $li = $('#' + file.id);
	    $.post(url, {'fileFileName' : $( '#'+file.id ).find('label.info').text()}, 
    		function(){
    			uploader.removeFile(file, true);
    			$li.off().find('.del').off().end().remove();
	    });
    }
});