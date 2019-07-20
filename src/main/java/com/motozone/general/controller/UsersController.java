package com.motozone.general.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.motozone.general.model.UsersBean;
import com.motozone.general.model.UsersInfoBean;
import com.motozone.general.model.service.GeneralService;
import com.motozone.general.model.service.UsersService;

@Controller
@SessionAttributes(names= {"loginUser"})
public class UsersController {
	@Autowired
	private UsersService usersService;
	@Autowired
	private GeneralService generalService;
	
	@PostMapping(path="/Users",produces="application/json")
	@ResponseBody
	public Map<String,String> regist(@RequestParam(name="user")  String uID,
								     @RequestParam(name="pwd")   String pwd,
								     @RequestParam(name="name")  String uName,
								     @RequestParam(name="email") String email,
								     HttpServletRequest httpServletRequest) {
		// 驗證資料
		Map<String, String> registMsg = new HashMap<String, String>();
		if (uID == null || uID.length() == 0 || !uID.matches("^[a-zA-Z]{1}[a-zA-Z0-9]{7,15}$")) {
			registMsg.put("errorMsg", "帳號格式錯誤");
			System.out.println("Wrong userID format");
			
			registMsg.put("status", "failure");
			return registMsg;
		}
		if (pwd == null || pwd.length() == 0 || !pwd.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])[A-Za-z\\d]{8,15}$")) {
			registMsg.put("errorMsg", "密碼格式錯誤");
			System.out.println("Wrong password format");
			
