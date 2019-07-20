package com.motozone.autobuy.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Component
@Entity
@Table(name="product_question")
public class ProductQuestionBean {
	private Integer no;			//流水號
	private Integer userNo;     //會員編號
	private Integer id;  //商品ID
	private String userName;	//會員名稱
	private String content; 	//內容
	private Timestamp date = new Timestamp(System.currentTimeMillis()); //發表時間

	@Override
	public String toString() {
		return "ProductQuestionBean [no=" + no + ", userNo=" + userNo + ", id=" + id + ", userName=" + userName
				+ ", content=" + content + ", date=" + date + "]";
	}
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
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
		
	@Column(name="uName")
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Timestamp getDate() {
		return date;
	}
	public void setDate(Timestamp date) {
		this.date = date;
	} 

	
}
