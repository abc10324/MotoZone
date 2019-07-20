let replyEditor;
var currentPostUrl;

// for posts loading use
// true for execute loading
// false for denied loading
var postLoadLock = true;

// post page start from page 1
var postPage = 1;

$(document).ready(function(){

    $(".content-area").click(function(){
        // change URL
        var id = $(this).children(".content-id").text();
        currentPostUrl = "/motozone/Posts/" + id
        history.pushState({foo: "Post"},"",currentPostUrl);
        
        // set post loadable
        postLoadLock = true;
        
        // reset page for loading
		postPage = 1;
        
        // get data
        displayPosts(id);
        
        // show pop-up window
    	$(".article-content-area").removeClass("hidden-window",700);
        $("#shadow").fadeIn(700);
        
    });
    
    // content area scroll event control
    $("#post-block").scroll(function(){
    	
    	if(($("#post-block").height() + $("#post-block").scrollTop()) > $("#posts-area").height() && postLoadLock){
    		postLoadLock = false;
    		
    		var currentPostUrl = location.href;
    		var postId = currentPostUrl.substring(currentPostUrl.lastIndexOf('/') + 1);
    		
    		// show next page
    		postPage++;
    		displayPosts(postId);
    	}
    	
    });
    
    // reply button
    $("#reply-btn").click(function(){
    	if(window.sessionStorage.getItem("loginUser") != ""){
	    	// set title
	    	$("#article-reply-title").text($("#posts-area h2").text());
	    	
	    	// show reply window
	    	$(".article-reply-area").removeClass("hidden-window", 700);
	    	$("#secondShadow").fadeIn(700);
    	} else {
    		alert("請先登入 !");
    		
    		$(".article-content-area").addClass("hidden-window", 700,function(){
    			$(".login-area").removeClass("hidden-window", 700);
    		});
    		
    	}
    });
    
    // reply area close button
    $(".article-reply-area .close-btn").click(function(){
    	// set history
    	history.replaceState({foo: "Post"},"",currentPostUrl);
    	
    	// set shadow
    	$("#secondShadow").fadeOut(700);
    	$("#shadow").fadeIn(700);
    	
//    	// delete temp image
//    	var jsonObj = {"urlList":[]};
//    	
//    	// get all image source in the edit block
//        $(".publish-area img").each(function(){
//        	jsonObj.urlList[jsonObj.urlList.length] = $(this).attr("src");
//        });
//        
//        // remove image from server and database
//        $.ajax({
//        	url:"/motozone/Images",
//            type:"DELETE",
//     contentType:"application/json",
//            data:JSON.stringify(jsonObj),
//                success:function(data){
//                	console.log(data);
//                }
//        });
        
        // clean edit block
        replyEditor.setData("");
    });
    
    // send a post
    $("#reply-submit").click(function(){
        // get image source
        var jsonObj = {"urlList":[]};
    	var imgSrc = "";
        
    	// get all image source in the edit block
        $(".publish-area img").each(function(){
        	jsonObj.urlList[jsonObj.urlList.length] = $(this).attr("src");
        });
        
        // get first image
        if(jsonObj.urlList.length != 0){
        	imgSrc = jsonObj.urlList[0];
        }
        
        if(replyEditor.getData()){
            
            // use ajax to send data to controller
            $.ajax({
                url:"/motozone/Post/" + location.href.substring(location.href.lastIndexOf("/") + 1),
                type:"POST",
                data:{
                     userNo: JSON.parse(window.sessionStorage.getItem("loginUser")).uNo,
                     author: JSON.parse(window.sessionStorage.getItem("loginUser")).uName,
                    content: replyEditor.getData(),
                     imgSrc: imgSrc},
            success:function(data){
	            	// set post not loadable
	                postLoadLock = false;
            	
            		console.log("Show all posts");
            		
                    // show all posts
                	$("#posts-area").html("");
                	$("#posts-area").append(Mustache.render(postHeaderTemplate,data));
	                $("#posts-area").append(Mustache.render(postBodyTemplate,data));
	                    
	                // scroll to the end
	                $("#post-block").scrollTop($("#posts-area").height());
	                
                	// empty input area
                    replyEditor.setData("");
                    
                    // close window
                    $(".article-reply-area").addClass("hidden-window", 700);
                    $("#secondShadow").fadeOut(700);
                	$("#shadow").fadeIn(700);
                }
            });
            }
    });
    
    // user info card area close button
    $(".user-info-card .close-btn").click(function(){
    	// close window
        $(".user-info-card").addClass("hidden-window", 700,function(){
        	// change add-friend button's display
            $("#add-friend-btn").text("加好友");
            $("#add-friend-btn").attr({"disabled":false});
            $("#add-friend-btn").removeClass("btn-fixed");
            
            // change chat button's display
	        $("#chat-btn").attr({"disabled":true});
	        $("#chat-btn").addClass("btn-fixed");
            
            // show button , for after click own user card
            $("#add-friend-btn").show();
        	$("#chat-btn").show();
        });
        
        $("#secondShadow").fadeOut(700);
    	$("#shadow").fadeIn(700);
    	
    });
    
    // user info card's add friend button
    $("#add-friend-btn").click(function(){
    	// login check
    	if(window.sessionStorage.getItem("loginUser") != ""){
    		
    		$.ajax({
    			url:"/motozone/Friends/" + JSON.parse(window.sessionStorage.getItem("loginUser")).uNo,
    		   type:"POST",
    		   data:{friendNo:$("#user-info-card-user-no").text()},
    		success:function(data){
    					if(data){
    						// set user information with new friend list
            		        window.sessionStorage.setItem("loginUser",JSON.stringify(data));
            		        
            		        // set new friend list to chat room object
            		        var chatRoomObj = JSON.parse(window.sessionStorage.getItem("chatRoom"));
            		        chatRoomObj.friendsList = data.friendsList;
            		        
            		        window.sessionStorage.setItem("chatRoom",JSON.stringify(chatRoomObj));
            		        
            		        // change add-friend button's display
            		        $("#add-friend-btn").text("已為好友");
            		        $("#add-friend-btn").attr({"disabled":true});
            		        $("#add-friend-btn").addClass("btn-fixed");
            		        
            		        // change chat button's display
            		        $("#chat-btn").attr({"disabled":false});
            		        $("#chat-btn").removeClass("btn-fixed");
            		        
            		        // set friend list to chat room
    			    		var friendsList = Mustache.render(chatRoomFriendsListTemplate,chatRoomObj);
    			    		$("#chat-room-friends").html(friendsList);
            		        
    			    		// get online list
    			    		sendWebSocketMessage({from:data.uNo,to:['regist'],message:'',time:Date.now()});
            		        
    					} else {
    						alert("已為好友 !")
    					}
    				}
    		});
    		
    	} else {
    		alert("請先登入 !");
    		
    		// close all pop-up window
    		$(".popup-window").addClass("hidden-window",700);
    		$("#secondShadow").fadeOut(700);
    		
    		$(".login-area").removeClass("hidden-window", 700);
            $("#shadow").fadeIn(700);
    		
    	}
    	
    	
    });
    
    
    // chat button
    $("#chat-btn").click(function(){
    	// hide all pop-up window
    	$("#shadow").fadeOut(700);
    	$("#secondShadow").fadeOut(700);
    	$(".popup-window").addClass("hidden-window",700);
    	
    	// show this friend's data from session
    	// and fill in the chat room
    	var loginUserObj = JSON.parse(window.sessionStorage.getItem("loginUser"));
    	var chatRoomObj = JSON.parse(window.sessionStorage.getItem("chatRoom"));
    	var friendUserNo = $("#user-info-card-user-no").text();
    	
    	// get friend data from friend list
		var userObj;
		for(let user of chatRoomObj.friendsList){
			
			if(user.uNo == friendUserNo){
				userObj = user;
			}
		}
    	
    	// check is this user exist in chat room's user list
    	var userListCheck = true;
    	for(let user of chatRoomObj.usersList){
    		
    		// user already exist in user list
    		if(user.uNo == friendUserNo){
    			
    			// close flag
    			userListCheck = false;
    		}
    	}
    	
    	if(userListCheck){
    		if(userObj != undefined){
    			var renderData = Mustache.render(chatRoomUsersTemplate,userObj);

                // append new user diaglog block to chat room
                $("#chat-room-users").append(renderData);
                
                // append new user in chat room's user list
                chatRoomObj.usersList[chatRoomObj.usersList.length] = userObj;
    		}
    	}
    	
    	// show chat window
    	if(userObj != undefined){
            var renderData = Mustache.render(chatRoomContentTemplate,userObj);

            $("#chat-room-content").html(renderData);
        }
    	
    	
    	// fill chat area with chat history
        var chatContent;
        for(let singleChat of chatRoomObj.chatHistory){
            if(singleChat.userNo == friendUserNo){
            	chatContent = singleChat.chatContent;
            }
        }
        
        // check chat history exist
        if(chatContent != undefined){
        	for(let content of chatContent){
        		if(content.from == loginUserObj.uNo){ // from me
        			$("#chat-message-area").append(Mustache.render(ownMsgTemplate,content));
        		} else { // from others
        			$("#chat-message-area").append(Mustache.render(replyMsgTemplate,content));
        		}
        	}
        	
        	// move the scroll bar to the end
    		$("#chat-message-area").scrollTop($("#chat-message-area").prop("scrollHeight"));
        } else {
//        	console.log("No history");
        }
    	
    	
    	// show chat room
    	$(".chat-room-area").removeClass("chat-room-hide",800);

        $("#chat-room-fn-area .fa-comments").hide(700);
        
        // set chat room object return to session
        window.sessionStorage.setItem("chatRoom",JSON.stringify(chatRoomObj));
        
        // reset history url
        history.pushState({foo: "main"},"",currentUrl);
    });
    
    // share button control
    $(".share").fadeOut();
    $("#share-btn").click(function(){
        $(".share").fadeToggle(700);
    });
    
    // fb share button
    $("#fb-share").click(function(){
        var shareUrl = "https://www.facebook.com/sharer/sharer.php?u=" + location.href;
        window.open(shareUrl,"_blank","left=400,top=200,width=500,height=500");
    });
    
    // line share button
    $("#line-share").click(function(){
        var shareUrl = "https://social-plugins.line.me/lineit/share?url=" + location.href;
        window.open(shareUrl,"_blank","left=400,top=200,width=750,height=500");
    });
    
    
    // expend button
    $(".expend-btn").click(function(){
        // expend or shrink the pop up window
        if($(this).parent().hasClass("expend-window")){
            $(this).parent().removeClass("expend-window");
        } else {
            $(this).parent().addClass("expend-window",700);
        }

        // change the icon
        if($(this).hasClass("fa-expand-arrows-alt")){
            $(this).removeClass("fa-expand-arrows-alt");
            $(this).addClass("fa-compress-arrows-alt");
        } else if($(this).hasClass("fa-compress-arrows-alt")){
            $(this).removeClass("fa-compress-arrows-alt");
            $(this).addClass("fa-expand-arrows-alt");
        }
        


    });

});

