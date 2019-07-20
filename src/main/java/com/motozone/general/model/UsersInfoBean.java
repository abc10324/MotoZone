package com.motozone.general.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "UsersInfoView")
public class UsersInfoBean {

	private Integer 		uNo;
	private String			aID;
	private String 			uID;
	private String 			uName;
	private String 			snapshot;
	private List<UsersInfoBean> friendsList;

	@Id
	public Integer getuNo() {
		return uNo;
	}

	public void setuNo(Integer uNo) {
		this.uNo = uNo;
	}
	
	public String getaID() {
		return aID;
	}

	public void setaID(String aID) {
		this.aID = aID;
	}

	public String getuID() {
		return uID;
	}

	public void setuID(String uID) {
		this.uID = uID;
	}

	public String getuName() {
		return uName;
	}

	public void setuName(String uName) {
		this.uName = uName;
	}

	public String getSnapshot() {
		return snapshot;
	}

	public void setSnapshot(String snapshot) {
		this.snapshot = snapshot;
	}

	@Transient
	public List<UsersInfoBean> getFriendsList() {
		return friendsList;
	}

	public void setFriendsList(List<UsersInfoBean> friendsList) {
		this.friendsList = friendsList;
	}
	
	

}
