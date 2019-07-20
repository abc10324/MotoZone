package com.motozone.general.model.dao;

import com.motozone.general.model.UsersBean;

public interface UsersDAO {
	public boolean insert(UsersBean bean);

	public UsersBean selectById(String uID);
	
	public UsersBean selectByNo(Integer userNo);
	
	public UsersBean update(UsersBean bean);
	
}
