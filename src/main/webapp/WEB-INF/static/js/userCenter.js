// check login
if(window.sessionStorage.getItem("loginUser") == ""){
	location.href = "/motozone/";
}

// check authority
$(document).ready(function(){
	if(JSON.parse(window.sessionStorage.getItem("loginUser")).aID != "SA"){
		$("#report-area").hide();
		$("#report-icon").hide();
	}
});


function showOpArea(opArea){
    $("#usercenter-title").fadeOut(500);
    $(".control-panel-area").fadeOut(500,function(){
        $(".side-control-panel-area").fadeIn(500,function(){
            opArea.removeClass("hidden-window");
            opArea.fadeIn(500);
        });
    });
}

function changeOpArea(opArea){
    $(".op-area").fadeOut(500);
    opArea.delay(500).fadeIn(500);
}


// field for Jcrop
var jcropObj;
var posX = 0;
var posY = 0;
var posW = 0;
var posH = 0;

$(document).ready(function(){
    $(".side-control-panel-area").fadeOut(700);

    // main panel click setting
    $("#user-data-area").click(function(){
        showOpArea($("#user-data-op-area"));
        showUserData();
    });

    $("#article-area").click(function(){
        showOpArea($("#article-op-area"));
        showArticleData();
    });

    $("#tx-area").click(function(){
        showOpArea($("#tx-op-area"));
        showTxOpAreaData();
    });

    $("#service-area").click(function(){
        showOpArea($("#service-op-area"));
    });

    $("#report-area").click(function(){
        showOpArea($("#report-op-area"));
    });

    // side panel click setting
    $("#user-data-icon").click(function(){
        changeOpArea($("#user-data-op-area"));
        showUserData();
    });

    $("#article-icon").click(function(){
        changeOpArea($("#article-op-area"));
        showArticleData();
    });

    $("#tx-icon").click(function(){
        changeOpArea($("#tx-op-area"));
        showTxOpAreaData();
    });

    $("#service-icon").click(function(){
        changeOpArea($("#service-op-area"));
    });

    $("#report-icon").click(function(){
        changeOpArea($("#report-op-area"));
    });

    // return main panel
    $(".op-close").click(function(){
        $(".op-area").fadeOut(500,function(){
            $(".side-control-panel-area").fadeOut(500,function(){
                $("#usercenter-title").fadeIn(500);
                $(".control-panel-area").fadeIn(500);
            });
        });
    });


    // user data operation area
    $("#basic-data-tab").click(function(){
        $(".tab").removeClass("tab-select");
        $("#basic-data-tab").addClass("tab-select");

        $(".data-area").fadeOut(500);
        $("#basic-data-area").fadeIn(500);
    });

    $("#tx-data-tab").click(function(){
        $(".tab").removeClass("tab-select");
        $("#tx-data-tab").addClass("tab-select");

        $(".data-area").fadeOut(500);
        $("#tx-data-area").fadeIn(500);
    });

    $("#auth-data-tab").click(function(){
        $(".tab").removeClass("tab-select");
        $("#auth-data-tab").addClass("tab-select");

        $(".data-area").fadeOut(500);
        $("#auth-data-area").fadeIn(500);
    });
    
    // user data operation area 190530 update
	$("#user-snapshot-popup-btn").click(function() {
		$("#user-snapshot-popup").removeClass("hidden-window", 700);
		$("#shadow").fadeIn(700);
	});

	$("#user-password-popup-btn").click(function() {
		$("#user-password-popup").removeClass("hidden-window", 700);
		$("#shadow").fadeIn(700);
	});

	$("#user-email-popup-btn").click(function() {
		$("#user-email-popup").removeClass("hidden-window", 700);
		$("#shadow").fadeIn(700);
	});

	$("#user-name-popup-btn").click(function() {
		$("#user-name-popup").removeClass("hidden-window", 700);
		$("#shadow").fadeIn(700);
	});

	$("#user-personid-popup-btn").click(function() {
		$("#user-personid-popup").removeClass("hidden-window", 700);
		$("#shadow").fadeIn(700);
	});

	$("#user-birth-popup-btn").click(function() {
		$("#user-birth-popup").removeClass("hidden-window", 700);
		$("#shadow").fadeIn(700);
	});

	$("#user-sex-popup-btn").click(function() {
		$("#user-sex-popup").removeClass("hidden-window", 700);
		$("#shadow").fadeIn(700);
	});

	$("#user-phone-popup-btn").click(function() {
		$("#user-phone-popup").removeClass("hidden-window", 700);
		$("#shadow").fadeIn(700);
	});

	$("#user-address-popup-btn").click(function() {
		$("#user-address-popup").removeClass("hidden-window", 700);
		$("#shadow").fadeIn(700);
	});

	// End data operation area 190530 update

	// snapshot data area
	$("#user-snapshot").change(function() {
		var files = document.getElementById("user-snapshot").files;
		
		for (var i = 0; i < files.length; i++) {
			var file = files[i];
		}
		
		document.getElementById("show-snapshot-area").innerHTML = file.name;
	});
	
	
	$("#image-crop-btn").click(function(){
		// disabled the update button
		$("#image-crop-btn").attr("disabled", true);
		
		// display loading gif
		$("#image-crop-btn img").css({"opacity":"1"});
		
		// get image source
		var imgSrc = $("#image-crop-target").attr("src");
		
        ratio = $("#image-crop-target").prop("width") / $("#image-crop-target").prop("naturalWidth");
        
        // send to server
        $.ajax({
        	url: "/motozone/Image",
           type: "PUT",
           data: JSON.stringify({posX:Math.round(posX / ratio),
					        	 posY:Math.round(posY / ratio),
					        	 posW:Math.round(posW / ratio),
					        	 posH:Math.round(posH / ratio),
					           imgSrc:imgSrc}),
        success: function(data){
        			
        			if(data){
        				// get new image's url
        				subImgSrc = data.imgSrc;
        				
        				userData.snapshot = subImgSrc;   
        	    		
        	    		$.ajax({
        					url : "/motozone/Users/" + userData.uID,
        					type : "PUT",
        					contentType : "application/json",
        					data : JSON.stringify(userData),
        					success : function(data) {
        						
        						if(data){
        							userData = data;
            						
            						// change user data operation area's snapshot
            						$("#user-snapshot-img").attr("src",data.snapshot);
            						
            						// change user icon in header
            						$(".loggedin-icon img").attr("src",data.snapshot);
            						
            						// update login user data in session
            						var loginUserData = JSON.parse(window.sessionStorage.getItem("loginUser"));
            						loginUserData.snapshot = data.snapshot;
            						window.sessionStorage.setItem("loginUser",JSON.stringify(loginUserData));
            						
            						
            						// close pop-up window
            						$(".image-crop-popup-area").addClass("hidden-window",700);
            						$("#shadow").fadeOut(700);
            						
            						// destroy jcrop instance
                        	        jcropObj.destroy();
        						} 
        						
        						
    							// active the update button
    	        				$("#image-crop-btn").attr("disabled", false);
    	        				
    	        				// hide loading gif
    	        				$("#image-crop-btn img").css({"opacity":"0"});
        					
        					}
        				});
        				
        				
        			} else {
        				alert("圖片上傳失敗");
        				
        				// active the update button
        				$("#image-crop-btn").attr("disabled", false);
        				
        				// hide loading gif
        				$("#image-crop-btn img").css({"opacity":"0"});
        			}
        			
        		},
		   error: function(){
		   		alert("上傳失敗！\n請檢查網路並重新上傳");
		   		
		   		// active the update button
				$("#image-crop-btn").attr("disabled", false);
				
				// hide loading gif
				$("#image-crop-btn img").css({"opacity":"0"});
		  }
        });
        
        
	});

	// check user data area
	$("#user-password").change(function() {
		var rePassword = /^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])[A-Za-z\d]{8,15}$/;
		
		if (rePassword.test($(this).val())) { // accepted format
			$(this).parent().removeClass("error-format");
			$(this).parent().addClass("accepted-format");
		} else if ($(this).val() == "") {
			$(this).parent().removeClass("error-format accepted-format");
		} else { // not-accepted format
			$(this).parent().removeClass("accepted-format");
			$(this).parent().addClass("error-format");
		}
	});

	$("#user-password-check").change(function() {
		var pwd = $(this).parent().prev().children("#user-password").val();
		
		if (pwd == $(this).val()) { // accepted format
			$(this).parent().removeClass("error-format");
			$(this).parent().addClass("accepted-format");
		} else if ($(this).val() == "") {
			$(this).parent().removeClass("error-format accepted-format");
		} else { // not-accepted format
			$(this).parent().removeClass("accepted-format");
			$(this).parent().addClass("error-format");
		}
	});

	$("#user-email").change(function() {
		var reEmail = /^[a-zA-Z]{1}[\w-]+@[a-z0-9]+\.[a-z]+$/;
		
		if (reEmail.test($(this).val())) { // accepted format
			$(this).parent().removeClass("error-format");
			$(this).parent().addClass("accepted-format");
		} else if ($(this).val() == "") {
			$(this).parent().removeClass("error-format accepted-format");
		} else { // not-accepted format
			$(this).parent().removeClass("accepted-format");
			$(this).parent().addClass("error-format");
		}
	});

	$("#user-name").change(function() {
		var reNameCh = /^[\u4E00-\u9FA5]{2,7}$/;
		var reNameEn = /^[A-Z]+\s{1}[A-Z]+(\s{0,1}[A-Z]+)*$/;

		if (reNameCh.test($(this).val())|| reNameEn.test($(this).val())) { // accepted format
			$(this).parent().removeClass("error-format");
			$(this).parent().addClass("accepted-format");
		} else if ($(this).val() == "") {
			$(this).parent().removeClass("error-format accepted-format");
		} else { // not-accepted format
			$(this).parent().removeClass("accepted-format");
			$(this).parent().addClass("error-format");
		}
	});

	$("#user-personid").change(function() {
		var rePersonid = /^[A-Z]{1}[1-2]{1}[0-9]{8}$/;
		
		if (rePersonid.test($(this).val())) { // accepted format
			$(this).parent().removeClass("error-format");
			$(this).parent().addClass("accepted-format");
		} else if ($(this).val() == "") {
			$(this).parent().removeClass("error-format accepted-format");
		} else { // not-accepted format
			$(this).parent().removeClass("accepted-format");
			$(this).parent().addClass("error-format");
		}
	});


	$("#user-phone").change(function() {
		var rePhone = /^[09]{2}[0-9]{8}$/;
		
		if (rePhone.test($(this).val())) { // accepted format
			$(this).parent().removeClass("error-format");
			$(this).parent().addClass("accepted-format");
		} else if ($(this).val() == "") {
			$(this).parent().removeClass("error-format accepted-format");
		} else { // not-accepted format
			$(this).parent().removeClass("accepted-format");
			$(this).parent().addClass("error-format");
		}
	});

	// update user data area

	$("#update-user-snapshot-btn").click(function() {
		// disabled the update button
		$("#update-user-snapshot-btn").attr("disabled", true);
		
		// display loading gif
		$("#update-user-snapshot-btn img").css({"opacity":"1"});
		
		let formData = new FormData();
    	formData.append('upload',$("#user-snapshot")[0].files[0]);
    	
    	$.ajax({
    		url:"/motozone/Imgur",
    	   type:"POST",
    	   data:formData,
    contentType: false, 
    processData: false,
    	success:function(data){
    		
		    		// set crop target image source
		    		$("#image-crop-target").attr({"src":data.url});
		    		
		    		// initialize Jcrop instance
		    		jcropObj = Jcrop.attach("image-crop-target",{
				        aspectRatio: 1
				   });
		
					jcropObj.listen('crop.change',function(widget,e){
					    const pos = widget.pos;
					    posX = pos.x;
					    posY = pos.y;
					    posW = pos.w;
					    posH = pos.h;
					});
					
					// close image upload pop-up window
					$("#user-snapshot-popup").addClass("hidden-window",700);
					
					// active the update button
					$("#update-user-snapshot-btn").attr("disabled", false);
					
					// hide loading gif
					$("#update-user-snapshot-btn img").css({"opacity":"0"});
					
					// clean input area
					cleanUserDataInput();
					
					// show image crop area
					$(".image-crop-popup-area").removeClass("hidden-window",700);
		    		
		    		
		    	},
	   error: function(){
		   		alert("上傳失敗！\n請檢查網路並重新上傳");
		   		
			   	// active the update button
				$("#update-user-snapshot-btn").attr("disabled", false);
				
				// hide loading gif
				$("#update-user-snapshot-btn img").css({"opacity":"0"});
	   		  }
    	});
	});

	$("#update-user-password-btn").click(function() {
		var inputCheck = true;

		$("#password-form .input-group").each(function() {
			if (!($(this).hasClass("accepted-format"))) {
				inputCheck = false;
			}
		});

		if (inputCheck) {
			userData.pwd = $("#user-password").val();

			$.ajax({
				url : "/motozone/Users/" + userData.uID,
				type : "PUT",
				contentType : "application/json",
				data : JSON.stringify(userData),
				success : function(data) {
					userData = data;
					$("#user-phone-show-area").text("**********");
					
					// close pop-up window
					$("#user-password-popup").addClass("hidden-window",700);
					$("#shadow").fadeOut(700);
					
					// clean input area
					cleanUserDataInput();
				}
			});
		} else {
			alert("輸入資料格式有誤");
		}
	});

	$("#update-user-email-btn").click(function() {
		var inputCheck = true;

		$("#email-form .input-group").each(function() {
			if (!($(this).hasClass("accepted-format"))) {
				inputCheck = false;
			}
		});

		if (inputCheck) {
			userData.email = $("#user-email").val();

			$.ajax({
				url : "/motozone/Users/" + userData.uID,
				type : "PUT",
				contentType : "application/json",
				data : JSON.stringify(userData),
				success : function(data) {
					userData = data;
					$("#user-email-show-area").text(data.email);

					// close pop-up window
					$("#user-email-popup").addClass("hidden-window",700);
					$("#shadow").fadeOut(700);
					
					// clean input area
					cleanUserDataInput();
				}
			});
		} else {
			alert("輸入資料格式有誤");
		}
	});

	$("#update-user-name-btn").click(function() {
		var inputCheck = true;

		$("#name-form .input-group").each(function() {
			if (!($(this).hasClass("accepted-format"))) {
				inputCheck = false;
			}
		});

		if (inputCheck) {
			userData.uName = $("#user-name").val();

			$.ajax({
				url : "/motozone/Users/" + userData.uID,
				type : "PUT",
				contentType : "application/json",
				data : JSON.stringify(userData),
				success : function(data) {
					userData = data;
					$("#user-name-show-area").text(data.uName);

					// close pop-up window
					$("#user-name-popup").addClass("hidden-window",700);
					$("#shadow").fadeOut(700);
					
					// clean input area
					cleanUserDataInput();
				}
			});
		} else {
			alert("輸入資料格式有誤");
		}
	});

	$("#update-user-personid-btn").click(function() {
		var inputCheck = true;

		$("#personid-form .input-group").each(function() {
			if (!($(this).hasClass("accepted-format"))) {
				inputCheck = false;
			}
		});

		if (inputCheck) {
			userData.personID = $("#user-personid").val();

			$.ajax({
				url : "/motozone/Users/" + userData.uID,
				type : "PUT",
				contentType : "application/json",
				data : JSON.stringify(userData),
				success : function(data) {
					userData = data;
					$("#user-personid-show-area").text(data.personID);
					
					// close pop-up window
					$("#user-personid-popup").addClass("hidden-window",700);
					$("#shadow").fadeOut(700);
					
					// clean input area
					cleanUserDataInput();
				}
			});
		} else {
			alert("輸入資料格式有誤");
		}
	});

	$("#update-user-birth-btn").click(function() {
		var inputCheck = true;

		if (inputCheck) {
			userData.birth = $("#user-birth").val();

			$.ajax({
				url : "/motozone/Users/" + userData.uID,
				type : "PUT",
				contentType : "application/json",
				data : JSON.stringify(userData),
				success : function(data) {
					userData = data;
					$("#user-birth-show-area").text(data.birth);
					
					// close pop-up window
					$("#user-birth-popup").addClass("hidden-window",700);
					$("#shadow").fadeOut(700);
					
					// clean input area
					cleanUserDataInput();
				}
			});
		} else {
			alert("輸入資料格式有誤");
		}
	});

	$("#update-user-sex-btn").click(function() {
		userData.sex = $("input[name='gender']:checked").val();

		$.ajax({
			url : "/motozone/Users/" + userData.uID,
			type : "PUT",
			contentType : "application/json",
			data : JSON.stringify(userData),
			success : function(data) {
				userData = data;
				if(data.sex == "male"){
					$("#user-sex-show-area").text("男");
				}else if(data.sex == "female"){
					$("#user-sex-show-area").text("女");
				}else{
					$("#user-sex-show-area").text("不願透露");
				}
				
				// close pop-up window
				$("#user-sex-popup").addClass("hidden-window",700);
				$("#shadow").fadeOut(700);
				
				// clean input area
				cleanUserDataInput();
			}
		});
	});

	$("#update-user-phone-btn").click(function() {
		var inputCheck = true;

		$("#phone-form .input-group").each(function() {
			if (!($(this).hasClass("accepted-format"))) {
				inputCheck = false;
			}
		});

		if (inputCheck) {
		userData.phone = $("#user-phone").val();

		$.ajax({
			 url: "/motozone/Users/" + userData.uID,
			type: "PUT",
	        contentType: "application/json",
			data: JSON.stringify(userData),
		 success: function(data) {
					userData = data;
					$("#user-phone-show-area").text(data.phone);
					
					// close pop-up window
					$("#user-phone-popup").addClass("hidden-window",700);
					$("#shadow").fadeOut(700);
					
					// clean input area
					cleanUserDataInput();
				}
		});
		} else {
			alert("輸入資料格式有誤");
		}
	});

	$("#update-user-address-btn").click(function() {
		userData.addr = $("select[name='city']").val() + $("select[name='townShip']").val() + $("input[name='address']").val();
		$.ajax({
			url : "/motozone/Users/" + userData.uID,
			type : "PUT",
			contentType : "application/json",
			data : JSON.stringify(userData),
			success : function(data) {
				userData = data;
				$("#user-address-show-area").text(data.addr);
				
				// close pop-up window
				$("#user-address-popup").addClass("hidden-window",700);
				$("#shadow").fadeOut(700);
				
				// clean input area
				cleanUserDataInput();
			}
		});
	});

	// empty popup

	$("#empty-popup-btn").click(function() {
		cleanUserDataInput();
	});
	
	// authority area
	$("#auth-pm-upgrade-btn").click(function(){
		authUpgrade(userData,'PM');
	});
	
	$("#auth-dm-upgrade-btn").click(function(){
		authUpgrade(userData,'DM');
	});
    
});

