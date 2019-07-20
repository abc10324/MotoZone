package com.motozone.general.model.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.motozone.general.model.ChatBotBean;

public class ChatBotDAOHibernateTest {
	ApplicationContext context;
	SessionFactory sessionFactory;
	ChatBotDAO dao;

	@Before
	public void init() {
		context = new AnnotationConfigApplicationContext(com.motozone.config.SpringJavaConfig.class);
		dao = (ChatBotDAO) context.getBean("chatBotDAOHibernate");
		sessionFactory = (SessionFactory) context.getBean("sessionFactory");
	}

	@Test
	public void insert() {
		Integer userNo = 0;
//		Integer userNo = 2;
		
		String messageNo = "4";
		String messageTitle = "如何發布文章SSS";
		String message = "在文章清單區塊的右側有個 \"+\"圖案的按鈕，點擊後即可發布文章<br/>"
					   + "*注意:此功能需先登入才能使用"
					   + "";
		
		
		ChatBotBean bean = new ChatBotBean();
		bean.setUserNo(userNo);
		bean.setMessageNo(messageNo);
		bean.setMessageTitle(messageTitle);
		bean.setMessage(message);
		
		
		sessionFactory.getCurrentSession().beginTransaction();
		ChatBotBean result = dao.insert(bean);
		sessionFactory.getCurrentSession().getTransaction().commit();
		
		
		if(result != null) {
			System.out.println(bean.getMessageNo());
			System.out.println(bean.getMessageTitle());
			System.out.println(bean.getMessage());
		} else {
			System.out.println("Insert 失敗");
		}
		
	}
	
	
	@Test
	public void update() {
		Integer userNo = 0;
//		Integer userNo = 2;
		
		String messageNo = "3";
		String messageTitle = "如何發布文章";
		String message = "在文章清單區塊的右側有個 \"+\"圖案的按鈕，點擊後即可發布文章<br/>"
					   + "*注意:此功能需先登入才能使用"
					   + "";
		
		
		
		
		sessionFactory.getCurrentSession().beginTransaction();
		ChatBotBean bean = dao.selectByUserAndMsgNo(userNo, messageNo);
		bean.setMessageTitle(messageTitle);
		
		ChatBotBean result = dao.insert(bean);
		sessionFactory.getCurrentSession().getTransaction().commit();
		
		
		if(result != null) {
			System.out.println(bean.getMessageNo());
			System.out.println(bean.getMessageTitle());
			System.out.println(bean.getMessage());
		} else {
			System.out.println("Update 失敗");
		}
	}
	
	@Test
	public void delete() {
		Integer userNo = 0;
		String messageNo = "4";
		
		sessionFactory.getCurrentSession().beginTransaction();
		ChatBotBean bean = dao.selectByUserAndMsgNo(userNo, messageNo);
		
		boolean result = dao.delete(bean);
		sessionFactory.getCurrentSession().getTransaction().commit();
		
		if(result) {
			System.out.println("delete success");
		} else {
			System.out.println("delete failure");
		}
	}
	
	@Test
	public void selectByUserNo() {
		Integer userNo = 0;
		
		sessionFactory.getCurrentSession().beginTransaction();
		List<ChatBotBean> list = dao.selectByUserNo(userNo);
		sessionFactory.getCurrentSession().getTransaction().commit();
		
		if(list.size() != 0) {
			for(ChatBotBean bean : list) {
				System.out.println(bean.getMessageNo() + "." + bean.getMessageTitle());
				System.out.println(bean.getMessageTitle() + ":<br/>" + bean.getMessage());
			}
			
		} else {
			System.out.println("No result");
		}
		
	}

	@Test
	public void selectByUserNoAndMsgNo() {
		Integer userNo = 0;
		String messageNo = "5";
		
		sessionFactory.getCurrentSession().beginTransaction();
		ChatBotBean result = dao.selectByUserAndMsgNo(userNo, messageNo);
		sessionFactory.getCurrentSession().getTransaction().commit();
		
		if(result != null) {
				System.out.println(result.getMessageNo() + "." + result.getMessageTitle());
				System.out.println(result.getMessageTitle() + ":<br/>" + result.getMessage());
			
		} else {
			System.out.println("No result");
		}
		
	}
	
	@After
	public void destory() {
		sessionFactory.close();
		((ConfigurableApplicationContext) context).close();
	}
}
