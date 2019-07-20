package com.motozone.general.model.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.motozone.general.model.CategoryBean;
import com.motozone.general.model.CategoryStageBean;
import com.motozone.general.model.ChatBotBean;
import com.motozone.general.model.ImageBean;
import com.motozone.general.model.UsersBean;
import com.motozone.general.model.dao.CategoryDAO;
import com.motozone.general.model.dao.CategoryTranslateDAO;
import com.motozone.general.model.dao.ChatBotDAO;
import com.motozone.general.model.dao.ImageDAO;
import com.motozone.general.model.util.MultipartInputStreamFileResource;

@Service
@Transactional
public class GeneralService {
	
	@Autowired
	ImageDAO imageDAO; 
	@Autowired
	CategoryDAO categoryDAO; 
	@Autowired
	private CategoryTranslateDAO categoryTranslateDAO;
	@Autowired
	private ChatBotDAO chatBotDAO;
	@Autowired
	private JavaMailSender mailSender;
	
	private boolean chatBotContentLoading = true;
	private Map<String,String> adminChatBot = new HashMap<>();
	
	private final String IMGUR_UPLOAD_URL = "https://api.imgur.com/3/upload";
	private final String IMGUR_CLIENT_ID = "d3301cc9f7bde12";
	
	public String getImageSrc(String category,String imgName) {
		// empty check
		CategoryBean categoryBean = categoryDAO.getCategory(category);
		
		if(categoryBean == null) {
			return null;
		}
		
		// get current number
		Integer currentNumber = imageDAO.getTotal(categoryBean);
		
		// plus one
		currentNumber++;
		
		// generate new source
		String imgCount = "00000000" + currentNumber.toString();
		imgCount = imgCount.substring(imgCount.length() - 8);
		
		String extension = imgName.substring(imgName.indexOf("."));
		
		String imgSrc = category + imgCount + extension; 
		
		// insert new image path data
		ImageBean bean = new ImageBean();
		bean.setCategory(categoryBean);
		bean.setImgSrc(imgSrc);

		// check whether insert success
		if(imageDAO.insert(bean) == null) {
			return null;
		};
		
		return imgSrc;
	}
	
	public void removeImageSrc(List<String> imgSrcList,String realFilePath) {
		
		/*
		 * get image source from .../.../.../xxx.jpg
		 * to xxx.jpg
		 */
		
		for(String imgSrc : imgSrcList) {
			imgSrc = imgSrc.substring(imgSrc.lastIndexOf("/") + 1);
			
			// delete file at server
			new File(realFilePath + imgSrc).delete();
			
			// delete record at database
			imageDAO.delete(imgSrc);
			
		}
	}
	
