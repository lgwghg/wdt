var video = 
{
	form : 
	{
		validateForm : function() 
		{
			$('#video-form').validate({
                errorElement : 'div',
                errorClass : 'help-block',
                focusInvalid : false,
                ignore : "",
                rules : {
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
	                "status.value" : {
						required : true
	                },
                },
                messages : {
					title : {
						required : "请填写视频标题",
	 					maxlength : "视频标题长度不能大于200个字符"
	                },
					cover : {
						required : "请填写视频封面",
						url : "请正确填写视频封面的url地址",
	 					maxlength : "视频封面长度不能大于200个字符"
	                },
					duration : {
						required : "请填写视频时长",
						number : "视频时长必须是不小于1的数字",
						min : "视频时长必须是不小于1的数字",
	 					maxlength : "视频时长长度不能大于10个字符"
	                },
	                "status.value" : {
						required : "请选择状态",
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
                        url = '/videoCtrl/edit.html';
                    } else {
                        url = '/videoCtrl/add.html';
                    }
                    webside.common.commit('video-form', url, '/videoCtrl/listUI.html');
                }
            });
		}
	}
};

$(function() 
{
    video.form.validateForm();
    $('select').select2();
});