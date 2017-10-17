var videoCommentLike = 
{
	form : 
	{
		validateForm : function() 
		{
			$('#videocommentlike-form').validate({
                errorElement : 'div',
                errorClass : 'help-block',
                focusInvalid : false,
                ignore : "",
                rules : {
					id : {
						required : true,
	 					maxlength : 32
	                },
					userId : {
						required : true,
	 					maxlength : 32
	                },
					commentId : {
						required : true,
	 					maxlength : 32
	                },
					createTime : {
						required : true,
	 					maxlength : 19
	                },
					status : {
						required : true,
	 					maxlength : 10
	                },
                },
                messages : {
					id : {
						required : "请填写点赞",
	 					maxlength : "点赞长度不能大于32个字符"
	                },
					userId : {
						required : "请填写点赞人",
	 					maxlength : "点赞人长度不能大于32个字符"
	                },
					commentId : {
						required : "请填写评论 t_video_comment",
	 					maxlength : "评论 t_video_comment长度不能大于32个字符"
	                },
					createTime : {
						required : "请填写创建时间",
	 					maxlength : "创建时间长度不能大于19个字符"
	                },
					status : {
						required : "请填写状态，1有效，0无效",
	 					maxlength : "状态，1有效，0无效长度不能大于10个字符"
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
                        url = '/videoCommentLike/edit.html';
                    } else {
                        url = '/videoCommentLike/add.html';
                    }
                    webside.common.commit('videocommentlike-form', url, '/videoCommentLike/listUI.html');
                }
            });
		}
	}
};

$(function() 
{
    videoCommentLike.form.validateForm();
    $('select').select2();
});
