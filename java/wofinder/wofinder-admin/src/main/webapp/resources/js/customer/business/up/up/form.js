var up = 
{
	form : 
	{
		validateForm : function() 
		{
			$('#up-form').validate({
                errorElement : 'div',
                errorClass : 'help-block',
                focusInvalid : false,
                ignore : "",
                rules : {
                	name : {
						required : true,
						maxlength : 50
	                },
	                introduction : {
						maxlength : 500
	                },
	                avatar : {
	                	url : true,
	 					maxlength : 200
	                },
	                popularityIndex : {
	                	digits : true,
						min : 0
	                },
	                "status.value" : {
						required : true
	                },
                },
                messages : {
                	name : {
						required : "请输入名称",
						maxlength : "名称长度不能大于50个字符"
	                },
	                introduction : {
						maxlength : "简介长度不能大于500个字符"
	                },
	                avatar : {
	                	url : "请正确填写头像的url地址",
	 					maxlength : "头像长度不能大于200个字符"
	                },
	                popularityIndex : {
	                	digits : "人气指数必须是不小于0的整数",
						min : "人气指数必须是不小于0的整数"
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
                        url = '/upCtrl/edit.html';
                    } else {
                        url = '/upCtrl/add.html';
                    }
                    webside.common.commit('up-form', url, '/upCtrl/listUI.html');
                }
            });
		}
	}
};

$(function() 
{
    up.form.validateForm();
    $('select').select2();
    uploadToId("avatar", "上传头像");
    if($("#avatar").val()!='') {
    	$("#avatar_view").attr("src",$("#avatar").val());  
    }
});

