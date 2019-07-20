package com.motozone.autobuy.model.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.motozone.autobuy.model.OrderIdBean;

public class OrderIdDAOHibernateTest {
	ApplicationContext context;
	SessionFactory factory;
	OrderIdDAO orderIdDAO;
	
	@Before
	public void init() {
		context = new AnnotationConfigApplicationContext(com.motozone.config.SpringJavaConfig.class);
		
		orderIdDAO = (OrderIdDAO) context.getBean("orderIdDAOHibernate");
		// get session factory
		factory = (SessionFactory) context.getBean("sessionFactory");
	}
	
	@Test
	public void insertTest() {
		factory.getCurrentSession().beginTransaction();
		OrderIdBean bean = new OrderIdBean();
		bean.setuNo(0);
		bean.setuName("bbbb");
		bean.setTotal(2331313);
		
		OrderIdBean result = orderIdDAO.insert(bean);
		factory.getCurrentSession().getTransaction().commit();
		
		System.out.println(result);
	}
	
	@Test
	public void updateTest() {
		factory.getCurrentSession().beginTransaction();
		OrderIdBean bean = orderIdDAO.selectByOID(3);
		bean.setTotal(321);
		bean.setuName("jjjjjj");
		OrderIdBean result = orderIdDAO.update(bean);
		
		factory.getCurrentSession().getTransaction().commit();
		
		System.out.println(result);
	}
	
	@Test
	public void deleteTest() {
		factory.getCurrentSession().beginTransaction();
		OrderIdBean bean = orderIdDAO.selectByOID(2);
		orderIdDAO.delete(bean);
		factory.getCurrentSession().getTransaction().commit();
	}
	
	@Test
	public void selectByOIDTest() {
		factory.getCurrentSession().beginTransaction();
		OrderIdBean result = orderIdDAO.selectByOID(1);
		factory.getCurrentSession().getTransaction().commit();
		
		System.out.println(result);
	}
	
	@Test
	public void selectByUnoTest() {
		factory.getCurrentSession().beginTransaction();
		List<OrderIdBean> list = orderIdDAO.selectByUno(1);
		factory.getCurrentSession().getTransaction().commit();
		
		if(list.size() != 0) {
			for(OrderIdBean bean : list) {
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
