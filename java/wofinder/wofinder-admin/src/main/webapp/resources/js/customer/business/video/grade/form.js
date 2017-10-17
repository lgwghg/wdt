var videoGrade = 
{
	form : 
	{
		validateForm : function() 
		{
			$('#videograde-form').validate({
                errorElement : 'div',
                errorClass : 'help-block',
                focusInvalid : false,
                ignore : "",
                rules : {
					id : {
						required : true,
	 					maxlength : 32
	                },
					userId : {
						required : true,
	 					maxlength : 32
	                },
					videoId : {
						required : true,
	 					maxlength : 32
	                },
					score : {
						required : true,
	 					maxlength : 2
	                },
					createTime : {
						required : true,
	 					maxlength : 19
	                },
					status : {
						required : true,
	 					maxlength : 10
	                },
                },
                messages : {
					id : {
						required : "请填写评分",
	 					maxlength : "评分长度不能大于32个字符"
	                },
					userId : {
						required : "请填写评分人",
	 					maxlength : "评分人长度不能大于32个字符"
	                },
					videoId : {
						required : "请填写视频 t_video",
	 					maxlength : "视频 t_video长度不能大于32个字符"
	                },
					score : {
						required : "请填写评分",
	 					maxlength : "评分长度不能大于2个字符"
	                },
					createTime : {
						required : "请填写创建时间",
	 					maxlength : "创建时间长度不能大于19个字符"
	                },
					status : {
						required : "请填写状态，1有效，0无效",
	 					maxlength : "状态，1有效，0无效长度不能大于10个字符"
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
                        url = '/videoGrade/edit.html';
                    } else {
                        url = '/videoGrade/add.html';
                    }
                    webside.common.commit('videograde-form', url, '/videoGrade/listUI.html');
                }
            });
		}
	}
};

$(function() 
{
    videoGrade.form.validateForm();
    $('select').select2();
});
