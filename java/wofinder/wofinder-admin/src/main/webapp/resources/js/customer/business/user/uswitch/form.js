var userSwitch = 
{
	form : 
	{
		validateForm : function() 
		{
			$('#userswitch-form').validate({
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
					switchType : {
						required : true,
	 					maxlength : 255
	                },
					value : {
						required : true,
	 					maxlength : 10
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
					switchType : {
						required : "请填写",
	 					maxlength : "类型长度不能大于255个字符"
	                },
					value : {
						required : "请填写",
	 					maxlength : "类型长度不能大于10个字符"
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
                        url = '/userSwitch/edit.html';
                    } else {
                        url = '/userSwitch/add.html';
                    }
                    webside.common.commit('userswitch-form', url, '/userSwitch/listUI.html');
                }
            });
		}
	}
};
