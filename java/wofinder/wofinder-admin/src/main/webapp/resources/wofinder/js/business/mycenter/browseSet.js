
function showHeaderBrowseSet() {
	if (userId) {
		$.ajax( {
			url : _path + "/my/showHeaderBrowseSet",
			type : "POST", 
			dataType : "html",
			success : function(result) {
				$("#headerBrowseSet").html(result);
				$.material.init();
				$("#headerBrowseSet input[type='checkbox']").click(function() {
					var value = 1;
					if ($(this).val() == 0) {
						value = 1;
					} else {
						value = 0;
					}
					var switchType = $(this).attr("switchType");
					var switchId = $(this).attr("switchId");
					editBrowseSet(switchId, switchType, value);
				});
			},error : function(errorMsg) {
				
			},
			complete:function(errorMsg) {
				
			}
		});
	}
}

function showBrowseSet() {
	$.ajax( {
		url : _path + "/my/showBrowseSet",
		type : "POST", 
		dataType : "html",
		success : function(result) {
			$("#centerMain").html(result);
			$.material.init();
			$(".browse-configuration-content input[type='checkbox']").click(function() {
				var value = 1;
				if ($(this).val() == 0) {
					value = 1;
				} else {
					value = 0;
				}
				var switchType = $(this).attr("switchType");
				var switchId = $(this).attr("switchId");
				editBrowseSet(switchId, switchType, value);
			});
		},error : function(errorMsg) {
            
        },
        complete:function(errorMsg) {
            
        }
	});
}


function editBrowseSet(switchId, switchType, value) {
	$.ajax( {
		url : _path + "/my/editSwitch",
		type : "POST",
		data : {
			id : switchId,
			switchType : switchType,
			value : value
		},
		dataType : "json",
		success : function(result) {
			if (result.success) {
				if (value == 1) {
					$("#switch_" + switchType).val(1);
					$("#headerswitch_" + switchType).val(1);
					$("#switch_" + switchType).attr("checked", true);
					$("#headerswitch_" + switchType).attr("checked",true);
				} else {
					$("#switch_" + switchType).val(0);
					$("#headerswitch_" + switchType).val(0);
					$("#switch_" + switchType).removeAttr("checked");
					$("#headerswitch_" + switchType).removeAttr("checked");
				}
			}
		},error : function(errorMsg) {
            
        },
        complete:function(errorMsg) {
            
        }
	});
}