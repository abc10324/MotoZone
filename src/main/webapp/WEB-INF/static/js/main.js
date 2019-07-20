/* ----------------------------------------------------------- */
/*  1. PRELOADER 
/* ----------------------------------------------------------- */ 

jQuery(window).load(function() { // makes sure the whole site is loaded
    $('#status').delay(1000).fadeOut(); // will first fade out the loading animation
    $('#preloader').delay(1000).fadeOut('slow'); // will fade out the white DIV that covers the website.
    $('body').delay(1000).css({'overflow':'visible'});
    })

/* ----------------------------------------------------------- */
/*  2. MENU SLIDE
/* ----------------------------------------------------------- */ 



window.onload = function() {
    var menuRight = document.getElementById( 'main-menu' ),
		showRight = document.getElementById( 'menu-btn' ),
		close = document.getElementById( 'close' );

    showRight.onclick = function(e) {
        e.preventDefault();
        classie.toggle( this, 'active' );
        classie.toggle( menuRight, 'menu-open' );			
    };

    // close.onclick = function() {
    //     // menuRight.hide();						
    // };

    menuRight.onclick = function() {
        classie.toggle( this, 'active' );
        classie.toggle( menuRight, 'menu-open' );				
    };
};

/* ----------------------------------------------------------- */
/*  3. MENU SMOOTH SCROLLING
/* ----------------------------------------------------------- */ 

//MENU SCROLLING WITH ACTIVE ITEM SELECTED

jQuery(".main-nav a").click(function(event){
    event.preventDefault();
    //calculate destination place
    var dest=0;
    if($(this.hash).offset().top > $(document).height()-$(window).height()){
        dest=$(document).height()-$(window).height();
    }else{
        dest=$(this.hash).offset().top;
    }
    //go to destination
    $('html,body').animate({scrollTop:dest}, 1000,'swing');
});


/* ----------------------------------------------------------- */
/*  4. LOGIN BUTTON & ICON CONTROL
/* ----------------------------------------------------------- */ 
var userCenterUrl = "/motozone/UserCenter";

$(document).ready(function(){
    $("#login-submit-btn").click(function(){
    	userLogin();
    });
    
    $("#login-form .input-group input").keypress(function(e){
    	if(e.key == "Enter"){
    		userLogin();
    	}
    });

    $(".loggedin-icon").click(function(){
        $("#loggedin-list").fadeToggle(500);
        
    });

    // in the list

    // user center button
    $("#user-center-btn").click(function(){
        $("#loggedin-list").fadeToggle(500);
        location.href = userCenterUrl;
    });

    // regist button
    $("#regist-btn").click(function(){
        $("#loggedin-list").fadeToggle(500);
        $(".regist-area").removeClass("hidden-window", 700);
        $("#shadow").fadeIn(700);
    });

    $("#logout").click(function(){
        $("#loggedin-list").fadeToggle(500); // close the list
        
        $(".loggedin-icon").addClass("disable",700,function(){ // hide loggedin icon
            $(".login-btn").removeClass("disable",700);        // show login button
        });      
        
        // google logout
        googleSignOut();
        
        // get userNo for offline message use
    	var userNo = JSON.parse(window.sessionStorage.getItem("loginUser")).uNo;
    	// send offline message to everyone
		sendWebSocketMessage({from:userNo,to:['logout'],message:'',time:Date.now()});
        
        $.ajax({
    		url:"/motozone/Users/" + JSON.parse(window.sessionStorage.getItem("loginUser")).uID,
    	   type:"DELETE",
    	success:function(data){
    			
    				// clean session
    				window.sessionStorage.setItem("loginUser","");
    				window.sessionStorage.setItem("chatRoom","");
    				
    				
    				// hide and clean chat room
    				hideChatRoom(true);
    				cleanChatRoom();
    		        disconnectChatRoom();
    				
    				// if user at User Center then redirect to index page
    				var userCenterUrlTest = /^.*UserCenter.*$/;
    				
    				if(userCenterUrlTest.test(location.href)){
    					location.href = "/motozone/";
    				}
    				
    				// if user at product post page then update form's action attribute
    				var productPostUrlTest = /^.*ProductPost.*$/;
    				
    				if(productPostUrlTest.test(location.href)){
    					// update AutoBuy productPost page
    		    		updateProductPostAttr('','');
    				}
    				
    				// if user at AutoBuy publish page then update form's attribute
    				var autoBuyUrlTest = /^.*AutoBuy.*$/;
    				
    				if(autoBuyUrlTest.test(location.href)){
    					// update AutoBuy publish
    					updateAutoBuyPublishAttr('');
    				}
    				
    				// if user at AutoBuy Cart Page then redirect to AutoBuy index page
    				var autoBuyCartUrlTest = /^.*AutoBuyCart.*$/;
    				
    				if(autoBuyCartUrlTest.test(location.href)){
    					location.href = "/motozone/AutoBuy";
    				}
    				
    				// hide shopping cart
    				if(autoBuyFlag){
    					$("#shoppingCart").hide(700);
    					updateCartLink('');
    				}
    				
    			}
    	});
    });


});

