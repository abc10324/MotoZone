package com.motozone.autobuy.model.dao;

import java.util.List;

import javax.persistence.NoResultException;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.motozone.autobuy.model.ProductQuestionBean;

@Repository
public class ProductQuestionDAOHibernate implements ProductQuestionDAO{
	
	@Autowired
	SessionFactory factory;
	private static final String SELECT_BY_NO_QUESTION = 
			"From com.motozone.autobuy.model.ProductQuestionBean a where a.no = :no";
	private static final String SELECT_BY_USERNO_QUSETION = 
			"From com.motozone.autobuy.model.ProductQuestionBean a where a.userNo = :userNo order by a.date desc";
	private static final String SELECT_ALL_BY_USERNO_AND_ID_QUESTION =
			"From com.motozone.autobuy.model.ProductQuestionBean a where a.id = :id and a.userNo = :userNo order by a.date desc";
	private static final String SELECT_ALL_QUESTION = 
			"From com.motozone.autobuy.model.ProductQuestionBean a where a.id = :id order by a.date desc";
	
	@Override
	public boolean insert(ProductQuestionBean bean) {
		
		try {
			factory.getCurrentSession().save(bean);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	
	@Override
	public ProductQuestionBean update(ProductQuestionBean bean) {
		
		try {
			factory.getCurrentSession().save(bean);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return bean;
	}
	
	
	@Override
	public boolean delete(ProductQuestionBean bean) {

		try {
			factory.getCurrentSession().delete(bean);
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	
	@Override
	public ProductQuestionBean selectByNo(Integer no) {
		ProductQuestionBean bean = null;
		try {
			bean = (ProductQuestionBean) factory.getCurrentSession()
											   .createQuery(SELECT_BY_NO_QUESTION)
											   .setParameter("no", no)
											   .getSingleResult();
		} catch (NoResultException e) {
//			e.printStackTrace();
			return null;
		}
		return bean;
		
	}
	@Override
	public List<ProductQuestionBean> selectAllByUser(Integer userNo) {
		List<ProductQuestionBean> list = null;
		try {
			list = factory.getCurrentSession()
				   .createQuery(SELECT_BY_USERNO_QUSETION)
				   .setParameter("userNo", userNo)
				   .getResultList();
		} catch (NoResultException e) {
			e.printStackTrace();
			return null;
		}
		return list;
	}


	@Override
	public List<ProductQuestionBean> selectAllByUserAndId(Integer userNo, Integer id) {
		List<ProductQuestionBean> list = null;
		try {
			list = factory.getCurrentSession()
						  .createQuery(SELECT_ALL_BY_USERNO_AND_ID_QUESTION)
						  .setParameter("id", id)
						  .setParameter("userNo", userNo)
						  .getResultList();
		}catch(NoResultException e) {
			e.printStackTrace();
			return null;
		}
		return list;
	}


	@Override
	public List<ProductQuestionBean> selectAllById(Integer id) {
		List<ProductQuestionBean> list = null;
		
		try {
			list = factory.getCurrentSession()
						  .createQuery(SELECT_ALL_QUESTION)
						  .setParameter("id", id)
						  .getResultList();
		} catch (NoResultException e) {
			e.printStackTrace();
			return null;
		}
		return list;
	}
	
}
