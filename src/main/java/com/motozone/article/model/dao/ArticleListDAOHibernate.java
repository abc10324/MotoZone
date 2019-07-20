package com.motozone.article.model.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.motozone.article.model.ArticleListBean;
import com.motozone.general.model.CategoryBean;

@Repository
public class ArticleListDAOHibernate implements ArticleListDAO {
	
	@Autowired
	private SessionFactory factory;
	private final String SELECT_BY_CAT = "From com.motozone.article.model.ArticleListBean a where a.category = :category order by date ";
	private final String SELECT_BY_ID = "From com.motozone.article.model.ArticleListBean a where a.id = :id";
	private final String SELECT_BY_CAT_ORDER_BY_VIEWS = "From com.motozone.article.model.ArticleListBean a where a.category = :category order by views desc";
	
	@Override
	public List<ArticleListBean> getArticleList(String category,Integer pageSize,Integer page) {
		List<ArticleListBean> list = null;
		
		try {
			list = factory.getCurrentSession().createQuery(SELECT_BY_CAT + "desc")
						  .setParameter("category", category)
						  .setFirstResult(pageSize*(page - 1))
						  .setMaxResults(pageSize)
						  .getResultList();
		} catch (NoResultException e) {
//			e.printStackTrace();
			return null;
		}
		
		return list;
	}
	
	@Override
	public List<ArticleListBean> getArticleList(String category,Integer pageSize,Integer page,String order) {
		List<ArticleListBean> list = null;
		
		try {
			list = factory.getCurrentSession().createQuery(SELECT_BY_CAT + order)
						  .setParameter("category", category)
						  .setFirstResult(pageSize*(page - 1))
					  	  .setMaxResults(pageSize)
						  .getResultList();
		} catch (NoResultException e) {
//			e.printStackTrace();
			return null;
		}
		
		return list;
	}
	
	@Override
	public List<ArticleListBean> getTopicList(List<CategoryBean> categories) {
		List<ArticleListBean> list = new ArrayList<>();
		
		try {
			for(CategoryBean bean : categories) {
				list.add((ArticleListBean)(factory.getCurrentSession().createQuery(SELECT_BY_CAT_ORDER_BY_VIEWS)
						  .setParameter("category", bean.getCategory())
						  .setFirstResult(0)
						  .setMaxResults(1)
						  .getResultList().get(0)));
			}
		} catch (NoResultException e) {
//			e.printStackTrace();
			return null;
		}
		
		return list;
	}
	
	@Override
	public ArticleListBean getArticleList(Integer id) {
		ArticleListBean bean = null;
		
		try {
			bean = (ArticleListBean) factory.getCurrentSession()
											.createQuery(SELECT_BY_ID)
											.setParameter("id", id)
											.getSingleResult();
		} catch (NoResultException e) {
//			e.printStackTrace();
			return null;
		}
		
		return bean;
	}


}
