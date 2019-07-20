package com.motozone.article.controller;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.motozone.article.model.ArticleBean;
import com.motozone.article.model.ArticleIdBean;
import com.motozone.article.model.ArticleListBean;
import com.motozone.article.model.ArticlePostBean;
import com.motozone.article.model.service.ArticleService;
import com.motozone.article.model.util.ArticleUtils;
import com.motozone.general.model.CategoryBean;
import com.motozone.general.model.service.GeneralService;

@Controller
public class ArticleController {
	@Autowired
	private ArticleService articleService;
	@Autowired
	private GeneralService generalService;
	
	
	@GetMapping("/Categories/{category}")
	public String getCategories(@PathVariable String category,Model model) {
		List<CategoryBean> categoryList = generalService.getSubcategory(category);
		
		// get category and category name
		Map<String,String> map = new HashMap<>();
		for(CategoryBean bean : categoryList) {
			map.put(bean.getCategory(), bean.getCategoryName());
		}
		
		List<ArticleListBean> topicList = articleService.getTopicList(categoryList);
		
		// cut the content
		topicList = ArticleUtils.postListContentExtractor(topicList,false);
		
		// replace category code to real category name
		for(ArticleListBean bean : topicList) {
			bean.setCategory(map.get(bean.getCategory()));
		}
		
		model.addAttribute("categoryList", categoryList);
		model.addAttribute("topicList", topicList);
		
		
		return "articleCategoryPage";
	}
	
	@GetMapping("/PostList/{category}")
	public String getPostList(@PathVariable String category ,Model model) {
		// get category for side icon
		CategoryBean bean = generalService.getCategory(category);
		
		// get post list
		List<ArticleListBean> list = articleService.getPostList(category, 1);
		
		list = ArticleUtils.postListContentExtractor(list,true);
		list = ArticleUtils.postListContentCutter(list, 100);
		
		model.addAttribute("postList", list);
		model.addAttribute("category", bean);
		
		return "postListPage";
	}
	
	@GetMapping(path="/Ajax/PostList/{category}/{page}",
				produces="application/json")
	@ResponseBody
	public List<ArticleListBean> getPostList(@PathVariable String category,@PathVariable Integer page ,Model model) {
		// get post list
		List<ArticleListBean> list = articleService.getPostList(category, page);
		
		// empty check
		if(list.size() == 0) {
			return null;
		}
		
		list = ArticleUtils.postListContentExtractor(list,true);
		list = ArticleUtils.postListContentCutter(list, 100);
		
		return list;
	}
	
	@GetMapping(path="/Ajax/PostList/{id}"
			   ,produces="application/json")
	@ResponseBody
	public ArticleListBean getPostListAjax(@PathVariable Integer id ,Model model) {
		ArticleListBean bean = articleService.getPostList(id);
		
		bean = ArticleUtils.postListContentExtractor(bean,true);
		bean = ArticleUtils.postListContentCutter(bean, 100);
		
		return bean;
	}
	
	@GetMapping("/Posts/{id}")
	public String getPost(@PathVariable String id,Model model) {
		List<ArticlePostBean> list = articleService.getPosts(Integer.valueOf(id),1);
		
		ArticleIdBean bean = articleService.getPostsInfo(Integer.valueOf(id));
		
		String category = bean.getCategory();
		String title = bean.getTitle();
		
		// get category for side icon
		CategoryBean categoryBean = generalService.getCategory(category);
		
		// place at last to prevent lazy commit
		List<ArticleListBean> postList = articleService.getPostList(category, 1);
		postList = ArticleUtils.postListContentExtractor(postList,true);
		
		model.addAttribute("category", categoryBean);
		
		model.addAttribute("postTitle", title);
		model.addAttribute("postCategory", category);
		
		model.addAttribute("postList", postList);
		
		model.addAttribute("posts", list);
		
		return "postListPage";
		
	}
	
	@GetMapping(path="/Ajax/Posts/{id}/{page}"
			   ,produces="application/json")
	@ResponseBody
	public Map<String,Object> getPostAjax(@PathVariable Integer id,@PathVariable Integer page,Model model) {
		List<ArticlePostBean> list = articleService.getPosts(id,page);

		// empty check
		if(list.size() == 0) {
			return null;
		}
		
		String title = list.get(0).getTitle();
		String category = list.get(0).getCategory();
		
		Map<String,Object> result = new HashMap<>();
		result.put("title", title);
		result.put("category", category);
		result.put("post", list);
		
		return result;
	}
	
	@PostMapping("/Post")
	@ResponseBody
	public String addPost(String title,String cat,String userNo,String author,String content,String url,String imgSrc) {
		// empty check
		if("".equals(imgSrc)) {
			imgSrc = null;
		}
		
		// add Post
		Integer id = articleService.getId(title, cat, imgSrc, url);
		boolean result = articleService.addPost(new ArticleBean(id,Integer.valueOf(userNo), author, new Timestamp(System.currentTimeMillis()) , content));
	
		return result ? id.toString() : "";
	}
	
	@PostMapping(path="/Post/{id}",
				 produces="application/json")
	@ResponseBody
	public Map<String,Object> addReply(@PathVariable Integer id,String userNo,String author,String content,String url,String imgSrc) {
		// empty check
		if("".equals(imgSrc)) {
			imgSrc = null;
		}
		
		// add Post
		boolean result = articleService.addPost(new ArticleBean(id,Integer.valueOf(userNo), author, new Timestamp(System.currentTimeMillis()) , content));
		
		// get new post
		List<ArticlePostBean> list = articleService.getAllPosts(id);
		
		String title = list.get(0).getTitle();
		String category = list.get(0).getCategory();
		
		Map<String,Object> resultMap = new HashMap<>();
		resultMap.put("title", title);
		resultMap.put("category", category);
		resultMap.put("post", list);
		
		return result ? resultMap : null;
	}
	
}
