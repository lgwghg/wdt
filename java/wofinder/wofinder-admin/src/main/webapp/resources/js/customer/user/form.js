var user = 
{
	form : 
	{
		validateForm : function() {
        	jQuery.validator.addMethod("mobile", function(value, element) {
                return this.optional(element) || /^1[3|4|5|7|8]\d{9}$/.test(value);
            }, "请输入11位手机号码");
            $('#userForm').validate({
                errorElement : 'div',
                errorClass : 'help-block',
                focusInvalid : false,
                ignore : "",
                rules : {
                	nickName : {
                		required : true,
                		remote : {//更新时不验证
                            param : {
                                url : sys.rootPath + '/user/withoutAuth/validateUserNick',
                                cache : false
                            },
                            depends : function() {
                                return typeof ($("#userId").val()) == "undefined" || $("#userId").val() == "";
                            }
                        }
                	},
                    email : {
                        required : false,
                        email : true,
                        remote : {//更新时不验证
                            param : {
                                url : sys.rootPath + '/user/withoutAuth/validateEmail',
                                cache : false
                            },
                            depends : function() {
                                return typeof ($("#userId").val()) == "undefined" || $("#userId").val() == "";
                            }
                        }
                    },
                    mobile : {
                    	required : true,
                        mobile : 'required',
                    	remote : {//更新时不验证
                            param : {
                                url : sys.rootPath + '/user/withoutAuth/validateUserMobile',
                                cache : false
                            },
                            depends : function() {
                                return typeof ($("#userId").val()) == "undefined" || $("#userId").val() == "";
                            }
                        }
                    },
                    password : {
                        required : true,
                        minlength : 6
                    },
                    repassword : {
                        required : true,
                        minlength : 6,
                        equalTo : "#password"
                    }
                },
                messages : {
                	nickName : {
                		required : "请填写用户昵称",
                		remote : "该昵称已注册,请使用其他昵称"
                	},
                    email : {
                        //required : "请填写邮箱",
                        email : "请填写正确的邮箱",
                        remote : "该邮箱已注册,请使用其他邮箱"
                    },
                    mobile : {
                    	required : "请填写手机号",
                    	mobile : "请填写11位的手机号",
                        remote : "该手机号已注册,请使用其他手机号"
                    },
                    password : {
                        required : "请填写密码",
                        minlength : "密码长度不能少于6个字符"
                    },
                    repassword : {
                        required : "请再次填写密码",
                        minlength : "密码长度不能少于6个字符",
                        equalTo : "两次填写密码不一致，请重新填写"
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
                    if (element.is('input[type=checkbox]') || element.is('input[type=radio]')) {
                        var controls = element.closest('div[class*="col-"]');
                        if (controls.find(':checkbox,:radio').length > 1)
                            controls.append(error);
                        else
                            error.insertAfter(element.nextAll('.lbl:eq(0)').eq(0));
                    } else if (element.is('.select2')) {
                        error.insertAfter(element.siblings('[class*="select2-container"]:eq(0)'));
                    } else if (element.is('.chosen-select')) {
                        error.insertAfter(element.siblings('[class*="chosen-container"]:eq(0)'));
                    } else
                        error.insertAfter(element.parent());
                },
                submitHandler : function(form) {
                    var userId = $("#userId").val();
                    var url = "";
                    if (userId != undefined && userId !="") {
                        url = '/userCtrl/edit.html';
                    } else {
                        url = '/userCtrl/add.html';
                    }
                    webside.common.commit('userForm', url, '/userCtrl/listUI.html');
                }
            });
        }
    }
}

$(function() 
{
	user.form.validateForm();
    $('select').select2();
});