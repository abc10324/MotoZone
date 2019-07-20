<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

 <div class="op-area" id="user-data-op-area">
       <i class="fas fa-times close-btn op-close"></i>

        <h1>會員資料設定</h1>
        <div class="tabs">
            <div class="tab" id="basic-data-tab">基本資料</div>
            <div class="tab" id="tx-data-tab">交易資料</div>
            <div class="tab" id="auth-data-tab">權限管理</div>
        </div>
        <div class="data-area" id="basic-data-area">
            <div id="user-snapshot-area">
            <img id="user-snapshot-img" src="<c:url value="/img/userIcon.png"/>" alt="user snapshot"/>
            <i class="fas fa-camera" id="user-snapshot-popup-btn"></i>
            </div>
            <br>
            <hr>
            <form>
                <div class="user-data-form-area">
                    <div class="user-data-tag-area">權限</div>
                    <div class="user-data-show-area" id="user-authority-show-area"></div>
                    <div class="user-data-btn-area"></div>
                </div>
                <hr>
                <div class="user-data-form-area">
                    <div class="user-data-tag-area">用戶名(帳號)</div>
                    <div class="user-data-show-area" id="user-id-show-area"></div>
                    <div class="user-data-btn-area"></div>
                </div>
                <hr>
                <div class="user-data-form-area">
                    <div class="user-data-tag-area">密碼</div>
                    <div class="user-data-show-area" id="user-password-show-area">**********</div>
                    <div class="user-data-btn-area"><i class="fas fa-edit" id="user-password-popup-btn"></i></div>
                </div>
                <hr>
                <div class="user-data-form-area">
                    <div class="user-data-tag-area">信箱</div>
                    <div class="user-data-show-area" id="user-email-show-area"></div>
                    <div class="user-data-btn-area"><i class="fas fa-edit" id="user-email-popup-btn"></i></div>
                </div>
                <hr>
                <div class="user-data-form-area">
                    <div class="user-data-tag-area">姓名</div>
                    <div class="user-data-show-area" id="user-name-show-area"></div>
                    <div class="user-data-btn-area"><i class="fas fa-edit" id="user-name-popup-btn"></i></div>
                </div>
                <hr>
                <div class="user-data-form-area">
                    <div class="user-data-tag-area">身分證字號</div>
                    <div class="user-data-show-area" id="user-personid-show-area"></div>
                    <div class="user-data-btn-area"><i class="fas fa-edit" id="user-personid-popup-btn"></i></div>
                </div>
                <hr>
                <div class="user-data-form-area">
                    <div class="user-data-tag-area">生日</div>
                    <div class="user-data-show-area" id="user-birth-show-area"></div>
                    <div class="user-data-btn-area"><i class="fas fa-edit" id="user-birth-popup-btn"></i></div>
                </div>
                <hr>
                <div class="user-data-form-area">
                    <div class="user-data-tag-area">性別</div>
                    <div class="user-data-show-area" id="user-sex-show-area"></div>
                    <div class="user-data-btn-area"><i class="fas fa-edit" id="user-sex-popup-btn"></i></div>
                </div>
                <hr>
                <div class="user-data-form-area">
                    <div class="user-data-tag-area">電話</div>
                    <div class="user-data-show-area" id="user-phone-show-area"></div>
                    <div class="user-data-btn-area"><i class="fas fa-edit" id="user-phone-popup-btn"></i></div>
                </div>
                <hr>
                <div class="user-data-form-area">
                    <div class="user-data-tag-area">地址</div>
                    <div class="user-data-show-area" id="user-address-show-area"></div>
                    <div class="user-data-btn-area"><i class="fas fa-edit" id="user-address-popup-btn"></i></div>
                </div>
            </form>
        </div>
        <div class="data-area" id="tx-data-area">
            <div id="user-snapshot-area"></div>
            <hr>
            <form>
                <div class="user-data-form-area">
                    <div class="user-data-tag-area">...</div>
                    <div class="user-data-show-area"></div>
                    <div class="user-data-btn-area"><i class="fas fa-edit"></i></div>
                </div>
                <hr>
                <div class="user-data-form-area">
                    <div class="user-data-tag-area">...</div>
                    <div class="user-data-show-area"></div>
                    <div class="user-data-btn-area"><i class="fas fa-edit"></i></div>
                </div>
                <hr>
                <div class="user-data-form-area">
                    <div class="user-data-tag-area">...</div>
                    <div class="user-data-show-area"></div>
                    <div class="user-data-btn-area"><i class="fas fa-edit"></i></div>
                </div>
                <hr>
                <div class="user-data-form-area">
                    <div class="user-data-tag-area">...</div>
                    <div class="user-data-show-area"></div>
                    <div class="user-data-btn-area"><i class="fas fa-edit"></i></div>
                </div>
                <hr>
                <div class="user-data-form-area">
                    <div class="user-data-tag-area">...</div>
                    <div class="user-data-show-area"></div>
                    <div class="user-data-btn-area"><i class="fas fa-edit"></i></div>
                </div>
                <hr>
                <div class="user-data-form-area">
                    <div class="user-data-tag-area">...</div>
                    <div class="user-data-show-area"></div>
                    <div class="user-data-btn-area"><i class="fas fa-edit"></i></div>
                </div>
                <hr>
                <div class="user-data-form-area">
                    <div class="user-data-tag-area">...</div>
                    <div class="user-data-show-area"></div>
                    <div class="user-data-btn-area"><i class="fas fa-edit"></i></div>
                </div>
                <hr>
                <div class="user-data-form-area">
                    <div class="user-data-tag-area">...</div>
                    <div class="user-data-show-area"></div>
                    <div class="user-data-btn-area"><i class="fas fa-edit"></i></div>
                </div>
                <hr>
                <div class="user-data-form-area">
                    <div class="user-data-tag-area">...</div>
                    <div class="user-data-show-area"></div>
                    <div class="user-data-btn-area"><i class="fas fa-edit"></i></div>
                </div>
                <hr>
                <div class="user-data-form-area">
                    <div class="user-data-tag-area">...</div>
                    <div class="user-data-show-area"></div>
                    <div class="user-data-btn-area"><i class="fas fa-edit"></i></div>
                </div>
            </form>
        </div>
         <div class="data-area" id="auth-data-area">
            <div class="auth-fa-user">
                <i class="fas fa-user"></i>
                <div>一般會員</div>
                    <ul>
                        <li>會員個人資料修改</li>
                        <li>文章區留言、分享</li>
                        <li>購物商城商品購買</li>
                        <li>聊天室</li>
                        <li>網頁瀏覽</li>
                    </ul>
                <button style="visibility:hidden">申請</button>
            </div>
            <div class="auth-fa-user">
                <i class="fas fa-user-tie"></i>
                <div>白金會員</div>
                    <ul>
                        <li>新增文章區發文</li>
                        <li>新增會員文章修改</li>
                        <li class="auth-content-area">xxx</li>
                        <li class="auth-content-area">xxx</li>
                        <li><b>升級費用：500 元</b></li>
                    </ul>
                <button id="auth-pm-upgrade-btn">申請</button>
            </div>
            <div class="auth-fa-user">
                <i class="fas fa-user-secret"></i>
                <div>鑽石會員</div>
                    <ul>
                        <li>新增購物商城刊登商品</li>
                        <li>新增會員商品管理</li>
                        <li class="auth-content-area">xxx</li>
                        <li class="auth-content-area">xxx</li>
                        <li><b>升級費用：2000 元</b></li>
                    </ul>
                <button id="auth-dm-upgrade-btn">申請</button>
            </div>
        </div>
    </div>


    <!-- basic data popup area -->
    <div class="user-popup-area popup-window hidden-window" id="user-snapshot-popup">
        <i class="fas fa-times close-btn" id="empty-popup-btn"></i>
        <i class="fas fa-user-circle user-popup-icon"></i>
        <h3>更新大頭貼</h3>
        <br>
        <div class="input-group">
            <label class="btn btn-info">
                <input type="file" id="user-snapshot" style="display: none"/>
                <i class="far fa-image" style="font-size: 15px;"></i>上傳圖片
            </label>
        </div>
        <div id="show-snapshot-area"></div>
        <br>
        <button id="update-user-snapshot-btn">裁切圖片<img src="<c:url value="/img/loading.gif"/>" /></button>
    </div>
    <div class="user-popup-area popup-window hidden-window" id="user-password-popup">
        <i class="fas fa-times close-btn" id="empty-popup-btn"></i>
        <i class="fas fa-key user-popup-icon"></i>
        <h3>更新密碼</h3>
        <br>
        <form id="password-form">
        <div class="input-group">
            <input type="password" id="user-password" required/>
            <span>新密碼</span>
        </div>
        <div class="input-group">
            <input type="password" id="user-password-check" required/>
            <span>再次輸入密碼</span>
        </div>
        </form>
        <button id="update-user-password-btn">更新</button>
    </div>
    <div class="user-popup-area popup-window hidden-window" id="user-email-popup">
        <i class="fas fa-times close-btn" id="empty-popup-btn"></i>
        <i class="fas fa-envelope user-popup-icon"></i>
        <h3>更新信箱</h3>
        <br>
        <form id="email-form">
	        <div class="input-group">
	            <input type="text" id="user-email" required/>
	            <span>信箱</span>
	        </div>
        </form>
        <button id="update-user-email-btn">更新</button>
    </div>
    <div class="user-popup-area popup-window hidden-window" id="user-name-popup">
        <i class="fas fa-times close-btn" id="empty-popup-btn"></i>
        <i class="fas fa-file-signature user-popup-icon"></i>
        <h3>更新姓名</h3>
        <br>
        <form id="name-form">
        <div class="input-group">
            <input type="text" id="user-name" required/>
            <span>姓名</span>
        </div>
        </form>
        <button id="update-user-name-btn">更新</button>
    </div>
    <div class="user-popup-area popup-window hidden-window" id="user-personid-popup">
        <i class="fas fa-times close-btn" id="empty-popup-btn"></i>
        <i class="fas fa-user-tie user-popup-icon"></i>
        <h3>更新身分證字號</h3>
        <br>
        <form id="personid-form">
        <div class="input-group">
            <input type="text" id="user-personid" required/>
            <span>身分證字號</span>
        </div>
        </form>
        <button id="update-user-personid-btn">更新</button>
    </div>
    <div class="user-popup-area popup-window hidden-window" id="user-birth-popup">
        <i class="fas fa-times close-btn" id="empty-popup-btn"></i>
        <i class="fas fa-birthday-cake user-popup-icon"></i>
        <h3>更新生日</h3>
        <br>
        <form id="birth-form">
        <div class="input-group">
            <input type="date" id="user-birth" required/>
        </div>
        </form>
        <button id="update-user-birth-btn">更新</button>
    </div>
    <div class="user-popup-area popup-window hidden-window" id="user-sex-popup">
        <i class="fas fa-times close-btn" id="empty-popup-btn"></i>
        <i class="fas fa-transgender-alt user-popup-icon"></i>
        <h3>更新性別</h3>
        <br>
        <div>
            <input type="radio" name="gender" value="male" required checked />男
            <input type="radio" name="gender" value="female" required />女
            <input type="radio" name="gender" value="other" required />不願透露
        </div>
        <br>
        <button id="update-user-sex-btn">更新</button>
    </div>
    <div class="user-popup-area popup-window hidden-window" id="user-phone-popup">
        <i class="fas fa-times close-btn" id="empty-popup-btn"></i>
        <i class="fas fa-mobile-alt user-popup-icon"></i>
        <h3>更新電話</h3>
        <br>
        <form id="phone-form">
        <div class="input-group">
            <input type="text" id="user-phone" required/>
            <span>電話</span>
        </div>
        </form>
        <button id="update-user-phone-btn">更新</button>
    </div>
    <div class="user-popup-area popup-window hidden-window" id="user-address-popup">
        <i class="fas fa-times close-btn" id="empty-popup-btn"></i>
        <i class="fas fa-map-marked-alt user-popup-icon"></i>
        <h3>更新地址</h3>
        <br>
        <form id="address-form">
        <div class="input-group">
            <input class="twaddress" id="user-address" value="台北市"/><br>
        </div>
        </form>
        <button id="update-user-address-btn">更新</button>
    </div>
    <div class="image-crop-popup-area popup-window hidden-window" id="user-address-popup">
        <i class="fas fa-times close-btn"></i>
        <h3>裁切圖片</h3>
        <br>
        <div id="image-crop-area">
        	<img id="image-crop-target" src="/motozone/img/upload/defaultProduct.jpg">
        </div>
        
        <button id="image-crop-btn">裁切並上傳<img src="<c:url value="/img/loading.gif"/>" /></button>
    </div>
    
    
<!-- Authority popup area -->
<div class="user-popup-area popup-window hidden-window" id="authority-redirect-popup"></div>
