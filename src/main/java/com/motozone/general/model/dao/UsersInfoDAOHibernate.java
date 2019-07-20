package com.motozone.general.model.dao;

import java.util.List;

import javax.persistence.NoResultException;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.motozone.general.model.UsersInfoBean;

@Repository
public class UsersInfoDAOHibernate implements UsersInfoDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	private final String SELECT_BY_UID = "From com.motozone.general.model.UsersInfoBean UsersInfo where UsersInfo.uID = :uID";
	private final String SELECT_BY_UNO = "From com.motozone.general.model.UsersInfoBean UsersInfo where UsersInfo.uNo = :uNo";
	private final String SELECT_FRIENDS_BY_UNO = "select b.* From Friends a"
											   + " inner join UsersInfoView b "
											   + " on a.fNo = b.uNo"
											   + " where a.uNo = :userNo";
	
	
	@Override
	public UsersInfoBean selectById(String uID) {
		UsersInfoBean result = null;
		try {
			result = (UsersInfoBean) sessionFactory.getCurrentSession().createQuery(SELECT_BY_UID)
					.setParameter("uID", uID).getSingleResult();
		} catch (NoResultException e) {
//			 e.printStackTrace();
			return null;
		}
		return result;
	}

	@Override
	public UsersInfoBean selectByNo(Integer uNo) {
		UsersInfoBean result = null;
		try {
			result = (UsersInfoBean) sessionFactory
					.getCurrentSession()
					.createQuery(SELECT_BY_UNO)
					.setParameter("uNo", uNo).getSingleResult();
		} catch (NoResultException e) {
//			 e.printStackTrace();
			return null;
		}
		return result;
	}
	

	@Override
	public List<UsersInfoBean> getFriendList(Integer userNo) {
		List<UsersInfoBean> list = null;
		
		try {
			list = sessionFactory
					.getCurrentSession()
					.createSQLQuery(SELECT_FRIENDS_BY_UNO)
					.setParameter("userNo", userNo)
					.addEntity(UsersInfoBean.class)
					.getResultList();
		} catch (NoResultException e) {
			e.printStackTrace();
			return null;
		}
		
		return list;
	}
}
