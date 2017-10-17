var dict = 
{
	form : 
	{
		validateForm : function() {
			$('#dictForm').validate({
                errorElement : 'div',
                errorClass : 'help-block',
                focusInvalid : false,
                ignore : "",
                rules : {
                	type : {
                        required : true,
                        maxlength : 100
                    },
                    label : {
                        required : true,
                        maxlength : 100
                    },
                    value : {
                        required : true,
                        maxlength : 100
                    },
                    sort : {
                        digits : true,
                        min : 1,
                        max : 99
                    },
                    description : {
                    	maxlength : 100
                    },
                    "status.value" : {
						required : true
	                }
                },
                messages : {
                	type : {
                        required : "请填写字典类型",
                        maxlength : "字典类型长度不能大于100个字符"
                    },
                    label : {
                        required : "请填写标签名",
                        maxlength : "标签名长度不能大于100个字符"
                    },
                    value : {
                        required : "请填写数据值",
                        maxlength : "数据值长度不能大于100个字符"
                    },
                    sort : {
                    	digits : "排序值必须为大于0小于100的正整数",
                    	min : "排序值必须为大于0小于100的正整数",
                    	max : "排序值必须为大于0小于100的正整数"
                    },
                    description : {
                        maxlength : "描述长度不能大于100个字符"
                    },
                    "status.value" : {
						required : "请选择状态"
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
                    if (element.is('input[type=checkbox]') || element.is('input[type=radio]')) {
                        var controls = element.closest('div[class*="col-"]');
                        if (controls.find(':checkbox,:radio').length > 1)
                            controls.append(error);
                        else
                            error.insertAfter(element.nextAll('.lbl:eq(0)').eq(0));
                    } else if (element.is('.select2')) {
                        error.insertAfter(element.siblings('[class*="select2-container"]:eq(0)'));
                    } else if (element.is('.chosen-select')) {
                        error.insertAfter(element.siblings('[class*="chosen-container"]:eq(0)'));
                    } else
                        error.insertAfter(element.parent());
                },
                submitHandler : function(form) {
                    var id = $("#id").val();
                    var url = "";
                    if (id != undefined) {
                        url = '/dictCtrl/edit.html';
                    } else {
                        url = '/dictCtrl/add.html';
                    }
                    webside.common.commit('dictForm', url, '/dictCtrl/listUI.html');
                }
            });
        }
    }
}

$(function() 
{
	dict.form.validateForm();
    $('select').select2();
});