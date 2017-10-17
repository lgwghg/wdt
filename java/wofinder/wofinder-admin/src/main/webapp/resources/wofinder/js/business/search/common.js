var su_wd = $("#wd").val();
var cookieName = "search_histroy";
//回车搜索
function searchByKey(e){
	$("#wd").attr("onkeyup","suByKey()");
	var ev= window.event||e;
	//回车
	if (ev.keyCode == 13) {
		search();
	}
	//37← 38↑ 39→ 40↓
	if (ev.keyCode >= 37 && ev.keyCode <= 40) {
		$("#wd").removeAttr("onkeyup");
		selectedSu(ev.keyCode);
	}
}

//搜索
function search(current) {
	var wd = $("#wd").val();
	//wd = escapeQueryChars(wd);
	if(trim(wd)==''){
		return false;
	}
	//var clearCache = $("#clearCache").is(':checked');
	if(current == undefined || current <= 0){
		current = 1;
	}
	//window.location.href = _path+'/s/'+encodeURIComponent(wd)+'?current='+current+(clearCache?'&cc='+clearCache:'');
	if(current > 1){
		window.location.href = encodeURI(_path+'/s/'+wd+'?current='+current);
	}else{
		window.location.href = _path+'/s/'+wd;
	}
}

function escapeQueryChars(s){
	var sb = "";
	for (var i = 0; i < s.length; i++) {
		var c = s.charAt(i);
		if (c != '\\') {
			sb+=c;
		}
	}
	return sb;
}

//显示历史搜索
function showHistory(){
	if(trim($("#wd").val())==""){
		var cookie = getCookie(cookieName);
		if(cookie){
			var content = '';
			var array = cookie.split("##--##");
			for(var i = array.length-1;i>=0;i--){
				if(trim(array[i])!=""){
					content += '<p>';
					content += 	'<span class="search-content">'+decodeURIComponent(array[i])+'</span>';
					content += '</p>';
				}
			}
			$("#search-record").html(content);
			$("#search-record p").hover(function(){
				$("#wd").val($(this).find("span").html());
				$("#search-record").parent().removeClass("is-empty");
			})
			$("#search-record").on("mousedown","p",function(){
				search();
			})
		}
	}
}
//自动补全
function suByKey(){
	var wd = $("#wd").val();
	wd = trim(wd);
	if(wd!=''){
		var url = _path + "/su";
		var dataJson = {wd:wd};
		ajaxMethod(url,dataJson,"post",false,function(data){
			var content = '';
			if(data.success) {
				su_wd = wd;
				var list = data.titleList;
				if(list != null && list.length>0){
					for(var i = 0;i<list.length;i++){
						content += '<p>';
						content += 	'<span class="search-content">'+list[i]+'</span>';
						content += '</p>';
					}
				}
			}else{
				
			}
			$("#search-record").html(content);
			$("#search-record p").hover(function(){
				$("#wd").val($(this).find("span").html());
			})
			
			$("#search-record").on("mousedown","p span",function(){
				search();
			})
		});
	}else{
		showHistory();
	}
}

//选中自动补全
function selectedSu(keyCode){
	var index = $("#search-record p.hover").index();
	$("#search-record p").removeClass("hover");
	var size = $("#search-record p").length;
	//37← 38↑ 39→ 40↓
	if(keyCode == 37 || keyCode == 38){
		if(index <= -1){
			index = size-1;
		}else{
			index -= 1;
		}
	}else{
		index += 1;
	}
	if(index <= -1 || index >= size){
		$("#wd").val(su_wd);
	}else{
		$("#search-record p:eq("+index+")").addClass("hover");
		$("#wd").val($("#search-record p:eq("+index+") span").html());
	}
}

//分享 type 1、QQ 2、微博
function share(type, url, title, summary, pic){
	var videoId = $("#videoId").val();
	if("1" == type) {
		var _url = 'http://connect.qq.com/widget/shareqq/index.html?url='+url+'&title='+title+'&summary='+summary+'&pics='+pic;
		window.open(_url);
	}else if("2" == type){
		window.open('http://service.weibo.com/share/share.php?appkey=&title='+title+'&url='+url+'&summary='+summary+'&pic='+pic);
	}
}

function showEWM(id, url) {
	$("#"+id).html("").qrcode({
	    render : "canvas",    //设置渲染方式，有table和canvas，使用canvas方式渲染性能相对来说比较好
	    text : url,    //扫描二维码后显示的内容,可以直接填一个网址，扫描二维码后自动跳向该链接
		width : "85",               //二维码的宽度
	    height : "85",              //二维码的高度
	    background : "#ffffff",       //二维码的后景色
		foreground : "#000000"       //二维码的前景色
	});
}

