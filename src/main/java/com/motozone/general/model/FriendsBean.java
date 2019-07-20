package com.motozone.general.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Component
@Entity
@Table(name="Friends")
public class FriendsBean {
	private Integer No;
	private Integer userNo;
	private Integer friendNo;
	
	public FriendsBean() {
		
	}
	
	public FriendsBean(Integer userNo, Integer friendNo) {
		super();
		this.userNo = userNo;
		this.friendNo = friendNo;
	}
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer getNo() {
		return No;
	}
	public void setNo(Integer no) {
		No = no;
	}
	
	@Column(name="uNo")
	public Integer getUserNo() {
		return userNo;
	}
	public void setUserNo(Integer userNo) {
		this.userNo = userNo;
	}
	
	@Column(name="fNo")
	public Integer getFriendNo() {
		return friendNo;
	}
	public void setFriendNo(Integer friendNo) {
		this.friendNo = friendNo;
	}
	
	

}
