package com.motozone.autobuy.model.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.motozone.autobuy.model.OrdersBean;

public class OrdersDAOHibernateTest {
	ApplicationContext context;
	SessionFactory factory;
	OrdersDAO ordersDAO;
	
	@Before
	public void init() {
		context = new AnnotationConfigApplicationContext(com.motozone.config.SpringJavaConfig.class);
		
		ordersDAO = (OrdersDAO) context.getBean("ordersDAOHibernate");
		// get session factory
		factory = (SessionFactory) context.getBean("sessionFactory");
	}
	
	@Test
	public void insertTest() {
		factory.getCurrentSession().beginTransaction();
		OrdersBean bean = new OrdersBean();
		bean.setOrderID(7);
		bean.setProductID(4);
		bean.setQuantity(2);
		ordersDAO.insert(bean);
		
		factory.getCurrentSession().getTransaction().commit();
		
		System.out.println(bean);
	}
	
	@Test
	public void updateTest() {
		factory.getCurrentSession().beginTransaction();
		OrdersBean bean = ordersDAO.selectByNo(2);
		bean.setQuantity(2);
		
		factory.getCurrentSession().getTransaction().commit();
		
		System.out.println(bean);
	}
	
	@Test
	public void deleteTest() {
		factory.getCurrentSession().beginTransaction();
		OrdersBean bean = ordersDAO.selectByNo(3);
		boolean result = ordersDAO.delete(bean);
		factory.getCurrentSession().getTransaction().commit();
		
		System.out.println(result);
	}
	
	@Test
	public void selectByNoTest() {
		factory.getCurrentSession().beginTransaction();
		System.out.println(ordersDAO.selectByNo(0));
		factory.getCurrentSession().getTransaction().commit();
		
		
	}
	@Test
	public void selectByUnoAndOIDTest() {
		factory.getCurrentSession().beginTransaction();
		List<OrdersBean> list = ordersDAO.selectByOID(5);
		factory.getCurrentSession().getTransaction().commit();
		
		if(list.size() != 0) {
			for(OrdersBean bean : list) {
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
