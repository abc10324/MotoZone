$(document).ready(function(){
	
	// zoom-master 放大鏡
    $(".zoom-img")
      .wrap('<div style="display:inline-block"></div>')
      .css('display', 'block')
      .parent()
      .zoom();
    
    //切換頁面
    $(".info").click(function() {
		$(".change-content").fadeOut(700);
		$("#content-info").fadeIn(700);
	});

	$(".record").click(function() {
		$(".change-content").fadeOut(700);
		$("#content-record").fadeIn(700);
	});

	$(".question").click(function() {
		$(".change-content").fadeOut(700);
		$("#content-question").fadeIn(700);
	});
    
	
	//購買前先登入
	$("#buy-btn").click(function(){
		
    	if(window.sessionStorage.getItem("loginUser") != ""){
    		if(parseInt($("#product_quantity").text()) != 0){
    			$("#productPostForm").submit();
    		} else {
    			alert("商品無庫存，無法購買！");
    		}
    	} else {
    		alert("請先登入 !");
    		
    		$(".login-area").removeClass("hidden-window", 700);
            $("#shadow").fadeIn(700);
    	}
        
    });
	
	
	//加入購物車前先登入
	$("#joinCart-btn").click(function(){
		
    	if(window.sessionStorage.getItem("loginUser") != ""){
    		
    		if(parseInt($("#product_quantity").text()) != 0){
    			var formData = $("#productPostForm").serialize();
                var userNo = JSON.parse(window.sessionStorage.getItem("loginUser")).uNo;
        		$.ajax({
        			url:"/motozone/ProductCart/" + userNo,
        			type: "POST",
        		    data:formData,
        		 success:function(data){
        			 	if(data){
        			 		alert("加入購物車成功");
        			 		
        			 		// refresh shopping cart
        			 		var currentCount = data.length;
        		    		
        		    		if(currentCount != 0){
        		    			$(".badge").show().text(data.length);
        		    		} else {
        		    			$(".badge").hide();
        		    		}
        			 	} else {
        			 		alert("加入購物車失敗");
        			 	}
        			 
        		    }
        		});
    		} else {
    			alert("商品已無庫存，無法購買！");
    		}

    	} else {
    		alert("請先登入 !");
    		
    		$(".login-area").removeClass("hidden-window", 700);
            $("#shadow").fadeIn(700);
    	}
        
    });
    
    //提問前先登入
	$("#addQuestion-btn").click(function(){
		
    	if(window.sessionStorage.getItem("loginUser") != ""){
    		$("#questionForm").submit();
    		
    	} else {
    		alert("請先登入 !");
    		
    		$(".login-area").removeClass("hidden-window", 700);
            $("#shadow").fadeIn(700);
    	}
        
    });
	
	// replace url when first post
	var productPostUrl = "/motozone/ProductPost/" + $(".hidden_itemid input[name='id']").val();
	history.replaceState({foo: "Post"},"",productPostUrl);
    
});

