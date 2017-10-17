$(function() {
	// 个人信息
	$("#personalBtn").click(function() {
		loadPersonalInfo();
	});
	// 消息
	$("#messageBtn").click(function() {
		showMessage();
	});
	
	// 点评记录
	$("#videoGradeBtn").click(function() {
		showVideoGrade();
	});
	// 获取未读消息
	unreadMsgNum();
	
});

////////////////////////////////////以下为用户信息设置/////////////////////////////////////////////////
// 编辑用户昵称
function nickNameCancelBtn() {
	$("#nickName").val($("#oldNickName").val());
	$("#nickNameError").html("");
  	$("#nickNameError").parent(".form-group").removeClass("has-error");
  	$("#nickNameError").hide();
}

function nickNameBtn() {
	var oldNickName = $.trim($("#oldNickName").val());
	var nickName = $.trim($("#nickName").val());
	if (oldNickName == nickName) {
		bottomHint("请编辑昵称后，再点保存哦");
		return;
	} else {
		setNickName();
	}
}

function checkNickName() {
	// 验证昵称
	var nickName = $("#nickName").val().replace(/\ +/g,"");
	$("#nickName").val(nickName);
	if (nickName == "") {
		$("#nickNameError").html("昵称不能为空");
		$("#nickNameError").parent(".form-group").addClass("has-error");
		$("#nickNameError").show();
		//$("#nickName").focus();
		return false;
	} else if (!checkNickLength(nickName)) {
		$("#nickNameError").html("昵称2-6个字");
		$("#nickNameError").parent(".form-group").addClass("has-error");
		$("#nickNameError").show();
		//$("#nickName").focus();
		return false;
	} else if (!/^[a-zA-Z0-9_\u4e00-\u9fa5]+$/.test(nickName)) {
		$("#nickNameError").html("昵称只能字母、数字或汉字组成");
		$("#nickNameError").parent(".form-group").addClass("has-error");
		$("#nickNameError").show();
		//$("#nickName").focus();
		return false;
	} else {
		var flag = true;
      /*$.ajax({
          type : "POST",
          url : _path + "/user/withoutAuth/validateUserNick",
          data : {
              "nickName" : nickName
          },
          dataType : "json",
          success : function(resultdata) {
              if (!resultdata) {
              	$("#nickNameError").html("该昵称已注册");
              	$("#nickNameError").parent(".form-group").addClass("has-error");
              	$("#nickNameError").show();
          		//$("#nickName").focus();
                  flag = false;
              } 
          },
          error : function(errorMsg) {
              $("#nickNameError").html("服务器未响应,请稍后再试");
              $("#nickNameError").parent(".form-group").addClass("has-error");
              $("#nickNameError").show();
      		//$("#nickName").focus();
              flag = false;
          }
      });*/
      if(!flag)
      {
          return flag;   
      } else {
      	$("#nickNameError").html("");
      	$("#nickNameError").parent(".form-group").removeClass("has-error");
      	$("#nickNameError").hide();
      	return true; 
      }
	}
}
/**
* 设置昵称
*/
var nickNameFlag = true;
function setNickName() {
	var nickName = $.trim($("#nickName").val());
	if (nickNameFlag) {
		nickNameFlag = false;
		if (checkNickName()) {
			$.ajax({
				type : "POST",
				url : _path + "/my/edit",
				data : {
					"nickName" : nickName
				},
				dataType : "json",
				success : function(resultdata) {
					if (resultdata.result > 0) {
						$("#oldNickName").val($("#nickName").val());
						//layer.msg("昵称保存成功", {icon : 1});
						bottomHint("昵称保存成功");
						$("#headerUserNick").html($("#nickName").val());
						nickNameFlag = true;
					} else {
						bottomHint("昵称保存失败");
					}
				},
				error : function(errorMsg) {
					nickNameFlag = true;
				}
			});
		} else {
			nickNameFlag = true;
		}
		
	}
	
}

