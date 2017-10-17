$(function() {
	$("#left_integralTaskBtn").click(function() {
		showIntegralTask();
	});
});

function showIntegralTask() {
	$.ajax( {
		url : _path + "/my/showIntegralTask",
		type : "POST", 
		dataType : "html",
		success : function(result) {
			$("#centerMain").html(result);
		},error : function(errorMsg) {
            
        },
        complete:function(errorMsg) {
            
        }
	});
}

function completeTask(id, type) {
	$.ajax( {
		url : _path + "/my/completeTask",
		type : "POST", 
		data : {
			type : type
		},
		dataType : "json",
		success : function(result) {
			if (result && result > 0) {
				$("#" + id).html("已完成");
				$("#" + id).attr("disabled", true);
				$("#" + id).parent().removeClass("wc").addClass("ywc");
				$("#" + id).parent().find("span").html("+10分").show();
			}
		},error : function(errorMsg) {
            
        },
        complete:function(errorMsg) {
            
        }
	});
}