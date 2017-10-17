<script type="text/javascript">var $cookie='SSESSIONID=${cookie.SSESSIONID.value}';</script>
<link href="${ctx}/resources/js/jquery-upload/uploadify.css" rel="stylesheet" type="text/css" />
<script src="${ctx}/resources/js/jquery-upload/jquery.uploadify.js" type="text/javascript"></script>
<script type="text/javascript">
function uploadToId(id, text) {
	$('#'+id+'_upload').Huploadify({
		auto : true,
		fileTypeVerify : '*.*', // 弹出的格式
		fileTypeExts : '*.jpg; *.png',// 允许的格式
		multi : false,
		formData : {
			jssessionid : '${cookie.SSESSIONID.value}',
			type : '0'
		},
		fileSizeLimit : 99999999999,
		showUploadedPercent : false,
		showUploadedSize : false,
		removeTimeout : 9999999,
		itemTemplate:"",// 是否显示删除按钮
		uploader : sys.rootPath + '/upload/fileUpload',
		buttonCursor : 'hand',
		buttonText : text,
		height : '25',
		onUploadSuccess : function(file, data, response) {
			$(".uploadify-progress").css("display", "none");
			// 设置图片预览
			if (data) {
				if (data.indexOf("error:") > -1) {
					alert(data.substring(data.indexOf("error:")));
					return;
				}
				$("#"+id).val(data.replace(new RegExp('"', "gm"), ''));
				$("#"+id+"_view").attr("src", data.replace(new RegExp('"', "gm"), ''));
			}
		}
	});
}
</script>