function displayPosts(id){
	
	// show loading image
	$("#posts-loading").css({'opacity':'1'});
	
    $.ajax({
        url:"/motozone/Ajax/Posts/" + id + "/" + postPage,
        type:"GET",
        success:function(data){
        	
        			// empty check
        			if(data){
//        				console.log("Page " + postPage);
        				
        				// hide loading image
        				$("#posts-loading").css({'opacity':'0'});
                        
                        if(postPage == 1){ // first click need to empty this window and show header
                        	$("#posts-area").html("");
                        	$("#posts-area").append(Mustache.render(postHeaderTemplate,data));
                        	// scroll to the top
        	                $("#post-block").scrollTop(0);
                        }
                        
                        $("#posts-area").append(Mustache.render(postBodyTemplate,data));
                        
                        // centered the image
                        $("#posts-area img").each(function(){
                        	if($(this).width() > 100){
                        		$(this).addClass("repos-img");
                        		
                        		if($(this).width() > 1000){
                        			$(this).addClass("resize-img");
                        		}
                        		
                        	}
                        });
                        
        				postLoadLock = true;
        			} else {
        				// hide loading image
        				$("#posts-loading").css({'opacity':'0'});
        				postLoadLock = false;
        				
        				console.log("reach end !");
        			}
                    
                }
    });
}

