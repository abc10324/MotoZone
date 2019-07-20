package com.motozone.autobuy.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonFormat;

@Component
@Entity
@Table(name="order_id")
public class OrderIdBean {
	private Integer oID;    	// 訂單編號
	private Integer uNo;    	// 買家編號
	private String uName; 		//買家名稱
	private Integer total;  	//總金額
	private Timestamp date = new Timestamp(System.currentTimeMillis());  //成立訂單時間
	
	@Override
	public String toString() {
		return "OrderIdBean [oID=" + oID + ", uNo=" + uNo + ", uName=" + uName + ", total=" + total + ", date=" + date
				+ "]";
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer getoID() {
		return oID;
	}
	public void setoID(Integer oID) {
		this.oID = oID;
	}
	public Integer getuNo() {
		return uNo;
	}
	public void setuNo(Integer uNo) {
		this.uNo = uNo;
	}
	public String getuName() {
		return uName;
	}
	public void setuName(String uName) {
		this.uName = uName;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	
	@JsonFormat(pattern="yyyy/MM/dd")
	public Timestamp getDate() {
		return date;
	}
	public void setDate(Timestamp date) {
		this.date = date;
	}
	
	
	
	
}
