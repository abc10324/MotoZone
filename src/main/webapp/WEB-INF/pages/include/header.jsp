<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- Start header area -->
<c:if test="${pageContext.request.requestURI != '/motozone/'}">
    <div id="header"></div>
</c:if>
<!-- End header area -->

<!-- Start icon area -->
<div id="icon">
	<a href="<c:url value="/"/>"> 
    	<img src="<c:url value="/img/logo.png"/>" alt="icon"/>
    </a>
</div>

<c:if test="${not empty AutoBuyArea}">
    <div id="autobuy-icon">
    	<a href="<c:url value="/AutoBuy"/>">
	   		<img src="<c:url value="/img/autobuy.png"/>" alt="icon"/>
	    </a>
	</div>
	
</c:if>

<!-- End icon area -->


<!-- Start login button area -->

<c:choose>
	<c:when test="${empty sessionScope.loginUser}">
		<!-- before log in --> 
		<div class="login-btn"><i class="fa fa-user" ></i><span>Login</span></div>
		
		<!-- after log in -->
		<div class="loggedin-icon disable"><img src="<c:url value="/img/userIcon.png"/>" alt="user icon"/></div>
	</c:when>
	<c:otherwise>
		<!-- before log in --> 
		<div class="login-btn disable"><i class="fa fa-user" ></i><span>Login</span></div>
		
		<!-- after log in -->
		<c:choose>
			<c:when test="${empty sessionScope.loginUser.snapshot}">
				<div class="loggedin-icon"><img src="<c:url value="/img/userIcon.png"/>" alt="user icon"/></div>
			</c:when>
			<c:otherwise>
				<div class="loggedin-icon"><img src="${sessionScope.loginUser.snapshot}" alt="user icon"/></div>
			</c:otherwise>
		</c:choose>
		
	</c:otherwise>
</c:choose>

<div id="loggedin-list">
    <div class="loggedin-list-item" id="user-center-btn">會員中心</div>
    <div class="loggedin-list-item" id="regist-btn">註冊</div>
    <div class="loggedin-list-item" id="logout">登出</div>
</div>
<!-- End login button area -->