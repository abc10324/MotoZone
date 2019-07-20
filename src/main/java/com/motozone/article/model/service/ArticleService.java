package com.motozone.article.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.motozone.article.model.ArticleBean;
import com.motozone.article.model.ArticleIdBean;
import com.motozone.article.model.ArticleListBean;
import com.motozone.article.model.ArticlePostBean;
import com.motozone.article.model.dao.ArticleDAO;
import com.motozone.article.model.dao.ArticleIdDAO;
import com.motozone.article.model.dao.ArticleListDAO;
import com.motozone.article.model.dao.ArticlePostDAO;
import com.motozone.general.model.CategoryBean;

@Service
@Transactional
public class ArticleService {
	@Autowired
	private ArticleDAO articleDAO;
	@Autowired
	private ArticleIdDAO articleIdDAO;
	@Autowired
	private ArticleListDAO articleListDAO;
	@Autowired
	private ArticlePostDAO articlePostDAO;

	
	private Integer postListPageSize = 15;
	private Integer postPageSize = 5;
	
	public Integer getId(String title,String category,String imgSrc) {
		// initial id
		Integer id = 0;		
		
		// qurey this id until make sure no one use it 
		do {
			id = ((int)(Math.random()*900000) + 100000);
			
			// if id do not exist , insert new id , and break the loop
			if(articleIdDAO.selectById(id) == null) {
				// self website post URL
				String url = "/Posts/" + id.toString();
				
				articleIdDAO.insert(new ArticleIdBean(id,title,category,imgSrc,url));
				break;
			}
		
		} while(true);
			
		return id;
	}
	
	public Integer getId(String title,String category,String imgSrc,String url) {
		// empty check
		if("".equals(url) || url == null) {
			return getId(title,category,imgSrc);
		}
		
		// initial id
		Integer id = 0;
			
		
		// if url already exist, then return its id
		ArticleIdBean bean = articleIdDAO.selectByUrl(url);
		if(bean != null) {
			// if image didn't exist in ArticleIdBean yet , set one to it
			if(bean.getImgSrc() == null && imgSrc !=null) {
				bean.setImgSrc(imgSrc);
				articleIdDAO.updateImgSrc(bean);
			}
			
			return bean.getId();
		}
		
		// else , qurey this id until make sure no one use it 
		do {
			id = ((int)(Math.random()*900000) + 100000);
			
			// if id do not exist , insert new id , and break the loop
			if(articleIdDAO.selectById(id) == null) {
				articleIdDAO.insert(new ArticleIdBean(id,title,category,imgSrc,url));
				break;
			}
		
		} while(true);
			
		return id;
	}
	
	public List<ArticlePostBean> getAllPosts(Integer id){
		List<ArticlePostBean> list = null;
		
		list = articlePostDAO.selectById(id);
		
		return list;
	}
	
	public List<ArticlePostBean> getPosts(Integer id,Integer page){
		List<ArticlePostBean> list = null;
		
		list = articlePostDAO.selectByIdAndPage(id,postPageSize, page);
		articleIdDAO.updateViewsById(id);
		
		return list;
	}
	
	public List<ArticlePostBean> getPosts(Integer userNo){
		List<ArticlePostBean> list = null;
		
		list = articlePostDAO.selectByUserNo(userNo);
		
		return list;
	}
	
	public ArticlePostBean getPost(Integer postNo){
		return articlePostDAO.selectByPostNo(postNo);
	}
	
	public ArticleIdBean getPostsInfo(Integer id){
		return articleIdDAO.selectById(id);
	}
	
	public String getPostsTitle(Integer id){
		return articleIdDAO.selectById(id).getTitle();
	}
	
	public String getPostsCategory(Integer id){
		return articleIdDAO.selectById(id).getCategory();
	}
	
	public List<ArticleListBean> getPostList(String category,Integer page){
		List<ArticleListBean> list = articleListDAO.getArticleList(category,postListPageSize, page);
		
		// shorten content in controller
		
		return list;
	}
	
	public ArticleListBean getPostList(Integer id){
		ArticleListBean bean = articleListDAO.getArticleList(id);
		
		// shorten content in controller
		
		return bean;
	}
	
	public List<ArticleListBean> getTopicList(List<CategoryBean> categories){
		
		return articleListDAO.getTopicList(categories);
	}
	
	
	public boolean addPost(ArticleBean bean) {
		return articleDAO.insert(bean);
	}
	
	public boolean addPosts(List<ArticleBean> list) {
		return articleDAO.insert(list);
	}
	
	public ArticlePostBean updatePost(Integer postNo,String content) {
		ArticleBean bean = articleDAO.selectByUserNo(postNo);
		ArticleBean result = null;
		
		if(bean != null) {
			bean.setContent(content);
			result = articleDAO.update(bean);
		} else {
			return null;
		}
		
		if(result != null) {
			return articlePostDAO.selectByPostNo(postNo);
		} else {
			return null;
		}
		
	}
	
	public boolean removePost(Integer postNo) {
		return articleDAO.delete(postNo);
	}
	
	public ArticleIdBean updateImgSrc(ArticleIdBean bean) {
		return articleIdDAO.updateImgSrc(bean);
	}
	
	
}
