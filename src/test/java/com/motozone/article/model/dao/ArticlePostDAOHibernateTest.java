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
import com.motozone.article.model.ArticlePostBean;

public class ArticlePostDAOHibernateTest {
	ApplicationContext context;
	SessionFactory factory;
	ArticlePostDAO dao;
	Integer pageSize = 10;
	Integer page = 1;
	
	
	@Before
	public void init() {
		context = new AnnotationConfigApplicationContext(com.motozone.config.SpringJavaConfig.class);
		
		dao = (ArticlePostDAO) context.getBean("articlePostDAOHibernate");
		
		
		// get session factory
		factory = (SessionFactory) context.getBean("sessionFactory");
	}
	
	@Test
	public void getArticles() {
		factory.getCurrentSession().beginTransaction();
		
		List<ArticlePostBean> list = dao.selectByIdAndPage(550187,pageSize, page);
		
		factory.getCurrentSession().getTransaction().commit();
		
		for(ArticlePostBean bean : list) {
			System.out.println(bean.getAuthor());
			System.out.println(bean.getSnapshot());
			System.out.println(bean.getTitle());
			System.out.println(bean.getCategory());
			System.out.println(bean.getDate());
		}
	}
	
	
	@Test
	public void getArticlesByUserNo() {
		factory.getCurrentSession().beginTransaction();
		
//		List<ArticlePostBean> list = dao.getArticles(5);
		List<ArticlePostBean> list = dao.selectByUserNo(1);
		
		factory.getCurrentSession().getTransaction().commit();
		
		if(list.size() != 0) {
			for(ArticlePostBean bean : list) {
				System.out.println(bean.getAuthor());
				System.out.println(bean.getSnapshot());
				System.out.println(bean.getTitle());
				System.out.println(bean.getCategory());
				System.out.println(bean.getDate());
			}
		} else {
			System.out.println("No Result");
		}
		
	}
	
	@Test
	public void getArticleByPostNo() {
		factory.getCurrentSession().beginTransaction();
		
//		ArticlePostBean result = dao.getArticle(1);
		ArticlePostBean result = dao.selectByPostNo(1000000);
		
		factory.getCurrentSession().getTransaction().commit();
		
		if(result != null) {
				System.out.println(result.getAuthor());
				System.out.println(result.getSnapshot());
				System.out.println(result.getTitle());
				System.out.println(result.getCategory());
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