function userLogin(){
	var userID = $("#login-form input[name='user']").val();
	var pwd = $("#login-form input[name='pwd']").val();
	
	// empty check
	if(userID != "" && pwd != ""){
		// for login AJAX operation use
		$.ajax({
			url:"/motozone/Users/" + userID,
		   type:"POST",
		   data:{pwd:pwd},
		success:function(data){
					if(data){
						
						// if user has snapshot , then use it
						if(data.snapshot){
							$(".loggedin-icon img").attr("src",data.snapshot);
						} else {
							$(".loggedin-icon img").attr("src","/motozone/img/userIcon.png");
						}
						
						// show user icon
	    				$(".login-btn").addClass("disable",700,function(){
	    		            $(".loggedin-icon").removeClass("disable",700);
	    		        });
	    				
	    				// close login window
	    				$("#login-submit-btn").parent().addClass("hidden-window", 700);
	    		        $("#shadow").fadeOut(700);
	    		        
	    		        // clear the form
	    		        $(".input-group input").each(function(){
	    		        	$(this).val("");
	    		        });
	    		        
	    		        $(".input-group").each(function(){
	    		        	$(this).removeClass("error-format accepted-format");
	    		        });

	    		        // get user information
	    		        window.sessionStorage.setItem("loginUser",JSON.stringify(data));
	    		        
	    		        // connect and show chat room
	    		        connectChatRoom();
			    		$(".chat-room-area").show();
			    		
			    		// set chat room object
			    		chatRoom.friendsList = JSON.parse(window.sessionStorage.getItem("loginUser")).friendsList;
			    		window.sessionStorage.setItem("chatRoom",JSON.stringify(chatRoom));
			    		
			    		// set friend list to chat room
			    		var friendsList = Mustache.render(chatRoomFriendsListTemplate,chatRoom);
			    		$("#chat-room-friends").html(friendsList);
			    		
			    		// if user at product post page then update form's action attribute
        				var productPostUrlTest = /^.*ProductPost.*$/;
        				
        				if(productPostUrlTest.test(location.href)){
        					// update AutoBuy productPost page
        		    		updateProductPostAttr(data.uNo,data.uName);
        				}
        				
        				// if user at AutoBuy publish page then update form's attribute
        				var autoBuyUrlTest = /^.*AutoBuy.*$/;
        				
        				if(autoBuyUrlTest.test(location.href)){
        					// update AutoBuy publish
        					updateAutoBuyPublishAttr(data.uNo);
        				}
        				
        				// show shopping cart
        				if(autoBuyFlag){
        					$("#shoppingCart").show(700);
        					updateCartLink(data.uNo);
        				}
	    		        
					} else {
						alert("帳號或密碼不符合");
					}
					
				}
		});
	} else {
		alert("欄位不得為空");
	}
	
}

/* ----------------------------------------------------------- */
/*  5. LOGIN & REGIST WINDOWS CONTROL
/* ----------------------------------------------------------- */ 
if(window.sessionStorage.getItem("loginUser") == undefined){
	window.sessionStorage.setItem("loginUser","");
} else if(window.sessionStorage.getItem("loginUser") != ""){
	$(document).ready(function(){
		connectChatRoom();
	});
}

