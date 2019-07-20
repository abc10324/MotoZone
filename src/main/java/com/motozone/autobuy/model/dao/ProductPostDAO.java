package com.motozone.autobuy.model.dao;

import java.util.List;

import com.motozone.autobuy.model.ProductPostBean;

public interface ProductPostDAO {
	public abstract boolean insert(ProductPostBean bean);
	public abstract ProductPostBean update(ProductPostBean bean);
	public abstract ProductPostBean selectById(Integer id);
	public abstract List<ProductPostBean> selectByUserNo(Integer userNo);
	public abstract List<ProductPostBean> selectByCategory(String category);
	public abstract List<ProductPostBean> selectByExplicitCategory(String explicitCategory);
}