function authUpgrade(userData,authID){
	$.ajax({
		url : "/motozone/EcPay/Authority/" + authID,
		type : "POST",
		data : JSON.stringify(userData),
		contentType : "application/json",
		success : function(data) {
			$("#authority-redirect-popup").html(data);
		}
	});
}



// user data operation area
// get user data
function showUserData(){
	$.ajax({
		url : "/motozone/UsersAdmin/"
				+ JSON.parse(window.sessionStorage
						.getItem("loginUser")).uID,
		type : "GET",
		success : function(data) {
			userData = data;
			if(data.snapshot){
				$("#user-snapshot-img").attr("src",data.snapshot);
			} else {
				$("#user-snapshot-img").attr("src","/motozone/img/userIcon.png");
			}
			
			if(data.authID == "GM"){
				$("#user-authority-show-area").text("一般會員");
				$("#auth-gm-upgrade-btn").css("visibility","hidden");
			} else if(data.authID == "PM"){
				$("#user-authority-show-area").text("白金會員");
				$("#auth-gm-upgrade-btn").css("visibility","hidden");
				$("#auth-pm-upgrade-btn").css("visibility","hidden");
			} else if(data.authID == "DM"){
				$("#user-authority-show-area").text("鑽石會員");
				$("#auth-gm-upgrade-btn").css("visibility","hidden");
				$("#auth-pm-upgrade-btn").css("visibility","hidden");
				$("#auth-dm-upgrade-btn").css("visibility","hidden");
			} else {
				$("#user-authority-show-area").text("");
			}
			
			if(data.uID == null || data.uID == ""){
				$("#user-id-show-area").text("");
			}else{
				$("#user-id-show-area").text(data.uID);
			}
			
			if(data.email == null || data.email == ""){
				$("#user-email-show-area").text("");
			}else{
				$("#user-email-show-area").text(data.email);
			}
			if(data.uName == null || data.uName == ""){
				$("#user-name-show-area").text("");
			}else{
				$("#user-name-show-area").text(data.uName);
			}
			if(data.personID == null || data.personID == ""){
				$("#user-personid-show-area").text("");
			}else{
				$("#user-personid-show-area").text(data.personID);
			}
			if(data.birth == null || data.birth == ""){
				$("#user-birth-show-area").text("");
			}else{
				$("#user-birth-show-area").text(data.birth);
			}
			if(data.sex == "male"){
				$("#user-sex-show-area").text("男");
			}else if(data.sex == "female"){
				$("#user-sex-show-area").text("女");
			}else{
				$("#user-sex-show-area").text("不願透露");
			}
			if(data.phone == null || data.phone == ""){
				$("#user-phone-show-area").text("");
			}else{
				$("#user-phone-show-area").text(data.phone);
			}
			if(data.addr == null || data.addr == ""){
				$("#user-address-show-area").text("");
			}else{
				$("#user-address-show-area").text(data.addr);
			}
		}
	});
}