// 编辑用户生日
function birthdayCancelBtn() {
	initBirthday();
}

function birthdayBtn() {
	var oldBirthday = $("#oldBirthday").val();
	var birthday = $("#birthday").val();
	if (birthday != null && birthday != '') {
		if (oldBirthday == null || oldBirthday == '') {
			saveBirthday();
		} else {
			var oldDate = getFormatDateByLong(parseInt(oldBirthday), "yyyy-MM-dd");
			if (oldDate == birthday) {
				return;
			} else {
				saveBirthday();
			}
		}
		
	}
}

// 保存生日
var saveBirthdayFlag = true;
function saveBirthday() {
	var birthday = $("#birthday").val();
	var incrementId = $("#incrementId").val();
	var nickName = $("#nickName").val();
	if (saveBirthdayFlag) {
		saveBirthdayFlag = false;
		$.ajax({
			type : "POST",
			url : _path + "/my/editBirthdayAndSex",
			data : {
				"birthday" : getLongDateFromStr(birthday)
			},
			dataType : "json",
			success : function(resultdata) {
				if (resultdata.result > 0) {
					$("#oldBirthday").val(getLongDateFromStr(birthday));
					//layer.msg("生日保存成功", {icon : 1});
					bottomHint("生日保存成功");
					saveBirthdayFlag = true;
				} 
			},
			error : function(errorMsg) {
				saveBirthdayFlag = true;
			}
		});
	}
}
function initBirthday() {
	var oldBirthday = $("#oldBirthday").val();
	if (oldBirthday != null && oldBirthday != '') {
		$('#birthday').val(getFormatDateByLong(parseInt(oldBirthday), "yyyy-MM-dd"));
	} else {
		$('#birthday').val('');
	}
}

// 编辑头像
function photoBtn() {
	var currentPhotoId = '';
	var currentPhoto = '';
	var deleteIds = '';
	$(".recteg").each(function(i) {
		if (i > 0) {
			if ($(this).hasClass("active")) {
				currentPhotoId = $(this).attr("photoId");
				currentPhoto = $(this).find("img").attr("src");
			}
			if ($(this).hasClass("delete")) {
				var id = $(this).attr("photoId");
				if (deleteIds == '') {
					deleteIds += id;
				} else {
					deleteIds += "," + id;
				}
			}
		}
	});
	
	if (currentPhotoId != '' || deleteIds != '') {
		savePhotoOperate(currentPhotoId, deleteIds);
	} else {
		photoCancelBtn();
	}
}

var photoFlag = true;
function savePhotoOperate(currentPhotoId, deleteIds) {
	if (photoFlag) {
		photoFlag = false;
		$.ajax({
			type : "POST",
			url : _path + "/my/editPhoto",
			data : {
				photoId : currentPhotoId,
				deleteIds : deleteIds
			},
			dataType : "json",
			success : function(resultdata) {
				if (resultdata.result > 0 || resultdata.deresult > 0) {
					//$("#photoBtn").html("修改");
					//$(".recteg").off("click").off("mouseover").off("mouseout").find("click").off("click");
					//$(".recteg").removeClass("active");
					var usrePhotoList = resultdata.usrePhotoList;
					var photoHistory = "";
					var photoStr = "";
					for (var i = 0; i < usrePhotoList.length; i++) {
						var photo = usrePhotoList[i];
						var active = '';
						if (i == 0) {
							active = 'active';
							photoStr = photo.photo;
						} 
						photoHistory += '<div class="recteg ' +active+ '" photoId="' + photo.id + '">';
						photoHistory += '<img src="' + photo.photo_133 + '" alt="" width="100%" height="100%">';
						photoHistory += '<a title="关闭" href="javascript:;" class="iconfont icon-wf_cuowushi cuowu"></a>';
						photoHistory += '</div>';
					}
					$("#photoHistory").html(photoHistory);
					// 历史头像
					$(".recteg").on("click",function(){
				        $(this).addClass("active").siblings().removeClass("active");
				    }).on("mouseover",function(){
				        $(this).find(".cuowu").show();
				    }).on("mouseout",function(){
				        $(this).find(".cuowu").hide();
				    }).on("click",".cuowu",function(event){
				        event.stopPropagation();
				        $(this).parent(".recteg").addClass("delete").hide();
				    });
					// 第一个不能操作
					$(".recteg").first().off("mouseover").off("mouseout").find(".cuowu").off("click");
					
					// 处理头部头像
	    			var photoArr = photoStr.split(".");
	    			var photo_70 = photoArr[0] + "_70." + photoArr[1];
	    			var photo_24 = photoArr[0] + "_24." + photoArr[1];
	    			$("#headerUserPhoto_24").attr("src", photo_24);
	    			$("#headerUserPhoto").attr("src", photo_70);
					photoFlag = true;
				} 
			},
			error : function(errorMsg) {
				photoFlag = true;
			}
		});
	}
}
function photoCancelBtn() {
	$(".recteg").removeClass("active").removeClass("hide").removeClass("delete");
	$(".recteg").first().addClass("active");
	$(".recteg").each(function(i) {
		if (i > 2) {
			$(this).hide();
		} else {
			$(this).show();
		}
	});
}


