var upRelation = 
{
	form : 
	{
		validateForm : function() 
		{
			$('#uprelation-form').validate({
                errorElement : 'div',
                errorClass : 'help-block',
                focusInvalid : false,
                ignore : "",
                rules : {
					upId : {
						required : true,
	 					maxlength : 32
	                },
					relationUpId : {
						required : true,
	 					maxlength : 32
	                },
					relationDesc : {
						required : true,
	 					maxlength : 60
					}
                },
                messages : {
					upId : {
						required : "请填写人物id",
	 					maxlength : "人物id类型长度不能大于32个字符"
	                },
					relationUpId : {
						required : "请填写相关人物id",
	 					maxlength : "相关人物id类型长度不能大于32个字符"
	                },
					relationDesc : {
						required : "请填写关系描述",
	 					maxlength : "关系描述类型长度不能大于60个字符"
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
                        url = '/upRelation/edit.html';
                    } else {
                        url = '/upRelation/add.html';
                    }
                    //webside.common.commit('uprelation-form', url, '/upRelation/listUI.html');
                    webside.wodota.commit('uprelation-form', url, 'formModal' , 'customSearch_relation');
                }
            });
		}
	}
};

$(document).ready(function() { 
	
	$("#relationUpId").select2({
		 ajax: {
			 url: "/upCtrl/selectRelationUp",
			 type: 'POST',
			 dataType: 'json',
			 delay: 250,
			 data: function (params) {
				 return {
					 name: params.term,
				 };
			 },
			 processResults: function (data) {
				 var list = data.list;
				 var result = [];
				 if (list && list.length > 0) {
            		for(var i= 0, len=list.length;i<len;i++){  
            			var up = list[i];  
            			var option = {"id":up.id, "text": up.name};  
            			result.push(option);  
            		}  
	             }
				 return {
					 results: result
				 };
			 },
			 cache: true
		 }/*,
		 escapeMarkup: function (markup) { return markup; }, 
		 minimumInputLength: 1,
		 templateResult: formatRepo, 
		 templateSelection: formatRepoSelection */
	});
});