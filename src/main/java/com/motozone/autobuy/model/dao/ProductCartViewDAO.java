package com.motozone.autobuy.model.dao;

import java.util.List;

import com.motozone.autobuy.model.ProductCartBean;
import com.motozone.autobuy.model.ProductCartViewBean;

public interface ProductCartViewDAO {
	public abstract ProductCartViewBean selectByNo(Integer no);
	public abstract ProductCartViewBean selectByIdAndUser(Integer id,Integer userNo);
	public abstract List<ProductCartViewBean> selectAll(Integer userNo); 
}
