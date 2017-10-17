$(function(){
    var a = $(".iconfont-x");
    var b = $(".dropdown").find(".dropdown-button");
    var c = $(".images-x");

    //iconfon-x
    a.append("<span></span>");
    a.on("click",function(){
        var iconFontSpan = $(this).find("span");
        iconFontSpan.animate({
            "opacity":"0.1"
        },200,function(){
            iconFontSpan.animate({
                "opacity":"0"
            },200);
        });
    })

    //dropdown
    b.siblings(".dropdown-x").addClass("none");
    b.on("click",function(){
        if($(this).hasClass("header") == true && $(this).find("i").hasClass("hide") != true){
            location.href = "/login";
        }
        else{
            if($(this).siblings(".dropdown-x").hasClass("none") == true){
                var dtb = $(this).siblings(".dropdown-triangle-bd");
                $(".dropdown .dropdown-x").addClass("none");
                $(".dropdown .dropdown-triangle,.dropdown .dropdown-triangle-bd").css("display","none");
                $(this).siblings(".dropdown-triangle").css({
                    "opacity":"0",
                    "display":"block"
                }).animate({
                    "opacity":"1"
                },138,function(){
                    dtb.css("display","block");
                });
                $(this).siblings(".dropdown-x").removeClass("none").css("opacity","0").animate({
                    "opacity":"1"
                },138);
            }else{
                $(".dropdown .dropdown-triangle,.dropdown .dropdown-triangle-bd").css("display","none");
                $(".dropdown .dropdown-x").addClass("none");
            }
        }
        $(document).click(function(e){
            if(!$(e.target).closest('.dropdown .dropdown-button,.dropdown .dropdown-x').length){
                $(".dropdown .dropdown-x").addClass("none");
                $(".dropdown .dropdown-triangle,.dropdown .dropdown-triangle-bd").css("display","none");
            }
        });
    })
    //image交互
    var timer2;
    $(".images-x-content").append("<div class='images-x-hover'></div>");
    /*$(document).on("mouseenter","image-x",function(){
        var mouseImg = $(this).find(".images-x-hover");
        timer2 = setTimeout(function(){
            mouseImg.animate({"opacity":"0"},99);
        },99)
        alert("a");
    }).on("mouseleave","image-x",function(){
        clearTimeout(timer2);
        $(this).find(".images-x-hover").animate({"opacity":"0.1"},99);
        alert("b");
    })*/

    var d;
    var e;
    //打星
    $(".grade-number-x .two div").mouseenter(function(){
        d = $(this).parent().siblings("ul").attr("class");
        e = $(this).parents(".grade-number-x").siblings(".grade-number").html();
        $(this).parent().siblings("ul").attr("class","" + $(this).attr("class") + "");
        /*$(this).parents(".grade-number-x").siblings(".grade-number").html($(this).index() + 1 +"分");*/
        if($(this).index() == 0 || $(this).index() == 1){
            $(this).parent().siblings(".three").css("left","-2px").html($(this).index() + 1);
        }
        else if($(this).index() == 2 || $(this).index() == 3){
            $(this).parent().siblings(".three").css("left","19px").html($(this).index() + 1);
        }
        else if($(this).index() == 4 || $(this).index() == 5){
            $(this).parent().siblings(".three").css("left","40px").html($(this).index() + 1);
        }
        else if($(this).index() == 6 || $(this).index() == 7){
            $(this).parent().siblings(".three").css("left","61px").html($(this).index() + 1);
        }
        else if($(this).index() == 8 || $(this).index() == 9){
            $(this).parent().siblings(".three").css("left","82px").html($(this).index() + 1);
        }
    }).mouseleave(function(){
        $(this).parent().siblings("ul").attr("class",""+ d +"");
        /*$(this).parents(".grade-number-x").siblings(".grade-number").html(e);*/
    }).on("click",function(){
        $(this).parent().siblings("ul").attr("class","" + $(this).attr("class") + "");
        d = $(this).attr("class");
        e = $(this).index() + 1 + "分";
    })

    //设置开关
    $(document).on("click",".on-off",function(){
        if($(this).hasClass("open-x") == true){
            $(this).addClass("close-x").removeClass("open-x");
        }else{
            $(this).removeClass("close-x").addClass("open-x");
        }
    })

    //切换视频来源
    $(".search-content-left .video .video-source .middle .video-source-content ul li").each(function () {
        if($(this).find("i").length <= 7 && document.documentElement.clientWidth>480){
            $(this).parents(".video-source-content").siblings(".icon-wf_zuo_qiehuan,.icon-wf_you_qiehuan").addClass("hide");
        }
        else if($(this).find("i").length <= 4 && document.documentElement.clientWidth<480){
            $(this).parents(".video-source-content").siblings(".icon-wf_zuo_qiehuan,.icon-wf_you_qiehuan").addClass("hide");
        }
    })
    $(".search-content-left .video .video-source .middle .icon-wf_zuo_qiehuan").on("click",function(){
        var _this = $(this);
        var fi = _this.siblings(".video-source-content").find("ul li i:first-child");
        var al = parseInt(fi.css("margin-left"));
        if(al != 0 && _this.hasClass("no-click") != true){
            _this.addClass("no-click");
            _this.siblings(".icon-wf_you_qiehuan").addClass("no-click");
            fi.animate({
                "margin-left" : al + 54 + "px"
            },288,function(){
                _this.removeClass("no-click");
                _this.siblings(".icon-wf_you_qiehuan").removeClass("no-click");
            });
        }
    })
    $(".search-content-left .video .video-source .middle .icon-wf_you_qiehuan").on("click",function(){
        var _this = $(this);
        var fi = _this.siblings(".video-source-content").find("ul li i:first-child");
        var al = parseInt(fi.css("margin-left"));
        var tl = $(this).siblings(".video-source-content").find("ul li i").length - 7;
        if(al != -(tl*54) && _this.hasClass("no-click") != true){
            _this.addClass("no-click");
            _this.siblings(".icon-wf_zuo_qiehuan").addClass("no-click");
            fi.animate({
                "margin-left" : al - 54 + "px"
            },288,function(){
                _this.removeClass("no-click");
                _this.siblings(".icon-wf_zuo_qiehuan").removeClass("no-click");
            });
        }
    })
    //搜索结果
    $(".search-record").on("mouseenter","p",function() {
        $(this).addClass("hover").siblings().removeClass("hover");
    }).on("mouseleave","p",function(){
        $(this).removeClass("hover");
    })
    //关闭底部提示
    $(document).on("click",".bottom-hint2",function(){
        $(".bottom-hint").animate({
            "bottom":"-40px"
        },300,function(){
            $(".bottom-hint").remove();
        })
        clearTimeout(Timer1);
    })
})
//评论滚动轮播
function autoScroll(obj){
    var _this = $(obj);
    if(_this.hasClass("noScroll") == true){
        return false;
    }else{
        _this.addClass("noScroll");
        _this.animate({
            opacity:0
        },1000,function(){
            _this.css({
                marginTop : "-65px"
            }).animate({
                opacity:"1"
            },1000,function(){
            	_this.css({marginTop : "0px"}).removeClass("noScroll");
            	if(_this.find("li").length>1){
            		_this.find("li:first").remove();
            	}
            })
        })
    }
}
//页面切换canvas
function drawNext(){
    var canvas = document.getElementById('drawNext');
    var drawNext = $("#drawNext");
    var ctx = canvas.getContext("2d");
    var w = window.innerWidth;
    var h = window.innerHeight;
    drawNext.attr({
        "width":w*4,
        "height":h*4
    })
    drawNext.css({
        "width":"400%",
        "height":"400%"
    })
    ctx.beginPath();
    ctx.moveTo(2*w,2*h+40);
    ctx.lineTo(2*w+1400,4*h);
    ctx.lineTo(4*w,4*h);
    ctx.lineTo(4*w,2*h+140);
    ctx.fillStyle="#aaaaaa";
    ctx.fill();
    ctx.beginPath();
    ctx.moveTo(0,2*h);
    ctx.lineTo(0,4*h);
    ctx.lineTo(2*w+1400,4*h);
    ctx.lineTo(2*w-29,2*h);
    ctx.fillStyle="#fafafa";
    ctx.fill();
    drawNext.css({
        "left":-2*w-700,
        "top":-h-70
    })
    drawNext.animate({
        "top":-2*h
    },500,function(){
        drawNext.animate({
            "left":-w+700
        },500,function(){
            drawNext.remove();
            $("body").removeClass("o-h");
        })
    })
}
//页面加载canvas
function drawLoad(){
    var canvas = document.getElementById('drawLoad');
    var ctx = canvas.getContext("2d");
    var w = window.innerWidth;
    var h = window.innerHeight;
    var LeftTopX = w/8*3.5;//左上
    var LeftTopY = h/8*3.5;
    var LeftBottomX = w/8*4.5;//右上
    var LeftBottomY = h/8*3.5;
    var RightTopX = w/8*3.5;//左下
    var RightTopY = h/8*4.5;
    var RightBottomX = w/8*4.5;//右下
    var RightBottomY = h/8*4.5;
    var a = 0;
    var b = 0;
    var c = 0;
    var d = 0;
    $("#drawLoad").attr({
        "width": w,
        "height": h
    })
    $("#drawLoad").css({
        "width":"100%",
        "height":"100%",
        "top":"0",
        "left":"0",
        "background-color": "#646464"
    })
    var timer1 = setInterval(function () {
        if (0 < LeftTopX) {
            if (LeftTopX >= 1 && LeftTopY >= 1) {
                LeftTopX = LeftTopX - w / 160;
                LeftTopY = LeftTopY - h / 160;
                a = a + 1;
            } else {
                LeftTopX = 0;
                LeftTopY = 0;
            }
        }
        if (a > 23) {
            if (0 < RightTopX) {
                if (RightTopX >= 1 && RightTopY < (h - 1)) {
                    RightTopX = RightTopX - w / 160;
                    RightTopY = RightTopY + h / 160;
                    b = b + 1;
                } else {
                    RightTopX = 0;
                    RightTopY = h;
                }
            }
        }
        if (b > 23) {
            if (0 < LeftBottomX) {
                if (LeftBottomX < (w - 1) && LeftBottomY >= 1) {
                    LeftBottomX = LeftBottomX + w / 160;
                    LeftBottomY = LeftBottomY - h / 160;
                    c = c + 1;
                } else {
                    LeftBottomX = w;
                    LeftBottomY = 0;
                }
            }
        }
        if (c > 23) {
            if (RightBottomX < w) {
                if (RightBottomX < (w - 1) && RightBottomY < (h - 1)) {
                    RightBottomX = RightBottomX + w / 160;
                    RightBottomY = RightBottomY + h / 160;
                    d = d + 1;
                }
            }
        }
        if (d == 70) {
            RightBottomX = w;
            RightBottomY = h;
            clearInterval(timer1);
            draw();
            canvas.remove();
        }
        draw();
    }, 1);
    function draw(){
        ctx.beginPath();
        ctx.moveTo(LeftTopX,LeftTopY);//左上
        ctx.lineTo(RightTopX,RightTopY);//左下
        ctx.lineTo(RightBottomX,RightBottomY);//右下
        ctx.lineTo(LeftBottomX,LeftBottomY);//右上
        ctx.fillStyle="#fafafa";
        ctx.fill();
    }
}