function cleanUserDataInput(){
	$("#show-snapshot-area").empty();
	$("#user-password").val("");
	$("#user-password-check").val("");
	$("#user-email").val("");
	$("#user-name").val("");
	$("#user-personid").val("");
	$("#user-birth").val("");
	$("#user-phone").val("");
	$("#user-address").val("");
	
	// clean checker mark
	$(".input-group").removeClass("accepted-format error-format");
}



// Start article operation area

$(document).ready(function(){
	$(".article-modify-area .close-btn").click(function(){
		$("#secondShadow").fadeOut(700);
	});
	
	$("#modify-submit").click(function(){
		// get postNo
		var postNo = $(".article-modify-area .article-no").text();
		
		// get content
		var content = modifyEditor.getData();
		
		// set JOSN object
		var obj = {postNo: postNo,
				  content: content}
		
		// empty check
		if(content != "" && postNo != ""){
			$.ajax({
				url: "/motozone/Article",
			   type: "PUT",
			   data: JSON.stringify(obj),
			success: function(data){
				
						// empty check
						if(data){
							
							// update specific post by no
							$("#user-post-list .article-no").each(function(){
								
								if($(this).text() == data.no){
									
									var rowData = $(this).parent().parent();
									
									rowData.find(".article-content").text(data.content);
								}
								
							});
							
							// close pop-up window
							$(".article-modify-area").addClass("hidden-window",700);
							$("#secondShadow").fadeOut(700);
						} else {
							console.log("更新失敗");
						}
					}
			});
			
		} else {
			alert("Something goes wrong");
		}
		
	});
});

