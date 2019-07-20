//數量控制
$(document).ready(function() {
	var total = 0;
	$(".price").each(function() {
		total += parseInt($(this).text());

		$("#total").text(total);
	});

	$(".qty").change(function() {
		var tr = $(this).parent().parent();
		var unitPrice = tr.find(".unit").text();
		var qty = $(this).val();
		var price = qty * unitPrice;

		tr.find(".price").text(price);
		
		total = 0;
		// total process

		$(".price").each(function() {
			total += parseInt($(this).text());

			$("#total").text(total);
		});
	});
	
	//結算處理
	$('#totalmoney').click(function(){
		// get login user's data from session
		var userNo = JSON.parse(window.sessionStorage.getItem("loginUser")).uNo;
		
		
		// prepare JSON for AJAX
		var orderList = [];
		$(".cart-list").each(function(){
			var id = $(this).find("input[name='id']").val();
			var qty = $(this).find("input[name='quantity']").val();
			orderList.push({"id":id,"qty":qty});
		});
		
		console.log(orderList);
		
		
		// check empty
		if(orderList.length != 0){
			
			//update 修改後表單進資料庫
			$.ajax({
				url : "/motozone/ProductCart/" + userNo ,
				type : "PUT",
				contentType : "application/json",
				data : JSON.stringify(orderList),
				success : function(data) {
					
							if(data){
								//連結金流
								$.ajax({
									url:"/motozone/EcPay/Orders/" + userNo,
								   type:"POST",
							    success:function(data){
							    			
							    			// connect to payment page
											$("#payment-popup").html(data);
										}
								});
							} else {
								alert("購物車空無一物!!!");
							}
						}		
			});
		} else {
			alert("購物車是空的 !!!");
		}
		
	});
})

function removeCartItem(element){
	var rowData = $(element).parent().parent();
	
	var id = rowData.find("input[name='id']").val();
	var userNo = rowData.find("input[name='userNo']").val();
	
	console.log('id = ' + id);
	console.log('userNo = ' + userNo);
	var rowDataObj = {id:id,userNo:userNo};
	
	$.ajax({
		url: "/motozone/AutoBuyCart/" + userNo,
	   type: "DELETE",
	   data: JSON.stringify(rowDataObj),
	success: function(data){
				
				if(data){
					rowData.remove();
					
					var total = 0;
					$(".cart-list .price").each(function(){
						total += parseInt($(this).text());
					});
					
					$("#total").text(total);
				} else {
					alert("商品移除失敗");
				}
		
			}
	});
	
}