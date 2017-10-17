package com.webside.ssl;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;

import javax.net.ssl.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

@Controller
@RequestMapping("/ssl/")
public class TrustSSL {
	public static final Logger logger = Logger.getLogger(TrustSSL.class);
	
	@RequestMapping(value = "recaptcha", method = RequestMethod.POST)
	@ResponseBody
	public Object recaptcha(String resp) throws Exception {
		InputStream in = null;
		OutputStream out = null;
		byte[] buffer = new byte[4096];
		String strReturn = "";
		JSONObject jsonReturn = new JSONObject();
		try {
			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, new TrustManager[] { new TrustAnyTrustManager() },
					new java.security.SecureRandom());
			//String res = "03AJz9lvR1dzNSopdavV-QHLnqQGwBZHCf9wG1aZTyf9YiunA0s3LCGpDNq-n3UalRUdaIJSrrIjb1s0qPXZNo0xbzGIt92jHP-dq0LhioUjKBXcQYoPcTahWrJmXlqM8C1Hbx9R9HHhCLWc6O11mego0TNmTmsm6j_9hwd8w83OWuFgD8QRsYUdluikIRF_fPc5WmsfGVkDSqYAct86ttxyeR5q2hflbfGcO5pmsXXoSHC1-4WF0UPEv-KxDYKUqhrpMJ299aH8co7Q51zWg4YL79DUYfmg8njDgsGm6jPdTtzBPCEJOCB4s";
			//String secret = "6LdZNyoUAAAAAGej_FzXd6wuGtZdgaPGRAg2Q9EK";// localhost
			String secret = "6LfWACoUAAAAAPDq5zeJ2ngGV3yRSkBPIbAI-Xvs";//wodota.com
			URL console = new URL(
					"https://recaptcha.net/recaptcha/api/siteverify?secret="
							+ secret + "&response=" + resp);
			HttpsURLConnection conn = (HttpsURLConnection) console
					.openConnection();
			conn.setSSLSocketFactory(sc.getSocketFactory());
			conn.setHostnameVerifier(new TrustAnyHostnameVerifier());
			conn.connect();
			InputStream is = conn.getInputStream();
			DataInputStream indata = new DataInputStream(is);
			String ret = "";
			while (ret != null) {
				ret = indata.readLine();
				if (ret != null && !ret.trim().equals("")) {
					strReturn = strReturn + new String(ret.getBytes("ISO-8859-1"), "UTF-8");
				}
			}
			jsonReturn = jsonReturn.parseObject(strReturn);
			conn.disconnect();
		} catch (ConnectException e) {
			logger.error("ConnectException", e);
			throw e;
		} catch (IOException e) {
			logger.error("IOException", e);
			throw e;
		} finally {
			try {
				in.close();
			} catch (Exception e) {
			}
			try {
				out.close();
			} catch (Exception e) {
			}
		}
		return jsonReturn;
	}
}