//modify editor instance
let modifyEditor;

function showArticleData(){
	var userNo = JSON.parse(window.sessionStorage.getItem("loginUser")).uNo;
	
	$.ajax({
		url: "/motozone/ArticlePost/" + userNo,
	   type: "GET",
	success: function(data){
		
				// empty check
				if(data){
					var postList = Mustache.render(postListTemplate,{postList:data});
					$("#user-post-list tbody").html(postList);
					
				} else {
					console.log("No data");
				}
			}
	});
}

// for article operation area
function showArticleModifyWindow(element){
    var rowData = $(element).parent().parent();
    var postNo = rowData.find(".article-no").text();
    var postTitle = rowData.find(".article-title").text();
    
    // set data
    var postObj;
    $.ajax({
    	url: "/motozone/ArticlePost/PostNo/" + postNo,
       type: "GET",
    success: function(data){
    	
    			// empty check
    			if(data){
    				
    				// set postNo
    				$(".article-modify-area .article-no").text(data.no);
    				
    				// set title
    				$("#article-modify-title").text(data.title);
    				
    				// set editor area
    				modifyEditor.setData(data.content);
    				
    				// show window
    			    $(".article-modify-area").removeClass("hidden-window",700);
    			    $("#secondShadow").fadeIn(700);
    			    
    			} else {
    				console.log("Fail to get post from database");
    			}
    			
    		}
    });
    
    
        

}

