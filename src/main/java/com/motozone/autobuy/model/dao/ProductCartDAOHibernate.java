package com.motozone.autobuy.model.dao;

import java.util.List;

import javax.persistence.NoResultException;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.motozone.autobuy.model.ProductCartBean;

@Repository
public class ProductCartDAOHibernate implements ProductCartDAO{
	@Autowired
	private SessionFactory factory;
	
	private static final String SELECT_BY_NO = 
			"From com.motozone.autobuy.model.ProductCartBean a where a.no = :no";
	private static final String SELECT_BY_ID_UNO = 
			"From com.motozone.autobuy.model.ProductCartBean a where a.id = :id and a.userNo = :userNo";
	private static final String SELECT_ALL = 
			"From com.motozone.autobuy.model.ProductCartBean a where a.userNo = :userNo order by a.no desc";
	private static final String CLEAN_TABLE_BY_USER_NO =
			"Delete From com.motozone.autobuy.model.ProductCartBean a where a.userNo = :userNo";

	

	@Override
	public ProductCartBean selectByNo(Integer no) {
		ProductCartBean bean = null;
		try {
			bean = (ProductCartBean) factory.getCurrentSession()
											.createQuery(SELECT_BY_NO)
											.setParameter("no", no)
											.getSingleResult();
			
		} catch (NoResultException e) {
//			e.printStackTrace();
			return null;
		}
		
		return bean;
	}
	
	@Override
	public ProductCartBean selectByIdAndUser(Integer id, Integer userNo) {
		ProductCartBean bean = null;
		try {
			bean = (ProductCartBean) factory.getCurrentSession()
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

	@Override
	public List<ProductCartBean> selectAll(Integer userNo) {
		List<ProductCartBean> list = null;
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
	
	
	@Override
	public boolean insert(ProductCartBean bean) {		
		try {
			factory.getCurrentSession().save(bean);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public ProductCartBean update(ProductCartBean bean) {	
		try {
			factory.getCurrentSession().save(bean);
		} catch (Exception e) {
//			e.printStackTrace();
			return null;
		}
		return bean;
	}

	@Override
	public boolean delete(ProductCartBean bean) {

		try {
			factory.getCurrentSession().delete(bean);
		} catch (Exception e) {
//			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	
	@Override
	public boolean cleanTable(Integer userNo) {
		
		int count = factory.getCurrentSession()
				.createQuery(CLEAN_TABLE_BY_USER_NO)
				.setParameter("userNo", userNo)
				.executeUpdate();
		
		
		if(count > 0) {
//			System.out.println("userNo:" + userNo + "\t訂單清空成功.");
			return true;
		} else {
//			System.out.println("訂單已空.");
			return false;
		}
	}
	
}
