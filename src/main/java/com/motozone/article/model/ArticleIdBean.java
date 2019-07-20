package com.motozone.article.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Component
@Entity
@Table(name="article_id")
public class ArticleIdBean {
	private Integer 		id;
	private String 			title;
	private String			category;
	private String			url;
	private String 			imgSrc = null; // default is no image
	private Integer			views = 0; // views default is 0
	
	
	public ArticleIdBean() {
	}
	
	public ArticleIdBean(Integer id,String title,String category,String imgSrc ,String url) {
		this.id = id;
		this.title = title;
		this.category = category;
		this.imgSrc = imgSrc;
		this.url = url;
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

	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	@Column(name="img")
	public String getImgSrc() {
		return imgSrc;
	}
	public void setImgSrc(String imgSrc) {
		this.imgSrc = imgSrc;
	}
	
	public Integer getViews() {
		return views;
	}
	public void setViews(Integer views) {
		this.views = views;
	}
	
	
}
