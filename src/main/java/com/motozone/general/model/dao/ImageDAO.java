package com.motozone.general.model.dao;

import com.motozone.general.model.CategoryBean;
import com.motozone.general.model.ImageBean;

public interface ImageDAO {
	public Integer getTotal(CategoryBean category);
	public ImageBean insert(ImageBean bean);
	public boolean delete(String imgSrc);
}
