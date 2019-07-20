package com.motozone.general.model.dao;

import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.motozone.general.model.CategoryTranslateBean;
import com.motozone.general.model.ImageBean;
import com.motozone.general.model.dao.ImageDAO;

public class ImageDAOHibernateTest {
	ApplicationContext context;
	SessionFactory factory;
	ImageDAO iDao;
	CategoryDAO cDao;
	
	
	@Before
	public void init() {
		context = new AnnotationConfigApplicationContext(com.motozone.config.SpringJavaConfig.class);
		
		iDao = (ImageDAO) context.getBean("imageDAOHibernate");
		cDao = (CategoryDAO) context.getBean("categoryDAOHibernate");
		
		// get session factory
		factory = (SessionFactory) context.getBean("sessionFactory");
	}
	
	@Test
	public void getTotal() {
		factory.getCurrentSession().beginTransaction();
		
		Integer total = iDao.getTotal(cDao.getCategory("LHA"));
		
		factory.getCurrentSession().getTransaction().commit();
		
		System.out.println(total);
		
	}
	
	@Test
	public void delete() {
		factory.getCurrentSession().beginTransaction();
		
		boolean result = iDao.delete("image7");
		
		factory.getCurrentSession().getTransaction().commit();
		
		System.out.println(result);
	}
	
	@Test
	public void insert() {
		factory.getCurrentSession().beginTransaction();
		
		ImageBean bean = new ImageBean();
		bean.setImgSrc("image99.jpg");
		bean.setCategory(cDao.getCategory("LHA"));
		
		ImageBean result = iDao.insert(bean);
		
		factory.getCurrentSession().getTransaction().commit();
		
		System.out.println(result);
		
	}
	
	
	@After
	public void destroy() {
		factory.close();
		((ConfigurableApplicationContext) context).close();
	}
}
