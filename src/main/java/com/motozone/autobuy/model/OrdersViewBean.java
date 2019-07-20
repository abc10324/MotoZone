package com.motozone.autobuy.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonFormat;

@Component
@Entity
@Table(name="Orders_view")
public class OrdersViewBean {
	private Integer no; 			// 流水號
	private Integer orderID; 		// 訂單編號
	private Integer productID; 		// 商品編號
	private Integer quantity; 		// 數量
	private String  sellerNo;		//賣家編號
	private String 	productName;	//商品名稱
	private Integer price;			//商品價格
	private Integer total;   		//計算金額
	private String  userName;       //買家名稱
	private Timestamp date = new Timestamp(System.currentTimeMillis()); //購買日期

	
	@Override
	public String toString() {
		return "OrdersViewBean [no=" + no + ", orderID=" + orderID + ", productID=" + productID + ", quantity="
				+ quantity + ", sellerNo=" + sellerNo + ", productName=" + productName + ", price=" + price + ", total="
				+ total + ", userName=" + userName + ", date=" + date + "]";
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

	
	@Column(name="sNo")
	public String getSellerNo() {
		return sellerNo;
	}

	public void setSellerNo(String sellerNo) {
		this.sellerNo = sellerNo;
	}

	@Column(name="name")
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}
	
	@Column(name="uName")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@JsonFormat(pattern="yyyy/MM/dd")
	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	
	


}
