var userForgetPassword = 
{
	form : 
	{
		validateForm : function() 
		{
			$('#userforgetpassword-form').validate({
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
					forgetReason : {
						required : true,
	 					maxlength : 255
	                },
					status : {
						required : true,
	 					maxlength : 10
	                },
					createId : {
						required : true,
	 					maxlength : 32
	                },
					createTime : {
						required : true,
	 					maxlength : 19
	                },
                },
                messages : {
					id : {
						required : "请填写主键",
	 					maxlength : "主键类型长度不能大于32个字符"
	                },
					userId : {
						required : "请填写用户",
	 					maxlength : "用户类型长度不能大于32个字符"
	                },
					forgetReason : {
						required : "请填写",
	 					maxlength : "类型长度不能大于255个字符"
	                },
					status : {
						required : "请填写状态",
	 					maxlength : "状态类型长度不能大于10个字符"
	                },
					createId : {
						required : "请填写创建者",
	 					maxlength : "创建者类型长度不能大于32个字符"
	                },
					createTime : {
						required : "请填写创建时间",
	 					maxlength : "创建时间类型长度不能大于19个字符"
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
                    var vId = $("#id").val();
                    var url = "";
                    if (vId != undefined) {
                        url = '/userForgetPassword/edit.html';
                    } else {
                        url = '/userForgetPassword/add.html';
                    }
                    webside.common.commit('userforgetpassword-form', url, '/userForgetPassword/listUI.html');
                }
            });
		}
	}
};
