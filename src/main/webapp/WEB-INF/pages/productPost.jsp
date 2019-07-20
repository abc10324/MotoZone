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
    <!-- product post style -->
	<link href="<c:url value="/css/productPost.css"/>" rel="stylesheet">
	
	
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
	<!-- img-zoom js -->
	<script src="<c:url value="/js/zoom-master/jquery.zoom.js"/>"></script>
    <script src="<c:url value="/js/productPost.js"/>"></script>
    
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



	<!-- Return button-->
	<div id="return-btn" title="return">
		<a href="<c:url value="/ProductCategories/${category.category}"/>"><img
			src="<c:url value="/img/return.png"/>" /></a>
	</div>
	<!-- End area -->

	<!-- Strat article area -->
	<div class="pages" id="item-info">
		<article class="arti">
			<!--menu-->
			<div id="trade-menu">
				<ul>
					<li class="info">拍賣商品資訊</li>
					<li class="record">購買紀錄</li>
					<li class="question">商品問題詢問</li>
				</ul>
			</div>
			<div class="h-line"></div>

			<!--itemblock-->

			<div class="item-block">
				
				<div class="item-title">
					<span id="product_name" title="${productBean.name}">${productBean.name}</span>
				</div>
				<div class="item-img">
					<c:choose>
						<c:when test="${empty productBean.img}">
							<img class="zoom-img" src="<c:url value="/img/upload/defaultProduct.jpg"/>">
						</c:when>
						<c:otherwise>
							<img class="zoom-img" src="<c:url value="${productBean.img}"/>">
						</c:otherwise>
					</c:choose>
				</div>
				<div class="item-status">
					<div class="status-width">
						<b>商品狀態</b>
						<ul>
							<li><span id="product_status">${productBean.status}</span></li>
						</ul>
					</div>
					<div class="status-width">
						<b>商品所在地</b>
						<ul>
							<li><span id="product_area">${productBean.area}</span></li>
						</ul>
					</div>
					<div class="status-width">
						<b>賣家名稱</b>
						<ul>
							<li>
								<span id="product_publisher">${usersBean.uName}</span>
							</li>
						</ul>
					</div>
					<div class="status-width">
						<b>狀態說明</b>
						<ul>
							<li>
								<span id="product_status">${productBean.statusDescription}</span>
							</li>
						</ul>
					</div>
					<div class="status-width">
						<b>拍賣天數</b>
						<ul>
							<li>
								<span id="product_auctionDays">${productBean.auctionDays}天</span>
							</li>
						</ul>
					</div>
					<div class="status-width">
						<b>刊登時間</b>
						<ul>
							<li><fmt:formatDate value="${productBean.date}"
									pattern="yyyy-MM-dd HH:mm" /></li>
						</ul>
					</div>					
				</div>
				<div class="item-trade">
					<form id="productPostForm" method="POST"
						action="<c:url value="/AutoBuyCart/${sessionScope.loginUser.uNo}"/>">
						<span class="hidden_itemid"><input type="text" name="id"
					value="${productBean.id}"></span>
						<b>購買價格   :  <span id="product_price">${productBean.price} </span></b><br>
						<b>購買數量   :  <input type="number" id="buy-quantity" name="quantity"
							value="1" max="${productBean.quantity}" min="1">
						</b><br> 
						<b>商品庫存   :  <span id="product_quantity">${productBean.quantity}</span>
						</b><br>
					</form>
					<button id="buy-btn">直接購買</button>
					<button id="joinCart-btn">加入購物車</button>
				</div>
			</div>
			<div class="h-line"></div>

			<!--transportblock-->
			<div class="trans-block">
				<ul>
					<li><b>付款方式:</b><br> <span id="product_payment">${productBean.payment}</span></li>
					<!--                 <li>貨到付款</li> -->
					<!--                 <li>線上刷卡</li> -->
				</ul>
				<ul>
					<li><b>交貨方式:</b><br> <span id="product_shipping">${productBean.shipping}</span></li>
					<!--                 <li>買方自取</li> -->
					<!--                 <li>黑貓宅配</li> -->
				</ul>
				<ul>
					<li><b>運送費用:</b><br> <span id="product_delivery">${productBean.delivery}</span></li>
					<!--                 <li>黑貓:100元</li> -->
				</ul>
			</div>
			<div class="h-line"></div>
			<div class="change">
				<!--iteminfoblock-->
				<div class="change-content" id="content-info">
					<div id="iteminfo-block">
						<span>
							<h2>
								<b>拍賣商品資訊</b>
							</h2>
						</span> <b>
						<span id="description">${productBean.description}</span></b>
					</div>
				</div>

				<!-- traderecord-block -->
				<div class="change-content" id="content-record">
					<div id="record-block">
						<span class="title">
							<h2>
								<b>購買紀錄</b>
							</h2> <br>
						</span>
						<table class="list-table">
							<thead>
								<tr>
									<th>
										<div class="record-buyer">購買人</div>
									</th>
									<th>
										<div class="record-quality">購買數量</div>
									</th>
									<th>
										<div class="record-buytime">購買時間</div>
									</th>
								</tr>
							</thead>
							<tbody>
							<c:forEach var="bean" items="${ordersViewList}">
								<tr>
									<td>
										<div class="record-buyer">
											<span class="buyer-account">${bean.userName}</span>
										</div>
									</td>
									<td>
										<div class="record-quality">${bean.quantity}</div>
									</td>
									<td>
										<div class="record-buytime">
										<fmt:formatDate
													value="${bean.date}" pattern="yyyy-MM-dd HH:mm" />
										</div>
									</td>
								</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>

				<!-- question-block -->
				<div class="change-content" id="content-question">
					<div id="question-block">
						<span class="title">
							<h2>
								<b>商品問題詢問</b>
							</h2>
						</span>
						<table class="list-table">
							<thead>
								<tr id="question-thead-tr">
									<th id="question-inquirer">發問人</th>
									<th id="question-content">內容</th>
									<th id="question-time">詢問時間</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="bean" items="${questionList}">
									<tr id="question-tbody-tr">
										<td><span id="account">${bean.userName}</span></td>
										<td><span id="content">${bean.content}</span></td>
										<td><span id="dateTime"> <fmt:formatDate
													value="${bean.date}" pattern="yyyy-MM-dd HH:mm" />
										</span></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
						<div class="h-line"></div>
						<span id="question-title">發表提問<br></span> 
						<form id="questionForm" method="POST" action="<c:url value="/ProductPost/${productBean.id}"/>">
						
						<span class="hidden_itemid">
						<input type="text" name="userNo" value="${sessionScope.loginUser.uNo}">
						</span>
						<span class="hidden_itemid">
						<input type="text" name="userName" value="${sessionScope.loginUser.uName}">
						</span>
						
						<span id="addQuestion"> 
							<input type="text" id="question-input" name="content">
						</span>
							</form>
							<button id="addQuestion-btn">送出</button>	
					</div>
				</div>
			</div>
		</article>
	</div>

	<!-- ItemInfomation -->

	<!-- End article area -->
	
	<!-- login and regist pop up windows (with shadow) -->
   	<%@ include file="include/loginArea.jsp" %>
   	
   	<!-- Start Chat Room Area -->
	<%@ include file="include/chatRoom.jsp" %>
	<!-- End Chat Room Area -->

</body>

</html>