var task = 
{
	form : 
	{
		validateForm : function() 
		{
			$('#task-form').validate({
                errorElement : 'div',
                errorClass : 'help-block',
                focusInvalid : false,
                ignore : "",
                rules : {
                	"up.id" : {
						required : true
	                },
					title : {
						required : true,
	 					maxlength : 200
	                },
					content : {
						required : true,
	 					maxlength : 65535
	                },
					viewCount : {
						required : true,
						digits:true,
	 					min:0
	                },
					likeCount : {
						required : true,
						digits:true,
	 					min:0
	                },
					status : {
						required : true
	                },
                },
                messages : {
					upId : {
						required : "请选择人物名称",
	                },
					title : {
						required : "请填写标题",
	 					maxlength : "标题长度不能大于200个字符"
	                },
					content : {
						required : "请填写内容",
	 					maxlength : "内容长度不能大于65535个字符"
	                },
					viewCount : {
						required : "请填写阅读量",
	 					digits:"阅读量只允许大于等于0的整数",
	 					min:"阅读量只允许大于等于0的整数"
	                },
					likeCount : {
						required : "请填写点赞量",
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
                        url = '/taskCtrl/edit.html';
                    } else {
                        url = '/taskCtrl/add.html';
                    }
                    webside.common.commit('task-form', url, '/taskCtrl/listUI.html');
                }
            });
		}
	}
};

$(function() 
{
    task.form.validateForm();
    $('select').select2();
    UE.getEditor("content");
});
