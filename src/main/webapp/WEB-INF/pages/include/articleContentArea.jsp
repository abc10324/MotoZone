<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
    
<c:choose>
	<c:when test="${empty posts}">
		<div class="article-content-area popup-window hidden-window">
	</c:when>
	<c:otherwise>
		<div class="article-content-area popup-window">
		<script>
	 		currentUrl = "/motozone/PostList/<c:out value="${postList[0].category}"/>";
	 	</script>
	</c:otherwise>
</c:choose>
  
	<i class="fas fa-times close-btn"></i>
	<i class="fas fa-expand-arrows-alt expend-btn"></i>
	
	<div id="post-block">
		<div id="posts-area">
			<h2>${postTitle}</h2>
			<div id="post-category">${postCategory}</div>
			<c:forEach var="bean" items="${posts}">
			    <div class="single-post">
			        <hr/>
			        <div class="post-header">
						<img class="post-header-icon" src="${bean.snapshot}" />
					    <div class="post-header-author">${bean.author}</div>
					    <div class="post-header-date"><fmt:formatDate value="${bean.date}" pattern="yyyy/MM/dd HH:mm"/></div>
			        </div>
			        <div class="post-body">${bean.content}</div>
			    </div>
			</c:forEach>
	     </div>
	     <div id="posts-loading">
	     	<img src="<c:url value="/img/loading.gif"/>" />
	     </div>
	</div>
	<div id="post-fn-block">
	    <i class="fas fa-reply post-fn-btn" id="reply-btn"></i>
	    <i class="fas fa-thumbs-up post-fn-btn"></i>
	    <i class="fas fa-share-alt post-fn-btn" id="share-btn"></i>
	    <i class="fab fa-facebook-square share post-fn-btn" id="fb-share"></i>
	    <i class="fab fa-line share post-fn-btn" id="line-share"></i>
	</div>
</div>

<!-- reply area -->
<div class="article-reply-area popup-window hidden-window">
	<i class="fas fa-times close-btn"></i>
	
	<h2>回應貼文</h2>
	<div id="article-reply-title"></div>
	
	<hr/>
	<form id="reply-form">
		<textarea name="content" id="reply-editor" rows="3"></textarea>
		<script>
			replyEditor = ClassicEditor
	        .create( document.querySelector( '#reply-editor' ) ,{
	            removePlugins: ['Heading'],
	        	ckfinder: {
// 	             uploadUrl: "/motozone/Image/" + location.href.substring(location.href.lastIndexOf("/") + 1)
	         	　uploadUrl: "/motozone/Imgur"	
	        	}
	        })
	        .then( newEditor => {
	        	replyEditor = newEditor
	        } )
	        .catch( error => {
	            console.error( error );
	        } );
		</script>
    </form>
    <br/>
    <button id="reply-submit">提交</button>
</div>

<!-- Start user info card area -->
<div class="user-info-card popup-window hidden-window">
    <i class="fas fa-times close-btn"></i>
    <img id="user-info-card-icon" src="<c:url value="/img/userIcon.png"/>" />
    <div id="user-info-card-name"></div>
    <div id="user-info-card-user-no"></div>
    <button id="add-friend-btn">加好友</button>
    <button id="chat-btn" class="btn-fixed" disabled>聊天</button>
</div>
<!-- End user info card area -->