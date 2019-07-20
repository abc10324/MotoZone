package com.motozone.article.model.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.motozone.article.model.ArticleBean;
import com.motozone.article.model.dao.ArticleDAO;
import com.motozone.article.model.dao.ArticleIdDAO;

public class ArticleDAOHibernateTest {
	ApplicationContext context;
	SessionFactory factory;
	ArticleDAO dao;
	Integer pageSize = 10;
	Integer page = 2;
	
	
	@Before
	public void init() {
		context = new AnnotationConfigApplicationContext(com.motozone.config.SpringJavaConfig.class);
		
		dao = (ArticleDAO) context.getBean("articleDAOHibernate");
		
		
		// get session factory
		factory = (SessionFactory) context.getBean("sessionFactory");
	}
	
	@Test
	public void update() {
		factory.getCurrentSession().beginTransaction();
		
		ArticleBean bean = dao.selectByUserNo(11);
		ArticleBean result = null;
		
		if(bean != null) {
			bean.setContent("會打滑是碗公的問題，跟機油沒關係<br/> ");
			
			result = dao.update(bean);
		}
		
		factory.getCurrentSession().getTransaction().commit();
		
		if(result != null) {
			System.out.println(bean.getContent());
			System.out.println(bean.getAuthor());
		} else {
			System.out.println("update failure");
			
		}
	}
	
	@Test
	public void delete() {
		factory.getCurrentSession().beginTransaction();
		boolean result = dao.delete(160068);
//		boolean result = dao.delete(1000000);
		factory.getCurrentSession().getTransaction().commit();
		
		System.out.println(result);
	}
	
	@Test
	public void getArticles() {
		factory.getCurrentSession().beginTransaction();
		
		List<ArticleBean> list = dao.getArticles(550187,pageSize, page);
		
		factory.getCurrentSession().getTransaction().commit();
		
		for(ArticleBean bean : list) {
			System.out.println(bean.getAuthor());
			System.out.println(bean.getDate());
		}
	}
	
	@Test
	public void getArticle() {
		factory.getCurrentSession().beginTransaction();
		
		ArticleBean result = dao.selectByUserNo(1234567);
		
		factory.getCurrentSession().getTransaction().commit();
		
		if(result != null) {
			System.out.println(result.getAuthor());
			System.out.println(result.getDate());
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
