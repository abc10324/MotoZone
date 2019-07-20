<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<div class="op-area" id="tx-op-area">
    <i class="fas fa-times close-btn op-close"></i>
    <h1>會員交易管理區</h1>
    
    <div class="tabs">
        <div class="tab" id="tx-buy-tab">購買商品</div>
        <div class="tab" id="tx-sell-tab">販售商品</div>
    </div>
    
    <div id="tx-buy-area" class="data-area">
        <table class="tx-table">
            <thead>
	            <tr>
	                <th>訂單編號</th>
	                <th>訂單日期</th>
	                <th>總金額</th>
	                <th>明細顯示</th>
	                <th>明細下載</th>
	            </tr>
            </thead> 
            <tbody></tbody>   
        </table>  
    </div>
    <div id="tx-sell-area" class="data-area">
        <table class="tx-table">
            <thead>
             <tr>
                 <th>商品名稱</th>
                 <th>單價</th>
                 <th>庫存數量</th>
                 <th>連結</th>
             </tr>
            </thead> 
            <tbody></tbody>   
        </table>  
    </div>
</div>

<div class="tx-sell-popup-area popup-window hidden-window">
    <i class="fas fa-times close-btn"></i>

    <h2>訂單明細</h2>
    
    <table>
        <thead>
            <tr>
                <th>商品名稱</th>
                <th>單價</th>
                <th>購買數量</th>
                <th>總價</th>
            </tr>
        </thead>
        <tbody></tbody>
    </table>
</div>