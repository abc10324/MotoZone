package com.motozone.autobuy.model.dao;


import java.util.List;

import javax.persistence.NoResultException;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.motozone.autobuy.model.ProductPostBean;

@Repository
public class ProductPostDAOHibernate implements ProductPostDAO {
	@Autowired
	private SessionFactory factory;

	private static final String SELECT_BY_ID = 
			"From com.motozone.autobuy.model.ProductPostBean a where a.id = :id";
	private static final String SELECT_BY_CAT =
			"From com.motozone.autobuy.model.ProductPostBean a where a.category like :category and a.quantity > 0 order by a.id desc";
	private static final String SELECT_BY_EXPLICIT_CAT =
			"From com.motozone.autobuy.model.ProductPostBean a where a.category = :category order by a.id desc";
	private static final String SELECT_BY_UNO = 
			"From com.motozone.autobuy.model.ProductPostBean a where a.userNo = :userNo";
	
	
	//insert
	@Override
	public boolean insert(ProductPostBean bean){
		try {
			factory.getCurrentSession().save(bean);
		}catch(Exception e) {
//			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	//update
	@Override
	public ProductPostBean update(ProductPostBean bean){
		
		try {	
			factory.getCurrentSession().save(bean);
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		return bean;
	}
	
	
	//query
	@Override
	public  ProductPostBean selectById(Integer id){
		ProductPostBean bean = null;
		try {
			bean = (ProductPostBean) factory.getCurrentSession()
											.createQuery(SELECT_BY_ID)
											.setParameter("id", id)
											.getSingleResult();
		} catch (NoResultException e) {
//			e.printStackTrace();
			return null;
		}
		
		return bean;

	}
	
	//query By Category
	@Override
	public  List<ProductPostBean> selectByCategory(String category){
		List<ProductPostBean> list = null;
		try {
			list = factory.getCurrentSession()
						.createQuery(SELECT_BY_CAT)
						.setParameter("category", category + "%")
						.getResultList();
			
		} catch (NoResultException e) {
//			e.printStackTrace();
			return null;
		}	
		return list;
	}

	@Override
	public List<ProductPostBean> selectByExplicitCategory(String explicitCategory) {
		List<ProductPostBean> list = null;
		try {
			list = factory.getCurrentSession()
						.createQuery(SELECT_BY_EXPLICIT_CAT)
						.setParameter("category", explicitCategory)
						.getResultList();
			
		} catch (NoResultException e) {
//			e.printStackTrace();
			return null;
		}	
		return list;
	}

	@Override
	public List<ProductPostBean> selectByUserNo(Integer userNo) {
		List<ProductPostBean> list = null;
		try {
			list = factory.getCurrentSession()
						.createQuery(SELECT_BY_UNO)
						.setParameter("userNo", userNo)
						.getResultList();
			
		} catch (NoResultException e) {
//			e.printStackTrace();
			return null;
		}	
		return list;
	}

	
}
