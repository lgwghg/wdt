var videoRecommend = 
{
	form : 
	{
		validateForm : function() 
		{
			$('#videorecommend-form').validate({
                errorElement : 'div',
                errorClass : 'help-block',
                focusInvalid : false,
                ignore : "",
                rules : {
					"video.id" : {
						required : true
	                },
					/*url : {
						required : true
	                },*/
					startTime : {
						required : true
	                },
	                "status.value" : {
						required : true
	                },
                },
                messages : {
					"video.id" : {
						required : "请选择视频"
	                },
					/*url : {
						required : "请上传展示视频"
	                },*/
					startTime : {
						required : "请选择开始展示时间"
	                },
	                "status.value" : {
						required : "请选择状态"
	                },
                },
                highlight : function(e) {
                    $(e).closest('.form-group').removeClass('has-info').addClass('has-error');
                },
                success : function(e) {
                    $(e).closest('.form-group').removeClass('has-error').addClass('has-success');
                    $(e).remove();
                },
                errorPlacement : function(error, element) {
                    error.insertAfter(element.parent());
                },
                submitHandler : function(form) {
                    var id = $("#id").val();
                    var url = "";
                    if (id != undefined) {
                        url = '/videoRecommendCtrl/edit.html';
                    } else {
                        url = '/videoRecommendCtrl/add.html';
                    }
                    webside.common.commit('videorecommend-form', url, '/videoRecommendCtrl/listUI.html');
                }
            });
		}
	}
};

$(function() 
{
	if($("#id").val() == undefined){
		$('#video').hide();
	}
    videoRecommend.form.validateForm();
    $('select').select2();
    
    var up = $('#video_upload').Huploadify({
		auto:true,
		fileTypeExts:'*.flv;*.mp4;*.avi',//允许的格式  
		multi:false,
		formData:{jssessionid: '${cookie.SSESSIONID.value}',type: 'videos'},
		fileSizeLimit:99999999999,
		showUploadedPercent:true,
		removeTimeout:9999999,
		uploader:sys.rootPath+'/upload/fileUpload',
		buttonCursor   : 'hand',  
	    buttonText     : '上传视频',  
	    height         : '25',  
		onUploadSuccess:function(file,data,response) 
		{  
	        //设置视频预览  
	        if(data)
	        {
	        	if(data.indexOf("error:") > -1)
	        	{
	        		alert(data.substring(data.indexOf("error:")));
	        		return;
	        	}
	        	$("#url").val(data.replace(new RegExp('"',"gm"),''));
	        	$("#video").attr("src",data.replace(new RegExp('"',"gm"),''));
	        	$('#video').show();
	        }
	        
	        $("#video_upload_file_upload_1-queue").html("");//清空上传任务进度条div
	    }
	});
});
