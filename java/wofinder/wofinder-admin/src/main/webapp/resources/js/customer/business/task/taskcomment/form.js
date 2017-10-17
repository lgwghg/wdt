var taskComment = 
{
	form : 
	{
		validateForm : function() 
		{
			$('#taskcomment-form').validate({
                errorElement : 'div',
                errorClass : 'help-block',
                focusInvalid : false,
                ignore : "",
                rules : {
					content : {
						required : true,
	 					maxlength : 65535
	                },
	                "parentComment.id" : {
						required : true,
	 					maxlength : 32
	                },
					likeNum : {
						required : true,
						digits:true,
	 					min:0
	                },
					status : {
						required : true
	                },
                },
                messages : {
					content : {
						required : "请填写评论内容",
	 					maxlength : "评论内容长度不能大于65535个字符"
	                },
	               "parentComment.id" : {
						required : "请填写父级评论id",
	 					maxlength : "父级评论id长度不能大于32个字符"
	                },
					likeNum : {
						required : "请填写点赞数量",
						digits:"点赞量只允许大于等于0的整数",
	 					min:"点赞量只允许大于等于0的整数"
	                },
					status : {
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
                        url = '/taskCommentCtrl/edit.html';
                    } else {
                        url = '/taskCommentCtrl/add.html';
                    }
                    webside.wodota.commit('taskcomment-form', url, 'formModal' , 'customSearch_c');
                }
            });
		}
	}
};

$(function() 
{
    taskComment.form.validateForm();
    $('select').select2();
});
