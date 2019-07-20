package com.motozone.autobuy.model.dao;

import java.util.List;

import com.motozone.autobuy.model.OrdersViewBean;

public interface OrdersViewDAO {
	public List<OrdersViewBean> selectByOrderID(Integer orderID);
	public List<OrdersViewBean> selectByProductID(Integer productID);
}
