var upName = 
{
	form : 
	{
		validateForm : function() 
		{
			$('#upname-form').validate({
                errorElement : 'div',
                errorClass : 'help-block',
                focusInvalid : false,
                ignore : "",
                rules : {
                	name : {
						required : true,
	 					maxlength : 50
	                },
					"type.value" : {
						required : true
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
	                "type.value" : {
						required : "请选择类别"
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
                        url = '/upNameCtrl/edit.html';
                    } else {
                        url = '/upNameCtrl/add.html';
                    }
                    webside.wodota.commit('upname-form', url, 'formModal' , 'customSearch_n' , 'customSearch');
                }
            });
		}
	}
};

$(function() 
{
    upName.form.validateForm();
    $('select').select2();
});
