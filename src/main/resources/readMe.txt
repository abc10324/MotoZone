MotoZone v1.0
1.初版釋出

MotoZone v1.1
1.新增AutoBuy區塊的網頁及controller/service/DAO/測試程式
2.註冊/登入/登出 功能串接前端頁面
3.新增註冊驗證信功能,更新如下
	a.general.model.util.EmailSender
	b.general.controller.UsersController.regist()
	c.general.model.service.UsersService.authCodeCheck()
	d.general.model.dao.UsersDAO.update()
	e.general.model.dao.UsersDAOHibernate.update()
	f.service/DAO 測試程式
4.AutoBuy發布貼文功能新增AJAX上傳圖片功能,更新如下
	a.autobuy.controller.AutoBuyController.processPost()
	b.pages/include/productPublishForm.jsp
	c.pages/tradeBlock.jsp
	d.autobuy.js
5.資料庫更新於createImage.sql,敘述如下
	a.article_list View更新 (效能改善)
	b.image Table新增 

MotoZone v1.2 (19/05/23)
1.註冊頁面新增輸入驗證碼的視窗,更新如下
	a.general.controller.UsersController.authCodeCheckAjax()
	b.pages/include/loginArea.jsp
	c.static/js/main.js
	d.static/css/style.css
2.文章發布按鈕修改為登入後才能使用,且可帶登入的用戶資料進貼文,更新如下
	a.article.controller.ArticleController.addPost()
	b.article.model.ArticleBean (新增建構子)
	c.static/js/article.js
3.新增第三方登入功能(Google登入) ,更新如下
	a.general.controller.UsersController.googleLogin()
	b.general.service.UsersService.googleLogin()
	c.static/js/main.js (7.GOOGLE LOGIN CONTROL)
	d.各網頁新增載入js檔 (https://apis.google.com/js/api:client.js)
	e.pom.xml新增google-api-client(v1.28.0)
4.文章區清單顯示新增顯示部分內容的功能,更新如下
	a.article.controller.ArticleController.getPostList() (新增呼叫Utils)
	b.article.model.util.ArticleUtils.postListContentCutter()
	c.測試程式併於ArticleServiceTest
5.為了改進文章清單讀取效能,修改article,article_id,article_list等資料表,並更動相對應之程式如下
	a.article.model.ArticleBean
	b.article.model.ArticleIdBean
	c.article.model.service.ArticleService
	d.article.controller.ArticleController
6.使用mustache模板引擎載入Ajax回傳的JSON內容(Post/PostList),更新如下
	a.article.controller.ArticleController.getPostListAjax()
	b.article.model.dao.ArticleListDAO.getArticleList(Integer id)
	c.article.model.dao.ArticleListDAOHibernate.getArticleList(Integer id)
	d.article.model.service.ArticleService.getPostList(Integer id)
	e.article.controller.ArticleController.getPostAjax()
	f.article.model.util.ArticleUtils.postListContentCutter(ArticleListBean bean)
	g.pages/article.jsp
	h.statics/css/article.css
	i.statics/css/article.js
7.更新AutoBuy區塊
8.Category相關Bean/DAO/Service方法盡數移至general package

MotoZone v1.3
1.Article為顯示使用者頭相,由article改為article_post(view)輸出
	a.新增資料庫View article_post(Article left outer join Users)
	b.新增article.model.articlePostBean
	c.新增article.model.dao.articlePostDAO
	d.新增article.model.dao.articlePostDAOHibernate
	e.新增article.model.dao.articlePostDAOHibernate測試程式
2.PostList/Category 顯示部分文章內容功能由java產生改為CSS產生,修改以下
	a.article.model.util.ArticleUtils.postListContentExtractor()
	b.article.model.util.ArticleUtils.postListContentCutter()
	c.statics/css/article.css
	d.statics/css/category.css
3.新增PDF/Excel的View (使用reflect將bean直接解析產出)
	a.config.view.GeneralPdfView
	b.config.view.GeneralExcelView
	c.config.view.GeneralViewJavaConfig.pdfDownload()
	d.config.view.GeneralViewJavaConfig.excelDownload()
	e.general.controller.UsersController.getPdfTest()
	f.general.controller.UsersController.getExcelTest()
4.新增聊天室 (每個頁面都可使用)
5.新增後台會員資料修改部分
6.新增AutoBuy區塊的貼文功能部分更新及新增購物車
7.新增Imgur圖床上傳功能 (僅AutoBuy貼文功能有串接此功能)
8.更換DataBase Name為MotoZoneDB (修改SpringJavaConfig及context.xml)

MotoZone v1.4
1.新增權限升級功能
2.修改Google登入JS載入得到null的Bug(改為畫面ready後執行startApp())
3.新增點擊標題列Logo回首頁的功能(含AutoBuy區塊)
4.文章清單頁面側邊上層類別圖示更新並新增連結上層頁面功能
5.文章清單頁面新增圖片延遲載入功能 (lazyload.js)
6.文章清單頁面導入瀑布流排板Library (masonry.pkgd.min.js)
7.文章顯示彈出視窗新增卷軸至底自動加載下一頁內容的功能
8.文章清單頁面新增卷軸至底自動加載下一頁內容的功能

MotoZone v1.5
1.使用者名片新增聊天按鈕的功能
2.使用者名片加好友及聊天按鈕的使用情境調整如下
	a.使用者點擊彈出自己的名片 - 不顯示加好友及聊天按鈕
	b.使用者點擊彈出非好友的名片 - 顯示加好友及聊天按鈕/加好友有功能/聊天按鈕鎖定無功能
	c.使用者點擊彈出好友的名片 - 顯示加好友及聊天按鈕/加好友鎖定無功能/聊天按鈕有功能
3.加好友按鈕點擊後可及時更新於聊天室(前版需重新登入)
4.MailSender修正上VM會寄出亂碼的問題
5.登入/註冊/驗證碼畫面皆可使用Enter觸發送出表單功能
6.後台點大圖示A顯示A操作區塊後點擊小圖示B顯示操作區塊時 , 無法正確觸發AJAX顯示內容的問題已修正
7.會員中心新增大頭貼裁切功能 (Jcrop套件)
8.新增管理者離線時使用的聊天機器人
9.新增購物車功能

