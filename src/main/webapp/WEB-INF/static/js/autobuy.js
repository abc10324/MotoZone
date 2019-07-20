let editor; // store CKEditor's instance

var showAdInterval = setInterval(showAd,5000);
var picCount = 1;
var maxCount = 3;
var cat = 1;

// for main.js control
autoBuyFlag = true;

function showAd(){
    var queryStr = ".ad-content:nth-child(" + cat + ") a:nth-child(" + picCount + ") img";
    
    $(queryStr).fadeIn(700,function(){
        $(queryStr).delay(3600).fadeOut(700);
    });

    picCount++;

    if(picCount == 3){
        picCount = 1;
    }
}

$(document).ready(function(){
    showAd();

    // advertise category button setting
    $("#hit-product").click(function(){
        $(".ad-content-area .ad-content").css({"z-index":"-1"});
        $("#hit-product-ad").css({"z-index":"1"});

        $(".ad-cat").removeClass("ad-cat-choose",300,function(){
            $("#hit-product").addClass("ad-cat-choose",700);
        });
        cat = 1;

        clearInterval(showAdInterval);
        showAdInterval = setInterval(showAd,5000);
        showAd();
    });

    $("#new-product").click(function(){
        $(".ad-content-area .ad-content").css({"z-index":"-1"});
        $("#new-product-ad").css({"z-index":"1"});

        $(".ad-cat").removeClass("ad-cat-choose",300,function(){
            $("#new-product").addClass("ad-cat-choose",700);
        });
        cat = 2;

        clearInterval(showAdInterval);
        showAdInterval = setInterval(showAd,5000);
        showAd();
    });

    $("#discount-product").click(function(){
        $(".ad-content-area .ad-content").css({"z-index":"-1"});
        $("#discount-product-ad").css({"z-index":"1"});

        $(".ad-cat").removeClass("ad-cat-choose",300,function(){
            $("#discount-product").addClass("ad-cat-choose",700);
        });
        cat = 3;

        clearInterval(showAdInterval);
        showAdInterval = setInterval(showAd,5000);
        showAd();
    });
});


//pulish form pop-up
$(document).ready(function(){
	
	// submit the form
    $(".publish-btn").click(function(){
    	if(window.sessionStorage.getItem("loginUser") != ""){
    		$(".publish-area").removeClass("hidden-window",700);
            $("#shadow").fadeIn(700);
            
            $(".publish-area").removeClass("hidden-window",700);
            $("#shadow").fadeIn(700);
    	}else {
    		alert("請先登入 !");
    		
    		$(".login-area").removeClass("hidden-window", 700);
            $("#shadow").fadeIn(700);
    	}        
    });
    
    
    // upload the image
    $("#imgUpload").change(function(){
    	let formData = new FormData();
    	formData.append('upload',$("#imgUpload")[0].files[0]);
    	
    	$.ajax({
    		url:"/motozone/Imgur",
    	   type:"POST",
    	   data:formData,
    contentType: false, 
    processData: false,
    	success:function(data){
    		$("#imgText").val(data.url);
    	}
    		
    	});
    });
    
    // clean edit block when close publish window
    $(".publish-area .close-btn").click(function(){
        
        // clean edit block
        editor.setData("");

    });
    
    
});


// cart icon control
$(document).ready(function(){
	if(window.sessionStorage.getItem("loginUser") != ""){
		$.ajax({
			url:"/motozone/ProductCartView/" + JSON.parse(window.sessionStorage.getItem("loginUser")).uNo,
			type:"GET",
			success:function(data){
						var currentCount = data.length;
			    		
			    		if(currentCount != 0){
			    			$(".badge").show().text(data.length);
			    		} else {
			    			$(".badge").hide();
			    		}
					}
		});
		
	} else {
		$("#shoppingCart").hide();
		$("#shoppingCartArea").hide();
	}
	
	// 購物車彈出事件 
    $("#shopcarticon").mouseenter(function(){
    	$("#shoppingCartArea").slideDown("slow");
    	var userNo = JSON.parse(window.sessionStorage.getItem("loginUser")).uNo;
    	
    	$.ajax({
    	url:"/motozone/ProductCartView/"+userNo,
    	type:"GET",
    	success:function(data){
    		
    		var currentCount = data.length;
    		
    		if(currentCount != 0){
    			$(".badge").show().text(data.length);
    		} else {
    			$(".badge").hide();
    		}
    		
    		
    		for(let rowData of data){
    			$("<div></div>").text(rowData.name).appendTo(".item");
    			$("<div></div>").text(rowData.quantity).appendTo(".qty");
    			$("<div></div>").text(rowData.price).appendTo(".price");
    		}

    	}
    	});
    });
    
    $("#shopcarticon").mouseleave(function(){
    	$("#shoppingCartArea").slideUp("slow");
    	$( ".item" ).empty();
    	$( ".qty" ).empty();
    	$( ".price" ).empty();

    });
	
	
});