var Timer1;
function bottomHint(content){
    clearTimeout(Timer1);
    if($("body").find(".bottom-hint").hasClass("no-click") == true){
        if($("body").find(".bottom-hint").length == "1"){
                $(".bottom-hint").html(
                    "<div class='bottom-hint-inside'>" +
                    "<span class='bottom-hint1'>" + content + "</span>" +
                    "<span class='bottom-hint2'>确定</span>" +
                    "</div>"
                );
        }
    }else{
        if($("body").find(".bottom-hint").length == "1"){
            $(".bottom-hint").addClass("no-click").animate({
                "bottom":"-40px"
            },300,function(){
                $(".bottom-hint").html(
                    "<div class='bottom-hint-inside'>" +
                    "<span class='bottom-hint1'>" + content + "</span>" +
                    "<span class='bottom-hint2'>确定</span>" +
                    "</div>"
                ).animate({
                        "bottom":"0px"
                    },300,function(){
                        $(".bottom-hint").removeClass("no-click");
                    })
            });
        }else{
            $("body").append(
                "<div class='bottom-hint'>" +
                "<div class='bottom-hint-inside'>" +
                "<span class='bottom-hint1'>" + content + "</span>" +
                "<span class='bottom-hint2'>确定</span>" +
                "</div>" +
                "</div>"
            );
            $(".bottom-hint").css("bottom","-40px").animate({
                "bottom":"0px"
            },300);
        }
    }
    Timer1 = setTimeout(function(){
        $(".bottom-hint").animate({
            "bottom":"-40px"
        },300,function(){
            $(".bottom-hint").remove();
        })
    },5000);
}
//顶部加载进度条
var loadSchedule = {};
loadSchedule.start = function (a){
    a.find(".one").animate({
        "width":"90%"
    },1299);
}
loadSchedule.end = function (a){
    a.find(".one").stop(true).animate({
        "width":"100%"
    },399,function(){
        a.find(".one").css("width","");
    });
}