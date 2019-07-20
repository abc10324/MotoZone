package com.motozone.autobuy.model.dao;

import java.util.List;

import com.motozone.autobuy.model.OrderIdBean;

public interface OrderIdDAO {
	public abstract OrderIdBean insert(OrderIdBean bean);
	public abstract OrderIdBean update(OrderIdBean bean);
	public abstract boolean delete(OrderIdBean bean);
	public abstract OrderIdBean selectByOID(Integer oID);
	public abstract List<OrderIdBean> selectByUno(Integer uNo);
}
