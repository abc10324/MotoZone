package com.motozone.general.model.dao;

import com.motozone.general.model.FriendsBean;

public interface FriendsDAO {
	public FriendsBean insert(FriendsBean bean);
	public FriendsBean selectByEachNo(Integer userNo,Integer friendNo);
}