function deletePost(element){
    var rowData = $(element).parent().parent();
    var postNo = rowData.find(".article-no").text();
    
    
    // prevent User's mis-clicking
    if(confirm("確認刪除？")){
    	$.ajax({
        	url: "/motozone/Article/" + postNo,
           type: "DELETE",
        success: function(data){
        			
        			// success check
        			if(data){
        				rowData.remove();
        			} 
        			
        		}
        });
    }
}

function connectToPost(element){
    var rowData = $(element).parent().parent();
    var postId = rowData.find(".article-id").text();

    window.open("/motozone/Posts/" + postId);
}

// post list template
//<tr>
//<td>
//    <div class="article-no">789</div>
//    <div class="article-id">888</div>
//    <div class="article-title">HONDA神車</div>
//</td>
//<td><div class="article-date">2019/05/12</div></td>
//<td><div class="article-content">hahahahahahahahahahahahahahaha</div></td>
//<td><i class="fas fa-external-link-alt" id="article-modify-connect" onclick="connectToPost(this)"></i></td>
//<td><i class="fas fa-edit" id="article-modify-submit" onclick="showArticleModifyWindow(this)"></i></td>
//<td><i class="fas fa-trash-alt" id="article-delete-submit" onclick="deletePost(this)"></i></td>
//</tr>

