package com.motozone.general.model.dao;

import javax.persistence.NoResultException;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.motozone.general.model.CategoryBean;
import com.motozone.general.model.ImageBean;

@Repository
public class ImageDAOHibernate implements ImageDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	private final String SELECT_BY_CAT = "select count(*) from com.motozone.general.model.ImageBean a where a.category = :category";
	private final String DELETE_BY_IMGSRC = "delete from com.motozone.general.model.ImageBean a where a.imgSrc = :imgSrc";
	
	@Override
	public Integer getTotal(CategoryBean category) {
		Long total = 0L;
		
		try {
			total = (Long) sessionFactory.getCurrentSession()
			.createQuery(SELECT_BY_CAT)
			.setParameter("category", category)
			.getSingleResult();
		} catch (NoResultException e) {
			e.printStackTrace();
			
			return 0;
		}
		
		return total.intValue();
	}


	@Override
	public ImageBean insert(ImageBean bean) {
		
		try {
			sessionFactory.getCurrentSession().save(bean);
		} catch (HibernateException e) {
			e.printStackTrace();
			
			return null;
		}
		
		return bean;
	}


	@Override
	public boolean delete(String imgSrc) {
		int result = 0;
		try {
			result = sessionFactory
					.getCurrentSession()
					.createQuery(DELETE_BY_IMGSRC)
					.setParameter("imgSrc", imgSrc)
					.executeUpdate();
		} catch (NoResultException e) {
			e.printStackTrace();
			return false;
		}
		
		return result != 0;
	}
	
	
	
	

}
