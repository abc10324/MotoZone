<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<link rel="icon" href="<c:url value="/img/favicon.ico"/>" />
	<title>AutoBuyCart</title>
	
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
	<!-- shopping cart style -->
	<link href="<c:url value="/css/autoBuyCart.css"/>" rel="stylesheet">
	
	
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
	<!-- main js -->
	<script src="<c:url value="/js/main.js"/>"></script>
	<!-- AutoBuyCart js -->
	<script src="<c:url value="/js/autoBuyCart.js"/>"></script>

</head>
<body>

	<!-- BEGAIN PRELOADER -->
	<div id="preloader">
		<div id="status">&nbsp;</div>
	</div>
	<!-- END PRELOADER -->

	<!-- Start menu area -->
	<%@ include file="include/menu.jsp"%>
	<!-- End menu area -->

	<!-- Start header area -->
	<%@ include file="include/header.jsp"%>
	<!-- End header area -->

    <div class="container" style="margin-top: 100px;">
		<div class="row">
			<div class="col-sm-12 col-md-10 col-md-offset-1">
				<table class="table table-hover">
					<thead class="itemtitle">
						<tr>
							<th>商品</th>
							<th>數量</th>
							<th class="text-center">價格</th>
							<th class="text-center">小計</th>
							<th> </th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="bean" items="${cartItem}">
							<tr class="cart-list">
								<td class="col-sm-8 col-md-6">
									<div class="media">
										<a class="thumbnail pull-left" href="<c:url value="/ProductPost/${bean.id}"/>"> 
											<img class="media-object" src="${bean.imgSrc}" style="width: 72px; height: 72px;">
										</a>
										<div class="media-body">
											<h3 class="media-heading">
												<b>${bean.name}</b>
											</h3>
											<h5 class="media-heading">
												by&nbsp;${bean.sellerName}
											</h5>
											<h5 class="media-heading">
												庫存 : &nbsp;${bean.stock}
											</h5>
										</div>
									</div>
								</td>
								<td class="col-sm-1 col-md-1">
									<input type="number" class="qty" name="quantity" value="${bean.quantity}" max="${bean.stock}" min="1">
								</td>
								<td class="col-sm-1 col-md-1 text-center">
									<span class="unit">${bean.price}</span>
								</td>
								<td class="col-sm-1 col-md-1 text-center">
									<span class="price">${bean.total}</span>
								</td>
								<td class="col-sm-1 col-md-1">
										
										<span class="hidden_item"> 
											<input type="text" name="id" value="${bean.id}">
										</span> 
										<span class="hidden_item"> 
											<input type="text" name="userNo" value="${bean.userNo}">
										</span>

										<button id="remove-item" class="btn btn-danger" onclick="removeCartItem(this)">
											<span class="glyphicon glyphicon-remove"></span>
											<span>移除商品</span>
										</button>
								</td>
							</tr>
						</c:forEach>

						<tr class="sum">
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td><h3>總金額</h3></td>
							<td class="text-right"><h3><p>$
									<strong id="total"></strong></p>
								</h3></td>
						</tr>
						<tr>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td><a href="<c:url value="/AutoBuy"/>">
									<button type="button" class="btn btn-default">
										<span class="glyphicon glyphicon-shopping-cart"></span>
										<span id="keepshopping">繼續購物</span>
									</button>
								</a>
							</td>
							<td>
								<button id="totalmoney" type="button" class="btn btn-success">
									<span id="totalaccount">結算</span>
									<span class="glyphicon glyphicon-play"></span>
								</button>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>

	<!-- ========================結算商品的彈出視窗======================== -->
	<div class="user-popup-area popup-window hidden-window" id="payment-popup"></div>
    
    <!-- login and regist pop up windows (with shadow) -->
   	<%@ include file="include/loginArea.jsp" %>
    
    <!-- Start Chat Room Area -->
	<%@ include file="include/chatRoom.jsp" %>
	<!-- End Chat Room Area -->

</body>
</html>