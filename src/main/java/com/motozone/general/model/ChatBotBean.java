package com.motozone.general.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Entity
@Table(name = "chatbot")
@Component
public class ChatBotBean {
	private Integer no;
	private Integer userNo;
	private String	messageNo;
	private String	messageTitle;
	private String	message;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer getNo() {
		return no;
	}
	public void setNo(Integer no) {
		this.no = no;
	}
	
	@Column(name="uNo")
	public Integer getUserNo() {
		return userNo;
	}
	public void setUserNo(Integer userNo) {
		this.userNo = userNo;
	}
	
	@Column(name="msgNo")
	public String getMessageNo() {
		return messageNo;
	}
	public void setMessageNo(String messageNo) {
		this.messageNo = messageNo;
	}
	
	@Column(name="msgTitle")
	public String getMessageTitle() {
		return messageTitle;
	}
	public void setMessageTitle(String messageTitle) {
		this.messageTitle = messageTitle;
	}
	
	@Column(name="msg")
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}
