package com.motozone.general.model.dao;

import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.motozone.general.model.CategoryTranslateBean;
import com.motozone.general.model.dao.CategoryTranslateDAO;

public class CategoryTranslateDAOHibernateTest {
	ApplicationContext context;
	SessionFactory factory;
	CategoryTranslateDAO ctDao;
	String source = "M01";
	
	
	@Before
	public void init() {
		context = new AnnotationConfigApplicationContext(com.motozone.config.SpringJavaConfig.class);
		
		ctDao = (CategoryTranslateDAO) context.getBean("categoryTranslateDAOHibernate");
		
		
		// get session factory
		factory = (SessionFactory) context.getBean("sessionFactory");
	}
	
	@Test
	public void getCategoryCode() {
		factory.getCurrentSession().beginTransaction();
		
		CategoryTranslateBean bean = ctDao.getCategoryCode("輕型機車綜合(250cc以下)",source);
		
		
		factory.getCurrentSession().getTransaction().commit();
		
		System.out.println(bean.getCategory().getCategory() + " : " + bean.getCategoryName());
		
	}
	
	
	@After
	public void destroy() {
		factory.close();
		((ConfigurableApplicationContext) context).close();
	}
}
