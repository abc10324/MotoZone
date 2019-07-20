package com.motozone.autobuy.model.dao;

import java.util.List;

import javax.persistence.NoResultException;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.motozone.autobuy.model.OrdersBean;

@Repository
public class OrdersDAOHibernate implements OrdersDAO{

	@Autowired
	private SessionFactory factory;
	
	private final String SELECT_BY_NO = 
			"From com.motozone.autobuy.model.OrdersBean where no = :no";
	private final String SELECT_BY_OID =
			"From com.motozone.autobuy.model.OrdersBean a where a.orderID = :orderID";
	private final String SELECT_BY_PID =
			"From com.motozone.autobuy.model.OrdersBean a where a.productID = :productID";
	private final String DELETE_BY_NO =
			"delete From com.motozone.autobuy.model.OrdersBean a where a.no = :no";
	
	@Override
	public boolean insert(OrdersBean bean) {
		try {
			if(bean != null) {
			factory.getCurrentSession().save(bean);
			}
		} catch (Exception e) {
//			e.printStackTrace();
			System.out.println("No Result.");
			return false;
		}
		return true;
	}
	
	@Override
	public OrdersBean update(OrdersBean bean) {
		try {
			if(bean != null) {
			factory.getCurrentSession().save(bean);
			}
		} catch (Exception e) {
//			e.printStackTrace();
			System.out.println("No Result.");
			return null;
		}
		return bean;
	}
	
	@Override
	public boolean delete(OrdersBean bean) {
		// empty check
		if(bean == null) {
			return false;
		}
		
		int result = factory.getCurrentSession()
					.createQuery(DELETE_BY_NO)
					.setParameter("no", bean.getNo())
					.executeUpdate();
		
		return result != 0;
	}
	
	@Override
	public OrdersBean selectByNo(Integer no) {
		OrdersBean bean = null;
		try {
			bean = (OrdersBean) factory.getCurrentSession()
					.createQuery(SELECT_BY_NO)
					.setParameter("no", no)
					.getSingleResult();
		} catch (NoResultException e) {
//			e.printStackTrace();
			System.out.println("No Result.");
			return null;
		}
		return bean;
	}
	
	@Override
	public List<OrdersBean> selectByOID(Integer orderID){
		List<OrdersBean> list = null;
		try {
			list = factory.getCurrentSession()
							.createQuery(SELECT_BY_OID)
							.setParameter("orderID", orderID)
							.getResultList();
		} catch (NoResultException e) {
//			e.printStackTrace();
			System.out.println("No Result.");
			return null;
		}
			return list;
		
	}
	
	@Override
	public List<OrdersBean> selectByPID(Integer productID){
		List<OrdersBean> list = null;
		try {
			list = factory.getCurrentSession()
							.createQuery(SELECT_BY_PID)
							.setParameter("productID", productID)
							.getResultList();
		} catch (NoResultException e) {
//			e.printStackTrace();
			System.out.println("No Result.");
			return null;
		}
			return list;
		
	}
}
