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
    <title>MotoZone User Center</title>

    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.1/css/all.css" integrity="sha384-50oBUHEmvpQ+1lW4y57PTFmhCaXp0ML5d60M1M7uH2+nqUivzIebhndOJK28anvf" crossorigin="anonymous">
    <!-- Bootstrap -->
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css" rel="stylesheet">
	<!-- Jcrop -->
	<link rel="stylesheet" href="https://unpkg.com/jcrop/dist/jcrop.css">
    <!-- main style -->
    <link href="<c:url value="/css/style.css"/>" rel="stylesheet">
    <!-- header style -->
    <link href="<c:url value="/css/header.css"/>" rel="stylesheet">
    <!-- user center style -->
    <link href="<c:url value="/css/userCenter.css"/>" rel="stylesheet">


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
	<!-- Jcrop -->
	<script src="https://unpkg.com/jcrop"></script>
    <!-- main js -->
    <script src="<c:url value="/js/main.js"/>"></script>
    <!-- user center js -->
    <script src="<c:url value="/js/userCenter.js"/>"></script>
    <!-- user address js -->
    <script src="<c:url value="/js/userAddress.js"/>"></script>
    
    <c:if test="${not empty sessionScope.loginUser}">
    	<script type="text/javascript">
	    	// update login user in sessionStorage
			var loginUser = JSON.parse(window.sessionStorage.getItem("loginUser"))
			loginUser.aID = '${sessionScope.loginUser.aID}';
			window.sessionStorage.setItem("loginUser",JSON.stringify(loginUser));
    	</script>
    </c:if>
    
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

    <!-- Start content area -->
    <article>
    <!-- Start User center platform area -->
    <div id="usercenter-title">
        <i class="fas fa-tools"></i>
        <div>管理中心</div>
    </div>

    <div class="control-panel-area">
        
        <div class="icons" id="user-data-area">
            <i class="fas fa-users-cog"></i>
            <div>會員資料設定</div>
        </div>
        <div class="icons" id="article-area">
            <i class="fas fa-file-signature"></i>
            <div>文章管理</div>
        </div>
        <div class="icons" id="tx-area">
            <i class="fas fa-dollar-sign" ></i>
            <div>交易管理</div>
        </div>
        <div class="icons" id="service-area">
            <i class="fas fa-headset"></i>
            <div>客服中心</div>
        </div>

        <div class="icons" id="report-area">
            <i class="fas fa-chart-line"></i>
            <div>網站統計資料</div>
        </div>

    </div>

    <!-- side panel area -->
    <div class="side-control-panel-area">
        
        <div class="icons" id="user-data-icon">
            <i class="fas fa-users-cog"></i>
        </div>
        <div class="icons" id="article-icon">
            <i class="fas fa-file-signature"></i>
        </div>
        <div class="icons" id="tx-icon">
            <i class="fas fa-dollar-sign" ></i>
        </div>
        <div class="icons" id="service-icon">
            <i class="fas fa-headset"></i>
        </div>

        <div class="icons" id="report-icon">
            <i class="fas fa-chart-line"></i>
        </div>

    </div>
    <!-- End User center platform area -->

    </article>
    <!-- End content area -->

    <!-- Start user data operation area -->
    <%@include file="include/userDataOpArea.jsp" %>
    <!-- End user data operation area -->

    <!-- Start article operation area -->
    <%@include file="include/articleOpArea.jsp" %>
    <!-- End article operation area -->

    <!-- Start transaction operation area -->
    <%@include file="include/txOpArea.jsp" %>
    <!-- End transaction operation area -->

    <!-- Start service operation area -->
    <%@include file="include/serviceOpArea.jsp" %>
    <!-- End service operation area -->

    <!-- Start report operation area -->
    <div class="op-area" id="report-op-area">
        <i class="fas fa-times close-btn op-close"></i>
        <h1>REPORT DATAS IN THIS PAGE</h1>
    </div>
    <!-- End report operation area -->


    <!-- login and regist pop up windows (with shadow) -->
   	<%@ include file="include/loginArea.jsp" %>
   	
   	<!-- Start Chat Room Area -->
	<%@ include file="include/chatRoom.jsp" %>
	<!-- End Chat Room Area -->
    
</body>
</html>