package com.motozone.article.model.util;

import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;

import com.motozone.article.model.ArticleListBean;
import com.motozone.article.model.ArticlePostBean;

public class ArticleUtils {
	
	public static List<ArticleListBean> postListContentExtractor(List<ArticleListBean> list,boolean extraNewLine){
		
		for(ArticleListBean bean : list) {
			postListContentExtractor(bean,extraNewLine);
		}
		
		return list;
	}
	
	public static List<ArticlePostBean> postContentExtractor(List<ArticlePostBean> list){
		
		for(ArticlePostBean bean : list) {
			postContentExtractor(bean);
		}
		
		return list;
	}
	
	public static ArticlePostBean postContentExtractor(ArticlePostBean bean){
	
		// get html DOM tree by each post's content
		Document doc = Jsoup.parse(bean.getContent());
		List<Node> nodeList = doc.body().childNodes();
		
		// get all text node
		int iCount = 0;
		StringBuilder stb = new StringBuilder();
		for(Node node : nodeList) {
			if(node instanceof TextNode) {
				// empty node check
				if(((TextNode) node).text().trim().length() == 0) {
					continue;
				}
				
				stb.append(((TextNode) node).text());
				
				stb.append("   ");
				
				
				iCount++;
			} else if(node.nodeName().equals("p")) {
				
				for(Node childNode : node.childNodes()) {
					if(childNode instanceof TextNode) {
						// empty node check
						if(((TextNode) childNode).text().trim().length() == 0) {
							continue;
						}
						
						stb.append(((TextNode) childNode).text());
						
						stb.append("   ");
						
						iCount++;
					}
				}
				
			} else {
				continue;
			}
			
		}
		
		// if no text node , clean the content
		if(iCount == 0) {
			bean.setContent("");
		} else {
			bean.setContent(stb.toString());
		}
		
		
		return bean;
	}
	
	public static ArticleListBean postListContentExtractor(ArticleListBean bean,boolean extraNewLine){
		
		// get html DOM tree by each post's content
		Document doc = Jsoup.parse(bean.getContent());
		List<Node> nodeList = doc.body().childNodes();
		
		// get all text node
		int iCount = 0;
		StringBuilder stb = new StringBuilder();
		for(Node node : nodeList) {
			if(node instanceof TextNode) {
				// empty node check
				if(((TextNode) node).text().trim().length() == 0) {
					continue;
				}
				
				stb.append(((TextNode) node).text());
				
				stb.append("<br/>");
				
				// for extra new line requirement
				if(extraNewLine) {
					stb.append("<br/>");
				}
				
				iCount++;
			} else if(node.nodeName().equals("p")) {
				
				for(Node childNode : node.childNodes()) {
					if(childNode instanceof TextNode) {
						// empty node check
						if(((TextNode) childNode).text().trim().length() == 0) {
							continue;
						}
						
						stb.append(((TextNode) childNode).text());
						
						stb.append("<br/>");
						
						// for extra new line requirement
						if(extraNewLine) {
							stb.append("<br/>");
						}
						
						iCount++;
					}
				}
				
			} else {
				continue;
			}
			
		}
		
		// if no text node , clean the content
		if(iCount == 0) {
			bean.setContent("");
		} else {
			bean.setContent(stb.toString());
		}
		
		
		return bean;
	}
	
	public static List<ArticleListBean> postListContentCutter(List<ArticleListBean> list,Integer requiredWord){
		for(ArticleListBean bean : list) {
			bean = postListContentCutter(bean,requiredWord);
		}
		
		return list;
	}
	
	public static List<ArticlePostBean> postContentCutter(List<ArticlePostBean> list,Integer requiredWord){
		for(ArticlePostBean bean : list) {
			bean = postContentCutter(bean,requiredWord);
		}
		
		return list;
	}
	
	public static ArticlePostBean postContentCutter(ArticlePostBean bean,Integer requiredWord){
		String content = bean.getContent();
		
		if(content.length() > requiredWord) {
			content = content.substring(0,requiredWord);
		}
		
		bean.setContent(content);
		
		return bean;
	}
	
	public static ArticleListBean postListContentCutter(ArticleListBean bean,Integer requiredWord){
		String content = bean.getContent();
		
		if(content.length() > requiredWord) {
			content = content.substring(0,requiredWord);
		}
		
		bean.setContent(content);
		
		return bean;
	}
}
