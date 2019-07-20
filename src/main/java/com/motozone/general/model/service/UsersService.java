package com.motozone.general.model.service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.api.client.auth.openidconnect.IdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.motozone.general.model.FriendsBean;
import com.motozone.general.model.UsersBean;
import com.motozone.general.model.UsersInfoBean;
import com.motozone.general.model.dao.FriendsDAO;
import com.motozone.general.model.dao.UsersDAO;
import com.motozone.general.model.dao.UsersInfoDAO;

@Service
@Transactional
public class UsersService {
	@Autowired
	private UsersDAO usersDao;
	@Autowired
	private UsersInfoDAO usersInfoDao;
	@Autowired
	private FriendsDAO friendsDAO;
	
//	private final String GOOGLE_CLIENT_ID = "703647999598-mtjqtb9jrnp6banoqnialqlhbppjc64h.apps.googleusercontent.com";
	private final String GOOGLE_CLIENT_ID = "671760878609-sfs632t6ndmgfk1qlb0htft9tvk67cf1.apps.googleusercontent.com";
	
	public boolean regist(UsersBean bean) {

		// 呼叫 Dao
		UsersBean result = usersDao.selectById(bean.getuID());

		if (result == null) {

			usersDao.insert(bean);
//			System.out.println("ID : \"" + bean.getuID() + "\" is register success" + "\n");
//			System.out.println("Register Information : ");
//			System.out.println("ID : \"" + bean.getuID() + "\"");
//			System.out.println("Password : \"*************\"");
//			System.out.println("Name : \"" + bean.getuName() + "\"");
//			System.out.println("Email : \"" + bean.getEmail() + "\"");
//			System.out.println("Snapshot : \"" + bean.getSnapshot() + "\"");
		} else {
			System.out.println("This ID already exists");
			return false;
		}
		return true;
	}
	
	public UsersBean login(String uID, String pwd) {
		UsersBean usersBean = usersDao.selectById(uID);
		if (usersBean != null) {
			if (pwd != null && pwd.length() != 0) {
				Byte[] pwdByte = usersBean.getPwd();
				byte[] pwdbyte = new byte[pwdByte.length];
				for (int i = 0; i < pwdByte.length; i++) {
					pwdbyte[i] = pwdByte[i].byteValue();
				}

				byte[] pwdinput = pwd.getBytes();

				if (Arrays.equals(pwdbyte, pwdinput)) {
					return usersBean;
				}
			}
		}
		return null;
	}
	
	public UsersInfoBean googleLogin(String idTokenStr) {
		// get verifier
		GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier
				.Builder(new NetHttpTransport(),JacksonFactory.getDefaultInstance())
				.setAudience(Collections.singletonList(GOOGLE_CLIENT_ID))
				.build();
		
		// set id token field
		GoogleIdToken idToken = null;
		
		try {
			idToken = verifier.verify(idTokenStr);
		} catch (GeneralSecurityException e) {
			System.out.println("驗證時出現GeneralSecurityException異常");
			return null;
		} catch (IOException e) {
			System.out.println("驗證時出現IOException異常");
			return null;
		}
		
		if (idToken != null) {
//			System.out.println("驗證成功.");
			
			//取得idToken內資料
			Payload payload = idToken.getPayload();
			String userId = payload.getSubject();
			String email=(String) payload.get("email");
			String name = (String) payload.get("name");
			String pictureUrl = (String) payload.get("picture");
			
			
			// 預設密碼google
			String pwd = "google";
			// 密碼轉型
			byte[] pwdbyte = pwd.getBytes();
			Byte[] pwdByte = new Byte[pwdbyte.length];
			for (int i = 0; i < pwdByte.length; i++) {
				pwdByte[i] = pwdbyte[i];
			}
			
			// examine whether the ID already registed
			UsersBean bean = login(userId, pwd);
			
			if(bean != null) {
				// if already registed , send data back
				UsersInfoBean usersLoginBean = showUserData(bean.getuID());
				
				return usersLoginBean;
				
			}else {
				
				// if haven't exist , regist a new account
				bean = new UsersBean();
				bean.setuID(userId);
				bean.setEmail(email);
				bean.setSnapshot(pictureUrl);
				bean.setuName(name);
				bean.setPwd(pwdByte);
				
				if(regist(bean)) { // regist success
					UsersInfoBean usersLoginBean = showUserData(bean.getuID());
					
					return usersLoginBean;
				} else { // regist fail
					return null;
				}
				
			}
			
		} else {
			System.out.println("Invalid ID token.");
			return null;
		}
	}
	
	public UsersBean authCodeCheck(String uID, String authCode) {
		UsersBean bean = usersDao.selectById(uID);
		String getuID = bean.getuID();
		String getauthCode = bean.getAuthCode().toString();
		if (uID.equals(getuID) && authCode.equals(getauthCode)) {
			bean.setAuthCode(null);
			UsersBean result = usersDao.update(bean);
			System.out.println("update success");
			return result;
		} else {
			System.out.println("update fail");
			return null;
		}
	}

	
	
	public UsersInfoBean showUserData(String uID) {
		UsersInfoBean bean = usersInfoDao.selectById(uID);
		
		if(bean != null) {
			// get friend list
			List<UsersInfoBean> list = usersInfoDao.getFriendList(bean.getuNo());
			
			// set friend list
			bean.setFriendsList(list);
			
			return bean;
		} else {
			return null;
		}
		
	}
	
	public UsersInfoBean showUserData(Integer userNo) {
		UsersInfoBean bean = usersInfoDao.selectByNo(userNo);
		
		if(bean != null) {
			// get friend list
			List<UsersInfoBean> list = usersInfoDao.getFriendList(bean.getuNo());
			
			// set friend list
			bean.setFriendsList(list);
			
			return bean;
		} else {
			return null;
		}
		
	}
	
	public UsersInfoBean addFriend(Integer userNo,Integer friendNo) {
		// check whether this friend exist or not
		if(friendsDAO.selectByEachNo(userNo, friendNo) != null) {
			return null;
		}
		
		FriendsBean result = friendsDAO.insert(new FriendsBean(userNo,friendNo));
		
		if(result != null) {
			return usersInfoDao.selectByNo(friendNo);
		} else {
			return null;
		}
	}
	
	public UsersBean getUserData(Integer userNo) {
		UsersBean result = usersDao.selectByNo(userNo);
		if(result != null) {
//			System.out.println(result);
			return result;
		}
		return null;
	}
}
