package com.motozone.article.model.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class UrlParser {
	private final String MOBILE01_URL = "https://www.mobile01.com/";
	
	public List<String> getM01ForumList(String forumListPageUrl){
		// initialize List
		List<String> list = new ArrayList<>();
		
		try {
			// get DOM
			Document doc = Jsoup.connect(forumListPageUrl).get();
			
			
			/*
			 * get forum list
			 *     <div class="tablelist"> -->
			 * --> <table> -->
			 * --> <tbody>
			 * 
			 */
			Element tableList = doc.getElementsByClass("tablelist").get(0).child(0).child(1);
			
			
			// get each forum's url
			for(Element tr : tableList.children()) {
				/*
				 * get each url in the list
				 *     <tbody> -->
				 * --> <tr> -->
				 * --> <td> -->
				 * --> <span> -->
				 * --> <a>[1] -->
				 * --> href Attribute in <a>
				 * 
				 */
				String href = tr.child(0).child(0).child(1).attr("href");
				list.add(MOBILE01_URL + href);
			}
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		
		return list;
	}
	
	/*
	 * invalid page will return null
	 * 
	 */
	
	public List<String> getM01TopicList(String topicListPageUrl){
		// initialize List
		List<String> list = new ArrayList<>();
		
		try {
			int page = 1;
			
			do {
				// set page
				String url = topicListPageUrl + "&p=" + page;
				// get DOM
				Document doc = Jsoup.connect(url).get();
				
				// check is this page valid , for one page case (yoda condition)
				if("".equals(doc.getElementsByClass("pagination").get(1).getElementsByClass("disable").text())) {
					// get topic url list
					/*
					 * get topic list
					 *     <div class="tablelist"> -->
					 * --> <table> -->
					 * --> <tbody>
					 * 
					 */
					Element tableList = doc.getElementsByClass("tablelist").get(0).child(0).child(1);
					
					// get each topic's url
					for(Element tr : tableList.children()) {
						/*
						 * get each url in the list
						 *     <tbody> -->
						 * --> <tr> -->
						 * --> <td> -->
						 * --> <span> -->
						 * --> <a>[1] -->
						 * --> href Attribute in <a>
						 * 
						 */
						String href = tr.child(0).child(0).child(1).attr("href");
						
						// prevent empty href condition
						if(!"".equals(href)) {
							list.add(MOBILE01_URL + href);
						}
						
					}
					
					// break the loop , because only have one page
					break;
				}
				
				// check is this page valid , for multiple page case
				/*
				 * get real page
				 *     <div class="pagination"> -->
				 * --> get(1) -->
				 * --> <span class="disable"> -->
				 * --> text node
				 * 
				 */
				int realPage = Integer.parseInt(doc.getElementsByClass("pagination").get(1).getElementsByClass("disable").text());
				int urlPage = Integer.parseInt(url.substring(url.indexOf("&p=") + 3));
				
				// if page is invalid , break the loop
				if(urlPage != realPage) {
					break;
				} else {
					// get topic url list
					/*
					 * get topic list
					 *     <div class="tablelist"> -->
					 * --> <table> -->
					 * --> <tbody>
					 * 
					 */
					Element tableList = doc.getElementsByClass("tablelist").get(0).child(0).child(1);
					
					// get each topic's url
					for(Element tr : tableList.children()) {
						/*
						 * get each url in the list
						 *     <tbody> -->
						 * --> <tr> -->
						 * --> <td> -->
						 * --> <span> -->
						 * --> <a>[1] -->
						 * --> href Attribute in <a>
						 * 
						 */
						String href = tr.child(0).child(0).child(1).attr("href");
						// prevent empty href condition
						if(!"".equals(href)) {
							list.add(MOBILE01_URL + href);
						}
					}
					
					// to next page
					page++;
				}
				
			} while(true);
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		
		return list;
	}
	
	public List<String> getM01ArticleList(String articlePageUrl){
		// initialize List
		List<String> list = new ArrayList<>();
		
		try {
			int page = 1;
			
			do {
				// set page
				String url = articlePageUrl + "&p=" + page;
				
				// get DOM
				Document doc = Jsoup.connect(url).get();
				
				
				// check is this page valid , for one page case (yoda condition)
				if("".equals(doc.getElementsByClass("pagination").get(1).getElementsByClass("disable").text())) {
					list.add(url);
					break;
				}
				
				
				// check is this page valid , for multiple page case
				/*
				 * get real page
				 *     <div class="pagination"> -->
				 * --> get(1) -->
				 * --> <span class="disable"> -->
				 * --> text node
				 * 
				 */
				int realPage = Integer.parseInt(doc.getElementsByClass("pagination").get(1).getElementsByClass("disable").text());
				int urlPage = Integer.parseInt(url.substring(url.indexOf("&p=") + 3));
				
				// if page is invalid , then break the loop
				if(urlPage != realPage) {
					break;
				} else {
					list.add(url);
					page++;
				}
			} while(true);
			
		} catch (IOException e) {
			System.out.println(articlePageUrl);
			e.printStackTrace();
		}
			
		
		return list;
	}
}
