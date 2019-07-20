<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<!DOCTYPE html>
<html lang="zh-tw">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="icon" href="<c:url value="/img/favicon.ico"/>" />
    <title>MotoZone</title>

    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.1/css/all.css" integrity="sha384-50oBUHEmvpQ+1lW4y57PTFmhCaXp0ML5d60M1M7uH2+nqUivzIebhndOJK28anvf" crossorigin="anonymous">
    <!-- Bootstrap -->
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css" rel="stylesheet">
    <!-- main style -->
    <link href="<c:url value="/css/style.css"/>" rel="stylesheet">
    <!-- header style -->
    <link href="<c:url value="/css/header.css"/>" rel="stylesheet">
    <!-- article content area style -->
    <link href="<c:url value="/css/articleContentArea.css"/>" rel="stylesheet">
    <!-- category style -->
    <link href="<c:url value="/css/category.css"/>" rel="stylesheet">


    <!-- jQuery library -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <!-- jQuery UI library -->
    <script src="https://code.jquery.com/ui/1.12.0/jquery-ui.min.js"
            integrity="sha256-eGE6blurk5sHj+rmkfsGYeKyZx3M4bG+ZlFyA7Kns7E="
            crossorigin="anonymous"></script>
    <!-- Google OAuth2 login library -->
	<script src="https://apis.google.com/js/api:client.js"></script>
    <!-- mustache Template Engine library -->
	<script src="https://cdnjs.cloudflare.com/ajax/libs/mustache.js/3.0.1/mustache.min.js"></script>
    <!-- WebSocket library -->
	<script src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.3.0/sockjs.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.js"></script>
    <!-- Off-canvas Menu -->   
    <script src="<c:url value="/js/classie.js"/>"></script>
    <!-- CKEditor library -->
    <script src="https://cdn.ckeditor.com/ckeditor5/12.0.0/classic/ckeditor.js"></script>
    <!-- main js -->
    <script src="<c:url value="/js/main.js"/>"></script>
    <!-- article content area js -->
    <script src="<c:url value="/js/articleContentArea.js"/>"></script>
    <!-- category js -->
    <script src="<c:url value="/js/category.js"/>"></script>
    
</head>
<body>
    <!-- BEGAIN PRELOADER -->
    <div id="preloader">
            <div id="status">&nbsp;</div>
    </div>
    <!-- END PRELOADER -->

    <!-- Start menu area -->
    <%@ include file="include/menu.jsp" %>
    <!-- End menu area -->

    <!-- Start header area -->
	<%@ include file="include/header.jsp" %>
	<!-- End header area -->

    <!-- Start catgory area -->
    <div id="cat-area">
        <c:forEach var="bean" items="${categoryList}">
			<a href="<c:url value="/PostList/${bean.category}"/>">
		        <div class="cat">
		            <img src="<c:url value="${bean.imgSrc}"/>" alt="${bean.category}"/>
		        </div>
		   	</a>
		</c:forEach>
    </div>
    <!-- End catgory area -->

    <!-- Strat article area -->
    <article>
    	<c:forEach var="bean" items="${topicList}">
    		<section class="content-area">
    			<span class="content-id">${bean.id}</span>
    			
	            <div class="content-img">
	                <c:choose>
	            		<c:when test="${empty bean.imgSrc}">
	            			<img src="<c:url value="/img/article-default.jpg"/>"/>
	            		</c:when>
	            		<c:otherwise>
	            			<img src="${bean.imgSrc}"/>
	            		</c:otherwise>
	            	</c:choose>
	            </div>
	            <div class="content">
	                <h3><b>${bean.category}</b></h3>
	                <h4><b>${bean.title}</b></h4>
	                <p>${bean.content}</p>
	            </div>
        	</section>
    	</c:forEach>

    </article>
    <!-- End article area -->

    <!-- Start article-content area -->
    <%@ include file="include/articleContentArea.jsp" %>
    <!-- End article-content area -->


    <!-- login and regist pop up windows (with shadow) -->
   	<%@ include file="include/loginArea.jsp" %>
   	
   	<!-- Start Chat Room Area -->
	<%@ include file="include/chatRoom.jsp" %>
	<!-- End Chat Room Area -->
    
</body>
</html>