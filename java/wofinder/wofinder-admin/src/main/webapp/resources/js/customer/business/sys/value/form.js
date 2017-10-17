var value = 
{
	form : 
	{
		validateForm : function() 
		{
			$('#value-form').validate({
                errorElement : 'div',
                errorClass : 'help-block',
                focusInvalid : false,
                ignore : "",
                rules : {
					name : {
						required : true,
	 					maxlength : 50
	                },
	                "status.value" : {
						required : true
	                },
                },
                messages : {
                	name : {
						required : "请填写名称",
	 					maxlength : "名称长度不能大于50个字符"
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
                        url = '/sysValueCtrl/edit.html';
                    } else {
                        url = '/sysValueCtrl/add.html';
                    }
                    webside.common.commit('value-form', url, '/sysValueCtrl/listUI.html');
                }
            });
		}
	}
};

$(function() 
{
    value.form.validateForm();
    $('select').select2();
});
