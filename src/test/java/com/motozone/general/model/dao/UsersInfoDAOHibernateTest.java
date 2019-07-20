package com.motozone.general.model.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.motozone.general.model.UsersInfoBean;

public class UsersInfoDAOHibernateTest {
	private static ApplicationContext context;
	private static SessionFactory sessionFactory;
	private static UsersInfoDAO usersInfoDAO = null;

	@Before
	public void setUp() throws Exception {
		context = new AnnotationConfigApplicationContext(com.motozone.config.SpringJavaConfig.class);
		sessionFactory = (SessionFactory) context.getBean("sessionFactory");
		usersInfoDAO = (UsersInfoDAO) context.getBean("usersInfoDAOHibernate");
	}

	

	@Test
	public void selectById() {
		sessionFactory.getCurrentSession().beginTransaction();
		UsersInfoBean bean = usersInfoDAO.selectById("abc10324");
//		UsersInfoBean bean = usersInfoDAO.selectById("xxx122");
		sessionFactory.getCurrentSession().getTransaction().commit();
		
		if(bean != null) {
			System.out.println("name=" + bean.getuName());
		} else {
			System.out.println("No Result");
		}
		
	}
	
	@Test
	public void selectByNo() {
		sessionFactory.getCurrentSession().beginTransaction();
		UsersInfoBean bean = usersInfoDAO.selectByNo(7);
//		UsersLoginBean bean = usersLogindao.selectByNo(3);
		sessionFactory.getCurrentSession().getTransaction().commit();
		
		if(bean != null) {
			System.out.println("name=" + bean.getuName());
		}
		
	}
	
	@Test
	public void getFriendList() {
		sessionFactory.getCurrentSession().beginTransaction();
		
		List<UsersInfoBean> list = usersInfoDAO.getFriendList(0);
		
		sessionFactory.getCurrentSession().getTransaction().commit();
		
		if(list != null) {
			for(UsersInfoBean bean : list) {
				System.out.println(bean.getuID());
				System.out.println(bean.getuName());
			}
		} else {
			System.out.println("No Result");
		}
	}
	
	@After
	public void tearDown() throws Exception {
		sessionFactory.close();
		((ConfigurableApplicationContext) context).close();
	}

}
