var userMessage = 
{
	form : 
	{
		validateForm : function() 
		{
			$('#usermessage-form').validate({
                errorElement : 'div',
                errorClass : 'help-block',
                focusInvalid : false,
                ignore : "",
                rules : {
					userId : {
						required : true,
	 					maxlength : 32
	                },
					businessType : {
						required : true,
	 					maxlength : 10
	                },
					businessId : {
						required : true,
	 					maxlength : 32
	                },
					content : {
						required : true,
	 					maxlength : 512
	                },
					state : {
						required : true,
	 					maxlength : 10
	                },
					isDeleted : {
						required : true,
	 					maxlength : 10
	                },
                },
                messages : {
					userId : {
						required : "请填写用户ID",
	 					maxlength : "用户ID长度不能大于32个字符"
	                },
					businessType : {
						required : "请填写业务类型 1:评论",
	 					maxlength : "业务类型 1:评论长度不能大于10个字符"
	                },
					businessId : {
						required : "请填写业务ID",
	 					maxlength : "业务ID长度不能大于32个字符"
	                },
					content : {
						required : "请填写消息描述",
	 					maxlength : "消息描述长度不能大于512个字符"
	                },
					state : {
						required : "请填写阅读状态",
	 					maxlength : "阅读状态 1：已读，0：未读长度不能大于10个字符"
	                },
					isDeleted : {
						required : "是否删除",
	 					maxlength : "默认0     1：已删除   0：正常长度不能大于10个字符"
	                },
					createTime : {
						required : "请填写消息创建时间",
	 					maxlength : "消息创建时间长度不能大于19个字符"
	                },
					updateTime : {
						required : "请填写更新已读状态时，更新时间",
	 					maxlength : "更新已读状态时，更新时间长度不能大于19个字符"
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
                        url = '/userMessageCtrl/edit.html';
                    } else {
                        url = '/userMessageCtrl/add.html';
                    }
                    webside.common.commit('usermessage-form', url, '/userMessageCtrl/listUI.html');
                }
            });
		}
	}
};

$(function() 
{
    userMessage.form.validateForm();
    $('select').select2();
});
