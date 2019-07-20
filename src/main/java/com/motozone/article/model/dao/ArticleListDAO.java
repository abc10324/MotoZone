package com.motozone.article.model.dao;

import java.util.List;

import com.motozone.article.model.ArticleListBean;
import com.motozone.general.model.CategoryBean;

public interface ArticleListDAO {
	public List<ArticleListBean> getArticleList(String category,Integer pageSize,Integer page); // default desc
	public List<ArticleListBean> getArticleList(String category,Integer pageSize,Integer page,String order);
	public List<ArticleListBean> getTopicList(List<CategoryBean> categories);
	public ArticleListBean getArticleList(Integer id);
}
