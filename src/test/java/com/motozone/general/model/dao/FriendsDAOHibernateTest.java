package com.motozone.general.model.dao;

import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.motozone.general.model.FriendsBean;
import com.motozone.general.model.UsersInfoBean;

public class FriendsDAOHibernateTest {
	ApplicationContext context;
	SessionFactory sessionFactory;
	UsersInfoDAO usersInfoDAO;
	FriendsDAO dao;

	@Before
	public void init() {
		context = new AnnotationConfigApplicationContext(com.motozone.config.SpringJavaConfig.class);
		usersInfoDAO = (UsersInfoDAO) context.getBean("usersInfoDAOHibernate");
		dao = (FriendsDAO) context.getBean("friendsDAOHibernate");
		sessionFactory = (SessionFactory) context.getBean("sessionFactory");
	}

	@Test
	public void insert() {
		Integer userNo = 0;
//		Integer userNo = 2;
//		Integer friendNo = 0;
		Integer friendNo = 7;
		
		sessionFactory.getCurrentSession().beginTransaction();
		
		FriendsBean bean = new FriendsBean();
		bean.setUserNo(userNo);
		bean.setFriendNo(friendNo);
		
		FriendsBean result = dao.insert(bean);
		System.out.println("result = " + (result != null));
		sessionFactory.getCurrentSession().getTransaction().commit();
		
	}
	
	@Test
	public void selectByNo() {
		Integer userNo = 0;
//		Integer friendNo = 7;
		Integer friendNo = 0;
		
		sessionFactory.getCurrentSession().beginTransaction();
		FriendsBean bean = dao.selectByEachNo(userNo, friendNo);
		sessionFactory.getCurrentSession().getTransaction().commit();
		
		if(bean != null) {
			System.out.println(bean.getFriendNo());
		} else {
			System.out.println("No Result");
		}
	}

	@After
	public void destory() {
		sessionFactory.close();
		((ConfigurableApplicationContext) context).close();
	}
}
