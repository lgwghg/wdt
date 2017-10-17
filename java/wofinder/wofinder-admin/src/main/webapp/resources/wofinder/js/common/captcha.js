/**
 * 生成邮箱验证码
 */
var genEmailCaptcha = function(email) {
	// 发验证码的请求
	$.ajax({
        type : "POST",
        url :  _path + "/fp/genEmailCaptcha",
        data : {
            "email" : email
        },
        dataType : "json",
        async : false,
        success : function(result) {
        	if (result == -1) {
        		layer.msg("操作频繁，请稍候重试", {title:"",icon: 5,shift : 6,time : 1000});
            } else if (result == 0) {
            	layer.msg("请稍候重试", {title:"",icon: 5,shift : 6,time : 1000});
            } else {
            	layer.msg("邮箱验证码已发", {title:"",icon: 6,shift : 6,time : 1000});
            }
        },
        error : function(errorMsg) {
            
        },
        complete:function(errorMsg) {
            
        }
    });
};

/**
 * 生成手机验证码
 * mobile ：手机号
 * type ： 验证码类型
 */
var genMobileCaptcha = function(mobile, gtcaptchaObj) {
	$.ajax({
        type : "POST",
        url :  _path + "/withoutAuth/genCaptcha",
        data : {
            "mobile" : mobile
        },
        dataType : "json",
        success : function(result) {
     	   if (result.code == 1) {
     		  layer.msg("动态码已发到尾号为" + mobile.substring(mobile.length-4) + "的手机", {title:"",icon: 6,shift : 6,time : 1000});
     	   } else {
     		   if (result && result.message) {
     			   layer.msg(result.message, {title:"",icon: 5,shift : 6,time : 1000});
     		   } else {
     			  layer.msg("请稍候重试", {title:"",icon: 5,shift : 6,time : 1000});
     		   }
     		   gtcaptchaObj.reset(); // 调用该接口进行重置
     	   }
        },
        error : function(errorMsg) {
            
        },
        complete:function(errorMsg) {
            
        }
    });
};