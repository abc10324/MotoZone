package com.motozone.article.model.dao;

import javax.persistence.NoResultException;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.motozone.article.model.ArticleIdBean;

@Repository
public class ArticleIdDAOHibernate implements ArticleIdDAO{
	
	@Autowired
	private SessionFactory factory;
	
	private final String SELECT_BY_ID = "FROM com.motozone.article.model.ArticleIdBean a WHERE a.id = :id";
	private final String SELECT_BY_URL = "FROM com.motozone.article.model.ArticleIdBean a WHERE a.url= :url";

	@Override
	public boolean insert(ArticleIdBean bean) {
		
		try {
			factory.getCurrentSession().save(bean);
		} catch (HibernateException e) {
			return false;
		}
		
		return true;
	}
	
	@Override
	public ArticleIdBean selectById(Integer id) {
		try {
			return (ArticleIdBean)factory.getCurrentSession().createQuery(SELECT_BY_ID)
					.setParameter("id", id).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	@Override
	public ArticleIdBean selectByUrl(String url) {
			
		try {
			return (ArticleIdBean)factory.getCurrentSession().createQuery(SELECT_BY_URL)
					.setParameter("url", url).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public ArticleIdBean updateViewsById(Integer id) {
		ArticleIdBean bean;
		try {
			bean = this.selectById(id);
			bean.setViews(bean.getViews() + 1);
			factory.getCurrentSession().save(bean);
		} catch (HibernateException e) {
			return null;
		}
		
		return bean;
	}
	
	@Override
	public ArticleIdBean updateImgSrc(ArticleIdBean bean) {
		try {
			factory.getCurrentSession().save(bean);
		} catch (HibernateException e) {
			return null;
		}
		return bean;
	}
	
	
	


}
