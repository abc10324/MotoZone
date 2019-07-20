<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="zh-tw">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="icon" href="<c:url value="/img/favicon.ico"/>" />
    <title>MotoZone AutoBuy</title>

    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.1/css/all.css" integrity="sha384-50oBUHEmvpQ+1lW4y57PTFmhCaXp0ML5d60M1M7uH2+nqUivzIebhndOJK28anvf" crossorigin="anonymous">
    <!-- Bootstrap -->
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css" rel="stylesheet">
    <!-- main style -->
    <link href="<c:url value="/css/style.css"/>" rel="stylesheet">
    <!-- header style -->
    <link href="<c:url value="/css/header.css"/>" rel="stylesheet">
    <!-- auto buy style -->
    <link href="<c:url value="/css/autobuy.css"/>" rel="stylesheet">


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
    <!-- auto buy js -->
    <script src="<c:url value="/js/autobuy.js"/>"></script>
    
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
    
    <!-- Start shopping cart area -->
    <%@ include file="include/cartArea.jsp" %>
	<!-- End shopping cart area -->
	
	
    <div id="autobuy-content-area">
        <div class="ad-cat-area">
            <div class="ad-cat ad-cat-choose" id="hit-product">熱門商品</div>
            <div class="ad-cat" id="new-product">新品上架</div>
            <div class="ad-cat" id="discount-product">優惠折扣</div>
        </div>

        <div class="ad-content-area">
            <div class="ad-content" id="hit-product-ad">
                <a href="<c:url value="/AutoBuy"/>"><img src="<c:url value="/img/ad/1.jpg"/>"/></a>
                <a href="<c:url value="/AutoBuy"/>"><img src="<c:url value="/img/ad/2.jpg"/>"/></a>
            </div>
            <div class="ad-content" id="new-product-ad">
                <a href="<c:url value="/AutoBuy"/>"><img src="<c:url value="/img/ad/3.jpg"/>"/></a>
                <a href="<c:url value="/AutoBuy"/>"><img src="<c:url value="/img/ad/4.jpg"/>"/></a>
            </div>
            <div class="ad-content" id="discount-product-ad">
                <a href="<c:url value="/AutoBuy"/>"><img src="<c:url value="/img/ad/5.jpg"/>"/></a>
                <a href="<c:url value="/AutoBuy"/>"><img src="<c:url value="/img/ad/6.jpg"/>"/></a>
            </div>
        </div>

        <div class="cat-area">
			<div class="cat-title">分類</div>
			<div class="cat-content-area">
				<c:forEach var="bean" items="${categoryList}">
					<div class="cat-content">
						<div>${bean.categoryName}</div>
						<a href="<c:url value="/ProductCategories/${bean.category}"/>"><img
							src="<c:url value="${bean.imgSrc}"/>" /></a>
					</div>
				</c:forEach>
			</div>
		</div>

    </div>
	
	<!--  Start product publish area -->
    <%@ include file="include/productPublishForm.jsp" %>
    <!--  End product publish area -->
	
   <!-- login and regist pop up windows (with shadow) -->
   	<%@ include file="include/loginArea.jsp" %>
    
    <!-- Start Chat Room Area -->
	<%@ include file="include/chatRoom.jsp" %>
	<!-- End Chat Room Area -->
	
</body>
</html>