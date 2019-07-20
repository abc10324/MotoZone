package com.motozone.article.model.dao;

import java.util.List;

import javax.persistence.NoResultException;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.motozone.article.model.ArticlePostBean;

@Repository
public class ArticlePostDAOHibernate implements ArticlePostDAO{
	@Autowired
	private SessionFactory factory;
	private final String SELECT_BY_ID = "From com.motozone.article.model.ArticlePostBean a where a.id = :id order by date asc";
	private final String SELECT_BY_NO = "From com.motozone.article.model.ArticlePostBean a where a.no = :postNo";
	private final String SELECT_BY_USER_NO = "From com.motozone.article.model.ArticlePostBean a where a.userNo = :userNo order by date desc";
	
	
	public ArticlePostDAOHibernate() {
		
	}
	
	@Override
	public List<ArticlePostBean> selectByIdAndPage(Integer id,Integer pageSize,Integer page) {
		List<ArticlePostBean> list = null;
		
		try {
			list = factory.getCurrentSession().createQuery(SELECT_BY_ID)
					.setFirstResult(pageSize*(page - 1)) // offset
					.setMaxResults(pageSize) 			// next rows
					.setParameter("id", id)
					.getResultList();
		} catch (NoResultException e) {
//			e.printStackTrace();
			return null;
		}
		
		return list;
	}

	@Override
	public List<ArticlePostBean> selectByUserNo(Integer userNo) {
		List<ArticlePostBean> list = null;
		
		try {
			list = factory.getCurrentSession()
					.createQuery(SELECT_BY_USER_NO)
					.setParameter("userNo", userNo)
					.getResultList();
		} catch (NoResultException e) {
			e.printStackTrace();
			return null;
		}
		
		return list;
	}

	@Override
	public ArticlePostBean selectByPostNo(Integer postNo) {
		ArticlePostBean result = null;
		try {
			result = (ArticlePostBean) factory.getCurrentSession()
											  .createQuery(SELECT_BY_NO)
											  .setParameter("postNo", postNo)
											  .getSingleResult();
		} catch (NoResultException e) {
//			e.printStackTrace();
			return null;
		}
		
		return result;
	}

	@Override
	public List<ArticlePostBean> selectById(Integer id) {
		List<ArticlePostBean> list = null;
		
		try {
			list = factory.getCurrentSession().createQuery(SELECT_BY_ID)
					.setParameter("id", id)
					.getResultList();
		} catch (NoResultException e) {
//			e.printStackTrace();
			return null;
		}
		
		return list;
	}
	
}
