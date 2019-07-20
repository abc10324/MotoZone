<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!-- publish button -->
<i class="fas fa-file-upload publish-btn" title="刊登商品"></i>

<div class="publish-area popup-window hidden-window">
    <i class="fas fa-times close-btn"></i>
    <div id="publish-content-area">
    	<form id="product-form" method="POST" action="<c:url value="/ProductPost/"/>">
    		<span class="hidden_itemid">
						<input type="text" name="userNo" value="${sessionScope.loginUser.uNo}">
			</span>
            <div class="publish-title">刊登商品</div>
            <div>
                <label class="title">商品類別:</label>
                <div id="cate-select">
                    <select name="category">
                        <optgroup label="人身部品">
                            <option value="BPNAL">防摔部品</option>
                            <option value="BPNGS">手套</option>
                            <option value="BPNHT">安全帽</option>
                        </optgroup>
                        <optgroup label="煞車系統">
                            <option value="BBECR">卡鉗</option>
                            <option value="BBEDC">碟盤</option>
                            <option value="BBEHE">油管</option>
                            <option value="BBEMR">總泵</option>
                        </optgroup>
                        <optgroup label="輪胎">
                            <option value="BTEHE">重機</option>
                            <option value="BTELE">輕機</option>
                        </optgroup>
                        <optgroup label="避震器">
                            <option value="BSNHE">重機</option>
                            <option value="BSNLE">輕機</option>
                        </optgroup>
                        <optgroup label="排氣管">
                            <option value="BPEHE">重機</option>
                            <option value="BPELE">輕機</option>
                        </optgroup>
                        <optgroup label="傳動系統">
                            <option value="BTNHE">重機</option>
                            <option value="BTNLE">輕機</option>
                        </optgroup>
                        <optgroup label="動力系統">
                            <option value="BPRHE">重機</option>
                            <option value="BPRLE">輕機</option>
                        </optgroup>
                        <optgroup label="電動機車">
                            <option value="BERCL">車殼</option>
                            <option value="BERNW">新車</option>
                            <option value="BEROD">二手</option>
                        </optgroup>
                        <optgroup label="重型機車">
                            <option value="BHECL">車殼</option>
                            <option value="BHENW">新車</option>
                            <option value="BHEOD">二手</option>
                        </optgroup>
                        <optgroup label="輕型機車">
                            <option value="BLECL">車殼</option>
                            <option value="BLENW">新車</option>
                            <option value="BLEOD">二手</option>
                        </optgroup>
                    </select>
                </div>
            </div>
            <div>
                <label class="title">商品照片:</label>
                <input type="file" id="imgUpload" style="margin-top: 5px;" >上傳圖片
                <input type="text" id="imgText" name="img" style="display:none">
            </div>

            <div>
                <label class="title">商品名稱:</label>
                <input type="text" name="name" value="" size="88" maxlength="60" class="input-text">
                ${errorMsgs.name}
            </div>
            <div>
                <label class="title">商品數量:</label>
                <input type="text" name="quantity" value="1" size="3" maxlength="3" class="input-text"
                    style="width: 80px;"> 件 ${errorMsgs.quantity}
            </div>
            <div>
                <label class="title">商品價格:</label>
                <input type="text" name="price"  value="" size="10" maxlength="8" class="input-text" style="width: 80px;">元 ${errorMsgs.price}
            </div>
            <div>
                <label class="title">交易地區:</label>
                <select name="area" id="city">
                    <option value="0">請選擇</option>
                    <option value="台北市">台北市</option>
                    <option value="新北市">新北市</option>
                    <option value="基隆市">基隆市</option>
                    <option value="桃園市">桃園市</option>
                    <option value="新竹市">新竹市</option>
                    <option value="新竹縣">新竹縣</option>
                    <option value="苗栗縣">苗栗縣</option>
                    <option value="台中市">台中市</option>
                    <option value="彰化縣">彰化縣</option>
                    <option value="南投縣">南投縣</option>
                    <option value="雲林縣">雲林縣</option>
                    <option value="嘉義市">嘉義市</option>
                    <option value="嘉義縣">嘉義縣</option>
                    <option value="台南市">台南市</option>
                    <option value="高雄市">高雄市</option>
                    <option value="屏東縣">屏東縣</option>
                    <option value="台東縣">台東縣</option>
                    <option value="花蓮縣">花蓮縣</option>
                    <option value="宜蘭縣">宜蘭縣</option>
                    <option value="澎湖縣">澎湖縣</option>
                    <option value="金門縣">金門縣</option>
                    <option value="連江縣">連江縣</option>
                </select>
            </div>
            <div>
                <label class="title">商品狀態:</label>
                <label>
                    <input type="radio" name="status" value="新品" checked> 新品</label>
                <label>
                    <input type="radio" name="status" value="中古"> 中古</label>

            </div>
            <div>
                <label class="title">狀態說明:</label>
                <input type="text" name="statusDescription" value="" size="40" maxlength="50" class="input-text"
                    placeholder="九成新,使用約三個月,無任何破損與刮傷">

            </div>

            <div>
                <label class="title">商品描述:</label>
                <div class="">
                    <textarea name="description" id="editor" rows="3"></textarea>
                    <script>
	                    editor = ClassicEditor
	                    .create( document.querySelector( '#editor' ) ,{
	                        removePlugins: ['Heading'],
	                    	ckfinder: {
// 		                        uploadUrl: "/motozone/Image/" + $("#cate-select > select[name='category']").val()
		                        uploadUrl: "/motozone/Imgur"
		                    }
	                    })
	                    .then( newEditor => {
	                        editor = newEditor
	                    } )
	                    .catch( error => {
	                        console.error( error );
	                    } );
	                    
	            	</script>
                </div>
            </div>
            <div>
                <label class="title">付款方式:</label>
                <label><input type="radio" name="payment"  value="面交/自取" checked> 面交/自取</label>
                <label><input type="radio" name="payment"  value="ATM轉帳"> ATM轉帳</label>
                <label><input type="radio" name="payment"  value="信用卡付款"> 信用卡付款</label>
                <label><input type="radio" name="payment"  value="貨到付款"> 貨到付款</label>
                <label><input type="radio" name="payment"  value="便利超商繳款">超商代碼繳款</label>
            </div>
            <div>
                <label class="title">交貨方式:</label>
                <label><input type="radio" name="shipping" value="買方付運費" checked> 買方付運費</label>
                <label><input type="radio" name="shipping" value="賣方付運費"> 賣方付運費</label>
                <label><input type="radio" name="shipping" value="不寄送"> 不寄送</label>
                <label><input type="radio" name="shipping" value="大型/超重物品運送"> 大型/超重物品運送</label>
            </div>
            <div>
                <label class="title">寄送方式:</label>

                <div style="padding-top: 0px">
                    <select name="delivery">
                        <option>請選擇</option>
                        <option value="面交/自取">面交/自取</option>
                        <option value="郵寄掛號">郵寄掛號</option>
                        <option value="郵寄包裹">郵寄包裹</option>
                        <option value="貨運">貨運</option>
                        <option value="宅配">宅配</option>
                        <option value="快遞">快遞</option>
                        <option value="貨到付款">貨到付款</option>
                        <option value="先付款再至便利商店取件">先付款再至便利商店取件</option>
                        <option value="便利商店付款取件">便利商店付款取件</option>
                    </select>
                </div>
            </div>
            <div>
                <label class="title">拍賣天數:</label>
                <select name="auctionDays" id="mp_day">
                    <option value="1">1</option>
                    <option value="2">2</option>
                    <option value="3">3</option>
                    <option value="4">4</option>
                    <option value="5">5</option>
                    <option value="6">6</option>
                    <option value="7">7</option>
                    <option value="8">8</option>
                    <option value="9">9</option>
                    <option value="10">10</option>
                    <option value="11">11</option>
                    <option value="12">12</option>
                    <option value="13">13</option>
                    <option value="14">14</option>
                    <option value="15">15</option>
                </select> 天
            </div>
                <button type="submit" name="submit" id="publish-submit">刊登商品</button>
        </form>
    </div>
</div>