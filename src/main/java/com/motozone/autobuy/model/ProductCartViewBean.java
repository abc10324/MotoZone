package com.motozone.autobuy.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Component
@Entity
@Table(name="autobuy_cart_view")
public class ProductCartViewBean {
	private Integer no; 			//流水號
	private Integer id;				//商品序號	
	private String  category; 		//商品類別
	private String 	name;			//商品名稱
	private String 	description;	//商品描述
	private Integer quantity; 		//商品數量
	private Integer price;			//商品價格
	private String  imgSrc;   		//商品照片
	private Integer userNo;   		//買家會員編號
	private Integer total;   		//計算金額
	private Integer sellerNo;		//賣家帳號
	private String  sellerName;		//賣家名稱
	private Integer stock;			//庫存
	
	
	@Override
	public String toString() {
		return "ProductCartViewBean [no=" + no + ", id=" + id + ", category=" + category + ", name=" + name
				+ ", description=" + description + ", quantity=" + quantity + ", price=" + price + ", imgSrc=" + imgSrc
				+ ", userNo=" + userNo + ", total=" + total + ", sellerNo=" + sellerNo + ", sellerName=" + sellerName
				+ ", stock=" + stock + "]";
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
	
	@Column(name="cat")
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Column(name="qty")
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	
	@Column(name="img")
	public String getImgSrc() {
		return imgSrc;
	}
	public void setImgSrc(String imgSrc) {
		this.imgSrc = imgSrc;
	}

	@Column(name="uNo")
	public Integer getUserNo() {
		return userNo;
	}
	public void setUserNo(Integer userNo) {
		this.userNo = userNo;
	}
	
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}

	@Column(name="sNo")
	public Integer getSellerNo() {
		return sellerNo;
	}

	public void setSellerNo(Integer sellerNo) {
		this.sellerNo = sellerNo;
	}

	@Column(name="sName")
	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}
	
	
	
	
}
