package com.motozone.general.model.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.motozone.general.model.UsersBean;
import com.motozone.general.model.UsersInfoBean;

public class UsersDAOHibernateTest {
	ApplicationContext context;
	SessionFactory sessionFactory;
	UsersDAO dao;

	@Before
	public void init() {
		context = new AnnotationConfigApplicationContext(com.motozone.config.SpringJavaConfig.class);
		dao = (UsersDAO) context.getBean("usersDAOHibernate");
		sessionFactory = (SessionFactory) context.getBean("sessionFactory");
	}

	@Test
	public void insert() {
		String pwd = "zzz222";
		byte[] pwdbyte = pwd.getBytes();
		Byte[] pwdByte = new Byte[pwdbyte.length];

		for (int i = 0; i < pwdByte.length; i++) {
			pwdByte[i] = pwdbyte[i];
		}

		UsersBean bean = new UsersBean();

		sessionFactory.getCurrentSession().beginTransaction();
		bean.setuID("xxx122");
		bean.setPwd(pwdByte);
		bean.setuName("懿軒");
		bean.setEmail("xxx111@gmail.com");
		boolean result = dao.insert(bean);
		sessionFactory.getCurrentSession().getTransaction().commit();
		System.out.println("Insert is " + result);
	}

	@Test
	public void selectById() {
		sessionFactory.getCurrentSession().beginTransaction();
		String uID = "xxx122";
//		String uID = "zzz222";
		UsersBean result = dao.selectById(uID);
		sessionFactory.getCurrentSession().getTransaction().commit();
		if (result != null) {
			System.out.println(result.getuNo());
			System.out.println(result.getuID());
			System.out.println(result.getPwd());
			System.out.println(result.getuName());
			System.out.println(result.getEmail());
		} else {
			System.out.println("No Result");
		}
	}
	
	@Test
	public void selectByNo() {
//		Integer uNo = 0;
		Integer uNo = 3;
		
		sessionFactory.getCurrentSession().beginTransaction();
		UsersBean bean = dao.selectByNo(uNo);
		sessionFactory.getCurrentSession().getTransaction().commit();
	
		if(bean != null) {
			System.out.println(bean.getuName());
		} else {
			System.out.println("No Result");
		}
	}
	
	@Test
	public void update() {
		sessionFactory.getCurrentSession().beginTransaction();
		String uID = "xxx122";
		UsersBean bean = dao.selectById(uID);
		if (bean != null) {
			String pwd = "Qwe123as";
			byte[] pwdbyte = pwd.getBytes();
			Byte[] pwdByte = new Byte[pwdbyte.length];

			for (int i = 0; i < pwdByte.length; i++) {
				pwdByte[i] = pwdbyte[i];
			}
			bean.setPwd(pwdByte);
			bean.setuName("帥哥");
			bean.setEmail("abc123@gmail.com");
			bean.setAuthCode(8888);
			UsersBean result = dao.update(bean);
			sessionFactory.getCurrentSession().getTransaction().commit();
			if (result != null) {
				System.out.println("update success");
			} else {
				System.out.println("update fail");
			}
		}

	}
	

	@After
	public void destory() {
		sessionFactory.close();
		((ConfigurableApplicationContext) context).close();
	}
}