			registMsg.put("status", "failure");
			return registMsg;
		}
		if (uName == null || uName.length() == 0 || !((uName.matches("^([\\u4E00-\\u9FA5]{2,7})$")
				|| uName.matches("^[A-Z]+\\s[A-Z]+\\s{0,1}[A-Z]+{3,15}$")))) {
			registMsg.put("errorMsg", "姓名格式錯誤");
			System.out.println("Wrong userName format");
			
			registMsg.put("status", "failure");
			return registMsg;
		}
		if (email == null || email.length() == 0 || 
				!(email.matches("^[a-zA-Z]{1}[\\w-]+@[a-z0-9]+\\.[a-z]+$") ||
				  email.matches("^[a-zA-Z]{1}[\\w-]+@[a-z0-9]+\\.[a-z]+\\.[a-z]+$"))) {
			registMsg.put("errorMsg", "Email格式錯誤");
			System.out.println("Wrong email format");
			
			registMsg.put("status", "failure");
			return registMsg;
		}

		if (registMsg != null && registMsg.isEmpty()) {

			// 轉型
			byte[] pwdbyte = pwd.getBytes();
			Byte[] pwdByte = new Byte[pwdbyte.length];
			for (int i = 0; i < pwdByte.length; i++) {
				pwdByte[i] = pwdbyte[i];
			}

			// 設定進入 Bean
			UsersBean bean = new UsersBean();
			bean.setuID(uID);
			bean.setPwd(pwdByte);
			bean.setuName(uName);
			bean.setEmail(email);
			bean.setAuthCode(randomNumber());

			// 呼叫 Service
			if(!usersService.regist(bean)) {
				registMsg.put("errorMsg", "帳號重複");
				registMsg.put("status", "failure");
				return registMsg;
			}

			String path = httpServletRequest.getContextPath();
			String authURL = httpServletRequest.getScheme() + "://" + httpServletRequest.getServerName() + ":"
					+ httpServletRequest.getServerPort() + path + "/Users/" + bean.getuID() + "/" + bean.getAuthCode();
			generalService.sendRegistEmail(bean, authURL);
		}
		return registMsg;
	}

	@PostMapping(path="/Users/{userID}",produces="application/json")
	@ResponseBody
	public UsersInfoBean login(@PathVariable String userID, String pwd,Model model) {
		UsersInfoBean usersLoginBean = null;

		Map<String, String> errors = new HashMap<String, String>();
		if (userID == null || userID.length() == 0) {
			errors.put("uID", "Wrong userID format");
			System.out.println("Wrong userID format");
			return null;
		}
		if (pwd == null || pwd.length() == 0) {
			errors.put("pwd", "Wrong password format");
			System.out.println("Wrong password format");
			return null;
		}

		if (errors != null && errors.isEmpty()) {
			UsersBean usersBean = null;
			usersBean = usersService.login(userID, pwd);
			
			if(usersBean != null && usersBean.getAuthCode() == null) {
				usersLoginBean = usersService.showUserData(usersBean.getuID());
			} else {
				return null;
			}
				
		}
		
		// set parameter into session scope
		model.addAttribute("loginUser", usersLoginBean);
		
		return usersLoginBean;
	}
	
	@GetMapping(path="/Users/{userNo}",produces="application/json")
	@ResponseBody
	public UsersInfoBean getUserInfo(@PathVariable Integer userNo,Model model) {
		UsersInfoBean usersLoginBean = null;

			
		usersLoginBean = usersService.showUserData(userNo);
		
		// set parameter into session scope
		model.addAttribute("userInfo", usersLoginBean);
		
		return usersLoginBean;
	}
	
	@PostMapping(value = "/Users/GoogleLogin", produces = "application/json")
	@ResponseBody
	public UsersInfoBean googleLogin(String idTokenStr,Model model) {
		UsersInfoBean usersLoginBean = usersService.googleLogin(idTokenStr);
		
		if(usersLoginBean != null) {
			model.addAttribute("loginUser", usersLoginBean);
			
			return usersLoginBean;
		} else {
			return null;
		}
		
	}
	
	@DeleteMapping(path="/Users/{userID}")
	@ResponseBody
	public String logout(@PathVariable String userID,SessionStatus sessionStatus) {
		sessionStatus.setComplete();
		
		return "logout";
	}
	
	@GetMapping(path = { "/Users/{uID}/{authCode}" })
	public String authCodeCheck(@PathVariable String uID, @PathVariable String authCode) {
		UsersBean bean = usersService.authCodeCheck(uID, authCode);
		return "indexPage";
	}
	
	@PostMapping(path = "/Friends/{userNo}"
				,produces = "application/json")
	@ResponseBody
	public UsersInfoBean addFriend(@PathVariable Integer userNo, Integer friendNo,Model model) {
		UsersInfoBean bean = usersService.addFriend(userNo, friendNo);
		
		// if add friend success , return new user data with new friend list
		if(bean != null) {
			UsersInfoBean usersLoginBean = usersService.showUserData(userNo);
			model.addAttribute("loginUser", usersLoginBean);
			
			return usersLoginBean;
		} else {
			return null;
		}
	}
	
	@GetMapping(path = { "/Ajax/Users/{uID}/{authCode}" })
	@ResponseBody
	public String authCodeCheckAjax(@PathVariable String uID, @PathVariable String authCode) {
		UsersBean bean = usersService.authCodeCheck(uID, authCode);
		
		if(bean != null) {
			return "success";
		} else {
			return "failure";
		}
	}
	
	@GetMapping("/PDFTest")
	public String getPdfTest(Model model) {
		
		List<Object> beanList = new ArrayList<>();
		beanList.add(usersService.showUserData("abc10324"));
		
//		model.addAttribute("beanList", beanList);
		model.addAttribute("beanList", generalService.getSubcategory("B"));
//		model.addAttribute("fileName","UserData");
		model.addAttribute("fileName","Category");
		
		return "pdfDownload";
	}
	
	@GetMapping("/ExcelTest")
	public String getExcelTest(Model model) {
		
		List<Object> beanList = new ArrayList<>();
		beanList.add(usersService.showUserData("abc10324"));
		
//		model.addAttribute("beanList", beanList);
		model.addAttribute("beanList", generalService.getSubcategory("B"));
//		model.addAttribute("fileName","UserData");
		model.addAttribute("fileName","Category");
		
		return "excelDownload";
	}
	
	private Integer randomNumber() {
		return (int) (Math.random() * 9000) + 1000;
	}
}
