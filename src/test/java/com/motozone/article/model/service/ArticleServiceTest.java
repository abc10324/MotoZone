package com.motozone.article.model.service;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.motozone.article.model.ArticleBean;
import com.motozone.article.model.ArticleListBean;
import com.motozone.article.model.ArticlePostBean;
import com.motozone.article.model.util.ArticleUtils;
import com.motozone.general.model.service.GeneralService;

public class ArticleServiceTest {
	ApplicationContext context;
	SessionFactory factory;
	ArticleService service;
	GeneralService generalService;
	Integer pageSize = 5;
	Integer page = 1;
	
	
	@Before
	public void init() {
		context = new AnnotationConfigApplicationContext(com.motozone.config.SpringJavaConfig.class);
		
		service = (ArticleService) context.getBean("articleService");
		
		generalService = (GeneralService) context.getBean("generalService");
		// get session factory
		factory = (SessionFactory) context.getBean("sessionFactory");
	}
	
	@Test
	public void getId() {
		
		factory.getCurrentSession().beginTransaction();
		
		System.out.println(service.getId("Hello", "LAN", "wwww"));
		
		factory.getCurrentSession().getTransaction().commit();
	}
	
	@Test
	public void addPost() {
		factory.getCurrentSession().beginTransaction();
		
		boolean result = service.addPost(new ArticleBean(service.getId("Hello", "LAN", "<img/>", "wwww"), "Sam", new Timestamp(System.currentTimeMillis()) , "<br/>Hello"));
		
		System.out.println(result);
		
		factory.getCurrentSession().getTransaction().commit();
	}
	
	@Test
	public void getPost() {
		factory.getCurrentSession().beginTransaction();
		
		List<ArticlePostBean> list = service.getPosts(550187, page);
		
		for(ArticlePostBean bean : list) {
			System.out.println(bean.getAuthor());
			System.out.println(bean.getDate());
			System.out.println(bean.getSnapshot());
		}
		
		factory.getCurrentSession().getTransaction().commit();
	}
	
	@Test
	public void getPostsListById() {
		factory.getCurrentSession().beginTransaction();
		
		ArticleListBean bean = service.getPostList(550187);
		
		System.out.println(bean.getTitle());
		
		factory.getCurrentSession().getTransaction().commit();
	}
	
	@Test
	public void getPostsTitle() {
		factory.getCurrentSession().beginTransaction();
		
		String result = service.getPostsTitle(550187);
		
		System.out.println(result);
		
		factory.getCurrentSession().getTransaction().commit();
	}
	
	@Test
	public void getPostsCategory() {
		factory.getCurrentSession().beginTransaction();
		
		String result = service.getPostsCategory(550187);
		
		System.out.println(result);
		
		factory.getCurrentSession().getTransaction().commit();
	}
	
	
	
	@Test
	public void postListContentCutter() {
		factory.getCurrentSession().beginTransaction();
		
		List<ArticleListBean> list = service.getPostList("LAN", page);
		
		factory.getCurrentSession().getTransaction().commit();
		
		list = ArticleUtils.postListContentExtractor(list,false);
		
		for(ArticleListBean bean : list) {
			System.out.println(bean.getContent());
		}
		
		
	}
	
	@Test
	public void getTopicList() {
		factory.getCurrentSession().beginTransaction();
		
		List<ArticleListBean> list = service.getTopicList(generalService.getSubcategory("E"));
//		List<ArticleListBean> list = service.getTopicList(generalService.getSubcategory("N"));
		
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
