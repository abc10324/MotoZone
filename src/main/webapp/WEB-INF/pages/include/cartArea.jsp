<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<!-- Start shopping cart icon -->
<div id="shoppingCart">
    <a href="<c:url value="/AutoBuyCart/${sessionScope.loginUser.uNo}"/>"> 
	     <i class="fas fa-cart-plus" id="shopcarticon">
	     	<span id="counter" class="badge"></span>
	     </i>
    </a>
</div>
<!-- End shopping cart icon -->

<!-- Start shopping cart popup window -->
<div id="shoppingCartArea" class="cartArea">
	<div class="popheader">
		<div class="cart-item-title">商品名稱</div>
		<div class="cart-item-qty">數量</div>
		<div class="cart-item-price">商品單價</div>
	</div>
	<div class="itemlist">
		<div class="item"></div>
		<div class="qty"></div>
		<div class="price"></div>
	</div>
</div>
<!-- End shopping cart popup window -->