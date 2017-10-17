var upSecondLevel = 
{
	form : 
	{
		validateForm : function() 
		{
			$('#upsecondlevel-form').validate({
                errorElement : 'div',
                errorClass : 'help-block',
                focusInvalid : false,
                ignore : "",
                rules : {
					upId : {
						required : true,
	 					maxlength : 255
	                },
					titleType : {
						required : true,
	 					maxlength : 10
	                },
					content : {
						required : true,
	 					maxlength : 65535
	                },
					sort : {
						required : true,
	 					maxlength : 10
	                }
                },
                messages : {
					upId : {
						required : "请填写人物id",
	 					maxlength : "人物id类型长度不能大于255个字符"
	                },
					titleType : {
						required : "请填写标题类型，字典存储",
	 					maxlength : "标题类型，字典存储类型长度不能大于10个字符"
	                },
					content : {
						required : "请填写内容",
	 					maxlength : "内容类型长度不能大于65535个字符"
	                },
					sort : {
						required : "请填写排序",
	 					maxlength : "排序类型长度不能大于10个字符"
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
                        url = '/upSecondLevel/edit.html';
                    } else {
                        url = '/upSecondLevel/add.html';
                    }
                    webside.wodota.commit('upsecondlevel-form', url, 'formModal', 'customSearch_secondlevel');
                }
            });
		}
	}
};
