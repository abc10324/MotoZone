package com.motozone.general.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Component
@Entity
@Table(name="category_stage")
public class CategoryStageBean {
	private Integer no;
	private String category;
	private CategoryBean subCategory;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer getNo() {
		return no;
	}
	public void setNo(Integer no) {
		this.no = no;
	}
	
	@Column(name="cat")
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="sub_cat")
	public CategoryBean getSubCategory() {
		return subCategory;
	}
	
	public void setSubCategory(CategoryBean subCategory) {
		this.subCategory = subCategory;
	}
	
}
