package com.motozone.article.model.dao;

import java.util.List;

import com.motozone.article.model.ArticleBean;
import com.motozone.article.model.ArticleIdBean;

public interface ArticleDAO {
	public boolean insert(ArticleBean bean);		// for single post (user)
	public boolean insert(List<ArticleBean> list);  // for multiple post (crawler)
	public ArticleBean update(ArticleBean bean);
	public boolean delete(Integer postNo);
	public List<ArticleBean> getArticles(Integer id,Integer pageSize,Integer page);
	public ArticleBean selectByUserNo(Integer postNo);
}
