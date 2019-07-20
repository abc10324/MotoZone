<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
    <!-- product category style -->
    <link href="<c:url value="/css/productCategory.css"/>" rel="stylesheet">

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
    <!-- CKEditor library -->
    <script src="https://cdn.ckeditor.com/ckeditor5/12.0.0/classic/ckeditor.js"></script>
    <!-- Off-canvas Menu -->   
    <script src="<c:url value="/js/classie.js"/>"></script>
    <!-- main js -->
    <script src="<c:url value="/js/main.js"/>"></script>
    <!-- auto buy js -->
    <script src="<c:url value="/js/autobuy.js"/>"></script>
    <!-- productCategory js -->
    <script src="<c:url value="/js/productCategory.js"/>"></script>
    
    
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
    
    <!--     Show ad area -->
	<div class="ad-area">
            <div class="ad-block" id="left-ad">
                <a href="<c:url value="/AutoBuy"/>"><img src="<c:url value="/img/ad/c1.jpg"/>"/></a>
                <a href="<c:url value="/AutoBuy"/>"><img src="<c:url value="/img/ad/c2.jpg"/>"/></a>
                <a href="<c:url value="/AutoBuy"/>"><img src="<c:url value="/img/ad/c3.jpg"/>"/></a>
                <a href="<c:url value="/AutoBuy"/>"><img src="<c:url value="/img/ad/c4.jpg"/>"/></a>
                <a href="<c:url value="/AutoBuy"/>"><img src="<c:url value="/img/ad/c5.jpg"/>"/></a>
            </div>
            
            <div class="ad-block" id="right-ad">
                <a target="_blank" href="<c:url value="https://forum.jorsindo.com/thread-2542018-1-1.html"/>"><img src="<c:url value="/img/ad/c6.jpg"/>"/></a>
                <a target="_blank" href="<c:url value="https://forum.jorsindo.com/thread-2542018-1-1.html"/>"><img src="<c:url value="/img/ad/c7.jpg"/>"/></a>
                <a target="_blank" href="<c:url value="https://forum.jorsindo.com/thread-2542376-1-1.html"/>"><img src="<c:url value="/img/ad/c8.jpg"/>"/></a>
                <a target="_blank" href="<c:url value="https://ppt.cc/fXw1Zx"/>"><img src="<c:url value="/img/ad/c9.jpg"/>"/></a>
                <a target="_blank" href="<c:url value="https://autos.udn.com/autos/story/10137/3862613"/>"><img src="<c:url value="/img/ad/c10.jpg"/>"/></a>
            </div>
   	</div>
    
    <!-- Category area -->
	<article id="article-category">
		<div id="category-title">
			<span id="cat-title">
				${categoryBean.categoryName}
			</span>
		</div>
		<div id="cate-ul">
			<ul>
				<c:choose>
					<c:when test="${empty categoryMainClass.category}">
				<!-- null -->
					</c:when>
					<c:otherwise>
						<li class="cate-list">
						<a href="<c:url value="/AutoBuy"/>">${categoryMainClass.categoryName}</a>
						</li>
					</c:otherwise>
				</c:choose>
				<c:choose>
				<c:when test="${categorySubClass.category == 'B'}">
				<li class="cate-list">
					<a href="<c:url value="/AutoBuy"/>">歐都Buy</a>
				</li>
				</c:when>
				<c:otherwise>
				<li class="cate-list">
					<a href="<c:url value="/ProductCategories/${categorySubClass.category}"/>">${categorySubClass.categoryName}</a>
				</li>
				</c:otherwise>
				</c:choose>
				<c:forEach var="bean" items="${categoryList}">
					<li class="cate-list"><a
						href="<c:url value="/ProductCategories/${bean.category}"/>">${bean.categoryName}</a>
					</li>
				</c:forEach>
			</ul>
		</div>

		<div id="item-list1">
			<c:forEach var="bean" items="${productList}">
			<div class="product">
				<div class="item-img">
					<a href="<c:url value="/ProductPost/${bean.id}"/>"> 
					<c:choose>
							<c:when test="${empty bean.img }">
								<img src="<c:url value="/img/upload/defaultProduct.jpg"/>" />
							</c:when>
							<c:otherwise>
								<img src="<c:url value="${bean.img}"/>" />
							</c:otherwise>
						</c:choose>
					</a>
				</div>
					<p class="product-name" title="${bean.name}">${bean.name}</p>
					<p class="product-price">${bean.price}元</p>
				</div>
			</c:forEach>
		</div>
	</article>
	
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