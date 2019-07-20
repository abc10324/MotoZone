<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="nav-area">      
    <a id="menu-btn" href="#"><i class="fa fa-bars"></i></a>
    <nav class="main-nav" id="main-menu">
        <span class="fas fa-times" id="close"></span>
        <ul> 
            <li><a href="<c:url value="/Categories/L"/>"><i class="fa fa-motorcycle" ></i><span>輕型機車</span></a></li>
            <li><a href="<c:url value="/Categories/H"/>"><i class="fa fa-motorcycle"></i><span>重型機車</span></a></li>
            <li><a href="<c:url value="/Categories/E"/>"><i class="fa fa-bolt" ></i><span>電動機車</span></a></li>
<!--             <li><a href="#"><i class="fa fa-info-circle"></i><span>綜合討論區</span></a></li> -->
            <li><a href="<c:url value="/AutoBuy"/>"><i class="fas fa-dollar-sign"></i><span>歐都Buy</span></a></li>
            <c:if test="${pageContext.request.requestURI != '/motozone/'}">
            	<li><a href="<c:url value="/"/>"><i class="fa fa-home"></i><span>回首頁</span></a></li>
            </c:if>               
        </ul>      
    </nav>
</div>  