$(document).ready(function(){
    $(".login-btn").click(function(){
    	
    	$(".login-area").removeClass("hidden-window", 700);
        $("#shadow").fadeIn(700);
        
    });

    $(".regist-btn").click(function(){
        $(this).parent().addClass("hidden-window", 700, function(){
            $(".regist-area").removeClass("hidden-window", 700);
        });
        $("#shadow").fadeIn(700);
    });

    
    // regist
    $("#regist-submit-btn").click(function(){
    	userRegist();
    });
    
    $("#regist-form .input-group input").keypress(function(e){
    	if(e.key == "Enter"){
    		userRegist();
    	}
    });
    
    $("#regist-form .input-group input").keydown(function(e){
    	if(e.key == "F2"){
//    		console.log("F2!!!");
    		var userID = "Yixuan168589";
    		var name = "YIXUAN SHI";
    		var email = "xuans8589@gmail.com";
    		
    		$("#regist-form .input-group input[name='user']").val(userID);
    		$("#regist-form .input-group input[name='name']").val(name);
    		$("#regist-form .input-group input[name='email']").val(email);
    		
    		$("#regist-form .input-group input[name='user']").parent().addClass("accepted-format");
    		$("#regist-form .input-group input[name='name']").parent().addClass("accepted-format");
    		$("#regist-form .input-group input[name='email']").parent().addClass("accepted-format");
    		
    	}
    });

    // auth code input
    $("#auth-code-submit-btn").click(function(){
    	userAuthCodeCheck();
    });
    
    
    
    $("#auth-code-form .input-group input").keypress(function(e){
    	if(e.key == "Enter"){
    		userAuthCodeCheck();
    	}
    });
    
    $("#return-login-btn").click(function(){
        $(this).parent().addClass("hidden-window", 700, function(){
            $(".login-area").removeClass("hidden-window", 700);
        });
    });

    $(".close-btn").click(function(){
        $(this).parent().addClass("hidden-window", 700);
        $("#shadow").fadeOut(700);
        
        // clear the form
        $(".input-group input").each(function(){
        	$(this).val("");
        });
        $(".input-group").each(function(){
        	$(this).removeClass("error-format accepted-format");
        });

        history.pushState({foo: "main"},"",currentUrl);
    });
    
    
    // regist data format check
    $("#regist-form .input-group input[name='user']").change(function(){
        var re = /^[a-zA-Z]{1}[a-zA-Z0-9]{7,15}$/;

        if(re.test($(this).val())){ // accepted format
            $(this).parent().removeClass("error-format");
            $(this).parent().addClass("accepted-format");
        } else if($(this).val() == ""){
            $(this).parent().removeClass("error-format accepted-format");
        } else { // not-accepted format
            $(this).parent().removeClass("accepted-format");
            $(this).parent().addClass("error-format");
            alert("帳號格式需求：\n1.開頭第一個字母須為大小寫英文字\n2.長度需8 - 16個字");
        };
    });

    $("#regist-form .input-group input[name='pwd']").change(function(){
    	// empty password check area
    	$("input[name='ck-pwd']").val("");
    	
    	var re = /^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])[A-Za-z\d]{8,15}$/;
        
        if(re.test($(this).val())){ // accepted format
            $(this).parent().removeClass("error-format");
            $(this).parent().addClass("accepted-format");
        } else if($(this).val() == ""){
            $(this).parent().removeClass("error-format accepted-format");
        } else { // not-accepted format
            $(this).parent().removeClass("accepted-format");
            $(this).parent().addClass("error-format");
            alert("密碼格式需求：\n1.開頭第一個字母須為大小寫英文字\n2.長度需8 - 16個字");
        };
    });

    $("#regist-form .input-group input[name='ck-pwd']").change(function(){
        var pwd = $(this).parent().prev().children("input[name='pwd']").val();

        if(pwd == $(this).val()){ // accepted format
            $(this).parent().removeClass("error-format");
            $(this).parent().addClass("accepted-format");
        } else if($(this).val() == ""){
            $(this).parent().removeClass("error-format accepted-format");
        } else { // not-accepted format
            $(this).parent().removeClass("accepted-format");
            $(this).parent().addClass("error-format");
        };
    });

    $("#regist-form .input-group input[name='name']").change(function(){
        var reC = /^[\u4E00-\u9FA5]{2,7}$/;
        var reE = /^[A-Z]+\s{1}[A-Z]+(\s{0,1}[A-Z]+)*$/;

        if(reC.test($(this).val()) || reE.test($(this).val())){ // accepted format
            $(this).parent().removeClass("error-format");
            $(this).parent().addClass("accepted-format");
        } else if($(this).val() == ""){
            $(this).parent().removeClass("error-format accepted-format");
        } else { // not-accepted format
            $(this).parent().removeClass("accepted-format");
            $(this).parent().addClass("error-format");
            alert("名稱格式需求：\n1.中文名字需2個字以上\n2.英文名字需比照護照格式全部大寫\n3.英文名字需兩個字節或三個字節");
        };
    });

    $("#regist-form .input-group input[name='email']").change(function(){
        var reShort = /^[a-zA-Z]{1}[\w-]+@[a-z0-9]+\.[a-z]+$/;
        var reLong = /^[a-zA-Z]{1}[\w-]+@[a-z0-9]+\.[a-z]+\.[a-z]+$/;

        if(reShort.test($(this).val()) || reLong.test($(this).val())){ // accepted format
            $(this).parent().removeClass("error-format");
            $(this).parent().addClass("accepted-format");
        } else if($(this).val() == ""){
            $(this).parent().removeClass("error-format accepted-format");
        } else { // not-accepted format
            $(this).parent().removeClass("accepted-format");
            $(this).parent().addClass("error-format");
        };
    });

});

function userRegist(){
	// input value empty check
	var inputEmptyCheck = true;
	
	// check each input-group
	$("#regist-form .input-group input").each(function(){
		if($(this).val() == ""){
			inputEmptyCheck = false;
		}
	});
	
	if(inputEmptyCheck){
		// input value format check
		var inputCheck = true;
		
		// check each input-group
		$("#regist-form .input-group").each(function(){
			if(!($(this).hasClass("accepted-format"))){
				inputCheck = false;
			}
		});
		
		if(inputCheck){
			// disabled the regist button
			$("#regist-submit-btn").attr("disabled", true);
			
			// display loading gif
			$("#regist-submit-btn img").css({"opacity":"1"});
			
			$.ajax({
	    		url:"/motozone/Users",
	    	   type:"POST",
	    	   data:$("#regist-form").serialize(),
	    	success:function(data){
	    				if(data.status != 'failure'){
	    					// redirect to auth code input area
	    					$(".regist-area").addClass("hidden-window", 700, function(){
	    						$(".auth-code-input-area").removeClass("hidden-window", 700);
	        		        });
	    					
	    					// get uID from regist form
	    					var uID = $("#regist-form input[name='user']").val();
	    					
	    					 // clear the form
	        		        $(".input-group input").each(function(){
	        		        	$(this).val("");
	        		        });
	        		        
	        		        // set uID to auth code form
	    					$("#auth-code-form input[name='user']").val(uID);
	        		        
	        		        $(".input-group").each(function(){
	        		        	$(this).removeClass("error-format accepted-format");
	        		        });
	        		        
	        		        // active the regist button
	    					$("#regist-submit-btn").attr("disabled", false);
	    					// hide loading gif
	    					$("#regist-submit-btn img").css({"opacity":"0"});
	    				} else {
	    					alert(data.errorMsg);
	    					
	    					// active the regist button
	    					$("#regist-submit-btn").attr("disabled", false);
	    					// hide loading gif
	    					$("#regist-submit-btn img").css({"opacity":"0"});
	    				}
	    			}
	    	});
			
		} else {
			alert("輸入資料格式有誤");
		}
	} else {
		alert("欄位不得為空");
	}
	
}

