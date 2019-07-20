package com.motozone.autobuy.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.motozone.autobuy.model.OrderIdBean;
import com.motozone.autobuy.model.OrdersBean;
import com.motozone.autobuy.model.OrdersViewBean;
import com.motozone.autobuy.model.ProductCartBean;
import com.motozone.autobuy.model.ProductCartViewBean;
import com.motozone.autobuy.model.ProductPostBean;
import com.motozone.autobuy.model.ProductQuestionBean;
import com.motozone.autobuy.model.service.AutoBuyService;
import com.motozone.general.model.CategoryBean;
import com.motozone.general.model.CategoryStageBean;
import com.motozone.general.model.UsersBean;
import com.motozone.general.model.service.GeneralService;
import com.motozone.general.model.service.UsersService;

@Controller
public class AutoBuyController {

	@Autowired
	private AutoBuyService autoBuyService;
	@Autowired
	private GeneralService generalService;
	@Autowired
	private UsersService usersService;

	@GetMapping("/AutoBuy")
	public String getAutoBuyPage(Model model) {
		// represent AutoBuy Area 
		model.addAttribute("AutoBuyArea","this page is included by AutoBuy");
		
		// B << 歐都拜的分類 category
		List<CategoryBean> list = generalService.getSubcategory("B");
		model.addAttribute("categoryList",list);
		
		return "autoBuyPage";
	}
	
	@PostMapping("/ProductPost")
	public String processPost(@ModelAttribute ProductPostBean bean, Model model) {
		// represent AutoBuy Area 
		model.addAttribute("AutoBuyArea","this page is included by AutoBuy");
				
		JSONObject jsonObj = new JSONObject(bean);
		model.addAttribute(jsonObj);
		System.out.println(jsonObj);
		// 欄位判斷
		
		Map<String, String> errors = new HashMap<>();
		model.addAttribute("errorMsgs", errors);

		// 資料檢查
		if (bean != null) {

			if (bean.getName() == null || bean.getName().length() > 100 || bean.getName().length() == 0) {

				errors.put("name", "名稱不可空白 or 超過100字");
			}

			if (bean.getQuantity() == null || bean.getQuantity() == 0) {

				errors.put("quantity", "數量不可空白或0");
			}

			if (bean.getPrice() == null || bean.getPrice() == 0) {

				errors.put("price", "價格不可空白或0");
			}

			if (errors != null && errors.isEmpty()) {
				
				// get post
				ProductPostBean productPostBean = autoBuyService.publishProductPost(bean);
				model.addAttribute("productBean", productPostBean);
				
				// get userData
				UsersBean usersBean = usersService.getUserData(productPostBean.getUserNo());
				model.addAttribute("usersBean", usersBean);
				
				// get main category by sub-category
				CategoryBean categoryBean = generalService.getCategory(productPostBean.getCategory());
				String category = generalService.getCategoryBySubCategory(categoryBean).getCategory();
				
				CategoryBean mainCategory = generalService.getCategory(category);
				model.addAttribute("category", mainCategory);
				
				return "productPostPage";
			} else {
				return "redirectAutoBuyPage";
			}
		} else {
			return "redirectAutoBuyPage";
		}
	}
	
	@GetMapping("/ProductPost/{id}")
	public String getProductInformationPage(@PathVariable int id,Model model) {
		// represent AutoBuy Area
		model.addAttribute("AutoBuyArea","this page is included by AutoBuy");
		
		
		// 拿到單筆商品資料
		ProductPostBean productPostBean = autoBuyService.getProductPost(id);
		model.addAttribute("productBean",productPostBean);	
		
		// 取得刊登者資料
		UsersBean publisherBean = usersService.getUserData(productPostBean.getUserNo());
		model.addAttribute("usersBean", publisherBean);
		
		// 取得上一層分類
		CategoryBean bean = generalService.getCategory(productPostBean.getCategory());
		String category = generalService.getCategoryBySubCategory(bean).getCategory();
		
		CategoryBean mainCategory = generalService.getCategory(category);
		model.addAttribute("category", mainCategory);	
		
		// 取得商品的提問
		List<ProductQuestionBean> questionList = autoBuyService.getQuestionById(id);
		model.addAttribute("questionList", questionList);
		
		// 取得購買紀錄
		List<OrdersViewBean> ordersViewList = autoBuyService.getAllOrdersViewByProductID(id);
//		System.out.println(ordersViewList);
		model.addAttribute("ordersViewList", ordersViewList);
		
		
		
		
		return "productPostPage";
	}
	
