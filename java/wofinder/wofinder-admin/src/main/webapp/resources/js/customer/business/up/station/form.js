jQuery.validator.addMethod("checkStation", function(value, ele, param) {
	var stationValue = $("#stationValue").val();
	var thirdPartyValue = $("#thirdPartyValue").val();
	if(stationValue == '' && thirdPartyValue == ''){
		return false;
	}
	return true;
}, "所属站点与所属第三方需要其中一个必填！");

jQuery.validator.addMethod("checkSameStation", function(value, ele, param) {
	var stationValue = $("#stationValue").val();
	var thirdPartyValue = $("#thirdPartyValue").val();
	if(stationValue != '' && thirdPartyValue != ''){
		return false;
	}
	return true;
}, "所属站点与所属第三方只能选择其中一个！");

var upStation = 
{
	form : 
	{
		validateForm : function() 
		{
			$('#upstation-form').validate({
                errorElement : 'div',
                errorClass : 'help-block',
                focusInvalid : false,
                ignore : "",
                rules : {
                	name : {
						required : true,
	 					maxlength : 50
	                },
	                homeUrl : {
						required : true,
						url : true,
	 					maxlength : 200
	                },
	                introduction : {
						maxlength : 500
	                },
	                upAvatar : {
	                	url : true,
	 					maxlength : 200
	                },
	                upVideoCount : {
	                	digits : true,
						min : 0
	                },
	                upFansCount : {
	                	digits : true,
						min : 0
	                },
	                upFriendCount : {
	                	digits : true,
						min : 0
	                },
	                upPlayCount : {
	                	digits : true,
						min : 0
	                },
	                "station.value" : {
	                	checkStation : true,
	                	checkSameStation : true
	                },
	                "thirdParty.value" : {
	                	checkStation : true,
	                	checkSameStation : true
	                },
	                "status.value" : {
						required : true
	                },
                },
                messages : {
                	name : {
						required : "请填写名称",
	 					maxlength : "名称长度不能大于50个字符"
	                },
	                homeUrl : {
						required : "请填写个人主页",
						url : "请正确填写个人主页的url地址",
	 					maxlength : "个人主页长度不能大于200个字符"
	                },
	                introduction : {
						maxlength : "简介长度不能大于500个字符"
	                },
	                upAvatar : {
	                	url : "请正确填写头像的url地址",
	 					maxlength : "头像长度不能大于200个字符"
	                },
	                upVideoCount : {
	                	digits : "视频数量必须是不小于0的整数",
						min : "视频数量必须是不小于0的整数"
	                },
	                upFansCount : {
	                	digits : "粉丝数量必须是不小于0的整数",
						min : "粉丝数量必须是不小于0的整数"
	                },
	                upFriendCount : {
	                	digits : "关注数量必须是不小于0的整数",
						min : "关注数量必须是不小于0的整数"
	                },
	                upPlayCount : {
	                	digits : "播放数量必须是不小于0的整数",
						min : "播放数量必须是不小于0的整数"
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
                        url = '/upStationCtrl/edit.html';
                    } else {
                        url = '/upStationCtrl/add.html';
                    }
                    webside.wodota.commit('upstation-form', url, 'formModal' , 'customSearch_s');
                }
            });
		}
	}
};

$(function() 
{
    upStation.form.validateForm();
    $('select').select2();
});
