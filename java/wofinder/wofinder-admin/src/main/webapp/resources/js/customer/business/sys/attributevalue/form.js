var attributeValue = 
{
	form : 
	{
		validateForm : function() 
		{
			$('#attributevalue-form').validate({
                errorElement : 'div',
                errorClass : 'help-block',
                focusInvalid : false,
                ignore : "",
                rules : {
	                "attribute.id" : {
						required : true
	                },
	                "value.id" : {
						required : true
	                },
	                "status.value" : {
						required : true
	                },
                },
                messages : {
                	"attribute.id" : {
						required : "请选择属性"
	                },
	                "value.id" : {
						required : "请选择属性值"
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
                        url = '/sysAttributeValueCtrl/edit.html';
                    } else {
                        url = '/sysAttributeValueCtrl/add.html';
                    }
                    webside.common.commit('attributevalue-form', url, '/sysAttributeValueCtrl/listUI.html');
                }
            });
		}
	}
};

$(function() 
{
    attributeValue.form.validateForm();
    $('select').select2();
});
