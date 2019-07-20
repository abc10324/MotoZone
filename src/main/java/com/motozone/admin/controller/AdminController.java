package com.motozone.admin.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.motozone.admin.model.service.UserAdminService;
import com.motozone.article.model.ArticlePostBean;
import com.motozone.article.model.service.ArticleService;
import com.motozone.article.model.util.ArticleUtils;
import com.motozone.general.model.UsersBean;
import com.motozone.general.model.UsersInfoBean;
import com.motozone.general.model.service.UsersService;

@Controller
@SessionAttributes(names= {"loginUser"})
public class AdminController {

	@Autowired
	private UserAdminService usersAdminService;
	@Autowired
	private UsersService usersService;
	@Autowired
	private ArticleService articleService;
	
	@GetMapping("/UserCenter")
	public String getUserCenter() {
		return "userCenterPage";
	}

	@GetMapping(path = { "/UsersAdmin/{uID}" }, produces = "application/json")
	@ResponseBody
	public UsersBean showUserData(@PathVariable String uID) {
		UsersBean bean = usersAdminService.showUserData(uID);
		return bean;
	}

	@PutMapping(path = { "/Users/{id}"} ,produces = "application/json")
	@ResponseBody
	public UsersBean updateUserData(@RequestBody UsersBean bean,Model model) {
		
		// return new data to show on page
		UsersBean result = usersAdminService.updateUserData(bean);
		
		if(result != null) {
			UsersInfoBean usersLoginBean = usersService.showUserData(bean.getuID());
			
			// update loginUser's parameter in session scope
			model.addAttribute("loginUser", usersLoginBean);
			
			return result;
		} else {
			return null;
		}
		
	}
	
	@GetMapping(path = { "/Authority/{authID}" })
	public String updateUserAuthPM(@SessionAttribute("userDataBean") UsersBean bean,
								   @PathVariable String authID,
								   HttpSession session, 
								   Model model) {
		// return new data to show on page
		UsersBean result = usersAdminService.updateUserData(bean);
		if (result != null) {
			UsersInfoBean usersLoginBean = usersService.showUserData(bean.getuID());
			usersLoginBean.setaID(authID);
			
			// update loginUser's parameter in session scope
			model.addAttribute("loginUser", usersLoginBean);
			session.removeAttribute("userDataBean");
			
			return "redirectUserCenterPage";
		} else {
			return "indexPage";
		}
	}
	
	@GetMapping(path="/ArticlePost/{userNo}",
				produces="application/json")
	@ResponseBody
	public List<ArticlePostBean> showUserPosts(@PathVariable Integer userNo){
		List<ArticlePostBean> list = articleService.getPosts(userNo);
		
		list = ArticleUtils.postContentExtractor(list);
		list = ArticleUtils.postContentCutter(list, 100);
		
		return list;
	}
	
	@GetMapping(path="/ArticlePost/PostNo/{postNo}",
			produces="application/json")
	@ResponseBody
	public ArticlePostBean showUserPost(@PathVariable Integer postNo) {
		return articleService.getPost(postNo);
	}
	
	@PutMapping(path="/Article",
			produces="application/json")
	@ResponseBody
	public ArticlePostBean updateUserPost(@RequestBody String body) {
		JSONObject jsonObj = new JSONObject(body);
		
		Integer postNo = Integer.valueOf(jsonObj.get("postNo").toString());
		String content = jsonObj.get("content").toString();
		
		ArticlePostBean bean = articleService.updatePost(postNo, content);
		
		if(bean != null) {
			bean.setContent(content);
			bean = ArticleUtils.postContentExtractor(bean);
			bean = ArticleUtils.postContentCutter(bean, 100);
		}
		
		return bean;
	}
	
	
	@DeleteMapping(path="/Article/{postNo}",
			   produces="application/json")
	@ResponseBody
	public boolean removeUserPost(@PathVariable Integer postNo) {
		return articleService.removePost(postNo);
	}
}