//上传头像
function uploadImg(){
	var avatar_data = $("#avatar_data").val();
	var avatarInput = $("#avatarInput").val();
	if(trim(avatar_data)=="" || trim(avatarInput)==""){
		//layer.alert("请选择要上传的图片", {title:"",icon: 5,time : 3000});
		bottomHint("请选择要上传的图片");
	}else{
		$("#uploadImgForm").ajaxSubmit({
	        type : "POST",
	        url : _path + "/avatar/upload",
	        data : $("#uploadImgForm").serialize(),
	        success : function(result) {
	        	var data = eval("("+result+")");
	        	if(parseInt(data.code) == 1)//上传成功
	            {
	        		bottomHint("上传成功!");
	        		//layer.msg("上传成功!", {title:"",icon: 6,time : 2000 ,end:function(){/*window.location.reload();*/}});
	        		//$("#user_pic").attr("src",data.src);
	        		$(".recteg").removeClass("active");
	        		var photoHistory = "";
	        		photoHistory += '<div class="recteg active" photoId="' + data.id + '">';
	        		photoHistory += '<img src="' + data.src + '" alt="" width="100%" height="100%">';
	        		photoHistory += '<a title="关闭" href="javascript:;" class="iconfont icon-wf_cuowushi cuowu"></a>';
	        		photoHistory += '</div>';
	        		$("#photoHistory").prepend(photoHistory);
	        		$(".recteg").each(function(i) {
	        			if (i > 2) {
	        				$(this).hide();
	        			}
	        		});
	        		// 历史头像
	    			$(".recteg").on("click",function(){
	    		        $(this).addClass("active").siblings().removeClass("active");
	    		    }).on("mouseover",function(){
	    		        $(this).find(".cuowu").show();
	    		    }).on("mouseout",function(){
	    		        $(this).find(".cuowu").hide();
	    		    }).on("click",".cuowu",function(event){
	    		        event.stopPropagation();
	    		        $(this).parent(".recteg").addClass("delete").hide();
	    		    });
	    			// 第一个不能操作
	    			$(".recteg").first().off("mouseover").off("mouseout").find(".cuowu").off("click");
	    			
	    			// 处理头部头像
	    			var photo_133 = data.src;
	    			var photoArr = photo_133.split(".");
	    			var photo_70 = photoArr[0].split("_")[0] + "_70." + photoArr[1];
	    			var photo_24 = photoArr[0].split("_")[0] + "_24." + photoArr[1];
	    			$("#headerUserPhoto_24").attr("src", photo_24);
	    			$("#headerUserPhoto_70").attr("src", photo_70);
	    			// 关闭弹框
	        		$(".close").click();
	            }
	            else
	            {
	            	var info = "未知错误,请稍后再试!";
	            	
	            	if(data.msg != "")
	            	{
	            		info = data.msg;
	            	}
	            	//layer.alert(info, {title:"",icon: 5,time : 3000});
	            	bottomHint(info);
	            }
	        }
	    });
	}
}

