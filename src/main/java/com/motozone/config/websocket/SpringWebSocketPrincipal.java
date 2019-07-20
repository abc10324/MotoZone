package com.motozone.config.websocket;

import java.security.Principal;

public class SpringWebSocketPrincipal implements Principal {
	
	// use as userNo
	private String  name;
	
	public SpringWebSocketPrincipal() {
		this.name = null;
	}
	
	
	public SpringWebSocketPrincipal(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return this.name;
	}


}
