package com.motozone.article.model.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.motozone.article.model.ArticleListBean;
import com.motozone.article.model.dao.ArticleListDAO;
import com.motozone.general.model.dao.CategoryDAO;

public class ArticleListDAOHibernateTest {
	ApplicationContext context;
	SessionFactory factory;
	ArticleListDAO dao;
	CategoryDAO cDao;
	Integer pageSize = 20;
	Integer page = 2;
	
	@Before
	public void init() {
		context = new AnnotationConfigApplicationContext(com.motozone.config.SpringJavaConfig.class);
		
		dao = (ArticleListDAO) context.getBean("articleListDAOHibernate");
		cDao = (CategoryDAO) context.getBean("categoryDAOHibernate");
		
		// get session factory
		factory = (SessionFactory) context.getBean("sessionFactory");
	}
	
	@Test
	public void getArticleList() {
		factory.getCurrentSession().beginTransaction();
		
		List<ArticleListBean> list = dao.getArticleList("LAN",pageSize, page);
//		List<ArticleListBean> list = dao.getArticleList("123",pageSize, page);
		
		factory.getCurrentSession().getTransaction().commit();
		
		
		for(ArticleListBean bean : list) {
			System.out.println(bean.getTitle());
		}
		
	}
	
	@Test
	public void getArticleListById() {
		factory.getCurrentSession().beginTransaction();
		
//		ArticleListBean bean = dao.getArticleList(101301);
		ArticleListBean bean = dao.getArticleList(1301);
		
		factory.getCurrentSession().getTransaction().commit();
		
		if(bean != null) {
			System.out.println(bean.getTitle());
		}
		
	}
	
	@Test
	public void getArticleListByCategoryAndViews() {
		factory.getCurrentSession().beginTransaction();
		
		List<ArticleListBean> list = dao.getTopicList(cDao.getSubCategory("L"));
		
		factory.getCurrentSession().getTransaction().commit();
		
		for(ArticleListBean bean : list) {
			System.out.println(bean.getTitle());
			System.out.println(bean.getCategory());
			System.out.println(bean.getViews());
		}
		
	}
	
	
	@After
	public void destroy() {
		factory.close();
		((ConfigurableApplicationContext) context).close();
	}
}
