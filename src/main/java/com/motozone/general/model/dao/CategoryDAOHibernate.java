package com.motozone.general.model.dao;

import java.util.List;

import javax.persistence.NoResultException;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.motozone.general.model.CategoryBean;
import com.motozone.general.model.CategoryStageBean;

@Repository
public class CategoryDAOHibernate implements CategoryDAO {
	@Autowired
	private SessionFactory factory;
	private String SELECT_BY_CAT = "From com.motozone.general.model.CategoryBean a where a.category = :category";
	private String SELECT_BY_MAIN_CAT = "select a.subCategory from com.motozone.general.model.CategoryStageBean a "
									  + "inner join a.subCategory "
									  + "where a.category = :category";
	private String SELECT_CAT_BY_SUBCAT = "From com.motozone.general.model.CategoryStageBean a where a.subCategory = :subCategory";
	
	@Override
	public CategoryBean getCategory(String category) {
		CategoryBean bean = null;
		
		try {
			bean = (CategoryBean) factory.getCurrentSession()
					.createQuery(SELECT_BY_CAT)
					.setParameter("category", category)
					.getSingleResult();
		} catch (NoResultException e) {
			e.printStackTrace();
			return null;
		}
		
		return bean;
	}
	
	@Override
	public List<CategoryBean> getSubCategory(String category) {
		return factory.getCurrentSession().createQuery(SELECT_BY_MAIN_CAT)
							.setParameter("category", category).getResultList();
	}
	
	@Override
	public CategoryStageBean getCategoryBySubCategory(CategoryBean subCategory) {
		CategoryStageBean result = null;
		try {
			result = (CategoryStageBean) factory.getCurrentSession().createQuery(SELECT_CAT_BY_SUBCAT)
					.setParameter("subCategory", subCategory).getSingleResult();
		} catch (NoResultException e) {
//			e.printStackTrace();
			return null;
		}
		return result;
	}
	
}
