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
@Table(name="product_post")
public class ProductPostBean {
	private Integer id;		//序號
	private Integer userNo;   //賣家會員編號
	private String 	category; //商品類別
	private String 	img;		//商品照片
	private String 	name;	//商品名稱
	private Integer quantity; 	//商品數量
	private Integer price;		//商品價格
	private String 	area;	//交易地區
	private String 	status;	//商品狀態
	private String 	statusDescription;	//狀態說明
	private String 	description;	//商品描述
	private String 	payment;		//付款方式
	private String 	delivery;	//交貨方式
	private String 	shipping;	//寄送方式
	private Integer auctionDays;	//拍賣天數
	private Timestamp date = new Timestamp(System.currentTimeMillis());  //刊登時間
	
	
	@Override
	public String toString() {
		return "ProductPostBean [id=" + id + ", category=" + category + ", img=" + img + ", name=" + name
				+ ", quantity=" + quantity + ", price=" + price + ", area=" + area + ", status=" + status
				+ ", statusDesc=" + statusDescription + ", description=" + description + ", payment=" + payment + ", delivery="
				+ delivery + ", shipping=" + shipping + ", auctionDays=" + auctionDays + "]";
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
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

	@Column(name="cat")
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name="statusDesc")
	public String getStatusDescription() {
		return statusDescription;
	}

	public void setStatusDescription(String statusDescription) {
		this.statusDescription = statusDescription;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPayment() {
		return payment;
	}

	public void setPayment(String payment) {
		this.payment = payment;
	}

	public String getDelivery() {
		return delivery;
	}

	public void setDelivery(String delivery) {
		this.delivery = delivery;
	}

	public String getShipping() {
		return shipping;
	}

	public void setShipping(String shipping) {
		this.shipping = shipping;
	}

	public Integer getAuctionDays() {
		return auctionDays;
	}

	public void setAuctionDays(Integer auctionDays) {
		this.auctionDays = auctionDays;
	}
	
	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}
	

}
