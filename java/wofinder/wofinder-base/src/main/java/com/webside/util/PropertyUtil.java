package com.webside.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.springframework.util.ResourceUtils;

/**
 * @title :属性文件解析、参数获取工具类
 * @author: LiGan
 */
public class PropertyUtil 
{
	private static final Logger logger = Logger.getLogger(PropertyUtil.class);
	
	public static final String DEFAULT_PROPERTY_FILE = "/webside.properties";
	
	public static final String DEFAULT_CLASS_FILE = "classpath:webside.properties";
	
	/**
	 * @description : 获取指定属性文件中对应的属性值
	 * @param fileName : 不带扩展名的文件名称：如wsConfFile
	 * @param proName  : 需要查询属性文件中的某个键值
	 * @return
	 */
    public static String getPro(String proName)
    {
    	InputStream in = null;
    	
    	try
    	{
    		if(proName==null || "".equals(proName.trim()))
    		{
    			return null;
    		}
    		else
    		{
    			Properties pps = new Properties();
    			in = new FileInputStream(ResourceUtils.getFile(DEFAULT_CLASS_FILE));
    			pps.load(in);
    			return pps.getProperty(proName.trim()).trim();
    		}
    	}
    	catch(Exception e)
    	{
    		logger.error("文件解析失败...", e);
    	}
    	finally
    	{
    		if(null != in)
    		{
    			try {in.close();} catch (IOException e) {}
    		}
    	}

		return null;
    }
	
	/**
	 * @description : 获取指定属性文件中对应的属性值
	 * @param fileName : 不带扩展名的文件名称：如wsConfFile
	 * @param proName  : 需要查询属性文件中的某个键值
	 * @return
	 */
    public static String getProInfo(String proName)
    {
    	InputStream in = null;
    	
    	try
    	{
    		if(proName==null || "".equals(proName.trim()))
    		{
    			return null;
    		}
    		else
    		{
    			Properties pps = new Properties();
  			    in = PropertyUtil.class.getClassLoader().getResourceAsStream(DEFAULT_PROPERTY_FILE);  
    			pps.load(in);
    			return pps.getProperty(proName.trim()).trim();
    		}
    	}
    	catch(Exception e)
    	{
    		logger.error("文件解析失败...", e);
    	}
    	finally
    	{
    		if(null != in)
    		{
    			try {in.close();} catch (IOException e) {}
    		}
    	}

		return null;
    }
	
	/**
	 * @description : 获取指定属性文件中对应的属性值
	 * @param fileName : 不带扩展名的文件名称：如wsConfFile
	 * @param proName  : 需要查询属性文件中的某个键值
	 * @return
	 */
    public static String getProInfo(String fileName,String proName){
    	try{
    		if(fileName==null || proName==null || "".equals(fileName.trim()) ||"".equals(proName.trim())){
    			return null;
    		}else{
    			ResourceBundle resourceBundle = ResourceBundle.getBundle(fileName, Locale.getDefault());
    			return resourceBundle.getString(proName);
    		}
    	}catch(Exception e){
    		return null;
    	}
    }
	
    /**
     * 获取指定属性文件数据集,用于一次操作需要取多个属性
     * **/
    public static Hashtable<String,String> getProInfoMap(String fileName){
    	Hashtable<String,String> map = null;
		if(fileName==null || "".equals(fileName.trim())){
			return null;
		}else{
			try{
				ResourceBundle resourceBundle = ResourceBundle.getBundle(fileName, Locale.getDefault());
		    	Enumeration<String> enumeration = resourceBundle.getKeys();
		    	String key = null;
		    	map = new Hashtable<String,String>();
		    	while(enumeration.hasMoreElements()){
					key = enumeration.nextElement();
					String value = resourceBundle.getString(key);
					map.put(key, value == null ? "" : value);
				}
			}catch(Exception e){
	    		return null;
	    	}
		}
    	return map;
    }
}
