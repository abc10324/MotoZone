package com.motozone.autobuy.model.dao;

import java.util.List;

import com.motozone.autobuy.model.ProductQuestionBean;

public interface ProductQuestionDAO {
	public abstract boolean insert(ProductQuestionBean bean);
	public abstract ProductQuestionBean update(ProductQuestionBean bean);
	public abstract boolean delete(ProductQuestionBean bean);
	public abstract ProductQuestionBean selectByNo(Integer no);
	public abstract List<ProductQuestionBean> selectAllByUser(Integer userNo);
	public abstract List<ProductQuestionBean> selectAllByUserAndId(Integer userNo,Integer id);
	public abstract List<ProductQuestionBean> selectAllById(Integer id);
}
