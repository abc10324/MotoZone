package com.motozone.article.model.dao;

import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.motozone.article.model.ArticleIdBean;

public class ArticleIdDAOHibernateTest {
	ApplicationContext context;
	SessionFactory factory;
	ArticleIdDAO daoId;
	
	
	@Before
	public void init() {
		context = new AnnotationConfigApplicationContext(com.motozone.config.SpringJavaConfig.class);
		
		daoId = (ArticleIdDAO) context.getBean("articleIdDAOHibernate");
		
		
		// get session factory
		factory = (SessionFactory) context.getBean("sessionFactory");
	}
	
	@Test
	public void insert() {
		factory.getCurrentSession().beginTransaction();
		
		ArticleIdBean bean = new ArticleIdBean();
		bean.setId(1);
		daoId.insert(bean);
		
		ArticleIdBean result = daoId.selectById(bean.getId());
		
		factory.getCurrentSession().getTransaction().commit();
		
		System.out.println(result.getId());
		System.out.println(result.getTitle());
		
	}
	
	@Test
	public void selectById() {
		factory.getCurrentSession().beginTransaction();
		
		ArticleIdBean bean = daoId.selectById(550187);
		
		factory.getCurrentSession().getTransaction().commit();
		
		System.out.println(bean.getTitle());
	}
	
	@Test
	public void selectByUrl() {
		factory.getCurrentSession().beginTransaction();
		
		ArticleIdBean bean = daoId.selectByUrl("https://www.mobile01.com/topicdetail.php?f=753&t=5225395");
		
		factory.getCurrentSession().getTransaction().commit();
		
		System.out.println(bean.getTitle());
		
	}
	
	@Test
	public void updateViewsById() {
		factory.getCurrentSession().beginTransaction();
		
		ArticleIdBean bean = daoId.updateViewsById(550187);
		
		factory.getCurrentSession().getTransaction().commit();
		
		System.out.println(bean.getViews());
	}
	
	@Test
	public void updateImgSrc() {
		factory.getCurrentSession().beginTransaction();
		
		ArticleIdBean bean = daoId.selectById(1);
		bean.setImgSrc("<img>");
		
		ArticleIdBean result = daoId.updateImgSrc(bean);
		
		factory.getCurrentSession().getTransaction().commit();
		
		System.out.println(result.getImgSrc());
	}
	
	@After
	public void destroy() {
		factory.close();
		((ConfigurableApplicationContext) context).close();
	}
}
