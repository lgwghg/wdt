var seoConfig = 
{
	form : 
	{
		validateForm : function() 
		{
			$('#seoconfig-form').validate({
                errorElement : 'div',
                errorClass : 'help-block',
                focusInvalid : false,
                ignore : "",
                rules : {
					keywords : {
	 					maxlength : 512
	                },
					description : {
	 					maxlength : 512
	                },
					title : {
	 					maxlength : 512
	                },
	                "type.value" : {
						required : true
	                },
	                "status.value" : {
						required : true
	                },
                },
                messages : {
					keywords : {
	 					maxlength : "keywords长度不能大于512个字符"
	                },
					description : {
	 					maxlength : "description长度不能大于512个字符"
	                },
					title : {
	 					maxlength : "标题长度不能大于512个字符"
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
                        url = '/seoConfigCtrl/edit.html';
                    } else {
                        url = '/seoConfigCtrl/add.html';
                    }
                    webside.common.commit('seoconfig-form', url, '/seoConfigCtrl/listUI.html');
                }
            });
		}
	}
};

$(function() 
{
    seoConfig.form.validateForm();
    $('select').select2();
});