function userAuthCodeCheck(){
	var uID = $("#auth-code-form input[name='user']").val();
	var authCode = $("#auth-code-form input[name='authCode']").val();
	
	$.ajax({
		url:"/motozone/Ajax/Users/" + uID + "/" + authCode,
	   type:"GET",
	success:function(data){
		
				if(data != 'failure'){
					$(".auth-code-input-area").addClass("hidden-window", 700, function(){
			            $(".regist-reply-area").removeClass("hidden-window", 700);
			        });
				} else {
					alert("輸入驗證碼格式有誤");
				}
				
			}
	});
}

/* ----------------------------------------------------------- */
/*  6. BROWSER HISTORY CONTROL
/* ----------------------------------------------------------- */ 

var currentUrl = location.href;


// if detect browser execute backward , reload the page , prevent cache
if (!!window.performance && window.performance.navigation.type === 2) {
    window.location.reload();
}
    

/* ----------------------------------------------------------- */
/*  7. GOOGLE LOGIN CONTROL
/* ----------------------------------------------------------- */ 

var googleUser = {};
var startApp = function() {
  gapi.load('auth2', function(){
    // Retrieve the singleton for the GoogleAuth library and set up the client.
    auth2 = gapi.auth2.init({
//      client_id: '703647999598-mtjqtb9jrnp6banoqnialqlhbppjc64h.apps.googleusercontent.com',
      client_id: '671760878609-sfs632t6ndmgfk1qlb0htft9tvk67cf1.apps.googleusercontent.com',
   cookiepolicy: 'single_host_origin',	  
          scope: 'profile email'
    });
    attachSignin(document.getElementById('google-login-btn'));
  });
};

function attachSignin(element) {
	
  // bind click event to specific id button
  auth2.attachClickHandler(element, {},
      function(googleUser) {
	  	  // get id token after login
          var id_token = googleUser.getAuthResponse().id_token;
          
          // connect motozone server
	      $.ajax({
	    	url:"/motozone/Users/GoogleLogin",
	       type:"POST",
	       data:{idTokenStr:id_token},
	    success:function(data){
			    	if(data){
						
						// if user has snapshot , then use it
						if(data.snapshot){
							$(".loggedin-icon img").attr("src",data.snapshot);
						}
						
						// show user icon
						$(".login-btn").addClass("disable",700,function(){
				            $(".loggedin-icon").removeClass("disable",700);
				        });
						
						// close login window
						$("#login-submit-btn").parent().addClass("hidden-window", 700);
				        $("#shadow").fadeOut(700);
				        
				        // clear the form
				        $(".input-group input").each(function(){
				        	$(this).val("");
				        });
				        
				        $(".input-group").each(function(){
				        	$(this).removeClass("error-format accepted-format");
				        });
		
				        // get user information
				        window.sessionStorage.setItem("loginUser",JSON.stringify(data));
				        
				        // connect and show chat room
        		        connectChatRoom();
    		    		$(".chat-room-area").show();
    		    		
    		    		// set chat room object
    		    		chatRoom.friendsList = JSON.parse(window.sessionStorage.getItem("loginUser")).friendsList;
    		    		window.sessionStorage.setItem("chatRoom",JSON.stringify(chatRoom));
    		    		
    		    		// set friend list to chat room
    		    		var friendsList = Mustache.render(chatRoomFriendsListTemplate,chatRoom);
    		    		$("#chat-room-friends").html(friendsList);
    		    		
    		    		// if user at product post page then update form's action attribute
        				var productPostUrlTest = /^.*ProductPost.*$/;
        				
        				if(productPostUrlTest.test(location.href)){
        					// update AutoBuy productPost page
        		    		updateProductPostAttr(data.uNo,data.uName);
        				}
        				
        				// if user at AutoBuy publish page then update form's attribute
        				var autoBuyUrlTest = /^.*AutoBuy.*$/;
        				
        				if(autoBuyUrlTest.test(location.href)){
        					// update AutoBuy publish
        					updateAutoBuyPublishAttr(data.uNo);
        				}
        				
        				// show shopping cart
        				if(autoBuyFlag){
        					$("#shoppingCart").show(700);
        					updateCartLink(data.uNo);
        				}
        				
					} else {
						alert("Google登入失敗");
					}
	    		}
	    });
      }, function(error) {
        alert(JSON.stringify(error, undefined, 2));
      });
}

