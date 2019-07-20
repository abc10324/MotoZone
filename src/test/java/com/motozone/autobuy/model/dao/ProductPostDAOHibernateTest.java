package com.motozone.autobuy.model.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.motozone.autobuy.model.ProductPostBean;

public class ProductPostDAOHibernateTest {
	ApplicationContext context;
	SessionFactory factory;
	ProductPostDAO dao;
	
	@Before
	public void init() {
		context = new AnnotationConfigApplicationContext(com.motozone.config.SpringJavaConfig.class);
		
		dao = (ProductPostDAO) context.getBean("productPostDAOHibernate");
		
		
		// get session factory
		factory = (SessionFactory) context.getBean("sessionFactory");
	}
	
	
	@Test
	public void insert() {
		factory.getCurrentSession().beginTransaction();
		
		ProductPostBean bean = new ProductPostBean();
		bean.setCategory("安安");
//		bean.setCategory("BBE");
		bean.setImg("http://ewjodwepqwo");
		bean.setName("安安");
		bean.setQuantity(123);
		bean.setPrice(123);
		bean.setArea("安安");
		bean.setStatus("安安");
		bean.setStatusDescription("安安");
		bean.setDescription("安安");
		bean.setPayment("安安");
		bean.setDelivery("安安");
		bean.setShipping("安安");
		bean.setAuctionDays(455);
		
		
		System.out.println("result=" + dao.insert(bean));
		System.out.println("id=" + bean.getId());
		
		factory.getCurrentSession().getTransaction().commit();
	}
	
	@Test
	public void selectById() {
		factory.getCurrentSession().beginTransaction();
		ProductPostBean bean = new ProductPostBean();
//		bean = dao.selectById(15);
		bean = dao.selectById(1);
		System.out.println("result = " + bean);
		factory.getCurrentSession().getTransaction().commit();
	}
	
	@Test
	public void selectByCat() {
		
		factory.getCurrentSession().beginTransaction();
		List<ProductPostBean> list = dao.selectByCategory("BER");
//		List<ProductPostBean> list = dao.selectByCategory("123");
		
		factory.getCurrentSession().getTransaction().commit();
		
		if(list != null) {
			if(list.size() != 0) {
				for(ProductPostBean bean : list) {
					System.out.println("result = " + bean);
				}
			} else {
				System.out.println("No Result");
			}
			
		} else {
			System.out.println("NULL");
		}
	}
	
	@Test
	public void selectBySubcat() {
		factory.getCurrentSession().beginTransaction();
		List<ProductPostBean> list = dao.selectByExplicitCategory("BER");
//		List<ProductPostBean> list = dao.selectByExplicitCategory("123");
		
		factory.getCurrentSession().getTransaction().commit();
		
		if(list != null) {
			if(list.size() != 0) {
				for(ProductPostBean bean : list) {
					System.out.println("result = " + bean);
				}
			} else {
				System.out.println("No Result");
			}
			
		} else {
			System.out.println("NULL");
		}
	}
	
	@Test
	public void update() {
		factory.getCurrentSession().beginTransaction();
		ProductPostBean bean = new ProductPostBean();
		
//		bean = dao.selectById(214);
		bean = dao.selectById(1);
		
		if(bean != null) {
			bean.setName("嚕嚕嚕");
			bean.setArea("台北");
			bean.setPrice(500000);
			dao.update(bean);
		} else {
			System.out.println("Data not found");
		}
		
		System.out.println("updateresult = " + bean);
		factory.getCurrentSession().getTransaction().commit();

	}
	
	@After
	public void destroy() {
		factory.close();
		
		((ConfigurableApplicationContext) context).close();
	}
}
