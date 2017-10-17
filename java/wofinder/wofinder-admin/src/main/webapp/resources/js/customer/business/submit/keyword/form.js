var submitKeyword = 
{
	form : 
	{
		validateForm : function() 
		{
			$('#submitkeyword-form').validate({
                errorElement : 'div',
                errorClass : 'help-block',
                focusInvalid : false,
                ignore : "",
                rules : {
	                "status.value" : {
						required : true
	                }
                },
                messages : {
                	"status.value" : {
						required : "请选择状态",
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
                    var id = $("#id").val();
                    var url = "";
                    if (id != undefined) {
                        url = '/submitKeywordCtrl/edit.html';
                    } else {
                        url = '/submitKeywordCtrl/add.html';
                    }
                    webside.common.commit('submitkeyword-form', url, '/submitKeywordCtrl/listUI.html');
                }
            });
		}
	}
};

$(function() 
{
    submitKeyword.form.validateForm();
    $('select').select2();
});