// google sign out function
function googleSignOut() {
    var auth2 = gapi.auth2.getAuthInstance();
    
    auth2.signOut().then(function () {
      console.log('User signed out.');
    });
}

// execute login button binding
$(document).ready(function(){
	startApp();
});



/* ----------------------------------------------------------- */
/*  8. CHAT ROOM CONTROL
/* ----------------------------------------------------------- */ 
var chatRoom = {friendsList:[],
				  usersList:[],
				chatHistory:[]}
var chatHistory = {userNo:'',
			  chatContent:[]}

if(window.sessionStorage.getItem("chatRoom") == undefined){
	window.sessionStorage.setItem("chatRoom","");
} else if(window.sessionStorage.getItem("chatRoom") != "") {
	chatRoom = JSON.parse(window.sessionStorage.getItem("chatRoom"));
}





$(document).ready(function(){
	
	// hide when user not login yet
	if(window.sessionStorage.getItem("loginUser") == ""){
		$(".chat-room-area").hide();
	} else {
		// set friend list to chat room
		var friendsList = Mustache.render(chatRoomFriendsListTemplate,chatRoom);
		$("#chat-room-friends").html(friendsList);
	}
    
    // chat room show button
    $("#chat-room-fn-area .fa-comments").click(function(){
        $(".chat-room-area").removeClass("chat-room-hide",800);

        $(this).hide(700);
    });

    // chat room hide button
    $("#chat-room-fn-btn-area .fa-minus").click(function(){
    	hideChatRoom();
    });

    // friend list toggle button
    $("#friend-list-toggle-btn").click(function(){

        // change icon and shrink the list
        if($("#friend-list-toggle-btn i").hasClass("fa-chevron-right")){
            $("#friend-list-toggle-btn i").removeClass("fa-chevron-right");
            $("#friend-list-toggle-btn i").addClass("fa-chevron-left");

            $(".chat-room-friend-list-area").removeClass("friend-list-show",700);
        } else {
            $("#friend-list-toggle-btn i").removeClass("fa-chevron-left");
            $("#friend-list-toggle-btn i").addClass("fa-chevron-right");

            $(".chat-room-friend-list-area").addClass("friend-list-show",700);
        }

    });


    // friend list click event
    $(".chat-room-friend").click(function(){

        var userObj = {};
        userObj.uNo = $(this).children(".chat-room-user-no").text();
        userObj.uName = $(this).children(".chat-room-friend-name").text();
        userObj.snapshot = $(this).children(".chat-room-friend-icon").attr("src");

        // in order to check is the user already in the user list
        var checkFlag = true;
        for(let user of chatRoom.usersList){
            if(user.uNo == userObj.uNo){
                checkFlag = false;
            }
        }
        
        // use check flag to determine render or not
        if(checkFlag){
            // apeend new user to usersList in chat room object
        	chatRoom.usersList[chatRoom.usersList.length] = userObj;

            var renderData = Mustache.render(chatRoomUsersTemplate,userObj);

            // append new user diaglog block to chat room
            $("#chat-room-users").append(renderData);
        }
        
    });
    
    // check user login
    // if already login then update chat room
    if(window.sessionStorage.getItem("loginUser") != ""){
    	initChatRoom();
    }


});

// init chat room when change page
function initChatRoom(){
	// get chat room object from sessionStorage
	var chatRoomObj = JSON.parse(window.sessionStorage.getItem("chatRoom"));
	
	for(let user of chatRoomObj.usersList){
		// append new user diaglog block to chat room
	    $("#chat-room-users").append(Mustache.render(chatRoomUsersTemplate,user));
	}

    
}

