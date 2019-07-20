package com.motozone.general.model.service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.motozone.general.model.CategoryBean;

public class GeneralServiceTest {
	ApplicationContext context;
	SessionFactory factory;
	GeneralService service;
	
	
	@Before
	public void init() {
		context = new AnnotationConfigApplicationContext(com.motozone.config.SpringJavaConfig.class);
		
		service = (GeneralService) context.getBean("generalService");
		
		// get session factory
		factory = (SessionFactory) context.getBean("sessionFactory");
	}
	
	@Test
	public void getImageSrc() {
		
		factory.getCurrentSession().beginTransaction();
		
		System.out.println(service.getImageSrc("LAN", "abcde.jpg"));
		
		factory.getCurrentSession().getTransaction().commit();
	}
	
	
	@Test
	public void removeImageSrc() {
		factory.getCurrentSession().beginTransaction();
		
		List<String> imgSrcList = new ArrayList<>();
		imgSrcList.add("efg/cde/abc/LAN00000006.jpg");
		
		String realFilePath = "D:/DataSource/workspace/MotoZone/src/main/webapp/WEB-INF/static/img";
		service.removeImageSrc(imgSrcList, realFilePath);
		
		factory.getCurrentSession().getTransaction().commit();
	}
	
	@Test
	public void getSubCategory() {
		factory.getCurrentSession().beginTransaction();
		
		List<CategoryBean> list = service.getSubcategory("B");
		
		for(CategoryBean bean : list) {
			System.out.println(bean.getCategory());
		}
		
		
		factory.getCurrentSession().getTransaction().commit();
	}
	
	@Test
	public void adminChatBot() {
		String userMsg = "1";
		
		factory.getCurrentSession().beginTransaction();
		String result = service.adminChatBot(userMsg);
		factory.getCurrentSession().getTransaction().commit();
		
		System.out.println(result);
	}
	
	@After
	public void destroy() {
		factory.close();
		
		((ConfigurableApplicationContext) context).close();
	}
}
