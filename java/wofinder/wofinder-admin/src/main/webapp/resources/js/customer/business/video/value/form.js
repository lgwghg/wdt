var videoValue = 
{
	form : 
	{
		validateForm : function() 
		{
			$('#videovalue-form').validate({
                errorElement : 'div',
                errorClass : 'help-block',
                focusInvalid : false,
                ignore : "",
                rules : {
                	"value.name" : {
						required : true
	                },
	                "status.value" : {
						required : true
	                },
                },
                messages : {
                	"value.name" : {
						required : "请输入属性值"
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
                        url = '/videoValueCtrl/edit.html';
                    } else {
                        url = '/videoValueCtrl/add.html';
                    }
                    webside.wodota.commit('videovalue-form', url, 'formModal' , 'customSearch_v');
                }
            });
		}
	}
};

$(function() 
{
    videoValue.form.validateForm();
    $('select').select2();
});
