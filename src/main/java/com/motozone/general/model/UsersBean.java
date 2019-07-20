package com.motozone.general.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "Users")
public class UsersBean {
	private Integer uNo;
	private String authID = "GM";
	private String uID;
	private Byte[] pwd;
	private String uName;
	private String personID;
	private java.util.Date birth;
	private String phone;
	private String email;
	private String sex;
	private String snapshot;
	private java.sql.Timestamp registTime = new Timestamp(System.currentTimeMillis());
	private String googleID;
	private String facebookID;
	private String lineID;
	private String addr;
	private String companyAddr;
	private Integer transactionCount;
	private Integer formCount;
	private String bankAccount;
	private Integer authCode;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getuNo() {
		return uNo;
	}

	public void setuNo(Integer uNo) {
		this.uNo = uNo;
	}

	@Column(name = "aID")
	public String getAuthID() {
		return authID;
	}

	public void setAuthID(String authID) {
		this.authID = authID;
	}

	public String getuID() {
		return uID;
	}

	public void setuID(String uID) {
		this.uID = uID;
	}

	public Byte[] getPwd() {
		return pwd;
	}

	public void setPwd(Byte[] pwd) {
		this.pwd = pwd;
	}

	public String getuName() {
		return uName;
	}

	public void setuName(String uName) {
		this.uName = uName;
	}

	@Column(name = "pID")
	public String getPersonID() {
		return personID;
	}

	public void setPersonID(String personID) {
		this.personID = personID;
	}

	@JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
	public java.util.Date getBirth() {
		return birth;
	}

	public void setBirth(java.util.Date birth) {
		this.birth = birth;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getSnapshot() {
		return snapshot;
	}

	public void setSnapshot(String snapshot) {
		this.snapshot = snapshot;
	}

	@Column(name = "regTime")
	public java.sql.Timestamp getRegistTime() {
		return registTime;
	}

	public void setRegistTime(java.sql.Timestamp registTime) {
		this.registTime = registTime;
	}

	@Column(name = "G_ID")
	public String getGoogleID() {
		return googleID;
	}

	public void setGoogleID(String googleID) {
		this.googleID = googleID;
	}

	@Column(name = "F_ID")
	public String getFacebookID() {
		return facebookID;
	}

	public void setFacebookID(String facebookID) {
		this.facebookID = facebookID;
	}

	@Column(name = "L_ID")
	public String getLineID() {
		return lineID;
	}

	public void setLineID(String lineID) {
		this.lineID = lineID;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	@Column(name = "cAddr")
	public String getCompanyAddr() {
		return companyAddr;
	}

	public void setCompanyAddr(String companyAddr) {
		this.companyAddr = companyAddr;
	}

	@Column(name = "tCount")
	public Integer getTransactionCount() {
		return transactionCount;
	}

	public void setTransactionCount(Integer transactionCount) {
		this.transactionCount = transactionCount;
	}

	public Integer getFormCount() {
		return formCount;
	}

	public void setFormCount(Integer formCount) {
		this.formCount = formCount;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public Integer getAuthCode() {
		return authCode;
	}

	public void setAuthCode(Integer authCode) {
		this.authCode = authCode;
	}
	
	public void setAllContent(UsersBean bean) {
		setAuthID(bean.getAuthID());
		if(bean.getPwd() != null) {
			setPwd(bean.getPwd());
		}
		if(bean.getuName() != null) {
			setuName(bean.getuName());
		}
		setPersonID(bean.getPersonID());
		setBirth(bean.getBirth());
		setPhone(bean.getPhone());
		if(bean.getEmail() != null) {
			setEmail(bean.getEmail());
		}
		setSex(bean.getSex());
		setSnapshot(bean.getSnapshot());
		if(bean.getRegistTime() != null) {
			setRegistTime(bean.getRegistTime());
		}
		setGoogleID(bean.getGoogleID());
		setFacebookID(bean.getFacebookID());
		setLineID(bean.getLineID());
		setAddr(bean.getAddr());
		setCompanyAddr(bean.getCompanyAddr());
		setTransactionCount(bean.getTransactionCount());
		setFormCount(bean.getFormCount());
		setBankAccount(bean.getBankAccount());
		setAuthCode(bean.getAuthCode());
	}

}
