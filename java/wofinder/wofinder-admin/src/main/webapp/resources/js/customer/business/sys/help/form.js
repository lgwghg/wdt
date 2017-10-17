var sysHelp = 
{
	form : 
	{
		validateForm : function() 
		{
			$('#syshelp-form').validate({
                errorElement : 'div',
                errorClass : 'help-block',
                focusInvalid : false,
                ignore : "",
                rules : {
					title : {
						required : true,
	 					maxlength : 100
	                },
					content : {
						required : true,
	 					maxlength : 65535
	                },
					type : {
						required : true,
	 					maxlength : 10
	                },
					sequence : {
	 					maxlength : 10
	                },
					status : {
						required : true,
	 					maxlength : 10
	                },
					code : {
	 					maxlength : 20
	                },
                },
                messages : {
					id : {
						required : "请填写系统帮助",
	 					maxlength : "系统帮助长度不能大于32个字符"
	                },
					title : {
						required : "请填写标题",
	 					maxlength : "标题长度不能大于100个字符"
	                },
					content : {
						required : "请填写通知内容",
	 					maxlength : "通知内容长度不能大于65535个字符"
	                },
					addTime : {
						required : "请填写创建时间",
	 					maxlength : "创建时间长度不能大于19个字符"
	                },
					sysUserId : {
						required : "请填写添加人员",
	 					maxlength : "添加人员长度不能大于32个字符"
	                },
					type : {
						required : "请填写1.公告 2.更新 3.介绍 4.使用帮助 5.用户须知 6.反馈",
	 					maxlength : "1.公告 2.更新 3.介绍 4.使用帮助 5.用户须知 6.反馈长度不能大于10个字符"
	                },
					sequence : {
						required : "请填写排序",
	 					maxlength : "排序长度不能大于10个字符"
	                },
					status : {
						required : "请填写状态 1：有效 0 无效 ，2：注册，3：底部 默认1",
	 					maxlength : "状态 1：有效 0 无效 ，2：注册，3：底部 默认1长度不能大于10个字符"
	                },
					code : {
						required : "请填写内码",
	 					maxlength : "内码长度不能大于20个字符"
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
                        url = '/helpCtrl/edit.html';
                    } else {
                        url = '/helpCtrl/add.html';
                    }
                    webside.common.commit('syshelp-form', url, '/helpCtrl/listUI.html');
                }
            });
		}
	}
};

$(function() 
{
    sysHelp.form.validateForm();
    $('select').select2();
});
