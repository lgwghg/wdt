var videoStation = 
{
	form : 
	{
		validateForm : function() 
		{
			$('#videostation-form').validate({
                errorElement : 'div',
                errorClass : 'help-block',
                focusInvalid : false,
                ignore : "",
                rules : {
					vid : {
						required : true,
	 					maxlength : 100
	                },
					url : {
						required : true,
						url : true,
	 					maxlength : 300
	                },
	                flashUrl : {
						required : true,
						url : true,
	 					maxlength : 300
	                },
					title : {
						required : true,
	 					maxlength : 200
	                },
					cover : {
						required : true,
						url : true,
	 					maxlength : 200
	                },
					duration : {
						required : true,
						number : true,
						min : 1,
	 					maxlength : 10
	                },
	                viewCount : {
	                	digits : true,
						min : 0
	                },
	                commentCount : {
	                	digits : true,
						min : 0
	                },
	                downCount : {
	                	digits : true,
						min : 0
	                },
	                favoriteCount : {
	                	digits : true,
						min : 0
	                },
	                "station.value" : {
						required : true
	                },
	                "status.value" : {
						required : true
	                },
                },
                messages : {
					vid : {
						required : "请填写视频源id",
	 					maxlength : "视频源id长度不能大于100个字符"
	                },
					url : {
						required : "请填写视频源url",
						url : "请正确填写视频源的url地址",
	 					maxlength : "视频源url长度不能大于300个字符"
	                },
	                flashUrl : {
						required : "请填写视频播放url",
						url : "请正确填写视频播放的url地址",
	 					maxlength : "视频播放url长度不能大于300个字符"
	                },
					title : {
						required : "请填写标题",
	 					maxlength : "标题长度不能大于200个字符"
	                },
					cover : {
						required : "请填写封面",
						url : "请正确填写封面的url地址",
	 					maxlength : "封面长度不能大于200个字符"
	                },
					duration : {
						required : "请填写时长",
						number : "视频时长必须是不小于1的数字",
						min : "视频时长必须是不小于1的数字",
	 					maxlength : "时长长度不能大于10个字符"
	                },
	                viewCount : {
	                	digits : "播放量必须是不小于0的整数",
						min : "播放量必须是不小于0的整数"
	                },
	                commentCount : {
	                	digits : "评论量必须是不小于0的整数",
						min : "评论量必须是不小于0的整数"
	                },
	                downCount : {
	                	digits : "下载量必须是不小于0的整数",
						min : "下载量必须是不小于0的整数"
	                },
	                favoriteCount : {
	                	digits : "收藏量必须是不小于0的整数",
						min : "收藏量必须是不小于0的整数"
	                },
	                "station.value" : {
						required : "请选择站点"
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
                        url = '/videoStationCtrl/edit.html';
                    } else {
                        url = '/videoStationCtrl/add.html';
                    }
                    webside.wodota.commit('videostation-form', url, 'formModal' , 'customSearch_s');
                }
            });
		}
	}
};

$(function() 
{
    videoStation.form.validateForm();
    $('select').select2();
});
