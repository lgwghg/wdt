var attribute = 
{
	form : 
	{
		validateForm : function() 
		{
			$('#attribute-form').validate({
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
                        url = '/sysAttributeCtrl/edit.html';
                    } else {
                        url = '/sysAttributeCtrl/add.html';
                    }
                    webside.common.commit('attribute-form', url, '/sysAttributeCtrl/listUI.html');
                }
            });
		}
	}
};

$(function() 
{
    attribute.form.validateForm();
    $('select').select2();
});
