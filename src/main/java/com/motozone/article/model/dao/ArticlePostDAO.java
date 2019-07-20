package com.motozone.article.model.dao;

import java.util.List;

import com.motozone.article.model.ArticlePostBean;

public interface ArticlePostDAO {
	public List<ArticlePostBean> selectByIdAndPage(Integer id,Integer pageSize,Integer page);
	public List<ArticlePostBean> selectByUserNo(Integer userNo);
	public List<ArticlePostBean> selectById(Integer id);
	public ArticlePostBean selectByPostNo(Integer postNo);
}