var postListTemplate = '{{#postList}}'
					 + '<tr>'
					 + '<td>'
					 + '<div class="article-no">{{no}}</div>'
					 + '<div class="article-id">{{id}}</div>'
					 + '<div class="article-title">{{title}}</div>'
					 + '</td>'
					 + '<td><div class="article-date">{{date}}</div></td>'
					 + '<td><div class="article-content">{{content}}</div></td>'
					 + '<td><i class="fas fa-external-link-alt" id="article-modify-connect" onclick="connectToPost(this)"></i></td>'
					 + '<td><i class="fas fa-edit" id="article-modify-submit" onclick="showArticleModifyWindow(this)"></i></td>'
					 + '<td><i class="fas fa-trash-alt" id="article-delete-submit" onclick="deletePost(this)"></i></td>'
					 + '</tr>'
					 + '{{/postList}}';

// End article operation area


// Start tx operation area
$(document).ready(function(){
	$("#tx-buy-tab").click(function(){
        $(".tab").removeClass("tab-select");
        $("#tx-buy-tab").addClass("tab-select");
        $(".data-area").fadeOut(500);
        $("#tx-buy-area").fadeIn(500);
    });

    
    $("#tx-sell-tab").click(function(){
        $(".tab").removeClass("tab-select");
        $("#tx-sell-tab").addClass("tab-select");
        $(".data-area").fadeOut(500);
        $("#tx-sell-area").fadeIn(500);
    });
    
    $(".tx-sell-popup-area .close-btn").click(function(){
    	$("#secondShadow").fadeOut(700);
    });
});

