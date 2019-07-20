<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

 <div class="op-area" id="article-op-area">
    <i class="fas fa-times close-btn op-close"></i>
     
    <h1>文章修改列表</h1>
    <hr/>
    <div id="user-post-list-area">
        <table id="user-post-list">
            <thead>
	            <tr>
	                <th>標題</th>
	                <th>發佈時間</th>
	                <th>內容</th>
	                <th>連結</th>
	                <th>修改</th>
	                <th>刪除</th>
	            </tr>
            </thead> 
            <tbody></tbody>   
        </table>  
    </div>
</div>

<!-- Start按鈕-文章修改區 -->
<div class="article-modify-area popup-window hidden-window">
    <i class="fas fa-times close-btn"></i>

    <h2>修改貼文</h2>
    <div class="article-no"></div>
    <div id="article-modify-title"></div>
    
    <hr/>
    <form id="modify-form">
        <textarea name="content" id="modify-editor" rows="3"></textarea>
        <script>
            modifyEditor = ClassicEditor
            .create( document.querySelector( '#modify-editor' ) ,{
                removePlugins: ['Heading'],
                ckfinder: {
                uploadUrl: "/motozone/Imgur"
            }
            })
            .then( newEditor => {
                modifyEditor = newEditor
            } )
            .catch( error => {
                console.error( error );
            } );
        </script>
    </form>
    <br/>
    <button id="modify-submit">提交</button>

</div>
<!-- End按鈕-文章修改區 -->