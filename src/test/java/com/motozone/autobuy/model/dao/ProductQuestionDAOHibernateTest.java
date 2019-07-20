package com.motozone.autobuy.model.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.motozone.autobuy.model.ProductQuestionBean;

public class ProductQuestionDAOHibernateTest {
	ApplicationContext context;
	SessionFactory factory;
	ProductQuestionDAO productQuestionDAO;
	
	@Before
	public void init() {
		context = new AnnotationConfigApplicationContext(com.motozone.config.SpringJavaConfig.class);
		productQuestionDAO = (ProductQuestionDAO) context.getBean("productQuestionDAOHibernate");
		// get session factory
		factory = (SessionFactory) context.getBean("sessionFactory");
	}
	@Test
	public void insertTest() {
		factory.getCurrentSession().beginTransaction();
		ProductQuestionBean bean = new ProductQuestionBean();
		bean.setUserNo(0);
		bean.setUserName("測試5555555號");
		bean.setId(14);
		bean.setContent("12355555");
		
		
		System.out.println(productQuestionDAO.insert(bean));
		factory.getCurrentSession().getTransaction().commit();
	}
	@Test
	public void updateTest() {
		factory.getCurrentSession().beginTransaction();
		
		ProductQuestionBean bean = productQuestionDAO.selectByNo(35);
		
		ProductQuestionBean result = null;
		
		if(bean != null) {
			bean.setContent("updateTest");
			result = productQuestionDAO.update(bean);
		} else {
			System.out.println("No Result");
		}
		
		
		
		factory.getCurrentSession().getTransaction().commit();
		
		System.out.println(result);
	}
	@Test
	public void deleteTest() {
		factory.getCurrentSession().beginTransaction();
		

		ProductQuestionBean bean = productQuestionDAO.selectByNo(37);
		boolean result = true;
		
		if(bean != null) {
			result = productQuestionDAO.delete(bean);
		} else {
			System.out.println("No Result");
		}
		
		factory.getCurrentSession().getTransaction().commit();
		
		System.out.println(result);
	}
	@Test
	public void selectByNoTest() {
		factory.getCurrentSession().beginTransaction();
		
		System.out.println(productQuestionDAO.selectByNo(1));
		
		factory.getCurrentSession().getTransaction().commit();
	}
	@Test
	public void selectAllByUserTest() {
		factory.getCurrentSession().beginTransaction();
		
		List<ProductQuestionBean> list = productQuestionDAO.selectAllByUser(0);
		
		factory.getCurrentSession().getTransaction().commit();
		
		if(list.size() != 0) {
			for(ProductQuestionBean bean : list) {
				System.out.println(bean);
			}
		} else {
			System.out.println("No Result");
		}
		
	}
	@Test
	public void selectAllByIdTest() {
		factory.getCurrentSession().beginTransaction();
		
		List<ProductQuestionBean> list = productQuestionDAO.selectAllById(14);
		
		factory.getCurrentSession().getTransaction().commit();
		
		if(list.size() != 0) {
			for(ProductQuestionBean bean : list) {
				System.out.println(bean);
			}
		} else {
			System.out.println("No Result");
		}
	}
	
	
	@Test
	public void selectAllByUserAndIdTest() {
		factory.getCurrentSession().beginTransaction();
		
		List<ProductQuestionBean> list = productQuestionDAO.selectAllByUserAndId(0, 14);
		
		factory.getCurrentSession().getTransaction().commit();
		
		if(list.size() != 0) {
			for(ProductQuestionBean bean : list) {
				System.out.println(bean);
			}
		} else {
			System.out.println("No Result");
		}
	}
	
	@After
	public void destroy() {
		factory.close();
		((ConfigurableApplicationContext) context).close();
	}
}
