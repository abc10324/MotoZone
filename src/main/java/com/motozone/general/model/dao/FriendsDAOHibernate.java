package com.motozone.general.model.dao;

import javax.persistence.NoResultException;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.motozone.general.model.FriendsBean;

@Repository
public class FriendsDAOHibernate implements FriendsDAO {
	@Autowired
	private SessionFactory sessionFactory;
	private final String SELECT_BY_BOTH_NO = "From com.motozone.general.model.FriendsBean a where a.userNo = :userNo and a.friendNo = :friendNo";
	
	@Override
	public FriendsBean insert(FriendsBean bean) {
		try {
			sessionFactory.getCurrentSession().save(bean);
		} catch (ConstraintViolationException e) {
//			e.printStackTrace();
			return null;
		}
		return bean;
	}

	@Override
	public FriendsBean selectByEachNo(Integer userNo, Integer friendNo) {
		FriendsBean bean = null;
		
		try {
			bean = (FriendsBean) sessionFactory
					.getCurrentSession()
					.createQuery(SELECT_BY_BOTH_NO)
					.setParameter("userNo", userNo)
					.setParameter("friendNo", friendNo)
					.getSingleResult();
		} catch (NoResultException e) {
//			e.printStackTrace();
			return null;
		}
		
		return bean;
	}
	
	

}
