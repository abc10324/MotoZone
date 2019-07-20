package com.motozone.admin.model.service;

import java.util.Date;

import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.motozone.general.model.UsersBean;

public class UserAdminServiceTest {
	private static ApplicationContext context;
	private static SessionFactory sessionFactory;
	private static UserAdminService userAdminService;

	@Before
	public void init() {
		context = new AnnotationConfigApplicationContext(com.motozone.config.SpringJavaConfig.class);
		userAdminService = (UserAdminService) context.getBean("userAdminService");
		sessionFactory = (SessionFactory) context.getBean("sessionFactory");
	}

	@Test
	public void showUserDataById() {
		sessionFactory.getCurrentSession().beginTransaction();
		UsersBean result = userAdminService.showUserData("117002814958269938043");
//		UsersBean result = userAdminService.showUserData("abc10324");
		sessionFactory.getCurrentSession().getTransaction().commit();

		if (result != null) {
			System.out.println("uNo : " + result.getuNo());
			System.out.println("uID : " + result.getuID());
			System.out.println("uName : " + result.getuName());
			System.out.println("email : " + result.getEmail());
		} else {
			System.out.println("NO result");
		}
	}

	@Test
	public void updateUserData() {
		sessionFactory.getCurrentSession().beginTransaction();
//		UsersBean bean = userAdminService.showUserData("117002814958269938043");
		UsersBean bean = userAdminService.showUserData("abc10324");
		if (bean != null) {
			bean.setPersonID("A123456789");
			bean.setBirth(new Date("July 04,1995"));
			bean.setPhone("0988181808");
			bean.setSex("male");
			
			
			UsersBean result = userAdminService.updateUserData(bean);
			sessionFactory.getCurrentSession().getTransaction().commit();
			System.out.println("Update Success");
			System.out.println("Update Information : ");
			System.out.println("pID : " + result.getPersonID());
			System.out.println("Birth : " + result.getBirth());
			System.out.println("Phone : " + result.getPhone());
			System.out.println("Sex : " + result.getSex());
		} else {
			System.out.println("userID Not Found");
		}
	}

	@After
	public void destory() {
		sessionFactory.close();
		((ConfigurableApplicationContext) context).close();
	}
}