function showUserInfoCard(element){
	
	// show user info card window
	$(".user-info-card").removeClass("hidden-window",700);
	$("#secondShadow").fadeIn(700);
    $(".user-info-card #user-info-card-icon").attr({"src":$(element).attr("src")});
    
    // get friend user number
    var friendUserNo = $(element).next().text();
    var userNo = JSON.parse(window.sessionStorage.getItem("loginUser")).uNo;
    
    // self identity check
    if(friendUserNo != userNo){
    	$.ajax({
        	url:"/motozone/Users/" + friendUserNo,
           type:"GET",
        success:function(data){
        			// set name and user number
        			$("#user-info-card-name").text(data.uName);
        			$("#user-info-card-user-no").text(data.uNo);
        			
        			// check after login
        			if(window.sessionStorage.getItem("loginUser") != ""){
        				var friendsList = JSON.parse(window.sessionStorage.getItem("loginUser")).friendsList;
            			
            			for(let friend of friendsList){
            				if(friend.uNo == data.uNo){
            					// change add-friend button's display
                		        $("#add-friend-btn").text("已為好友");
                		        $("#add-friend-btn").attr({"disabled":true});
                		        $("#add-friend-btn").addClass("btn-fixed");
                		        
                		        // change chat button's display
                		        $("#chat-btn").attr({"disabled":false});
                		        $("#chat-btn").removeClass("btn-fixed");
            				};
            			}
        			}
        			
        		}
        });
    } else {
    	
    	// don't show button when click own user info card
    	$("#add-friend-btn").hide();
    	$("#chat-btn").hide();
    }
    
}


