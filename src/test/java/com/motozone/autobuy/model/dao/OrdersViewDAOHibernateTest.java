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
import com.motozone.autobuy.model.OrdersViewBean;

public class OrdersViewDAOHibernateTest {
	ApplicationContext context;
	SessionFactory factory;
	OrdersViewDAO ordersViewDAO;
	
	@Before
	public void init() {
		context = new AnnotationConfigApplicationContext(com.motozone.config.SpringJavaConfig.class);
		
		ordersViewDAO = (OrdersViewDAO) context.getBean("ordersViewDAOHibernate");
		// get session factory
		factory = (SessionFactory) context.getBean("sessionFactory");
	}
	
	
	
	@Test
	public void selectByOrderID() {
		factory.getCurrentSession().beginTransaction();
		List<OrdersViewBean> list = ordersViewDAO.selectByOrderID(7);
		factory.getCurrentSession().getTransaction().commit();
		
		if(list.size() != 0) {
			for(OrdersViewBean bean : list) {
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
