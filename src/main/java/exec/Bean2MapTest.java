package exec;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import com.motozone.article.model.ArticleBean;

public class Bean2MapTest {

	public static void main(String[] args) {
		
		ArticleBean bean = new ArticleBean();
		bean.setId(123);
		bean.setAuthor("Sam");
		bean.setContent("Hello");
		bean.setDate(new Timestamp(0));
		bean.setUserNo(0);
		bean.setNo(1);
		
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
        
        for(String key : map.keySet()) {
        	System.out.println(key + " : " + map.get(key));
        }
        


	}

}
