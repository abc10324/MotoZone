package com.motozone.article.model.util;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.motozone.article.model.util.UrlParser;

public class UrlParserTest {
	
	@Test
	public void getM01ForumListTest() {
		UrlParser urlParser = new UrlParser();
		String url = "https://www.mobile01.com/forumlist.php?f=29";
		
		// get url list
		List<String> list = urlParser.getM01ForumList(url);
		
		// print the forum's url list
		
		System.out.println("\ngetM01ForumListTest");
		System.out.println("-------------------------------------------");
		for(String forumUrl : list) {
			System.out.println("url = " + forumUrl);
		}
		
	}
	
	@Test
	public void getM01TopicList() {
		UrlParser urlParser = new UrlParser();
		String url = "https://www.mobile01.com/topiclist.php?f=654";
		
		// get url list
		List<String> list = urlParser.getM01TopicList(url);
		
		// print the topic's url list
		System.out.println("\ngetM01TopicListTest");
		System.out.println("-------------------------------------------");
		for(String topicUrl : list) {
			System.out.println(topicUrl);
		}
		
	}
	
	@Test
	public void getM01ArticleList() {
		UrlParser urlParser = new UrlParser();
		String url = "https://www.mobile01.com/topicdetail.php?f=654&t=1856990";
		
		// get url list
		List<String> list = urlParser.getM01ArticleList(url);
		
		// print the topic's url list
		System.out.println("\ngetM01ArticleListTest");
		System.out.println("-------------------------------------------");
		for(String topicUrl : list) {
			System.out.println(topicUrl);
		}
		
	}
	
//	@Test
//	public void getM01ArticleTotalList() {
//		UrlParser urlParser = new UrlParser();
//		String url = "https://www.mobile01.com/forumlist.php?f=29";
//		
//		// get url list
//		List<String> forumList = urlParser.getM01ForumList(url);
//		
//		// print the topic's url list
//		System.out.println("\ngetM01ArticleTotalTest");
//		System.out.println("-------------------------------------------");
//		
//		for(String forumUrl : forumList) {
//			List<String> topicList = urlParser.getM01TopicList(forumUrl);
//			
//			for(String topicUrl : topicList) {
//				List<String> articleList = urlParser.getM01ArticleList(topicUrl);
//				
//				for(String articleUrl : articleList) {
//					System.out.println(articleUrl);
//				}
//			}
//		}
//		
//	}
	
}