function showTxOpAreaData(){
	var userNo = JSON.parse(window.sessionStorage.getItem("loginUser")).uNo;
	
	$.ajax({
		url: "/motozone/OrderId/" + userNo,
	   type: "GET",
	success: function(data){
				
				if(data){
					var renderData = Mustache.render(txBuyListTemplate,{orderList:data});
					
					$("#tx-buy-area table tbody").html(renderData);
				} else {
					console.log("No Result");
				}
			
			}
		
	});
	
	$.ajax({
		url: "/motozone/ProductPosts/" + userNo,
	   type: "GET",
	success: function(data){
				
				if(data){
					var renderData = Mustache.render(txSellListTemplate,{sellProductList:data});
					
					$("#tx-sell-area table tbody").html(renderData);
				} else {
					console.log("No Result");
				}
			
			}
		
	});
}

function showOrderDetail(element){
    var rowData = $(element).parent().parent();
    var orderID = parseInt(rowData.find(".order-id").text());
    
    $.ajax({
    	url: "/motozone/OrderView/" + orderID,
       type: "GET",
    success: function(data){
    			
    			//empty check
    			if(data){
    				var renderData = Mustache.render(txBuyListDetailTemplate,{orderViewList:data});
					
					$(".tx-sell-popup-area table tbody").html(renderData);
    				
					// show window after data input
					$(".tx-sell-popup-area").removeClass("hidden-window",700);
					$("#secondShadow").fadeIn(700);
					
    			} else {
    				console.log("No Result");
    			}
    			
    		}
    	
    });

}

