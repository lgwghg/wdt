var submitUrl = 
{
	form : 
	{
		validateForm : function() 
		{
			$('#submiturl-form').validate({
                errorElement : 'div',
                errorClass : 'help-block',
                focusInvalid : false,
                ignore : "",
                rules : {
					id : {
						required : true,
	 					maxlength : 32
	                },
					skId : {
						required : true,
	 					maxlength : 32
	                },
					url : {
						required : true,
	 					maxlength : 200
	                },
                },
                messages : {
					id : {
						required : "请填写主键",
	 					maxlength : "主键长度不能大于32个字符"
	                },
					skId : {
						required : "请填写提交搜索关键字id",
	 					maxlength : "提交搜索关键字id长度不能大于32个字符"
	                },
					url : {
						required : "请填写url",
	 					maxlength : "url长度不能大于200个字符"
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
                        url = '/submitUrl/edit.html';
                    } else {
                        url = '/submitUrl/add.html';
                    }
                    webside.common.commit('submiturl-form', url, '/submitUrl/listUI.html');
                }
            });
		}
	}
};

$(function() 
{
    submitUrl.form.validateForm();
    $('select').select2();
});