// 编辑性别
function sexBtn() {
	var oldSex = $("#oldSex").val();
	var sex = $("input[name='sex']:checked").val();
	
	if (sex != null || sex != '') {
		if (oldSex != null && oldSex != '') {
			if (oldSex != sex) {
				saveSex();
			}
		} else {
			saveSex();
		}
	}
	
}

function sexCancelBtn() {
	var oldSex = $("#oldSex").val();
	if (oldSex == null || oldSex == '') {
		$("input:radio[name='sex']:checked").removeAttr("checked");
	} else {
		$("input:radio[value=" + oldSex + "]").attr('checked','true');
	}
}

var sexFlag = true;
function saveSex() {
	var sex = $("input:radio[name='sex']:checked").val();
	if (sexFlag) {
		sexFlag = false;
		$.ajax({
			type : "POST",
			url : _path + "/my/editBirthdayAndSex",
			data : {
				"sex" : sex
			},
			dataType : "json",
			success : function(resultdata) {
				if (resultdata.result > 0) {
					$("#oldSex").val(sex);
					//layer.msg("性别保存成功", {icon : 1});
					bottomHint("性别保存成功");
					sexFlag = true;
				} 
			},
			error : function(errorMsg) {
				sexFlag = true;
			}
		});
	}
}

// 编辑兴趣
function interestBtn() {
	var deleteIds = '';
	$(".counter-item").each(function(i) {
		if ($(this).hasClass("delete")) {
			var interestId = $(this).attr("interestId");
			if (deleteIds == '') {
				deleteIds += interestId;
			} else {
				deleteIds += "," + interestId;
			}
		}
	});
	saveInterest('', deleteIds);
}

function interestCancelBtn() {
	$("#interest").val('');
	$(".counter-item").removeClass("hide").removeClass("delete");
}
function checkInterest() {
	var interest = $("#interest").val();
	if (interest != null && interest != '') {
		var length = $(".neirong .counter-item").length;
		if (length >= 5) {
			$("#interestError").html("每个用户最多只可以设置5个兴趣哦");
			$("#interestError").parent(".form-group").addClass("has-error");
			$("#interestError").show();
			return false;
		}
		
		if (interest.length > 10) {
			$("#interestError").html("兴趣不能超过10个字符");
			$("#interestError").parent("form-group").addClass("has-error");
			$("#interestError").show();
			return false;
		}
	}
	
	return true;
}
var interestFlag = true;
function saveInterest(interest, deleteIds) {
	if (checkInterest()) {
		if (interestFlag) {
			interestFlag = false;
			$.ajax({
				type : "POST",
				url : _path + "/my/editInterest",
				data : {
					"interest" : interest,
					"deleteIds" : deleteIds
				},
				dataType : "json",
				success : function(resultdata) {
					if (resultdata.result > 0 || resultdata.deresult > 0) {
						if (resultdata.result) {
							if (resultdata.result > 0) {
								var interestContent = "";
								interestContent += '<span class="counter-item" interestId="' + resultdata.id + '">' + interest;
								interestContent += '<i class="iconfont icon-wf_cuowushi"></i>';
								interestContent += '</span>';
								$(".counter").prepend(interestContent);
								$(".neirong").prepend(interestContent);
								
							} else {
								bottomHint(resultdata.msg);
							}
						}
						if (resultdata.deresult && resultdata.deresult > 0) {
							var interestList = resultdata.interestList;
							var interestContent = "";
							if (interestList && interestList.length > 0) {
								for (var i=0; i < interestList.length; i++) {
									var interestObj = interestList[i];
									interestContent += '<span class="counter-item" interestId="' + interestObj.id + '">' + interestObj.interest;
									interestContent += '<i class="iconfont icon-wf_cuowushi"></i>';
									interestContent += '</span>';
								}
								$(".counter").html(interestContent);
								$(".neirong").html(interestContent);
							} else {
								$(".counter").html(interestContent);
								$(".neirong").html(interestContent);
							}
							$("#title-modal .close").click();
						}
						$(".counter-item").on("click",".icon-wf_cuowushi",function(){
				            $(this).parent(".counter-item").addClass("hide").addClass('delete');
				        });
						interestFlag = true;
					} 
				},
				error : function(errorMsg) {
					interestFlag = true;
				}
			});
		}
	}
}

