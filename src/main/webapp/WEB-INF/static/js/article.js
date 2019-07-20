let editor; // store CKEditor's instance

//for posts list loading use
//true for execute loading
//false for denied loading
var postListLoadLock = true;

//post list page start from page 1
var postListPage = 1;


$(document).ready(function(){
	
	// initialize image lazy loading
	lazyload();
	
	// initialize masonry layout and settings
	var masonryObj = $('#postList').masonry({
				        itemSelector: '.content-area',
//				        horizontalOrder: true,
				        percentPosition: true,
				        columnWidth: '.content-area-sizer'
				    });
	
	// response to image loading event
	$('img.lazyload').on('load',function(){
		masonryObj.masonry('layout');
	});
	
	// post list area scroll event control
	$(window).scroll(function(){
		
		if(($(window).scrollTop() + $(window).height()) > ($(document).height() - 200) && postListLoadLock){
			
			// disable loading
			postListLoadLock = false;
			
			// show next page
			postListPage++;
			
			$.ajax({
				url: "/motozone/Ajax/PostList/" + location.href.substring(location.href.lastIndexOf("/") + 1) + "/" + postListPage,
			   type: "GET",
			success: function(data){
						
						// empty check
						if(data){
//							console.log("Post List Page " + postListPage);
							
							for(let post of data){
								// add new post to the list
	                			var newPost = $.parseHTML(Mustache.render(postListTemplate,post));
	                			masonryObj.append(newPost).masonry('appended',newPost);
							}
							
							lazyload();
							
							// rebind image loading event
							$('img.lazyload').on('load',function(){
								masonryObj.masonry('layout');
							});
							
							// rebind click event
							$(".content-area").off('click');
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
							
							// set post list loadable
							postListLoadLock = true;
							
							
						} else {
							postListLoadLock = false;
							console.log("List reach end !");
						}
					
					}
			});
		}
	});
	

    $("#publish-btn").click(function(){
    	
    	if(window.sessionStorage.getItem("loginUser") != ""){
    		$(".publish-area").removeClass("hidden-window",700);
            $("#shadow").fadeIn(700);
    	} else {
    		alert("請先登入 !");
    		
    		$(".login-area").removeClass("hidden-window", 700);
            $("#shadow").fadeIn(700);
    	}
        
    });

    $("#content-opt").click(function(event){
        event.stopPropagation();
        // custom menu
        /*

            TBD

        */

    });
    
    // temp image clear
    $(".publish-area .close-btn").click(function(){
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
        $("#title").val("");
        editor.setData("");
        

    });
    
    // send a post
    $("#submit").click(function(e){
    	// get url
        var cat = location.href.substring(location.href.lastIndexOf("/") + 1);

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
        
        if(editor.getData() && $("#title").val()){
        
	        // use ajax to send data to controller
	        $.ajax({
	            url:"/motozone/Post",
	            type:"POST",
	            data:{title: $("#title").val(),
	                    cat: cat,
	                 userNo: JSON.parse(window.sessionStorage.getItem("loginUser")).uNo,
	                 author: JSON.parse(window.sessionStorage.getItem("loginUser")).uName,
	                content: editor.getData(),
	                 imgSrc: imgSrc},
	        success:function(id){
	                
	                // get new post data
	                $.ajax({
	                	url:"/motozone/Ajax/PostList/" + id,
	                   type:"GET",
	                success:function(data){
	                			// add new post to the list
	                			var newPost = $.parseHTML(Mustache.render(postListTemplate,data));
	                			masonryObj.prepend(newPost).masonry('prepended',newPost);
	                			lazyload();
	                            
	                            // rebind the click event for new post
	                            $(".content-area").off('click');
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
	                            
	                		}                                          
	                });
	                
	                // empty input area
	                $("#title").val("");
	                editor.setData("");
	                
	                // close window
	                $(".publish-area").addClass("hidden-window", 700);
	                $("#shadow").fadeOut(700);
	            }
	        });
        }
        
        
    });


});



// post list template
//<c:forEach var="bean" items="${postList}">
//    <section class="content-area">
//        <span class="content-id">${bean.id}</span>
//
//        <div class="content-img">
//        	<c:choose>
//        		<c:when test="${empty bean.imgSrc}">
//        			<img src="<c:url value="/img/article-default.jpg"/>"/>
//        		</c:when>
//        		<c:otherwise>
//        			<img src="${bean.imgSrc}"/>
//        		</c:otherwise>
//        	</c:choose>
//        </div>
//        <div class="content">
//            <h4><b>${bean.title}</b></h4>
//            <p>${bean.content}</p>
//        </div>
//        <div class="content-info">
//            <span><fmt:formatDate value="${bean.date}" pattern="yyyy/MM/dd"/></span>
//            <div class="view">
//                    <i class="far fa-eye"></i>
//                    <span>${bean.views}</span>
//            </div>
//            <i class="fas fa-ellipsis-h" id="content-opt"></i>
//        </div>
//    </section>
//</c:forEach>

var postListTemplate = '<section class="content-area">'
					 + '<span class="content-id">{{id}}</span>'
					 + '<div class="content-img">'
					 + '<img class="lazyload" data-src="{{imgSrc}}{{^imgSrc}}/motozone/img/article-default.jpg{{/imgSrc}}"/>'
					 + '</div>'
					 + '<div class="content">'
					 + '<h4><b>{{title}}</b></h4>'
					 + '<p>{{&content}}</p>'
					 + '</div>'
					 + '<div class="content-info">'
					 + '<span>{{date}}</span>'
					 + '<div class="view">'
					 + '<i class="far fa-eye"></i>'
					 + '<span>{{views}}</span>'
					 + '</div>'
					 + '<i class="fas fa-ellipsis-h" id="content-opt"></i>'
					 + '</div>'
					 + '</section>';


