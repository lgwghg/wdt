var videoComment = 
{
	form : 
	{
		validateForm : function() 
		{
			$('#videocomment-form').validate({
                errorElement : 'div',
                errorClass : 'help-block',
                focusInvalid : false,
                ignore : "",
                rules : {
					commentId : {
						required : true,
	 					maxlength : 50
	                },
	                commentContent : {
						required : true,
	 					maxlength : 300
	                },
	                commentCreatetime : {
						required : true
	                },
	                "station.value" : {
						required : true
	                },
	                "status.value" : {
						required : true
	                }
                },
                messages : {
                	commentId : {
						required : "请填写评论id",
	 					maxlength : "评论id长度不能大于50个字符"
	                },
	                commentContent : {
						required : "请填写评论内容",
	 					maxlength : "评论内容长度不能大于300个字符"
	                },
	                commentCreatetime : {
						required : "请选择评论时间"
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
                        url = '/videoCommentCtrl/edit.html';
                    } else {
                        url = '/videoCommentCtrl/add.html';
                    }
                    webside.wodota.commit('videocomment-form', url, 'formModal' , 'customSearch_c');
                }
            });
		}
	}
};

$(function() 
{
    videoComment.form.validateForm();
    $('select').select2();
});
