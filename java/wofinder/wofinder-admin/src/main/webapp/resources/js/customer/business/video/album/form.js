var videoAlbum = 
{
	form : 
	{
		validateForm : function() 
		{
			$('#videoalbum-form').validate({
                errorElement : 'div',
                errorClass : 'help-block',
                focusInvalid : false,
                ignore : "",
                rules : {
                	name : {
						required : true,
	 					maxlength : 50
	                },
					"status.value" : {
						required : true
	                },
                },
                messages : {
                	name : {
						required : "请填写合集名称",
	 					maxlength : "合集名称长度不能大于50个字符"
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
                    var flag = true;
                    if (id != undefined) {
                    	var parentId = $("#parentId").val();
                    	if(parentId == id) {
                    		if(!confirm("父级专辑与本专辑相同，会自动清空，是否继续提交")) {
                    			flag = false;
                    		}
                    	}
                    	url = '/videoAlbumCtrl/edit.html';
                    } else {
                        url = '/videoAlbumCtrl/add.html';
                    }
                    if(flag) {
                    	webside.common.commit('videoalbum-form', url, '/videoAlbumCtrl/listUI.html');
                    }
                }
            });
		}
	}
};

$(function() 
{
    videoAlbum.form.validateForm();
    $('select').select2();
});