	//更新商品提問
	@PostMapping("/ProductPost/{id}")
	public String getProductQuestionToPage(@PathVariable int id,
										   @ModelAttribute ProductQuestionBean questionBean,
										   Model model) {
		// represent AutoBuy Area
		model.addAttribute("AutoBuyArea", "this page is included by AutoBuy");
		// 加入提問
		questionBean.setId(id);
		System.out.println(questionBean);
		autoBuyService.addQuestion(questionBean);
		
		// 拿到單筆商品資料
		ProductPostBean productPostBean = autoBuyService.getProductPost(id);
		model.addAttribute("productBean", productPostBean);
		
		// 取得刊登者資料
		UsersBean usersBean = usersService.getUserData(productPostBean.getUserNo());
		model.addAttribute("usersBean", usersBean);
				
		// 取得上一層分類
		CategoryBean bean = generalService.getCategory(productPostBean.getCategory());
		String category = generalService.getCategoryBySubCategory(bean).getCategory();

		CategoryBean mainCategory = generalService.getCategory(category);
		model.addAttribute("category", mainCategory);
		
		// 取得商品的提問
		List<ProductQuestionBean> list = autoBuyService.getQuestionById(id);
		model.addAttribute("questionList", list);
		
		// 取得購買紀錄
		List<OrdersViewBean> ordersViewList = autoBuyService.getAllOrdersViewByProductID(id);
		System.out.println(ordersViewList);
		model.addAttribute("ordersViewList", ordersViewList);
		
		return "productPostPage";
	}
	
	@GetMapping(path="/ProductPosts/{userNo}",produces="application/json")
	@ResponseBody
	public List<ProductPostBean> getProductPostList(@PathVariable Integer userNo) {
		return autoBuyService.getProductPostByUserNo(userNo);
	}
	
	@GetMapping("/ProductCategories/{category}")
	public String getCategoryPage(@PathVariable String category,Model model) {
		// represent AutoBuy Area
		model.addAttribute("AutoBuyArea","this page is included by AutoBuy");
		
		
		// get data by category
		// 大分類資料
		List<ProductPostBean> productList = autoBuyService.getCategoryProductPost(category);	
		model.addAttribute("productList",productList);	
		
		// 子分類資料
		List<CategoryBean> categoryList = generalService.getSubcategory(category);
		model.addAttribute("categoryList",categoryList);
		
		// 拿到分類名稱
		CategoryBean categoryBean = generalService.getCategory(category);
		model.addAttribute("categoryBean", categoryBean);
		
		//回上一層
		String categorySubClass = generalService.getCategoryBySubCategory(categoryBean).getCategory();
		CategoryBean subClass = generalService.getCategory(categorySubClass);
		model.addAttribute("categorySubClass", subClass);
		System.out.println(categorySubClass);
		
		//回上兩層
		CategoryStageBean result = generalService.getCategoryBySubCategory(subClass);
		CategoryBean mainClass = null;		
		if(result != null) {
			String categoryMainClass = result.getCategory();
			mainClass = generalService.getCategory(categoryMainClass);
			System.out.println(categoryMainClass);
		}	
		model.addAttribute("categoryMainClass", mainClass);
		
		
		return "productCategoryPage";
	}
	
	//購物車頁面
	@PostMapping("/AutoBuyCart/{userNo}")
	public String getAutoBuyCartPage(@ModelAttribute ProductPostBean bean, Model model) {
		// represent AutoBuy Area
		model.addAttribute("AutoBuyArea", "this page is included by AutoBuy");
		
		// set new product into cart
		ProductPostBean search = autoBuyService.getProductPost(bean.getId());
		autoBuyService.joinAutoBuyCart(search, bean.getQuantity(), bean.getUserNo());
		
		// get new cart list
		List<ProductCartViewBean> list = autoBuyService.getAllViewProductFromCart(bean.getUserNo());
		model.addAttribute("cartItem", list);
		
		return "autoBuyCartPage";
	}
	
