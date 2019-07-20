package com.motozone.article.model;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonFormat;

@Component
@Entity
@Table(name="article")
public class ArticleBean {
	
	private Integer			no;
	private Integer			id;
	private Integer 		userNo = 0; // 0 present GM account
	private String 			author;
	private Timestamp 		date;
	private String 			content;
	
	public ArticleBean() {
		
	}
	
	
	public ArticleBean(Integer id,Integer userNo, String author, Timestamp date, String content) {
		this.id = id;
		this.userNo = userNo;
		this.author = author;
		this.date = date;
		this.content = content;
	}
	
	public ArticleBean(Integer id, String author, Timestamp date, String content) {
		this.id = id;
		this.author = author;
		this.date = date;
		this.content = content;
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer getNo() {
		return no;
	}

	public void setNo(Integer no) {
		this.no = no;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name="uNo")
	public Integer getUserNo() {
		return userNo;
	}
	
	public void setUserNo(Integer userNo) {
		this.userNo = userNo;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	
	@JsonFormat(pattern="yyyy/MM/dd HH:mm")
	public Timestamp getDate() {
		return date;
	}
	public void setDate(Timestamp date) {
		this.date = date;
	}
	public void setDate(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		
		try {
			this.date = new Timestamp(sdf.parse(date).getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
	
}
