package com.motozone.general.model.dao;

import com.motozone.general.model.CategoryTranslateBean;

public interface CategoryTranslateDAO {
	public CategoryTranslateBean getCategoryCode(String categoryName,String source);
}
