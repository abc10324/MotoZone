package com.motozone.general.model.dao;

import java.util.List;

import com.motozone.general.model.CategoryBean;
import com.motozone.general.model.CategoryStageBean;

public interface CategoryDAO {
	public CategoryBean getCategory(String category);
	public List<CategoryBean> getSubCategory(String category);
	public CategoryStageBean getCategoryBySubCategory(CategoryBean subCategory);
}
