package com.webside.sms;

import com.alibaba.fastjson.JSONObject;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;
import com.webside.config.service.ConfigService;
import com.webside.sms.model.SmsLog;
import com.webside.sms.service.SmsLogService;
import com.webside.util.IdGen;
import com.webside.util.SpringBeanUtil;

public class SMS {
	
	//官网的URL
	private static String url="http://gw.api.taobao.com/router/rest";  
	private static SmsLogService smsLogService = null;
	private static ConfigService configService = null;
	public static final String SMS_REGISTER_KEY = "sms_register";
	public static final String MOBILE_AUTHENTICATION_KEY = "mobile_authentication";
	public static final String MODIFY_PASSWORD_KEY = "modify_password";
	static {
		smsLogService = (SmsLogService)SpringBeanUtil.getBean("smsLogService");
		configService = (ConfigService) SpringBeanUtil.getBean("configService");
	}

	public static int sendKaptcha(String kaptcha, String recNum, String configKey) {
		String smsConfigValue = configService.findConfigValueByKey(configKey);
		JSONObject smsConfigJo = JSONObject.parseObject(smsConfigValue);
		String appKey = smsConfigJo.getString("appKey");
		String secret = smsConfigJo.getString("secret");
		String smsTemplateCode = smsConfigJo.getString("smsTemplateCode");
		String smsSign = smsConfigJo.getString("smsSign");
		String product = smsConfigJo.getString("product");
		TaobaoClient client = new DefaultTaobaoClient(url, appKey, secret); 
		JSONObject param = new JSONObject();
		param.put("code", kaptcha);
		param.put("product", product);
		 AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();  
		 req.setExtend("");  // 公共回传参数，比如用户Id，传回来之后可以知道短信发给了谁
		 req.setSmsType("normal");  // 短信类型，必须，目前就只是“normal”
		 req.setSmsFreeSignName(smsSign);  // 短信签名，必须
		 req.setSmsParamString(param.toJSONString());  // 短信模版中的参数，json格式传送，可选，模版中如果没有变量则不需要
		 req.setRecNum(recNum);  // 要发送的手机号，多个手机号要逗号分隔，一次最多200，必须
		 req.setSmsTemplateCode(smsTemplateCode);  // 短信模版编号 必须
		 AlibabaAliqinFcSmsNumSendResponse rsp = null;
		 try {  
			rsp = client.execute(req);  
			//System.out.println(rsp.getBody());
			return 1;  
		} catch (Exception e) {  
			// TODO: handle exception  
			return -1;  
		} finally {
			SmsLog smsLog = new SmsLog();
			smsLog.setId(IdGen.uuid());
			smsLog.setMobile(recNum);
			smsLog.setContent(kaptcha);
			if (rsp != null) {
				smsLog.setResult(rsp.getBody());
			}
			smsLog.setSendTime(System.currentTimeMillis());
			smsLogService.insert(smsLog);
		}
	}
	public static void main(String[] args) {
//		短信模板的内容  
//		 SMS.sendKaptcha("test123", "18062691807");
		JSONObject jo = new JSONObject();
		jo.put("appKey", "23436816");
		jo.put("secret", "9f00fe3dd94869d8a46bdef2c0286974");
		jo.put("smsTemplateCode", "SMS_13023438");
		jo.put("smsSign", "WoDotA网");
		//System.out.println(jo.toString().length());
	}
//	{"smsTemplateCode":"SMS_13023438","appKey":"23436816","secret":"9f00fe3dd94869d8a46bdef2c0286974","smsSign":"WoDotA网"}
}
