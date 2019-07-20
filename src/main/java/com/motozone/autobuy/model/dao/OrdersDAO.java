package com.motozone.autobuy.model.dao;

import java.util.List;

import com.motozone.autobuy.model.OrdersBean;

public interface OrdersDAO {
	public abstract boolean insert(OrdersBean bean);
	public abstract OrdersBean update(OrdersBean bean);
	public abstract boolean delete(OrdersBean bean);
	public abstract OrdersBean selectByNo(Integer no);
	public abstract List<OrdersBean> selectByOID(Integer oID);
	public abstract List<OrdersBean> selectByPID(Integer pID);
	
	
}
