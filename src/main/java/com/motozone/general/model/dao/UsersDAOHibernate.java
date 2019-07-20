package com.motozone.general.model.dao;

import javax.persistence.NoResultException;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.motozone.general.model.UsersBean;

@Repository
public class UsersDAOHibernate implements UsersDAO {

	@Autowired
	private SessionFactory sessionFactory;

	private final String SELECT_BY_UID = "From com.motozone.general.model.UsersBean Users where Users.uID = :uID";
	private final String SELECT_BY_UNO = "From com.motozone.general.model.UsersBean Users where Users.uNo = :uNo";
	
	public UsersDAOHibernate() {

	}

	@Override
	public boolean insert(UsersBean bean) {
		try {
			sessionFactory.getCurrentSession().save(bean);
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public UsersBean selectById(String uID) {
		UsersBean result = null;
		try {
			result = (UsersBean) sessionFactory.getCurrentSession().createQuery(SELECT_BY_UID).setParameter("uID", uID)
					.getSingleResult();
		} catch (NoResultException ex) {
//			ex.printStackTrace();
			return null;
		}
		return result;
	}
	
	
	@Override
	public UsersBean selectByNo(Integer userNo) {
		UsersBean bean = null;
		
		try {
			bean = (UsersBean)sessionFactory
					.getCurrentSession()
					.createQuery(SELECT_BY_UNO)
					.setParameter("uNo", userNo)
					.getSingleResult();
		} catch (NoResultException e) {
//			e.printStackTrace();
			return null;
		}
		
		return bean;
	}
	
	
	@Override
	public UsersBean update(UsersBean bean) {
		try {
			sessionFactory.getCurrentSession().save(bean);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
		return bean;
	}
	

}
