package com.motozone.article.model.dao;

import com.motozone.article.model.ArticleIdBean;

public interface ArticleIdDAO {
	public boolean insert(ArticleIdBean bean);
	public ArticleIdBean selectById(Integer id);
	public ArticleIdBean selectByUrl(String url);
	public ArticleIdBean updateViewsById(Integer id);
	public ArticleIdBean updateImgSrc(ArticleIdBean bean);
}
