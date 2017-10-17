$(function() {
    	$(".personal-center-main .personal-center-main-left .list,.personal-center-main-right-main").css("height", window.innerHeight - 218 + "px").mCustomScrollbar();
        window.onresize = function () {
            $(".personal-center-main .personal-center-main-left .list,.personal-center-main-right-main").css("height", window.innerHeight - 218 + "px").mCustomScrollbar("update");
        }
        /*drawNext();*/
        //个人中心切换tab
        $(".grzx-content").on("click","li",function(){
            $(".grzx-content li").removeClass("checked");
            $(".grzx-x").addClass("none");
            $("." + $(this).attr("class") + "-content").removeClass("none").css("opacity","0").animate({
                "opacity":"1"
            },400);
            $(this).addClass("checked");
        })
        //切换左边导航和右边头部及页面
        $(".personal-center-main .personal-center-main-left").on("click", ".list li", function () {
            if($(this).hasClass("tcdl") != true){// 非退出登录
                $(this).addClass("checked").siblings().removeClass("checked");
                $(".personal-center-main .personal-center-main-right .title").html($(this).find(".left-label").html());
                //var url = $(this).attr("data-href"); // 保存点击的地址
                //$('#container').remove();
                //$('.personal-center-main-right-main-inside').load(url + ' #container').fadeIn('slow'); // 加载新内容,url地址与该地址下的选择器之间要有空格,表示该url下的#container
            }
            /**else{
              	  点击退出登录
            }*/
        })
        
        //小尺寸展开左侧菜单
        $(".personal-center-main").on("click",".personal-center-main-right .title-outside .icon-wf_liebiao",function(){
            $(".personal-center-main .personal-center-main-left").css("display","block");
            $(document).click(function(e){
                if(!$(e.target).closest('.personal-center-main .personal-center-main-left,.personal-center-main .personal-center-main-right .title-outside .icon-wf_liebiao').length){
                    $(".personal-center-main .personal-center-main-left").css("display","");
                }
            });
        })
        
    });
$(function() {
	var url = window.location.href;
	if(url.indexOf("#") != -1) {
		url = url.split("#")[1];
		var array = url.split("&");
		var type = array[0];
		if("help" == type) {
			var id = "";
			if(array.length > 1) {
				id = array[1];
			}
			showHelpInfo(id);
		} else if ("account" == type) {
			$("#personalBtn").click();
		}
	} else {
		welcome();
	}
	$("#left_welcomeBtn").click(function() {
		welcome();
	});
	
	$("#left_browseSetBtn").click(function() {
		showBrowseSet();
	});
});

function welcome() {
	$.ajax( {
		url : _path + "/my/welcome",
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