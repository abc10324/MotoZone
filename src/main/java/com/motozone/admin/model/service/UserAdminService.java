package com.motozone.admin.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.motozone.general.model.UsersBean;
import com.motozone.general.model.dao.UsersDAO;

@Service
@Transactional
public class UserAdminService {

	@Autowired
	private UsersDAO usersDao;
	
	public UsersBean showUserData(String uID) {
		UsersBean bean = usersDao.selectById(uID);
		return bean;
	}

	public UsersBean updateUserData(UsersBean bean) {
		UsersBean result = usersDao.selectById(bean.getuID());
		result.setAllContent(bean);
		
		if(result != null) {
			usersDao.update(result);
		}else {
			System.out.println("UID Not Found");
		}
		return result;
	}
}
