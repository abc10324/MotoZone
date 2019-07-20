package com.motozone.general.model.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JavaBeanUtils {
	
	public static Map<String,Object> bean2Map(Object bean){
		
		// empty check
		if(bean == null) {
			return null;
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();
 
                // 过滤class属性
                if (!key.equals("class")) {
                    // 得到property对应的getter方法
                    Method getter = property.getReadMethod();
                    Object value = getter.invoke(bean);
 
                    map.put(key, value);
                }
 
            }
        } catch (Exception e) {
            System.out.println("transBean2Map Error " + e);
        }
        
        return map;
	}
	
	public static List<Map<String, Object>> bean2Map(List<Object> list){
		
		// empty check
		if(list == null) {
			return null;
		}
		
		List<Map<String, Object>> result = new ArrayList<>();
		
		for(Object bean : list) {
			result.add(bean2Map(bean));
		}
		
		return result;
	}
}
