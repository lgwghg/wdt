/**
 * @title : 分页
 * pageSize 每页显示条数
 * startRecord 开始记录数 初始化 0
 * nowPage 当前页 初始化 1 
 * recordCount 记录总数 初始化 -1
 * pageCount 总页数 初始化 -1
 * */
function pages(pageSize,startRecord,nowPage,recordCount,pageCount){
	return '{"pageSize":"'+pageSize+'","startRecord":"'+startRecord+'","nowPage":"'+nowPage+'","recordCount":"'+recordCount+'","pageCount":"'+pageCount+'"}';
}

//去左右空格
function trim(s){
	if(s == "" || s == undefined) {
		return "";
	}
    return s.replace(/(^\s*)|(\s*$)/g, "");
}

//返回字符串长度，英文占1个字符，中文汉字占2个字符
function strLength(str){
	  var len = 0;  
	  for (var i=0; i<str.length; i++) {  
	    if (str.charCodeAt(i)>127 || str.charCodeAt(i)==94) {  
	       len += 2;  
	    }else {  
	       len ++;  
	    }  
	  }  
	  return len;  
}

//将form转为AJAX提交
function ajaxSubmit(frm, fn) 
{
	var dataPara = getFormJson(frm);
  
	$.ajax({
		url: frm.action,
		type: frm.method,
		dataType : "json",
		data: dataPara,
		success: fn
	});
}

//将form中的值转换为键值对
function getFormJson(frm) 
{
	var o = {};
	var a = $(frm).serializeArray();
  
	$.each(a, function ()
    {
		if (o[this.name] !== undefined) 
		{
			if (!o[this.name].push) 
          	{
				o[this.name] = [o[this.name]];
          	}
          
			o[this.name].push(this.value || '');
		} 
		else 
      	{
			o[this.name] = this.value || '';
      	}
    });

	return o;
}

//生成随机数
var randomChars = ['0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'];
function generateMixed(n) {
     var res = "";
     for(var i = 0; i < n ; i ++) {
         var id = Math.ceil(Math.random()*35);
         res += randomChars[id];
     }
     return res;
}

//是否为电子邮箱
function isEmail(mail) {  
    var filter = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;  
    if (filter.test(mail)) {  
        return true;   
    } else {  
        return false;  
    }   
} 

//是否为正整数  
function isPositiveNum(s){
    var re = /^[0-9]*[1-9][0-9]*$/ ;  
    return re.test(s)  
}