	public String uploadToImgur(MultipartFile image) {
		// use REST Template to throw request and response
		RestTemplate template = new RestTemplate();
		
		// prepare for body content
		LinkedMultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
		try {
			body.add("image", new MultipartInputStreamFileResource(image.getInputStream(),image.getOriginalFilename()));
		} catch (IOException e) {
//			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
		// get request entity
		URI uri = URI.create(IMGUR_UPLOAD_URL);
		RequestEntity<LinkedMultiValueMap<String, Object>> req = RequestEntity
							.post(uri)
							.header("Authorization", "Client-ID " + IMGUR_CLIENT_ID)
							.contentType(MediaType.MULTIPART_FORM_DATA)
							.body(body);
		
		// get response entity
		ResponseEntity<Map> res = template.exchange(req,Map.class);
		
		
		// check http status is 200 OK
		if(res.getStatusCodeValue() == 200) {
			
			String imgUrl = ((Map)res.getBody().get("data")).get("link").toString();
			
			return imgUrl;
		} else {
			
			return null;
		}
	}
	
	public String uploadToImgur(InputStream subImage,String imageName) {
		// use REST Template to throw request and response
		RestTemplate template = new RestTemplate();
		
		// prepare for body content
		LinkedMultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
		body.add("image", new MultipartInputStreamFileResource(subImage,imageName));
		
		// get request entity
		URI uri = URI.create(IMGUR_UPLOAD_URL);
		RequestEntity<LinkedMultiValueMap<String, Object>> req = RequestEntity
							.post(uri)
							.header("Authorization", "Client-ID " + IMGUR_CLIENT_ID)
							.contentType(MediaType.MULTIPART_FORM_DATA)
							.body(body);
		
		// get response entity
		ResponseEntity<Map> res = template.exchange(req,Map.class);
		
		
		// check http status is 200 OK
		if(res.getStatusCodeValue() == 200) {
			
			String imgUrl = ((Map)res.getBody().get("data")).get("link").toString();
			
			return imgUrl;
		} else {
			
			return null;
		}
	}
	
	public String cropImage(String imgSrc,Integer posX,Integer posY,Integer posW,Integer posH) {
		
		String subImgSrc = null;
		
		try {
			// get original image
			URL url = new URL(imgSrc);
			
			// get image name
			String imageName = imgSrc.substring(imgSrc.lastIndexOf("/") +1);
			
			BufferedImage img = ImageIO.read(url.openStream());
			
			// get sub image
			BufferedImage subImg = img.getSubimage(posX,posY,posW,posH);

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(subImg,imageName.substring(imageName.lastIndexOf(".") + 1),baos);

			// get inputStream for file uploading
			InputStream is = new ByteArrayInputStream(baos.toByteArray());
			
			subImgSrc = uploadToImgur(is,imageName);
			
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
			
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			
			return null;
		}
		
		return subImgSrc;
	}
	
	public CategoryBean getCategory(String category) {
		return categoryDAO.getCategory(category);
	}
	
	public List<CategoryBean> getSubcategory(String category) {
		return categoryDAO.getSubCategory(category);
	}
	
	public CategoryStageBean getCategoryBySubCategory(CategoryBean subCategory) {
		return categoryDAO.getCategoryBySubCategory(subCategory);
	}
	
	public String getCategoryCode(String categoryName, String source) {
		return categoryTranslateDAO.getCategoryCode(categoryName, source)
				.getCategory().getCategory();
	}
	
	public void sendRegistEmail(UsersBean bean, String authURL) {
		SimpleMailMessage message = new SimpleMailMessage();
		
		// header setting
		message.setTo(bean.getEmail());
		message.setSubject("MotoZone會員 驗證信");
		
		// content setting
		String content = "Dear " 
				+ bean.getuID() 
				+ ", \n\n您的驗證碼為：\n\n" 
				+ bean.getAuthCode()
				+ "\n\n請輸入驗證碼進行確認 完成註冊 .\n\n" 
				+ authURL;
		message.setText(content);
		
		// send email
		mailSender.send(message);
	}
	
	public void sendEmailWithAttachment(UsersBean bean, String authURL) {
		MimeMessage message = mailSender.createMimeMessage();
		
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message,true);
			
			// header setting
			helper.setTo(bean.getEmail());
			helper.setSubject("MotoZone驗證信 with file");
			
			// content setting
			String content = "Dear " 
					+ bean.getuID() 
					+ ", \n\n您的驗證碼為：\n\n" 
					+ bean.getAuthCode()
					+ "\n\n請輸入驗證碼進行確認 完成註冊 .\n\n" 
					+ authURL;
			helper.setText(content);
			
			FileSystemResource file = new FileSystemResource("C:/xxx.jpg");
			helper.addAttachment(file.getFilename(), file);
			
//			String url = "https://cdp.azureedge.net/products-private/prod/0a01f94c-0bc0-4d9b-909d-a3b5446701a5/de5d4be1-1f39-4389-b97b-a64f00dc13f5/00000000-0000-0000-0000-000000000000/94b980af-8a27-4552-817c-a97c00aa98d6/4ce5301c-6b61-4c89-bd76-a9e60004eebd/6000000001.jpg";
			String url = "http://localhost:8080/motozone/img/category/LAN.png";
			UrlResource urlResource = new UrlResource(url);
			helper.addAttachment(urlResource.getFilename(), urlResource);
			
		} catch (MessagingException | MalformedURLException e) {
			e.printStackTrace();
		}
		
		// send email
		mailSender.send(message);
	}
	
	public String adminChatBot(String userMsg) {
		
		if(chatBotContentLoading) {
			// get data from data base
			List<ChatBotBean> list = chatBotDAO.selectByUserNo(0);
			
			// set data into chatbot Map
			StringBuilder stb = new StringBuilder();
			stb.append("客服人員目前離線中，以下提供自動回應的問題集，請輸入特定數字以取得所需回覆：<br/>");
			
			// get menu and set each row data
			for(ChatBotBean bean : list) {
				// combine menu by StringBuilder
				stb.append(bean.getMessageNo() + "." + bean.getMessageTitle() + "<br/>");
				
				String replyMsg = bean.getMessageTitle() + ":<br/>" + bean.getMessage();
				adminChatBot.put(bean.getMessageNo(), replyMsg);
			}
			
			// set menu
			adminChatBot.put("menu", stb.toString());
			
			// loading once when first connect to chat bot
			chatBotContentLoading = false;
			
		}
		
		if(adminChatBot.containsKey(userMsg)) {
			return adminChatBot.get(userMsg);
		} else {
			return adminChatBot.get("menu");
		}
		
	}
}
