package com.motozone.config.view;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.InternalResourceView;

@Configuration
public class ArticleViewJavaConfig {
	
	
	@Bean
	public View articleCategoryPage() {
		InternalResourceView view = new InternalResourceView();
		view.setUrl("/WEB-INF/pages/category.jsp");
		return view;
	}
	
	@Bean
	public View postListPage() {
		InternalResourceView view = new InternalResourceView();
		view.setUrl("/WEB-INF/pages/article.jsp");
		return view;
	}
	
	
	// AJAX Resource
	
}
