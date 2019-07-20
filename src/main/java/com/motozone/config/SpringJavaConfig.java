package com.motozone.config;

import java.util.Properties;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages= {"com.motozone.article",
							  "com.motozone.autobuy",
							  "com.motozone.admin",
							  "com.motozone.general"})
@Import(com.motozone.config.SpringWebSocketJavaConfig.class)
public class SpringJavaConfig {
	
	
	// standard dataSource
//	@Bean
//	public DataSource dataSource() {
//		DataSource ds = null;
//		
//		try {
//			InitialContext context = new InitialContext();
//			ds = (DataSource) context.lookup("java:comp/env/jdbc/MotoZoneDB");
//		} catch (NamingException e) {
//			e.printStackTrace();
//		}
//		
//		return ds;
//		
//	}
	
	// spring fake dataSource
//	@Bean
//	public DataSource dataSource() {
//		DriverManagerDataSource dmds = new DriverManagerDataSource();
//		
//		// set properties
//		dmds.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//		dmds.setUrl("jdbc:sqlserver://localhost:1433;databaseName=MotoZone");
//		dmds.setUsername("sa");
//		dmds.setPassword("passw0rd");
//		
//		return dmds;
//		
//	}
	
	@Bean
	public DataSource dataSource() {
		JndiObjectFactoryBean factory = new JndiObjectFactoryBean();
		factory.setJndiName("java:comp/env/jdbc/MotoZoneDB");
		factory.setProxyInterface(DataSource.class);
		
		try {
			
			// look up
			factory.afterPropertiesSet();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return (DataSource) factory.getObject();
		
	}
	
	@Bean
	public SessionFactory sessionFactory() {
		return new LocalSessionFactoryBuilder(dataSource())
				.configure("hibernate.cfg.xml").buildSessionFactory();
	}
	
	@Bean
	public PlatformTransactionManager transactionManagement() {
		return new HibernateTransactionManager(sessionFactory());
		
	}
	
	@Bean
	public JavaMailSender javaMailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost("smtp.gmail.com");
	    mailSender.setPort(587);
	    mailSender.setDefaultEncoding("UTF-8");
	    mailSender.setUsername("xuans8589@gmail.com");
	    mailSender.setPassword("iunyyfetsgsjyvqw");
	     
	    Properties props = mailSender.getJavaMailProperties();
	    props.put("mail.transport.protocol", "smtp");
	    props.put("mail.smtp.auth", "true");
	    props.put("mail.smtp.starttls.enable", "true");
	    props.put("mail.debug", "true");
	    
	    return mailSender;
	}

}