////////////////////////////////////以上为用户信息设置/////////////////////////////////////////////////


//用户信息展示
function loadPersonalInfo() {
	$.ajax( {
		url : _path + "/my/personalInfo",
		type : "POST", 
		dataType : "html",
		success : function(result) {
			$("#centerMain").html(result);
			
			$.material.init();
			// 生日初始化
			var oldBirthday = $("#oldBirthday").val();
			if (oldBirthday != null && oldBirthday !=  '') {
				$('#birthday').bootstrapMaterialDatePicker(); 
				$('#birthday').bootstrapMaterialDatePicker('setDate', new Date(parseInt(oldBirthday)));
			} else {
				$('#birthday').bootstrapMaterialDatePicker(); 
			}
	    	
			/*if (oldBirthday != null && oldBirthday != '') {
				var birth = new Date(parseInt(oldBirthday));
				var year = birth.getFullYear();
				var month = birth.getMonth();
				var day = birth.getDate();
				YMDselect('year1','month1','day1', year, month, day);
			} else {
				new YMDselect('year1','month1','day1');
			}
			$(".select select").dropdown();*/
	        // 历史头像
			$(".recteg").on("click",function(){
		        $(this).addClass("active").siblings().removeClass("active");
		    }).on("mouseover",function(){
		        $(this).find(".cuowu").show();
		    }).on("mouseout",function(){
		        $(this).find(".cuowu").hide();
		    }).on("click",".cuowu",function(event){
		        event.stopPropagation();
		        $(this).parent(".recteg").addClass("delete").hide();
		    });
			// 第一个不能操作
			$(".recteg").first().off("mouseover").off("mouseout").find(".cuowu").off("click");
			
			// 头像点击
	        $(".avatarwrap").hover(function(){
	            $(this).find(".imgzhezhao").toggle();
	        }); 
	        /*$(".amend").click(function(){
	            if($(this).html().indexOf("修改") != -1){
	                $(this).html("保存");             
	            }else{  
	                $(this).html("修改");
	            }
	        });*/
	        
	        $("#mobileBtn").click(function() {
	        	$("#left_accountSetBtn").click();
	        });
	        
	        $('#interest').bind("keydown", function(event) {
	    		if(event.keyCode==13) {
	    			var interest = $("#interest").val();
	    			saveInterest(interest, '');
	    			$("#interest").val("")
	    			event.stopPropagation();
	    	    }
	    	});
	        $(".counter-item").on("click",".icon-wf_cuowushi",function(){
	            $(this).parent(".counter-item").addClass("hide").addClass('delete');
	        });
	        
		},error : function(errorMsg) {
            
        },
        complete:function(errorMsg) {
            
        }
	});
}
// 未消息数量
function unreadMsgNum() {
	ajaxMethod("/my/unreadMsgNum","","post",false,function(data){
		if (data && data > 0) {
			$("#left_unreadMsgNum").html(data);
			$("#left_unreadMsgNum").removeClass("hide");
		} else {
			$("#left_unreadMsgNum").addClass("hide");
		}
	});
}
// 消息列表
function showMessage() {
	$.ajax( {
		url : _path + "/my/msg",
		type : "POST", 
		dataType : "html",
		success : function(result) {
			$("#centerMain").html(result);
			loadMessage();
		},error : function(errorMsg) {
            
        },
        complete:function(errorMsg) {
            
        }
	});
}
function loadMessage() {
	var options = {};
	options.url = _path + "/my/msgData";
	options.pageSize = "10";

	$.pagination("msgPagination", options, function(data) {
		var msgLi = '';
		if(data.length > 0) {
			var message ;
			for(var i = 0; i<data.length; i++) {
				message = data[i];
				if (message.type==0) {
					msgLi += '<li type="' + message.type + '" businessId="'+message.businessId+'"><span class="dot"></span><i class="iconfont icon-wf_shezhi-copy"></i><span>' + message.content + '</span></li>';
				} else if (message.type==1) {
					msgLi += '<li type="' + message.type + '" businessId = "'+ message.id +'"><span class="dot"></span><i class="iconfont icon-wf_shezhi-copy"></i><span>' + message.content + '</span></li>';
				} else if (message.type==2) {
					msgLi += '<li type="' + message.type + '" businessId="'+ message.videoId +'"><span class="dot"></span><i class="iconfont icon-wf_pinglun1"></i><div class="imgwrap">' + ReferrerKiller.imageHtml(message.videoCover) + '</div><span>有' + message.count + '条回复</span></li>';
				}
				
			}
			
		} else {
			msgLi = '<p style="text-align:center">暂无数据</p>';
		}
		$("#messageUL").html(msgLi);
		
		$(".pagination li").click(function(){
			$(this).find("a").addClass("choose").parent().siblings().find("a").removeClass("choose");
		});

		$("#messageUL li").click(function() {
			var type = $(this).attr("type");
			var businessId = $(this).attr("businessId");
			var _this = $(this);
			$.ajax( {
				url : _path + "/my/readMsg",
				type : "POST", 
				data : {
					"type" : type,
					"businessId" : businessId
				},
				dataType : "json",
				success : function(result) {
					if (result && result > 0) {
						//_this.find("span").removeClass("dot");
						_this.find(".dot").css("opacity","0");
						_this.addClass("lifoucus");
						if (type == 0) {//系统公告去帮助与反馈
							//window.location.href =  "/my#help&" + businessId;
							$("#sysHelpBtn").click();
							showHelpInfo(businessId);
						} else if (type == 2) {//视频回复消息
							window.location.href = "/video/" + businessId + "?gto=comment";
						}
						// 获取未读消息
						unreadMsgNum();
					}
				},error : function(errorMsg) {
		            
		        },
		        complete:function(errorMsg) {
		            
		        }
			});
		});
	})
}

// 评分列表
function showVideoGrade() {
	$.ajax( {
		url : _path + "/my/grade",
		type : "POST", 
		dataType : "html",
		success : function(result) {
			$("#centerMain").html(result);
			loadVideoGrade();
		},error : function(errorMsg) {
            
        },
        complete:function(errorMsg) {
            
        }
	});
}
function loadVideoGrade() {
	var options = {};
	options.url = _path + "/my/gradeData";
	options.pageSize = "10";

	$.pagination("gradePagination", options, function(data) {
		var gradeLi = '';
		if(data.length > 0) {
			var grade ;
			for(var i = 0; i<data.length; i++) {
				grade = data[i];
				var video = grade.video;
				gradeLi += '<li><a href="'+_path+'/video/' + video.shortId + '"><i class="iconfont icon-wf_bofang1"></i><span class="text">' + video.title + '</span><span>' + grade.score + '分</span><span class="time">' + getDifToNow(grade.createTime) + '</span></a></li>';
			}
			
		} else {
			gradeLi = '<p style="text-align:center">暂无数据</p>';
		}
		$("#gradeUL").html(gradeLi);
	})
}