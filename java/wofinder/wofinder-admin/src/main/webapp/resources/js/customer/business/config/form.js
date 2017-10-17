var sysConfig = 
{
	form : 
	{
		validateForm : function() 
		{
			$('#sysconfig-form').validate({
                errorElement : 'div',
                errorClass : 'help-block',
                focusInvalid : false,
                ignore : "",
                rules : {
					configKey : {
						required : true,
	 					maxlength : 100
	                },
					configValue : {
						required : true,
	 					maxlength : 500
	                }
                },
                messages : {
					configKey : {
						required : "请填写配置键",
	 					maxlength : "配置键类型长度不能大于100个字符"
	                },
					configValue : {
						required : "请填写配置值",
	 					maxlength : "配置值类型长度不能大于500个字符"
	                }
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
                        url = '/configCtrl/edit.html';
                    } else {
                        url = '/configCtrl/add.html';
                    }
                    webside.common.commit('sysconfig-form', url, '/configCtrl/listUI.html');
                }
            });
		}
	}
};

$(function() 
{
    sysConfig.form.validateForm();
});
