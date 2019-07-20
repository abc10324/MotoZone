package com.motozone.autobuy.model.dao;

import java.util.List;

import javax.persistence.NoResultException;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.motozone.autobuy.model.ProductCartBean;
import com.motozone.autobuy.model.ProductCartViewBean;

@Repository
public class ProductCartViewDAOHibernate implements ProductCartViewDAO {

	@Autowired
	SessionFactory factory;

	private static final String SELECT_BY_NO = 
			"From com.motozone.autobuy.model.ProductCartViewBean a where a.no = :no";
	private static final String SELECT_BY_ID_UNO = 
			"From com.motozone.autobuy.model.ProductCartViewBean a where a.id = :id and a.userNo = :userNo";
	private static final String SELECT_ALL = 
			"From com.motozone.autobuy.model.ProductCartViewBean a where a.userNo = :userNo order by a.no desc";

	// query by no
	@Override
	public ProductCartViewBean selectByNo(Integer no) {
		ProductCartViewBean bean = null;
		try {
			bean = (ProductCartViewBean) factory.getCurrentSession()
												.createQuery(SELECT_BY_NO)
												.setParameter("no", no)
												.getSingleResult();
		} catch (NoResultException e) {
//			e.printStackTrace();
			return null;
		}
		return bean;
	}

	// query by id
	@Override
	public ProductCartViewBean selectByIdAndUser(Integer id, Integer userNo) {
		ProductCartViewBean bean = null;
		try {
			bean = (ProductCartViewBean) factory.getCurrentSession()
												.createQuery(SELECT_BY_ID_UNO)
												.setParameter("id", id)
												.setParameter("userNo", userNo)
												.getSingleResult();
			
		} catch (NoResultException e) {
//			e.printStackTrace();
			return null;
		}
		return bean;
	}

	// query by category
	@Override
	public List<ProductCartViewBean> selectAll(Integer userNo) {
		List<ProductCartViewBean> list = null;
		try {
			list = factory.getCurrentSession()
						  .createQuery(SELECT_ALL)
						  .setParameter("userNo", userNo)
						  .getResultList();
			
		} catch (NoResultException e) {
//			e.printStackTrace();
			return null;
		}
		return list;
	}
}
