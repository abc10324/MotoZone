package com.motozone.autobuy.model.dao;

import java.util.List;

import javax.persistence.NoResultException;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.motozone.autobuy.model.OrderIdBean;

@Repository
public class OrderIdDAOHibernate implements OrderIdDAO{
	@Autowired
	private SessionFactory factory;
	
	private final String SELECT_BY_OID =
			"From com.motozone.autobuy.model.OrderIdBean a where a.oID = :oID";
	private final String SELECT_BY_UNO = 
			"From com.motozone.autobuy.model.OrderIdBean a where a.uNo = :uNo";
	
	@Override
	public OrderIdBean insert(OrderIdBean bean) {
		try {
			if(bean != null) {
			factory.getCurrentSession().save(bean);
			}
		} catch (Exception e) {
//			e.printStackTrace();
			System.out.println("Insert fail");
			return null;
		}
		return bean;
	}
	
	@Override
	public OrderIdBean update(OrderIdBean bean) {
		try {
			if(bean != null) {
			factory.getCurrentSession().save(bean);
			}
		} catch (Exception e) {
//			e.printStackTrace();
			System.out.println("Update fail");
			return null;
		}
		return bean;
	}
	
	@Override
	public boolean delete(OrderIdBean bean) {
		try {
			if(bean != null) {
				factory.getCurrentSession().delete(bean);
			}
		} catch (Exception e) {
//			e.printStackTrace();
			System.out.println("Delete fail");
			return false;
		}
		return true;
	}
	
	@Override
	public OrderIdBean selectByOID(Integer oID) {
		OrderIdBean result = null;
		try {
			result = (OrderIdBean) factory.getCurrentSession()
											.createQuery(SELECT_BY_OID)
											.setParameter("oID", oID)
											.getSingleResult();
		} catch (NoResultException e) {
//			e.printStackTrace();
			System.out.println("No Result.");
			return null;
		}
		
		return result;
	}
	
	@Override
	public List<OrderIdBean> selectByUno(Integer uNo){
		List<OrderIdBean> list = null;
		try {
			list = factory.getCurrentSession()
								.createQuery(SELECT_BY_UNO)
								.setParameter("uNo", uNo)
								.getResultList();
		}catch(NoResultException e) {
			return null;
		}
		return list;
	}
}
