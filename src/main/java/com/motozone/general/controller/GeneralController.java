package com.motozone.general.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.motozone.general.model.WebSocketMessageBean;
import com.motozone.general.model.service.GeneralService;

@Controller
public class GeneralController {
	@Autowired
	private ServletContext servletContext;
	@Resource(name="brokerMessagingTemplate")
	private SimpMessagingTemplate simpMessagingTemplate;
	@Autowired
	private GeneralService generalService;
	@Autowired
	private SimpUserRegistry simpUserRegistry;
	
	
	
//	@GetMapping(path= {"/*","/*/*"})
//	public String handleUnexistPage() {
//		return "indexPage";
//	}
	
	
	@PostMapping(
			path="/Image/{category}",
			produces="application/json"
	)
	@ResponseBody
	public Map<String,String> imageUpload(@RequestPart(name="upload") MultipartFile image,@PathVariable String category) {
		String name = generalService.getImageSrc(category, image.getOriginalFilename());
		String path = servletContext.getRealPath("WEB-INF/static/img/upload/") + name;
		
		// empty check
		if(name == null) {
			Map<String,String> result = new HashMap<>();
			result.put("uploaded", "false");
			return result;
		}
		
		try {
			image.transferTo(new File(path));
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		} 
		
		Map<String,String> result = new HashMap<>();
		result.put("uploaded", "true");
        result.put("url", servletContext.getContextPath() +"/img/upload/" + name);
        
        return result;
	}
	
	@PostMapping(
			path="/Images/{category}",
			produces="application/json"
	)
	@ResponseBody
	public Map<String,Object> imagesUpload(@RequestPart(name="images") MultipartFile[] images,@PathVariable String category) {
		List<String> urlList = new ArrayList<>();
		
		String realPath = servletContext.getRealPath("WEB-INF/static/img/upload/");
		String urlPath = servletContext.getContextPath() +"/img/upload/";
		
		try {
			
			for(MultipartFile image : images) {
				String name = generalService.getImageSrc(category, image.getOriginalFilename());
				String filePath = realPath + name;
				image.transferTo(new File(filePath));
				
				urlList.add(urlPath + name);
			}
			
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		} 
		
		Map<String,Object> result = new HashMap<>();
        result.put("urlList", urlList);
        
        return result;
	}
	
	@PutMapping(path="/Image",produces="application/json")
	@ResponseBody
//	public String imageCrop(String posX,String posY,String posW,String posH,String imgSrc) {
	public Map<String,String> imageCrop(@RequestBody String body) {
		JSONObject jsonObj = new JSONObject(body);
		
		String imgSrc = jsonObj.getString("imgSrc");
		Integer posX = (int) jsonObj.get("posX");
		Integer posY = (int) jsonObj.get("posY");
		Integer posW = (int) jsonObj.get("posW");
		Integer posH = (int) jsonObj.get("posH");
		
		String subImgSrc = generalService.cropImage(imgSrc, posX, posY, posW, posH);
		
		Map<String,String> resultMap = new HashMap<>();
		resultMap.put("imgSrc", subImgSrc);
		
		return resultMap; 
		
	}
	
	@DeleteMapping(
			path="/Images",
			consumes="application/json",
			produces="application/json"
	)
	@ResponseBody
	public Map<String,Object> imagesDelete(@RequestBody Map<String,Object> map) {
		List<String> urlList = new ArrayList<>();
		
		String realFilePath = servletContext.getRealPath("WEB-INF/static/img/upload/");
		
		// get temp image source list
		List<String> imgSrcList = (List) map.get("urlList");
		
		// remove file
		generalService.removeImageSrc(imgSrcList, realFilePath);
		
		Map<String,Object> result = new HashMap<>();
//        result.put("result", urlList);
        result.put("result", "true");
        
        return result;
	}
	
	@PostMapping(
			path="/Imgur",
			produces="application/json"
	)
	@ResponseBody
	public Map<String,String> imgurUpload(@RequestPart(name="upload") MultipartFile image) {
		
		String imgUrl = generalService.uploadToImgur(image);
		
		Map<String,String> result = new HashMap<>();
		
		if(imgUrl != null) {
			result.put("uploaded", "true");
	        result.put("url", imgUrl);
		} else {
			result.put("uploaded", "false");
	        result.put("url", null);
		}
        
        return result;
	}
	
	
	@MessageMapping("/chat")
	public void sendBySimpSingle(@RequestBody WebSocketMessageBean message) throws Exception {
		
		List<String> userList = new ArrayList<String>();
		Map<String,String> userMap = new HashMap<>();
		
		for(SimpUser user : simpUserRegistry.getUsers() ) {
			userList.add(user.getName());
			userMap.put(user.getName(), "online");
		}
		
		
		if("regist".equals(message.getTo()[0])) {
			simpMessagingTemplate.convertAndSend("/regist/messages", userList);
		} else if("logout".equals(message.getTo()[0])) {
			simpMessagingTemplate.convertAndSend("/topic/messages", message.getFrom());
		} else if("broadcast".equals(message.getTo()[0])){
			simpMessagingTemplate.convertAndSend("/topic/messages", message);
		}else {
			
			for(String to : message.getTo()) {
				
				// offline routine message
				if(!userMap.containsKey(to)){
					String replyMsg = "使用者目前離線中";
					if("0".equals(to)) {
						replyMsg = generalService.adminChatBot(message.getMessage());
					}
					
					WebSocketMessageBean msg = new WebSocketMessageBean(to,replyMsg,new String[] {message.getFrom()}) ;
					
					simpMessagingTemplate.convertAndSendToUser(message.getFrom(),"/queue/messages", msg);
				} else {
					simpMessagingTemplate.convertAndSendToUser(to,"/queue/messages", message);
				}
			}
		}
		
	}
	
	
}