	//購物車  移除商品
	@DeleteMapping(path="/AutoBuyCart/{userNo}",produces="application/json")
	@ResponseBody
	public boolean getUpdateCartToPage(@RequestBody String body,@PathVariable Integer userNo,Model model) {
		JSONObject obj = new JSONObject(body);
		
		ProductCartBean search = autoBuyService.getCartProductByIdAndUser(Integer.valueOf(obj.getString("id")), userNo);
		
		boolean result = autoBuyService.deleteProductFromCart(search);
		
		return result;
	}
	
	
	@PutMapping(path = { "/ProductCart/{userNo}" }, produces = "application/json")
	@ResponseBody
	public boolean updateCart(@PathVariable Integer userNo,@RequestBody String body) {
		JSONArray cartList = new JSONArray(body);
		
		if(cartList != null) {
			for(int i=0 ; i<cartList.length() ; i++ ) {
				
				// get product info from cart list
				JSONObject orderItem = cartList.getJSONObject(i);
				Integer id = Integer.parseInt(orderItem.getString("id"));
				
				
				// update shopping cart's each item's qty
				ProductCartBean bean = autoBuyService.getCartProductByIdAndUser(id, userNo);
				bean.setQuantity(Integer.parseInt(orderItem.getString("qty")));
				autoBuyService.updateProductFromCart(bean);
				
			}
			
		} else {
			return false;
		} 
		return true;
	}
	
	
	@GetMapping(path= {"/Orders/{userNo}"})
    public String addOrder(@PathVariable Integer userNo) {
		
		// insert order
		OrderIdBean orderIdBean = autoBuyService.addOrder(userNo);
		System.out.println(orderIdBean);
		// -------------
		
		// decrease stock number
		List<OrdersBean> list = autoBuyService.getAllOrdersByOrderID(orderIdBean.getoID());
		System.out.println(list);
		for(OrdersBean bean : list) {
			ProductPostBean productPostBean = autoBuyService.getProductPost(bean.getProductID());
			int stock = 0;
			stock =  productPostBean.getQuantity() - bean.getQuantity();
			productPostBean.setQuantity(stock);
			autoBuyService.updateProductPost(productPostBean);
		}
		
		// clean table
		autoBuyService.cartClean(userNo);

		return "redirectAutoBuyPage";
		
	}
	
	@PostMapping(path="/ProductCart/{userNo}",produces = "application/json")
	@ResponseBody
	public List<ProductCartViewBean> addCarItem(@ModelAttribute ProductPostBean bean,@PathVariable Integer userNo ) {
		ProductPostBean insertitem = autoBuyService.getProductPost(bean.getId());
		System.out.println("insertitem = " + insertitem);
		
		autoBuyService.joinAutoBuyCart(insertitem, bean.getQuantity(), userNo);
		
		List<ProductCartViewBean> list = autoBuyService.getAllViewProductFromCart(bean.getUserNo());

		return list;
		
	}
	
	@GetMapping("/AutoBuyCart/{userNo}")
	public String shoppingCartPage(@PathVariable Integer userNo,Model model) {
		// represent AutoBuy Area
		model.addAttribute("AutoBuyArea", "this page is included by AutoBuy");
				
		List<ProductCartViewBean> list = autoBuyService.getAllViewProductFromCart(userNo);
		model.addAttribute("cartItem", list);
		
		return "autoBuyCartPage";
	}
	
	@GetMapping(path="/ProductCartView/{userNo}",produces="application/json")
	@ResponseBody
	public List<ProductCartViewBean> checkCartItem(@PathVariable Integer userNo,Model model) {
		List<ProductCartViewBean> list = autoBuyService.getAllViewProductFromCart(userNo);
		
		return list;
		
	}
	
	@GetMapping(path="/OrderId/{userNo}",produces="application/json")
	@ResponseBody
	public List<OrderIdBean> getUserOrders(@PathVariable Integer userNo) {
		
		List<OrderIdBean> list = autoBuyService.getAllOrderIdByUser(userNo);
		
		return list;
		
	}
	
	@GetMapping(path="/OrderView/{orderID}",produces="application/json")
	@ResponseBody
	public List<OrdersViewBean> getOrderDetail(@PathVariable Integer orderID) {
		
		List<OrdersViewBean> list = autoBuyService.getAllOrdersViewByOrderID(orderID);
		
		return list;
		
	}
	
	@GetMapping("/OrdersView/PDF/{orderID}")
	public String getOrderDetailPDF(@PathVariable Integer orderID,Model model) {
		
		model.addAttribute("beanList", autoBuyService.getAllOrdersViewByOrderID(orderID));
		model.addAttribute("fileName","OrderDetail");
		
		return "pdfDownload";
	}
	
	@GetMapping("/OrdersView/Excel/{orderID}")
	public String getOrderDetailExcel(@PathVariable Integer orderID,Model model) {
		
		model.addAttribute("beanList", autoBuyService.getAllOrdersViewByOrderID(orderID));
		model.addAttribute("fileName","OrderDetail");
		
		return "excelDownload";
	}
	
	
}