// user list click event
function showChatContentArea(element){
	// get chat room object from sessionStorage
	var chatRoomObj = JSON.parse(window.sessionStorage.getItem("chatRoom"));
	// get loginUser object from sessionStorage
	var loginUserObj = JSON.parse(window.sessionStorage.getItem("loginUser"));
	
	
    var userNo = $(element).children(".chat-room-user-no").text();

    // get user object from user list in chat room object
    var userObj = "";

    for(let user of chatRoomObj.usersList){
        if(user.uNo == userNo){
            userObj = user;
        }
    }

    // empty check
    if(userObj != ""){
        var renderData = Mustache.render(chatRoomContentTemplate,userObj);

        $("#chat-room-content").html(renderData);
    }
    
    // fill chat area with chat history
    var chatContent;
    for(let singleChat of chatRoomObj.chatHistory){
        if(singleChat.userNo == userNo){
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
//    	console.log("No history");
    }

}

// chat room content area close button control
function emptyChatContentArea(){
    // clean chat room content area
    $("#chat-room-content").html("");
}

function showUsersDiaglog(element){
	// get chat room object from sessionStorage
	var chatRoomObj = JSON.parse(window.sessionStorage.getItem("chatRoom"));

	// get user data from friend list
    var userObj = {};
    userObj.uNo = $(element).children(".chat-room-user-no").text();
    userObj.uName = $(element).children(".chat-room-friend-name").text();
    userObj.snapshot = $(element).children(".chat-room-friend-icon").attr("src");

    // in order to check is the user already in the user list
    var checkFlag = true;
    for(let user of chatRoomObj.usersList){
        if(user.uNo == userObj.uNo){
            checkFlag = false;
        }
    }
    
    // use check flag to determine render or not
    if(checkFlag){
        // apeend new user to usersList in chat room object
    	chatRoomObj.usersList[chatRoomObj.usersList.length] = userObj;
    	
    	// update chat room object in session
    	window.sessionStorage.setItem("chatRoom",JSON.stringify(chatRoomObj));

        var renderData = Mustache.render(chatRoomUsersTemplate,userObj);

        // append new user diaglog block to chat room
        $("#chat-room-users").append(renderData);
    }
    
}

function showUserListFromMessage(msg){
	// get chat room object from sessionStorage
	var chatRoomObj = JSON.parse(window.sessionStorage.getItem("chatRoom"));
	
	// in order to check is the user already in the user list
    var checkFlag = true;
    for(let user of chatRoomObj.usersList){
        if(user.uNo == msg.from){
            checkFlag = false;
            
            // update brief message
            user.message = msg.message;
            user.time = msg.time;
        }
    }
    
    // use check flag to determine render or not
    if(checkFlag){
    	// get user data from friend list
        var userObj = {};
        userObj.uNo = msg.from;
        userObj.message = msg.message;
        userObj.time = msg.time;
        for(let user of chatRoomObj.friendsList){
        	if(user.uNo == userObj.uNo){
        		userObj.uName = user.uName;
        		userObj.snapshot = user.snapshot;
        	}
        }
        
    	if(userObj != undefined){
    		// apeend new user to usersList in chat room object
        	chatRoomObj.usersList[chatRoomObj.usersList.length] = userObj;
    	}
    	
    	// update chat room object in session
    	window.sessionStorage.setItem("chatRoom",JSON.stringify(chatRoomObj));

        var renderData = Mustache.render(chatRoomUsersTemplate,userObj);

        // append new user diaglog block to chat room
        $("#chat-room-users").append(renderData);
    }
	
	// update chat room object in session
	window.sessionStorage.setItem("chatRoom",JSON.stringify(chatRoomObj));
}

function hideChatRoom(flag){

    // close friend list
    $(".chat-room-friend-list-area").removeClass("friend-list-show",700,function(){
        // change button icon
        $("#friend-list-toggle-btn i").removeClass("fa-chevron-right");
        $("#friend-list-toggle-btn i").addClass("fa-chevron-left");

        // hide chat room area
        $(".chat-room-area").addClass("chat-room-hide",600,function(){
        	$("#chat-room-fn-area .fa-comments").show(700);
        	
        	// for logout use
        	if(flag){
        		$(".chat-room-area").hide();
        	}
        });
    });
    
}

function sendMessage(event){
	if(event.key == "Enter"){
		// get chat room object from sessionStorage
		var chatRoomObj = JSON.parse(window.sessionStorage.getItem("chatRoom"));
		
		var className = $("#chat-message-area").attr("class");
		var userNo = className.substring(className.indexOf("-") + 1);
		
		// get data from message input area
		var dateObj = new Date();
		var hours = '0' + dateObj.getHours();
		hours = hours.substring(hours.length - 2);
		var minutes = '0' + dateObj.getMinutes();
		minutes = minutes.substring(minutes.length - 2);
        var currentTime = hours + ':' + minutes;
        
        // set object for render message use
		var msgObj = {from: JSON.parse(window.sessionStorage.getItem("loginUser")).uNo,
					    to: [userNo],
				   message: $("#chat-room-input").val(),
					  time: Date.now()}
		
		// set object for sending use
		sendWebSocketMessage(msgObj);
		
		// set formated time for print
		msgObj.time = currentTime;
		
		// update brief message in users list
		$(".chat-room-user").each(function(){
			if($(this).children(".chat-room-user-no").text() == userNo){
				$(this).children(".chat-user-brief-message").text($("#chat-room-input").val());
				$(this).children(".chat-user-message-time").text(currentTime);
			}
		});
		
		// update chat history in session
		var flag = true;
		for(let singleChat of chatRoomObj.chatHistory){
			if(singleChat.userNo == userNo){
				singleChat.chatContent[singleChat.chatContent.length] = msgObj;
				flag = false;
			}
		}
		
		if(flag){
			var chatContent = {userNo:userNo,
						  chatContent:[msgObj]}
			
			chatRoomObj.chatHistory[chatRoomObj.chatHistory.length] = chatContent;
		}
		
		// update user list
		for(let i=0,len=chatRoomObj.usersList.length ; i<len ; i++){
			if(chatRoomObj.usersList[i].uNo == msgObj.to[0]){
				chatRoomObj.usersList[i].message = msgObj.message;
				chatRoomObj.usersList[i].time = msgObj.time;
//				console.log("Should update here");
			}
		}
		
		
		window.sessionStorage.setItem("chatRoom",JSON.stringify(chatRoomObj));
		

		
		// show data from message input area
		$("#chat-message-area").append(Mustache.render(ownMsgTemplate,msgObj));
		
		// move the scroll bar to the end
		$("#chat-message-area").scrollTop($("#chat-message-area").prop("scrollHeight"));
		
		// clean message input area
		$("#chat-room-input").val("");
	}
}

function cleanChatRoom(){
	$("#chat-room-users").html("");
	$("#chat-room-content").html("");
	$("#chat-room-friends").html("");
	chatRoom.usersList = [];
}

// WebSocket-STOMP control function
var stompClient = null;

function connectChatRoom() {
    var socket = new SockJS('/motozone/chat');
    stompClient = Stomp.over(socket);
    
    // console debug log setting (show / not show message)
    stompClient.debug = null;
    
    stompClient.connect({}, function(frame) {
        var url = stompClient.ws._transport.url;
//        console.log('Connected: ' + frame);
        stompClient.subscribe('/regist/messages', function(msgOutput) {
        	showOnlineUsers(JSON.parse(msgOutput.body));
        });
        stompClient.subscribe('/topic/messages', function(msgOutput) {
            if(JSON.parse(msgOutput.body).from == undefined){
            	hideOfflineUser(JSON.parse(msgOutput.body));
            } else {
            	showMessageOutput(JSON.parse(msgOutput.body));
            }
        });
        stompClient.subscribe('/user/queue/messages', function(msgOutput) {
            showMessageOutput(JSON.parse(msgOutput.body));
        });
        
		// get online list
        var data = JSON.parse(window.sessionStorage.getItem("loginUser"));
		sendWebSocketMessage({from:data.uNo,to:['regist'],message:'',time:Date.now()});
    });
}
 
function disconnectChatRoom() {
    if(stompClient != null) {
        stompClient.disconnect();
        console.log("Disconnected");
    } else {
    	console.log("Already Disconnected");
    }
}
 
function sendWebSocketMessage(msg) {
    stompClient.send("/app/chat", {}, JSON.stringify(msg));
}
 
function showMessageOutput(msgOutput) {
	// show in user list
	showUserListFromMessage(msgOutput);
	
	// get chat room object from sessionStorage
	var chatRoomObj = JSON.parse(window.sessionStorage.getItem("chatRoom"));
	
	// get user snapshot
	for(let friend of JSON.parse(window.sessionStorage.getItem("loginUser")).friendsList){
		if(friend.uNo == msgOutput.from) {
			msgOutput.snapshot = friend.snapshot;
		}
	}
	
	// update brief message and time in users list
	$(".chat-room-user").each(function(){
		if($(this).children(".chat-room-user-no").text() == msgOutput.from){
			$(this).children(".chat-user-brief-message").text(msgOutput.message);
			$(this).children(".chat-user-message-time").text(msgOutput.time);
		}
	});
	
	// add message into chat history
	var flag = true;
	for(let singleChat of chatRoomObj.chatHistory){
		if(singleChat.userNo == msgOutput.from){
			singleChat.chatContent[singleChat.chatContent.length] = msgOutput;
			flag = false;
		}
	}
	
	if(flag){
		var chatContent = {userNo:msgOutput.from,
					  chatContent:[msgOutput]}
		
		chatRoomObj.chatHistory[chatRoomObj.chatHistory.length] = chatContent;
	}
	
	// show data from message input area
	if($("#chat-message-area").hasClass("userNo-" + msgOutput.from)){
		$("#chat-message-area").append(Mustache.render(replyMsgTemplate,msgOutput));
		
		// move the scroll bar to the end
		$("#chat-message-area").scrollTop($("#chat-message-area").prop("scrollHeight"));
	}
	
	// update chat room object in session
	window.sessionStorage.setItem("chatRoom",JSON.stringify(chatRoomObj));
	
}

function showOnlineUsers(userList){
	// close all online mark first
	$(".chat-room-friend .chat-room-friend-online-light").hide(500);
	
	// show specific online mark
	$(".chat-room-friend").each(function(){
		var userNo = $(this).children(".chat-room-user-no").text();
		
		for(var user of userList){
			if(userNo == user){
				$(this).children(".chat-room-friend-online-light").show(500);
			}
		}
	});
}

function hideOfflineUser(user){
	// hide specific online mark
	$(".chat-room-friend").each(function(){
		var userNo = $(this).children(".chat-room-user-no").text();
//		console.log(userNo);
		
		if(userNo == user){
			$(this).children(".chat-room-friend-online-light").hide(500);
		}
	});
}

//chat room friend list template
//<div class="chat-room-friend">
//	<img class="chat-room-friend-icon" src="img/userIcon.png" />
//	<div class="chat-room-friend-name">我GM</div>
//	<div class="chat-room-user-no">100</div>
//	<div class="chat-room-friend-online-light"></div>
//</div>

var chatRoomFriendsListTemplate = '{{#friendsList}}'
							   + '<div class="chat-room-friend" onclick="showUsersDiaglog(this)">'
							   + '<img class="chat-room-friend-icon" src="{{&snapshot}}{{^snapshot}}/motozone/img/userIcon.png{{/snapshot}}" />'
							   + '<div class="chat-room-friend-name">{{uName}}</div>'
							   + '<div class="chat-room-user-no">{{uNo}}</div>'
							   + '<div class="chat-room-friend-online-light"></div>'
							   + '</div>'
							   + '{{/friendsList}}';

// chat room users template
//<div class="chat-room-user" onclick="showChatContentArea(this)" >
//    <img class="chat-user-icon" src="img/userIcon.png" />
//    <div class="chat-user-name">名字名字名字名字名字名字名字</div>
//    <div class="chat-room-user-no">0</div>
//    <div class="chat-user-brief-message">
//        訊息訊息訊息訊息訊息訊息訊息訊息
//    </div>
//    <div class="chat-user-message-time">
//        下午13:03
//    </div>
//</div>

var chatRoomUsersTemplate = '<div class="chat-room-user" onclick="showChatContentArea(this)" >'
                          + '<img class="chat-user-icon" src="{{&snapshot}}{{^snapshot}}/motozone/img/userIcon.png{{/snapshot}}" />'
                          + '<div class="chat-user-name">{{uName}}</div>'
                          + '<div class="chat-room-user-no">{{uNo}}</div>'
                          + '<div class="chat-user-brief-message">{{message}}</div>'
                          + '<div class="chat-user-message-time">{{time}}</div>'
                          + '</div>';

// char room content area template
//<div id="chat-header-area">
//    <div id="chat-header-name">
//        名字在這名字在這名字在這名字在這名字在這名字在這
//    </div>
//    <i class="fas fa-times" onclick="emptyChatContentArea()"></i>
//</div>
//<div id="chat-message-area">
//    <div class="chat-messages">
//        <img class="chat-message-user-icon" src="img/userIcon.png"/>
//        <div class="chat-message">
//            安安安安安安安
//        </div>
//        <div class="chat-time">
//            下午 13:03
//        </div>
//    </div>
//    <div class="chat-messages own-messages">
//        <img class="chat-message-user-icon" src="img/userIcon.png"/>
//        <div class="chat-message">
//            安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安
//        </div>
//        <div class="chat-time">
//            下午 13:03
//        </div>
//    </div>
//</div>
//<div id="chat-room-input-area">
//    <input type="text" id="chat-room-input"/>
//</div>

var chatRoomContentTemplate = '<div id="chat-header-area">'
                            + '<div id="chat-header-name">{{uName}}</div>'
                            + '<i class="fas fa-times" onclick="emptyChatContentArea()"></i>'
                            + '</div>'
                            + '<div id="chat-message-area" class="userNo-{{uNo}}"></div>'
                            + '<div id="chat-room-input-area">'
                            + '<input type="text" id="chat-room-input" autocomplete="off" onkeypress="sendMessage(event)"/>'
                            + '</div>';
  
  
//chat message template
//<div id="chat-message-area">
//<div class="chat-messages">
//    <img class="chat-message-user-icon" src="img/userIcon.png"/>
//    <div class="chat-message">
//       	 安安安安安安安
//    </div>
//    <div class="chat-time">
//        	下午 13:03
//    </div>
//</div>
//<div class="chat-messages own-messages">
//    <img class="chat-message-user-icon" src="img/userIcon.png"/>
//    <div class="chat-message">
//       	 安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安安
//    </div>
//    <div class="chat-time">
//        	下午 13:03
//    </div>
//</div>
//</div>

var replyMsgTemplate = '<div class="chat-messages">'
				     + '<img class="chat-message-user-icon" src="{{&snapshot}}{{^snapshot}}/motozone/img/userIcon.png{{/snapshot}}"/>'
				     + '<div class="chat-message">{{&message}}</div>'
				     + '<div class="chat-time">{{time}}</div>'
				     + '</div>';

var ownMsgTemplate = '<div class="chat-messages own-messages">'
	   			   + '<div class="chat-message">{{&message}}</div>'
	   			   + '<div class="chat-time">{{time}}</div>'
	   			   + '</div>';


/* ----------------------------------------------------------- */
/*  9. AUTOBUY CONTROL
/* ----------------------------------------------------------- */ 

var autoBuyFlag = false;

function updateProductPostAttr(userNo,userName){
	$("#productPostForm").attr({"action":"/motozone/AutoBuyCart/" + userNo});
	
	$(".hidden_itemid input[name='userNo']").attr({"value":userNo});
	
	$(".hidden_itemid input[name='userName']").attr({"value":userName});
	
}

function updateAutoBuyPublishAttr(userNo){
	$(".hidden_itemid input[name='userNo']").attr({"value":userNo});
}

function updateCartLink(userNo){
	
	if(userNo == ""){
		$("#shoppingCart a").attr({"href":"#"});
	} else {
		$("#shoppingCart a").attr({"href":"/motozone/AutoBuyCart/" + userNo});
	}
	
}


