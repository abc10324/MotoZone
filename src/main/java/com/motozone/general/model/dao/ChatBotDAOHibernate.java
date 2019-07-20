package com.motozone.general.model.dao;

import java.util.List;

import javax.persistence.NoResultException;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.motozone.general.model.ChatBotBean;


@Repository
public class ChatBotDAOHibernate implements ChatBotDAO {
	@Autowired
	private SessionFactory sessionFactory; 
	
	private final String DELETE_BY_BEAN = "delete from com.motozone.general.model.ChatBotBean a where a.no = :no";
	private final String SELECT_BY_USER = "from com.motozone.general.model.ChatBotBean a where a.userNo = :userNo";
	private final String SELECT_BY_USER_MSGNO = "from com.motozone.general.model.ChatBotBean a where a.userNo = :userNo and a.messageNo = :messageNo";
	
	@Override
	public ChatBotBean insert(ChatBotBean bean) {
		
		try {
			sessionFactory.getCurrentSession().save(bean);
		} catch (HibernateException e) {
//			e.printStackTrace();
			return null;
		}
		
		return bean;
	}

	@Override
	public ChatBotBean update(ChatBotBean bean) {
		try {
			sessionFactory.getCurrentSession().save(bean);
		} catch (HibernateException e) {
			e.printStackTrace();
			return null;
		}
		
		return bean;
	}

	@Override
	public boolean delete(ChatBotBean bean) {
		int result = 0;
		
		result = sessionFactory.getCurrentSession().createQuery(DELETE_BY_BEAN).setParameter("no", bean.getNo()).executeUpdate();
		
		return result > 0;
	}

	@Override
	public List<ChatBotBean> selectByUserNo(Integer userNo) {
		
		return sessionFactory.getCurrentSession()
					  		 .createQuery(SELECT_BY_USER)
					  		 .setParameter("userNo", userNo)
					  		 .getResultList();
	}

	@Override
	public ChatBotBean selectByUserAndMsgNo(Integer userNo, String messageNo) {
		ChatBotBean result = null;
		
		try {
			result = (ChatBotBean) sessionFactory.getCurrentSession()
					 			   .createQuery(SELECT_BY_USER_MSGNO)
								   .setParameter("userNo", userNo)
								   .setParameter("messageNo", messageNo)
								   .getSingleResult();
		} catch (NoResultException e) {
//			e.printStackTrace();
			return null;
		}
		
		return result;
	}

}
