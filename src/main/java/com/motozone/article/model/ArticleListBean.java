package com.motozone.article.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonFormat;

@Component
@Entity
@Table(name="article_list") // this is a view
public class ArticleListBean {
	
	private Integer 	id;
	private String 		title;
	private String 		category;
	private String 		author;
	private Timestamp 	date;
	private String 		content;
	private Integer		views;
	private String 		imgSrc;
	
	
	
	public ArticleListBean() {
	}

	public ArticleListBean(Integer id, String title, String category, String author, Timestamp date, String content,
			Integer views, String imgSrc) {
		this.id = id;
		this.title = title;
		this.category = category;
		this.author = author;
		this.date = date;
		this.content = content;
		this.views = views;
		this.imgSrc = imgSrc;
	}
	@Id
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	@Column(name="cat")
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	
	@JsonFormat(pattern="yyyy/MM/dd")
	public Timestamp getDate() {
		return date;
	}
	public void setDate(Timestamp date) {
		this.date = date;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getViews() {
		return views;
	}
	public void setViews(Integer views) {
		this.views = views;
	}
	
	@Column(name="img")
	public String getImgSrc() {
		return imgSrc;
	}
	public void setImgSrc(String imgSrc) {
		this.imgSrc = imgSrc;
	}
	
	
	
}