function connectToProductPost(element){
    var rowData = $(element).parent().parent();
    var productID = parseInt(rowData.find(".tx-sell-id").text());

    window.open("/motozone/ProductPost/" + productID);
}

//<tr>
//<td class="order-id">00000001</td>
//<td>2019/05/12</td>
//<td>5000</td>
//<td><i class="fas fa-list-alt order-detail" onclick="showOrderDetail(this)"></i></td>
//<td>
//    <a href="/motozone/OrdersView/PDF/">
//        <i class="fas fa-file-pdf pdf-download"></i>
//    </a>
//    <a href="/motozone/OrdersView/Excel/">
//        <i class="fas fa-file-excel excel-download"></i>
//    </a>
//</td>
//</tr>

var txBuyListTemplate = '{{#orderList}}'
					  + '<tr>'
					  + '<td class="order-id">{{oID}}</td>'
					  + '<td>{{date}}</td>'
					  + '<td>{{total}}</td>'
					  + '<td><i class="fas fa-list-alt order-detail" onclick="showOrderDetail(this)"></i></td>'
					  + '<td>'
					  + '<a href="/motozone/OrdersView/PDF/{{oID}}">'
					  + '<i class="fas fa-file-pdf pdf-download"></i>'
					  + '</a>'
					  + '<a href="/motozone/OrdersView/Excel/{{oID}}">'
					  + '<i class="fas fa-file-excel excel-download"></i>'
					  + '</a>'
					  + '</td>'
					  + '</tr>'
					  + '{{/orderList}}';


//<tr>
//<td>
//    <div class="tx-sell-id">1</div>
//    <div class="tx-sell-title">Honda神車</div>
//    
//</td>
//<td><div class="tx-sell-price">1000</div></td>
//<td><div class="tx-sell-qty">1000</div></td>
//<td>
//    <i class="fas fa-external-link-alt tx-sell-connect" onclick="connectToProductPost(this)"></i>
//</td>                            
//</tr>

var txSellListTemplate =  '{{#sellProductList}}'
						+ '<tr>'
						+ '<td>'
						+ '<div class="tx-sell-id">{{id}}</div>'
						+ '<div class="tx-sell-title">{{name}}</div>'
						+ '</td>'
						+ '<td><div class="tx-sell-price">{{price}}</div></td>'
						+ '<td><div class="tx-sell-qty">{{quantity}}</div></td>'
						+ '<td>'
						+ '<i class="fas fa-external-link-alt tx-sell-connect" onclick="connectToProductPost(this)"></i>'
						+ '</td> '
						+ '</tr>'
						+ '{{/sellProductList}}';


//<tr>
//<td><div class="tx-sell-title">Honda神車</div></td>
//<td><div class="tx-sell-price">1000</div></td>
//<td><div class="tx-sell-qty">2</div></td>
//<td><div class="tx-sell-total">2000</div></td>
//</tr>

var txBuyListDetailTemplate = '{{#orderViewList}}' 
							+ '<tr>'
							+ '<td><div class="tx-sell-title">{{productName}}</div></td>'
							+ '<td><div class="tx-sell-price">{{price}}</div></td>'
							+ '<td><div class="tx-sell-qty">{{quantity}}</div></td>'
							+ '<td><div class="tx-sell-total">{{total}}</div></td>'
							+ '</tr>'
							+ '{{/orderViewList}}';



// service operation area
$(document).ready(function(){
	
    $("#user-service-QA-tab").click(function(){
        $(".tab").removeClass("tab-select");
        $("#user-service-QA-tab").addClass("tab-select");
        $(".data-area").fadeOut(500);
        $("#user-service-QA-area").fadeIn(500);
    });

	$("#article-service-QA-tab").click(function(){
	    $(".tab").removeClass("tab-select");
	    $("#article-service-QA-tab").addClass("tab-select");
	    $(".data-area").fadeOut(500);
	    $("#article-service-QA-area").fadeIn(500);
	});
	$("#autobuy-service-QA-tab").click(function(){
	    $(".tab").removeClass("tab-select");
	    $("#autobuy-service-QA-tab").addClass("tab-select");
	    $(".data-area").fadeOut(500);
	    $("#autobuy-service-QA-area").fadeIn(500);
	});
});


