//是否为网址
function isUrl(url){
	var reg = /(http|ftp|https):\/\/[\w\-_]+(\.[\w\-_]+)+([\w\-\.,@?^=%&:/~\+#]*[\w\-\@?^=%&/~\+#])?/;
	if (reg.test(url)) {  
        return true;   
    } else {  
        return false;  
    } 
}

//判断对象是否为空
function judge(obj){
　　for(var i in obj){//如果不为空，则会执行到这一步，返回true
 　　　　return true;
 　　}
　　 return false;
}

/**
 * 时间秒数格式化
 * @param s 秒数
 * @returns {*} 格式化后的时分秒
 */
var secToTime = function(s) {
    var t;
    if(s > -1){
        var hour = Math.floor(s/3600);
        var min = Math.floor(s/60) % 60;
        var sec = s % 60;
        if(hour < 10) {
            t = '0'+ hour + ":";
        } else {
            t = hour + ":";
        }

        if(min < 10){t += "0";}
        t += min + ":";
        if(sec < 10){t += "0";}
        t += sec.toFixed(0);
    }
    return t;
}

// 根据数值计算以千、万结尾的显示字符串
function getCountStr(count) {
	var str = count;
	if (count / 100000000 > 1) {// 亿
		str = Number(count/100000000).toFixed(2) + "亿";
	} else if (count / 10000 > 1) {// 万
		str = Number(count/10000).toFixed(2) + "万";
	}
	return str;
}

//计算打分显示样式
function getScoreClass(score){
	var scoreClass = "";
	if (score > 9) {
		scoreClass = "item5-two";
	} else if (score > 8) {
		scoreClass = "item5-one";
	} else if (score > 7) {
		scoreClass = "item4-two";
	} else if (score > 6) {
		scoreClass = "item4-one";
	} else if (score > 5) {
		scoreClass = "item3-two";
	} else if (score > 4) {
		scoreClass = "item3-one";
	} else if (score > 3) {
		scoreClass = "item2-two";
	} else if (score > 2) {
		scoreClass = "item2-one";
	} else if (score > 1) {
		scoreClass = "item1-two";
	} else if (score > 0) {
		scoreClass = "item1-one";
	}
	return scoreClass;
}

//计算打分分数
function getScoreNum(scoreClass){
	var score = 0;
	if (scoreClass == "item5-two") {
		score = 10;
	} else if (scoreClass == "item5-one") {
		score = 9;
	} else if (scoreClass == "item4-two") {
		score = 8;
	} else if (scoreClass == "item4-one") {
		score = 7;
	} else if (scoreClass == "item3-two") {
		score = 6;
	} else if (scoreClass == "item3-one") {
		score = 5;
	} else if (scoreClass == "item2-two") {
		score = 4;
	} else if (scoreClass == "item2-one") {
		score = 3;
	} else if (scoreClass == "item1-two") {
		score = 2;
	} else if (scoreClass == "item1-one") {
		score = 1;
	}
	return score;
}

function getDifToNow(long) {
	var now = new Date();// 当前日期
	
	var dif = now.valueOf() - long;
	dif = dif/1000/60/60; // 小时
	
	if(dif >= 24) {
		var date = parseInt(dif/24);
		if(date > 30) {
			var month = parseInt(date/30);
			if(month > 12) {
				return parseInt(month/12) + "年前";
			} else {
				return month + "月前";
			}
		} else {
			return date + "天前";
		}
	} else if(dif >= 1) {
		return parseInt(dif) + "小时前";
	} else {
		var minutes = parseInt(dif*24);
		if(minutes <= 1) {
			return "刚刚";
		} else {
			return parseInt(dif*24) + "分钟前";
		}
	}
}

//扩展Date的format方法   
Date.prototype.format = function (format) {  
    var o = {  
        "M+": this.getMonth() + 1,  
        "d+": this.getDate(),  
        "h+": this.getHours(),  
        "m+": this.getMinutes(),  
        "s+": this.getSeconds(),  
        "q+": Math.floor((this.getMonth() + 3) / 3),  
        "S": this.getMilliseconds()  
    }  
    if (/(y+)/.test(format)) {  
        format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));  
    }  
    for (var k in o) {  
        if (new RegExp("(" + k + ")").test(format)) {  
            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));  
        }  
    }  
    return format;  
} 

/**
 * 时间戳转日期 yyyy-MM-dd
 * @param l 时间戳
 */
function getSmpFormatDate(l) {
	var pattern = "yyyy-MM-dd"; 
	var date = new Date(l);
    return date.format(pattern);  
}

//数字转中文
function getChineseByNum(num) {
	if(num == null || isNaN(num)) {
		return;
	}
	var array = ["", "一", "二", "三", "四", "五", "六", "七", "八", "九"];
	if(num < 10) {
		return array[num];
	} else if(num < 100) {
		return array[parseInt(num/10)] + "十" + array[num%10];
	}
}

//点击更换验证码，传this
function changeCaptcha(obj,id){
	if(obj){
		$(obj).attr("src",_path+"/captcha?r="+Math.random());
	}else{
		$("#"+id).attr("src",_path+"/captcha?r="+Math.random());
	}
	
}


//中文：2-6个；英文：4-12；中英结合：5-13
function checkNickLength(str) {  
  var realLength = 0;  
  var x = 0;//一个字节的字符数
  var y = 0;// 汉子的字符数
  for (var i = 0; i < str.length; i++) {  
      charCode = str.charCodeAt(i);  
      if (charCode >= 0 && charCode <= 128) {
      	realLength += 1;
      	x += 1;
      } else {
      	realLength += 3;
      	y += 1;
      } 
  }
  if (x > 0 && y == 0) {
  	if (x < 4 || x > 12) {
  		return false;
  	}
  } else if (x == 0 && y > 0) {
  	if (y < 2 || y > 6) {
  		return false;
  	}
  } else if (x > 0 && y > 0) {
  	if (x + y < 2 || x + y > 13) {
  		return false;
  	}
  }
  return true;  
}

//写cookies 

function setCookie(name,value) 
{ 
  var Days = 30; 
  var exp = new Date(); 
  exp.setTime(exp.getTime() + Days*24*60*60*1000); 
  document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString(); 
} 

//读取cookies 
function getCookie(name) 
{ 
  var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");

  if(arr=document.cookie.match(reg))

      return arr[2]; 
  else 
      return null; 
} 

//删除cookies 
function delCookie(name) 
{ 
  var exp = new Date(); 
  exp.setTime(exp.getTime() - 1); 
  var cval=getCookie(name); 
  if(cval!=null) 
      document.cookie= name + "="+cval+";expires="+exp.toGMTString(); 
} 