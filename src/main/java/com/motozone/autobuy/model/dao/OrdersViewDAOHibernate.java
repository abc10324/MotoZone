package com.motozone.autobuy.model.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.motozone.autobuy.model.OrdersViewBean;

@Repository
public class OrdersViewDAOHibernate implements OrdersViewDAO {
	@Autowired
	SessionFactory sessionFactory;
	
	private final String SELECT_BY_OID = "From com.motozone.autobuy.model.OrdersViewBean a where a.orderID = :orderID";
	
	private final String SELECT_BY_PID = "From com.motozone.autobuy.model.OrdersViewBean a where a.productID = :productID";
	
	@Override
	public List<OrdersViewBean> selectByOrderID(Integer orderID) {
		List<OrdersViewBean> list = null;
		
		try {
			list = sessionFactory.getCurrentSession()
								 .createQuery(SELECT_BY_OID)
								 .setParameter("orderID", orderID)
								 .getResultList();
		} catch (Exception e) {
//			e.printStackTrace();
			return null;
		}
		
		return list;
	}

	@Override
	public List<OrdersViewBean> selectByProductID(Integer productID) {
		List<OrdersViewBean> list = null;
		
		try {
			list = sessionFactory.getCurrentSession()
								 .createQuery(SELECT_BY_PID)
								 .setParameter("productID", productID)
								 .getResultList();
		} catch (Exception e) {
//			e.printStackTrace();
			return null;
		}
		
		return list;
	}
}
