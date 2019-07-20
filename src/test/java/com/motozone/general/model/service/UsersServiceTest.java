package com.motozone.general.model.service;

import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.motozone.general.model.UsersBean;
import com.motozone.general.model.UsersInfoBean;

public class UsersServiceTest {
	private static ApplicationContext context;
	private static SessionFactory sessionFactory;
	private static UsersService usersService;

	@Before
	public void init() {
		context = new AnnotationConfigApplicationContext(com.motozone.config.SpringJavaConfig.class);
		usersService = (UsersService) context.getBean("usersService");
		sessionFactory = (SessionFactory) context.getBean("sessionFactory");
	}

	@Test
	public void regist() {
		UsersBean bean = new UsersBean();
		sessionFactory.getCurrentSession().beginTransaction();
		String pwd = "zzz222";
		byte[] pwdbyte = pwd.getBytes();
		Byte[] pwdByte = new Byte[pwdbyte.length];

		for (int i = 0; i < pwdByte.length; i++) {
			pwdByte[i] = pwdbyte[i];
		}
//		bean.setuID("xxx112");
		bean.setuID("xxx122");
		bean.setPwd(pwdByte);
		bean.setuName("懿軒");
		bean.setEmail("xxx111@gmail.com");
		boolean result = usersService.regist(bean);
		sessionFactory.getCurrentSession().getTransaction().commit();
		System.out.println(result);
	}
	
	@Test
	public void login() {
		sessionFactory.getCurrentSession().beginTransaction();
//		UsersBean result = loginService.login("xyz123", "12345");
		UsersBean result = usersService.login("xxx112", "zzz222");
		System.out.println(result != null ? "Login Success" : "Login failure");
		sessionFactory.getCurrentSession().getTransaction().commit();
	}
	
	@Test
	public void authCodeCheck() {
		sessionFactory.getCurrentSession().beginTransaction();
		UsersBean result = usersService.authCodeCheck("xxx122", "8888");
		sessionFactory.getCurrentSession().getTransaction().commit();
		System.out.println(result);
		
	}
	
	@Test
	public void showUserDataById() {
		sessionFactory.getCurrentSession().beginTransaction();
		UsersInfoBean result = usersService.showUserData("abc10324");
//		UsersLoginBean result = usersService.showUserData("xxx");
		sessionFactory.getCurrentSession().getTransaction().commit();
		
		if(result != null) {
			System.out.println("name = " + result.getuName());
			
			for(UsersInfoBean bean : result.getFriendsList()) {
				System.out.println(bean.getuID());
				System.out.println(bean.getuName());
			}
		} else {
			System.out.println("No result");
		}
	}
	
	@Test
	public void showUserDataByNo() {
		sessionFactory.getCurrentSession().beginTransaction();
		UsersInfoBean result = usersService.showUserData(0);
//		UsersInfoBean result = usersService.showUserData(3);
		sessionFactory.getCurrentSession().getTransaction().commit();
		
		if(result != null) {
			System.out.println("name = " + result.getuName());
			
			for(UsersInfoBean bean : result.getFriendsList()) {
				System.out.println(bean.getuID());
				System.out.println(bean.getuName());
			}
		} else {
			System.out.println("No result");
		}
	}
	
	@Test
	public void addFriend() {
		sessionFactory.getCurrentSession().beginTransaction();
		UsersInfoBean result = usersService.addFriend(0, 8);
//		UsersInfoBean result = usersService.addFriend(0, 2);
		sessionFactory.getCurrentSession().getTransaction().commit();
		
		if(result != null) {
			System.out.println(result.getuName());
		} else {
			System.out.println("Fail to insert");
		}
		
	
	}

	@After
	public void destory() {
		sessionFactory.close();
		((ConfigurableApplicationContext) context).close();
	}

}
