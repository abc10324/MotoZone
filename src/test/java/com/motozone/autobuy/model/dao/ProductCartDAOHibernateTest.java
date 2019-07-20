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
import com.motozone.autobuy.model.ProductPostBean;

public class ProductCartDAOHibernateTest {
	ApplicationContext context;
	SessionFactory factory;
	ProductCartDAO productCartDAO;
	ProductPostDAO productPostDAO;
	ProductCartViewDAO productCartViewDAO;
	@Before
	public void init() {
		context = new AnnotationConfigApplicationContext(com.motozone.config.SpringJavaConfig.class);
		
		productCartDAO = (ProductCartDAO) context.getBean("productCartDAOHibernate");
		productPostDAO = (ProductPostDAO) context.getBean("productPostDAOHibernate");
		productCartViewDAO = (ProductCartViewDAO) context.getBean("productCartViewDAOHibernate");
		// get session factory
		factory = (SessionFactory) context.getBean("sessionFactory");
	}
	
	@Test
	public void selectByNo() {
		factory.getCurrentSession().beginTransaction();
		
//		ProductCartBean bean = productCartDAO.selectByNo(0);
		ProductCartBean bean = productCartDAO.selectByNo(40);
		
		factory.getCurrentSession().getTransaction().commit();
		
		if(bean != null) {
			System.out.println(bean);
		} else {
			System.out.println("No Result");
		}
	}
	
	@Test
	public void selectByIdAndUserTest() {
		factory.getCurrentSession().beginTransaction();
		
//		ProductCartBean bean = productCartDAO.selectByIdAndUser(203,2);
		ProductCartBean bean = productCartDAO.selectByIdAndUser(203,1);
//		ProductCartBean bean = productCartDAO.selectByIdAndUser(2,1);
		
		factory.getCurrentSession().getTransaction().commit();
		
		if(bean != null) {
			System.out.println(bean);
		} else {
			System.out.println("No Result");
		}
	}
	
	@Test
	public void selectAll() {
		factory.getCurrentSession().beginTransaction();
		
//		List<ProductCartBean> list = productCartDAO.selectAll(2);
		List<ProductCartBean> list = productCartDAO.selectAll(1);
		
		factory.getCurrentSession().getTransaction().commit();
		
		if(list != null) {
			if(list.size() != 0) {
				for(ProductCartBean bean : list) {
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
	public void insertTest() {
		factory.getCurrentSession().beginTransaction();
		ProductPostBean search  = new ProductPostBean();
		search = productPostDAO.selectById(203);
		ProductCartBean bean = new ProductCartBean();
		
//		bean.setId(search.getId());
//		bean.setCategory(search.getCategory());
//		bean.setName(search.getName());
//		bean.setDescription(search.getDescription());
//		bean.setQuantity(search.getQuantity());
//		bean.setPrice(search.getPrice());
//		bean.setImgSrc(search.getImg());
		bean.setContent(search);
		bean.setQuantity(2);
		bean.setUserNo(2);
		System.out.println("result = " + productCartDAO.insert(bean));
		System.out.println(bean);
		System.out.println("id = " + bean.getNo());
		
		factory.getCurrentSession().getTransaction().commit();
	}
	
	@Test
	public void updateTest() {
		factory.getCurrentSession().beginTransaction();
	
		ProductCartBean bean = new ProductCartBean();
//		bean = productCartDAO.selectByNo(1);
		bean = productCartDAO.selectByNo(40);
		
		ProductCartBean result = null;
		
		if(bean != null) {
			bean.setQuantity(10);
			result = productCartDAO.update(bean);
			System.out.println("result = " + result);
		} else {
			System.out.println("Data not found");
		}
		
		factory.getCurrentSession().getTransaction().commit();
		
		if(result != null) {
			System.out.println(result);
		} else {
			System.out.println("Update Fail");
		}
	}
	
	@Test
	public void deleteTest() {
		factory.getCurrentSession().beginTransaction();
	
		ProductCartBean bean = new ProductCartBean();
		bean = productCartDAO.selectByNo(1);
//		bean = productCartDAO.selectByNo(4);
		
		boolean result = productCartDAO.delete(bean);
		
		factory.getCurrentSession().getTransaction().commit();
		
		System.out.println("result = " + result);
	}
	
	@Test
	public void cleanTable() {
		factory.getCurrentSession().beginTransaction();
//		boolean result = productCartDAO.cleanTable(1);  //userNo
		boolean result = productCartDAO.cleanTable(2);  //userNo
		factory.getCurrentSession().getTransaction().commit();
		
		System.out.println("result = " + result);
	}
	
	
	@After
	public void destroy() {
		factory.close();
		((ConfigurableApplicationContext) context).close();
	}
}
