package com.motozone.general.model.dao;

import java.util.List;

import com.motozone.general.model.ChatBotBean;

public interface ChatBotDAO {
	public ChatBotBean insert(ChatBotBean bean);
	public ChatBotBean update(ChatBotBean bean);
	public boolean delete(ChatBotBean bean);
	public List<ChatBotBean> selectByUserNo(Integer userNo);
	public ChatBotBean selectByUserAndMsgNo(Integer userNo,String messageNo);
}
