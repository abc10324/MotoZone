package com.motozone.article.model.dao;

import java.util.List;

import javax.persistence.NoResultException;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.motozone.article.model.ArticleBean;
import com.motozone.article.model.ArticleIdBean;

@Repository
public class ArticleDAOHibernate implements ArticleDAO{
	@Autowired
	private SessionFactory factory;
	private final String SELECT_BY_ID = "From com.motozone.article.model.ArticleBean a where a.id = :id order by date asc";
	private final String SELECT_BY_NO = "From com.motozone.article.model.ArticleBean a where a.no = :postNo";
	private final String DELETE_BY_NO = "delete From com.motozone.article.model.ArticleBean a where a.no = :postNo";
	
	public ArticleDAOHibernate() {
		
	}
	
	@Override
	public boolean insert(ArticleBean bean) {
		
		try {
			factory.getCurrentSession().save(bean);
		} catch (HibernateException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	@Override
	public boolean insert(List<ArticleBean> list) {
		
		try {
			for(ArticleBean bean : list) {
				factory.getCurrentSession().save(bean);
			}
		} catch (HibernateException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	@Override
	public ArticleBean update(ArticleBean bean) {
		
		try {
			factory.getCurrentSession().save(bean);
		} catch (HibernateException e) {
			e.printStackTrace();
			return null;
		}
		
		return bean;
	}
	
	@Override
	public boolean delete(Integer postNo) {
		
		int result = factory.getCurrentSession()
				   			.createQuery(DELETE_BY_NO)
				   			.setParameter("postNo", postNo)
				   			.executeUpdate();
		
		if(result != 0) {
			return true;
		} else {
			return false;
		}
	}
	
	
	@Override
	public List<ArticleBean> getArticles(Integer id,Integer pageSize,Integer page) {
		
		return factory.getCurrentSession().createQuery(SELECT_BY_ID)
									.setFirstResult(pageSize*(page - 1)) // offset
									.setMaxResults(pageSize) 			// next rows
									.setParameter("id", id)
									.getResultList();
		
	}

	@Override
	public ArticleBean selectByUserNo(Integer postNo) {
		ArticleBean result = null;
		
		try {
			result = (ArticleBean) factory.getCurrentSession()
										  .createQuery(SELECT_BY_NO)
										  .setParameter("postNo", postNo)
										  .getSingleResult();
		} catch (NoResultException e) {
//			e.printStackTrace();
			return null;
		}
		
		return result;
	}
	
}
