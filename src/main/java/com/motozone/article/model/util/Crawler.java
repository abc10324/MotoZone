package com.motozone.article.model.util;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.motozone.article.model.ArticleBean;
import com.motozone.article.model.ArticleIdBean;
import com.motozone.article.model.service.ArticleService;
import com.motozone.general.model.service.GeneralService;

@Service
public class Crawler {
	@Autowired
	private ArticleService articleService;
	@Autowired
	private GeneralService generalService;
	private final String CRAWL_SRC = "M01";
	
	
	public List<ArticleBean> parseFromM01(String articleUrl) {
    	// list for article bean
    	List<ArticleBean> list = null;
    	
    	try {   		
    		// get DOM by input url
			Document doc = Jsoup.connect(articleUrl).get();
			
			// get title in meta tag
			String title = "";
			Elements metaTags = doc.getElementsByTag("meta");
			
			// find the meta tag which have attribute "name" , and value is "title"
			// that tag's content attribute value is what we need
			for(Element metaTag : metaTags) {
				if(metaTag.hasAttr("name")) {
					if(metaTag.attr("name").equals("title")) {
						title = metaTag.attr("content");
					}
					
				}
			}
			
			
			
			//get category from navigation bar
			Elements navElements = doc.getElementsByClass("nav").get(0).getElementsByTag("a");
			Element nav = navElements.get(navElements.size()-1); // get last one
			
			// get category name from site , then get category code by category translate DAO
			String category = generalService.getCategoryCode(nav.text().trim(), CRAWL_SRC);
			
			
			// get id
			Integer id = articleService.getId(title,category,null,articleUrl.substring(0,articleUrl.indexOf("&p=")));
			
			// image field prepare for image update
			String imgSrc = null;
			// for taking first image in each post
			boolean imgFlag = true;
			
			// list for article bean
			list = new ArrayList<ArticleBean>();
			
			// get each post from <article> tag into a list
			Elements article = doc.getElementsByTag("article");
			
			// use for loop to extract each post
			for (Element post : article) {
				// create Bean
				ArticleBean bean = new ArticleBean();
				
				// use StringBuilder to collect content data
				StringBuilder stb = new StringBuilder();
				
				// set id
				bean.setId(id);
				
				
				if (post.getElementsByClass("single-post-author group").size() != 0) {
					// get post author info from class "single-post-author group" in a list
					Element authorInfo = post.getElementsByClass("single-post-author group").get(0).getElementsByClass("inner").get(0);
					// get class "single-post-author group" node from index 0 (should only have one node)
					// this node's first child will be the author info <div> node
					// get author
					String author = authorInfo.getElementsByClass("fn").get(0).getElementsByTag("a").get(0).text();
					bean.setAuthor(author);
					
					// get date
					String date = authorInfo.getElementsByClass("date").get(0).text();
					bean.setDate(date.substring(0,date.indexOf("#")).trim());
					
				}
				
				// get class "single-post-content" node from index 0 (should only have one node)
				// this node's first child will be the content <div> node
				Element content = null;
				if(post.getElementsByClass("single-post-content").get(0).getElementsByTag("div").size() != 0) {
					content = post.getElementsByClass("single-post-content").get(0).child(0);
				} else {
					content = post.getElementsByClass("single-post-content").get(0);
				}
				
				
				// travel all child node in this content node
				for (Node child : content.childNodes()) {
					
					// text node
				    if (child instanceof TextNode) {
				    	stb.append(((TextNode) child).text());
				    } else if(child.nodeName().matches("img")) { // <img> node
				    	stb.append("<img src=\"" + child.attr("data-src") + "\"/>");
				    	
				    	// get first img's tag , prevent emoji from attach2.mobile01.com/images/smile/
				    	if(imgFlag && !child.attr("data-src").matches("^.*attach2.*$")) {
				    		imgSrc = child.attr("data-src");
				    		imgFlag = false;
				    	}
				    	
				    } else if(child.nodeName().matches("br")) { // <br/> node
				    	stb.append("<br/>");
				    }
				}
				
				// set content
				bean.setContent(stb.toString());

				// add bean into list
				list.add(bean);
			} 
			
			// set image to ArticleIdBean if image is null
	    	ArticleIdBean articleIdBean = articleService.getPostsInfo(id);
	    	if(articleIdBean.getImgSrc() == null && imgSrc != null) {
	    		articleIdBean.setImgSrc(imgSrc);
	    		articleService.updateImgSrc(articleIdBean);
	    	}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	
    	
    	return list;
    }
}
