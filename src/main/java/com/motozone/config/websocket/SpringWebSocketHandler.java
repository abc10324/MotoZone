package com.motozone.config.websocket;

import java.security.Principal;
import java.util.Map;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import com.motozone.general.model.UsersInfoBean;

public class SpringWebSocketHandler extends DefaultHandshakeHandler {

	@Override
	protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler,
			Map<String, Object> attributes) {
		
		String userNo = "-1";
		
		
		if(attributes.containsKey("loginUser")) {
			userNo = ((UsersInfoBean) attributes.get("loginUser")).getuNo().toString();
		}
			
		
		return new SpringWebSocketPrincipal(userNo);
	}
	

	
	
}
