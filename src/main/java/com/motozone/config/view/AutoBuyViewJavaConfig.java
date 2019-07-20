package com.motozone.config.view;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.InternalResourceView;
import org.springframework.web.servlet.view.RedirectView;

@Configuration
public class AutoBuyViewJavaConfig {
	
	@Bean
	public View autoBuyPage() {
		InternalResourceView view = new InternalResourceView();
		view.setUrl("/WEB-INF/pages/autoBuy.jsp");
		return view;
	}
	
	@Bean
	public View productPostPage() {
		InternalResourceView view = new InternalResourceView();
		view.setUrl("/WEB-INF/pages/productPost.jsp");
		return view;
	}
	
	@Bean
	public View productCategoryPage() {
		InternalResourceView view = new InternalResourceView();
		view.setUrl("/WEB-INF/pages/productCategory.jsp");
		return view;
	}
	
	@Bean
	public View autoBuyCartPage() {
		InternalResourceView view = new InternalResourceView();
		view.setUrl("/WEB-INF/pages/autoBuyCart.jsp");
		return view;
	}
	
	@Bean
	public View redirectAutoBuyPage() {
		RedirectView view = new RedirectView();
		view.setUrl("/AutoBuy");
		view.setContextRelative(true);
		return view;
	}
}