// post template
//<h2>${postsTitle}</h2>
//<div id="post-category">${category}</div>
//<c:forEach var="bean" items="${posts}">
//    <div class="single-post">
//        <hr/>
//        <div class="post-header">
//			<img class="post-header-icon" src="${bean.snapshot}" />
//		    <div class="post-header-author">UserName</div>
//		    <div class="post-header-date">2019-05-27</div>
//        </div>
//        <div class="post-body">${bean.content}</div>
//    </div>
//</c:forEach>
var postHeaderTemplate = '<h2>{{title}}</h2>'
	 				   + '<div id="post-category">{{category}}</div>';

var postBodyTemplate = '{{#post}}'
					 + '<div class="single-post">'
					 + '<hr/>'
					 + '<div class="post-header">'
					 + '<img class="post-header-icon" src="{{snapshot}}{{^snapshot}}/motozone/img/author-M01.jpg{{/snapshot}}"'
					 + ' onclick="showUserInfoCard(this)"/>'
					 + '<div class="post-header-user-no">{{userNo}}</div>'
					 + '<div class="post-header-author">{{author}}</div>'
					 + '<div class="post-header-date">{{date}}</div>'
					 + '</div>'
					 + '<div class="post-body">{{&content}}</div>'
					 + '</div>'
					 + '{{/post}}';

