package com.motozone.autobuy.model.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.motozone.autobuy.model.ProductCartBean;
import com.motozone.autobuy.model.ProductCartViewBean;

public class ProductCartViewDAOHibernateTest {
	ApplicationContext context;
	SessionFactory factory;
	ProductCartViewDAO productCartViewDAO;
	
	@Before
	public void init() {
		context = new AnnotationConfigApplicationContext(com.motozone.config.SpringJavaConfig.class);
		productCartViewDAO = (ProductCartViewDAO) context.getBean("productCartViewDAOHibernate");
		// get session factory
		factory = (SessionFactory) context.getBean("sessionFactory");
	}
	
	
	@Test
	public void selectAllTest() {
		factory.getCurrentSession().beginTransaction();
	
//		List<ProductCartViewBean> list = productCartViewDAO.selectAll(1); //userNo
		List<ProductCartViewBean> list = productCartViewDAO.selectAll(2); //userNo
		
		factory.getCurrentSession().getTransaction().commit();
		
		if(list != null) {
			if(list.size() != 0) {
				for(ProductCartViewBean bean : list) {
					System.out.println(bean);
				}
			} else {
				System.out.println("No Result");
			}
			
		} else {
			System.out.println("NULL");
		}
	}
	
	@Test
	public void selectByIdAndUserTest() {
		factory.getCurrentSession().beginTransaction();
//		ProductCartViewBean result = productCartViewDAO.selectByIdAndUser(200, 1);
		ProductCartViewBean result = productCartViewDAO.selectByIdAndUser(202, 2);
		factory.getCurrentSession().getTransaction().commit();
		
		if(result != null) {
			System.out.println(result);
		} else {
			System.out.println("No Result");
		}
	}
	
	@Test
	public void selectByNoTest() {
		factory.getCurrentSession().beginTransaction();
//		ProductCartViewBean result = productCartViewDAO.selectByNo(1);
		ProductCartViewBean result = productCartViewDAO.selectByNo(9);
		factory.getCurrentSession().getTransaction().commit();
		
		if(result != null) {
			System.out.println(result);
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
