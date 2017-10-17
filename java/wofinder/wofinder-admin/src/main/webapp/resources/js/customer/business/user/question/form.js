var userSecurityQuestion = 
{
	form : 
	{
		validateForm : function() 
		{
			$('#usersecurityquestion-form').validate({
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
					question : {
						required : true,
	 					maxlength : 100
	                },
					answer : {
						required : true,
	 					maxlength : 100
	                },
					createTime : {
						required : true,
	 					maxlength : 19
	                },
					updateTime : {
						required : true,
	 					maxlength : 19
	                },
                },
                messages : {
					id : {
						required : "请填写",
	 					maxlength : "类型长度不能大于32个字符"
	                },
					userId : {
						required : "请填写",
	 					maxlength : "类型长度不能大于32个字符"
	                },
					question : {
						required : "请填写问题",
	 					maxlength : "问题类型长度不能大于100个字符"
	                },
					answer : {
						required : "请填写答案",
	 					maxlength : "答案类型长度不能大于100个字符"
	                },
					createTime : {
						required : "请填写",
	 					maxlength : "类型长度不能大于19个字符"
	                },
					updateTime : {
						required : "请填写",
	 					maxlength : "类型长度不能大于19个字符"
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
                        url = '/userSecurityQuestion/edit.html';
                    } else {
                        url = '/userSecurityQuestion/add.html';
                    }
                    webside.common.commit('usersecurityquestion-form', url, '/userSecurityQuestion/listUI.html');
                }
            });
		}
	}
};
