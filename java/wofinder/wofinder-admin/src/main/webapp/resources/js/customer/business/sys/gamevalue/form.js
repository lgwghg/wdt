var gameValue = 
{
	form : 
	{
		validateForm : function() 
		{
			$('#gamevalue-form').validate({
                errorElement : 'div',
                errorClass : 'help-block',
                focusInvalid : false,
                ignore : "",
                rules : {
                	"game.id" : {
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
                	"game.id" : {
						required : "请选择游戏"
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
                        url = '/sysGameValueCtrl/edit.html';
                    } else {
                        url = '/sysGameValueCtrl/add.html';
                    }
                    webside.common.commit('gamevalue-form', url, '/sysGameValueCtrl/listUI.html');
                }
            });
		}
	}
};

$(function() 
{
    gameValue.form.validateForm();
    $('select').select2();
});
