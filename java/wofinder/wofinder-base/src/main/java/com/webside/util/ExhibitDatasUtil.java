package com.webside.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExhibitDatasUtil {
	
	/**
	 * 追加操作类到集合中的每个对象中
	 * @param list
	 * @param operation
	 * @return
	 */
	public static Object addpenOperation(List<?> list,String ...operation){
		if(operation==null || operation.length<1){
			return list;
		}
		List<Object> dataList = new ArrayList<Object>();
		for (Object object : list) {
			Map<String, Object> map = objectToMap(object);
			for (int i = 0; i < operation.length; i++) {
				if(StringUtils.isNoneBlank(operation[i])){
					map.put(operation[i], true);
				}
			}
			dataList.add(map);
		}
		return dataList;
	}
	
	/**
	 * 对象转map 返回由对象的属性为key,值为map的value的Map集合 
	 * @param obj
	 * @return
	 * @author zengxn
	 */
	private static Map<String,Object> objectToMap(Object obj) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			Class<?> cls = obj.getClass();  
			/* 
             * 得到类中的属性 
             */  
            Field[] fields = cls.getDeclaredFields();  
            for (Field field : fields) {  
                String name = field.getName();  
                String strGet = "get" + name.substring(0, 1).toUpperCase() + name.substring(1, name.length());  
                map.put(strGet,name );  
            }
            /* 
             * 得到类中的方法 
             */  
            Method[] methods = cls.getMethods();  
            for(int i = 0; i < methods.length; i++){  
                Method method = methods[i];  
                if(map.containsKey(method.getName())){  
                	resultMap.put(StringUtils.toString(map.get(method.getName())), method.invoke(obj));
                }  
            }  
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultMap;
	}
}