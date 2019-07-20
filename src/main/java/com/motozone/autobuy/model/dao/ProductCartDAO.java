package com.motozone.autobuy.model.dao;

import java.util.List;

import com.motozone.autobuy.model.ProductCartBean;


public interface ProductCartDAO{
	public abstract ProductCartBean selectByNo(Integer no);
	public abstract ProductCartBean selectByIdAndUser(Integer id,Integer userNo);
	public abstract List<ProductCartBean> selectAll(Integer userNo);
	public abstract boolean insert(ProductCartBean bean);
	public abstract ProductCartBean update(ProductCartBean bean);
	public abstract boolean delete(ProductCartBean bean);
	public abstract boolean cleanTable(Integer userNo);
	
}
