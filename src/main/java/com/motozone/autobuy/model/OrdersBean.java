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
@Table(name="orders")
public class OrdersBean {
	private Integer no; 	// 流水號
	private Integer orderID; 	// 訂單編號
	private Integer productID; 	// 商品編號
	private Integer quantity; 	// 數量

	

	@Override
	public String toString() {
		return "OrdersBean [no=" + no + ", orderID=" + orderID + ", productID=" + productID + ", quantity=" + quantity
				+ "]";
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="No")
	public Integer getNo() {
		return no;
	}

	public void setNo(Integer no) {
		this.no = no;
	}

	@Column(name="oID")
	public Integer getOrderID() {
		return orderID;
	}

	public void setOrderID(Integer orderID) {
		this.orderID = orderID;
	}
	
	@Column(name="pID")
	public Integer getProductID() {
		return productID;
	}

	public void setProductID(Integer productID) {
		this.productID = productID;
	}

	@Column(name="qty")
	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}



}
