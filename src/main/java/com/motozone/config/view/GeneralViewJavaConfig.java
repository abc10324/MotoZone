package com.motozone.config.view;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.InternalResourceView;
import org.springframework.web.servlet.view.RedirectView;

@Configuration
public class GeneralViewJavaConfig {
	@Bean
	public View userCenterPage() {
		InternalResourceView view = new InternalResourceView();
		view.setUrl("/WEB-INF/pages/userCenter.jsp");
		return view;
	}
	
	@Bean
	public View redirectUserCenterPage() {
		RedirectView view = new RedirectView();
		view.setUrl("/UserCenter");
		view.setContextRelative(true);
		return view;
	}
	
	@Bean
	public View indexPage() {
		RedirectView view = new RedirectView();
		view.setUrl("/");
		view.setContextRelative(true);
		
		return view;
	}
	
	@Bean
	public View pdfDownload() {
		return new GeneralPdfView();
	}
	
	@Bean
	public View excelDownload() {
		return new GeneralExcelView();
	}
}
