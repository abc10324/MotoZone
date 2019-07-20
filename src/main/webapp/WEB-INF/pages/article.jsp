<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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
    <!-- article style -->
    <link href="<c:url value="/css/article.css"/>" rel="stylesheet">


    <!-- jQuery library -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <!-- jQuery UI library -->
    <script src="https://code.jquery.com/ui/1.12.0/jquery-ui.min.js"
            integrity="sha256-eGE6blurk5sHj+rmkfsGYeKyZx3M4bG+ZlFyA7Kns7E="
            crossorigin="anonymous"></script>
    <!-- lazyload library -->       
    <script src="https://cdn.jsdelivr.net/npm/lazyload@2.0.0-rc.2/lazyload.js"></script>
	<!-- masonry layout library -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/masonry/4.2.2/masonry.pkgd.min.js"></script>
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
    <!-- article js -->
    <script src="<c:url value="/js/article.js"/>"></script>

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
        <div class="cat">
        	<a href="<c:url value="/Categories/${fn:substring(category.category,0,1)}"/>">
            	<img src="<c:url value="/img/category/${fn:substring(category.category,0,1)}.png"/>" alt="${fn:substring(category.category,0,1)}"/>
        	</a>
        </div>
        <div class="cat">
            <div></div>
            <c:choose>
            	<c:when test="${empty category.imgSrc}">
            		<img src="<c:url value="/img/category/LAN.png"/>" alt="default" />
            	</c:when>
            	<c:otherwise>
            		<img src="<c:url value="${category.imgSrc}"/>" alt="${category.categoryName}"/>
            	</c:otherwise>
            </c:choose>
            
        </div>
    </div>
    <!-- End catgory area -->

    <!-- Strat article area -->
    <article id="postList">
    	<c:forEach var="bean" items="${postList}">
    		<div class="content-area-sizer"></div>
	        <section class="content-area">
	            <span class="content-id">${bean.id}</span>
	
	            <div class="content-img">
	            	<c:choose>
	            		<c:when test="${empty bean.imgSrc}">
	            			<img class="lazyload" data-src="<c:url value="/img/article-default.jpg"/>"/>
	            		</c:when>
	            		<c:otherwise>
	            			<img class="lazyload" data-src="${bean.imgSrc}"/>
	            		</c:otherwise>
	            	</c:choose>
	            </div>
	            <div class="content">
	                <h4><b>${bean.title}</b></h4>
	                <p>${bean.content}</p>
	            </div>
	            <div class="content-info">
	                <span><fmt:formatDate value="${bean.date}" pattern="yyyy/MM/dd"/></span>
	                <div class="view">
	                        <i class="far fa-eye"></i>
	                        <span>${bean.views}</span>
	                </div>
	                <i class="fas fa-ellipsis-h" id="content-opt"></i>
	            </div>
	        </section>
        </c:forEach>

    </article>
    <!-- End article area -->

    <!-- Start article-content area -->
    <%@ include file="include/articleContentArea.jsp" %>
    <!-- End article-content area -->

    
    

    <!-- Start publish area -->

    <!-- publish button -->
    <div id="publish-btn">
        <img src="<c:url value="/img/publish.png"/>" alt="publish"/>
    </div>

    <!-- publish window -->
    <div class="publish-area popup-window hidden-window">
        <i class="fas fa-times close-btn"></i>

        <h2>發佈貼文</h2><br/>
        <form>
            <label for="title">標題：</label>
            <input type="text" name="title" id="title"/><br/>
            <hr/>
            <textarea name="content" id="editor" rows="3"></textarea>
            <script>
                    editor = ClassicEditor
                    .create( document.querySelector( '#editor' ) ,{
                        removePlugins: ['Heading'],
                    	ckfinder: {
// 	                        uploadUrl: "/motozone/Image/" + location.href.substring(location.href.lastIndexOf("/") + 1)
                    		uploadUrl: "/motozone/Imgur"
                    	}
                    })
                    .then( newEditor => {
                        editor = newEditor
                    } )
                    .catch( error => {
                        console.error( error );
                    } );
                    
            </script>
        </form>
        <br/>
        <button id="submit">提交</button>
    </div>
    <!-- End publish area -->



    <!-- login and regist pop up windows (with shadow) -->
   	<%@ include file="include/loginArea.jsp" %>
   	
   	<!-- Start Chat Room Area -->
	<%@ include file="include/chatRoom.jsp" %>
	<!-- End Chat Room Area -->

</body>
</html>