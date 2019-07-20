package com.motozone.general.model.dao;

import java.util.List;

import com.motozone.general.model.UsersInfoBean;

public interface UsersInfoDAO {
	public abstract UsersInfoBean selectById(String uID);
	public abstract UsersInfoBean selectByNo(Integer uNo);
	public abstract List<UsersInfoBean> getFriendList(Integer userNo);
}
