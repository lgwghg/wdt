var upPhoto = 
{
	form : 
	{
		validateForm : function() 
		{
			$('#upphoto-form').validate({
                errorElement : 'div',
                errorClass : 'help-block',
                focusInvalid : false,
                ignore : "",
                rules : {
					upId : {
						required : true,
	 					maxlength : 32
	                },
					upSecondId : {
						required : false,
	 					maxlength : 32
	                },
					photo : {
						required : true,
	 					maxlength : 300
	                },
					isMain : {
						required : true,
	 					maxlength : 10
	                }
                },
                messages : {
					upId : {
						required : "请填写人物id",
	 					maxlength : "人物id类型长度不能大于32个字符"
	                },
					upSecondId : {
						required : "请填写人物二级id",
	 					maxlength : "人物二级id类型长度不能大于32个字符"
	                },
					photo : {
						required : "请填写图片地址",
	 					maxlength : "图片地址类型长度不能大于300个字符"
	                },
					isMain : {
						required : "请填写是否是主图，默认0   0：非主图  1：主图",
	 					maxlength : "是否是主图，默认0   0：非主图  1：主图类型长度不能大于10个字符"
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
                        url = '/upPhoto/edit.html';
                    } else {
                        url = '/upPhoto/add.html';
                    }
                    webside.wodota.commit('upphoto-form', url, 'formModal' , 'customSearch_photo');
                }
            });
		}
	}
};

$(function() {
	uploadToId("photo", "上传图片");
    if($("#photo").val()!='') {
    	$("#photo_view").attr("src",$("#photo").val());  
    }
});