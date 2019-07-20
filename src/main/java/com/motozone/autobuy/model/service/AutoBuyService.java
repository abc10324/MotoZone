package com.motozone.autobuy.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.motozone.autobuy.model.OrderIdBean;
import com.motozone.autobuy.model.OrdersBean;
import com.motozone.autobuy.model.OrdersViewBean;
import com.motozone.autobuy.model.ProductCartBean;
import com.motozone.autobuy.model.ProductCartViewBean;
import com.motozone.autobuy.model.ProductPostBean;
import com.motozone.autobuy.model.ProductQuestionBean;
import com.motozone.autobuy.model.dao.OrderIdDAO;
import com.motozone.autobuy.model.dao.OrdersDAO;
import com.motozone.autobuy.model.dao.OrdersViewDAO;
import com.motozone.autobuy.model.dao.ProductCartDAO;
import com.motozone.autobuy.model.dao.ProductCartViewDAO;
import com.motozone.autobuy.model.dao.ProductPostDAO;
import com.motozone.autobuy.model.dao.ProductQuestionDAO;
import com.motozone.general.model.dao.UsersDAO;

@Service
@Transactional
public class AutoBuyService {
	@Autowired
	private ProductPostDAO productPostDAO;
	@Autowired
	private ProductCartDAO productCartDAO;
	@Autowired
	private ProductCartViewDAO productCartViewDAO;
	@Autowired
	private ProductQuestionDAO productQuestionDAO;
	@Autowired
	private OrderIdDAO orderIdDAO;
	@Autowired
	private OrdersDAO ordersDAO;
	@Autowired
	private OrdersViewDAO ordersViewDAO;
	@Autowired
	private UsersDAO usersDAO;

	// insert
	public ProductPostBean publishProductPost(ProductPostBean bean) {
		ProductPostBean result = null;
		
			if(productPostDAO.insert(bean)) {
				result = productPostDAO.selectById(bean.getId());
				return result;
			}else {
				return null;
			}
	}
	
	//update
	public ProductPostBean updateProductPost(ProductPostBean bean) {
		ProductPostBean result = null;
		
		if(productPostDAO.selectById(bean.getId())!=null) {
			result = productPostDAO.update(bean);
			
			return result;
		}else {
			return null;
		}
	}
	
	//query
	public ProductPostBean getProductPost(Integer id) {
		ProductPostBean result = productPostDAO.selectById(id);
		
		if(result != null) {
			
			return result;
		}else {
			System.out.println("查無此商品資料.");
			return null;
		}
	}
	
	//query by userNo
	public List<ProductPostBean> getProductPostByUserNo(Integer userNo) {
		return productPostDAO.selectByUserNo(userNo);
	}
	
	//query by category
	public List<ProductPostBean> getCategoryProductPost(String category) {
		return productPostDAO.selectByCategory(category);
	}

	// query by subcategory
	public List<ProductPostBean> getSubCategoryProductPost(String category) {
		return productPostDAO.selectByExplicitCategory(category);
	}
	
	//	-----------------------------------------------------------------------------------
	//	Question
	public List<ProductQuestionBean> addQuestion(ProductQuestionBean bean) {
	
		if (bean.getContent() != null) {
			if (productQuestionDAO.insert(bean)) {
				return productQuestionDAO.selectAllById(bean.getId());
			}
		}
		
		return null;
	}
	
	//public List<ProductQuestionBean> updateQuestion(Integer userNo , Integer id){
	//List<ProductQuestionBean> list = null;
	//if(getQuestion(userNo,id).size() != 0) {
	//
	//}
	//return null;
	//}
	public List<ProductQuestionBean> getQuestionById(Integer id){
		List<ProductQuestionBean> list = null;
		list = productQuestionDAO.selectAllById(id);
		
		if(list.size() != 0) {
			return list;
		}
		
//		System.out.println("無資料.");
		return null;
	}
	
	
	public List<ProductQuestionBean> getQuestionByUserAndId(Integer userNo, Integer id) {
		List<ProductQuestionBean> list = null;
		list = productQuestionDAO.selectAllByUserAndId(userNo, id);
		
		if (list.size() != 0) {
			return list;
		}
		System.out.println("無資料.");
		return null;
	}

//	----------------------------------------------------------------------
	// 購物車Service productCartViewDAO(select) productCartDAO(insert,delete,update)

	// insert
	public ProductCartViewBean joinAutoBuyCart(ProductPostBean bean, Integer quantity, Integer userNo) {

		ProductCartBean result = null;

		if (getViewProductFromCartByIdAndUser(bean.getId(), userNo) == null) {
			// set cart data by Input ProductCartBean from product id
			// and input data
			result = new ProductCartBean();
			result.setContent(bean);
			result.setQuantity(quantity);
			result.setUserNo(userNo);

			if (productCartDAO.insert(result)) {

				return productCartViewDAO.selectByNo(result.getNo());
			}else {
				System.out.println("加入失敗.");
				return null;
			}
			
		}else {
			System.out.println("此商品已加入購物車.");
			return null;
		}
	}

	// update
	public ProductCartBean updateProductFromCart(ProductCartBean bean) {
		if(bean != null) {
			return productCartDAO.update(bean);
		}else {
			System.out.println("修改失敗.");
			return null;
		}
		
	}

	// 移除一個商品
	public boolean deleteProductFromCart(ProductCartBean bean) {
		if(productCartDAO.selectByNo(bean.getNo()) != null) {
			System.out.println("更新成功.");
			return productCartDAO.delete(bean);
		}else {
			System.out.println("移除失敗.");
			return false;
		}
		
	}

	// clean table
	public boolean cartClean(Integer uNo) {
		if(productCartViewDAO.selectAll(uNo) != null) {
			System.out.println("清除成功.");
			return productCartDAO.cleanTable(uNo);
		} else {
			System.out.println("查無該會員購物車資料.");
			return false;
		}
		
	}
//---------------------query productCartBean--------------------
	
	//query by No productCartBean
	public ProductCartBean getCartProductByNo(Integer no) {
		return productCartDAO.selectByNo(no);
	}
	
	//query by id productCartBean
	public ProductCartBean getCartProductByIdAndUser(Integer id,Integer userNo) {
		return productCartDAO.selectByIdAndUser(id, userNo);
	}
	
	//query all productCartBean
	public List<ProductCartBean> getCartProductAll(Integer userNo) {
		return productCartDAO.selectAll(userNo);
	}
	
//---------------------------query productCartViewBean---------------------------
	
	// query by No productCartViewBean
	public ProductCartViewBean getViewProductFromCartByNo(Integer no) {
		return productCartViewDAO.selectByNo(no);
	}

	// query by id productCartViewBean
	public ProductCartViewBean getViewProductFromCartByIdAndUser(Integer id, Integer userNo) {
		return productCartViewDAO.selectByIdAndUser(id, userNo);
	}

	// query all product productCartViewBean
	public List<ProductCartViewBean> getAllViewProductFromCart(Integer userNo) {
		return productCartViewDAO.selectAll(userNo);
	}
	
//	------------------------------order block------------------------------------
	
	public OrderIdBean addOrder(Integer userNo) {
		//1.select cart by uNo
		//2.insert orderId
		//3.insertOrders by cart's list
		//4.return orderId
		OrderIdBean orderIdBean = new OrderIdBean();
		
		//1
		List<ProductCartViewBean> list = productCartViewDAO.selectAll(userNo);
		if(list.size() == 0) {
			return null;
		}
		
		int total = 0;
		for(ProductCartViewBean bean : list) {
				total += bean.getTotal();
		}
		//2
		orderIdBean.setuNo(userNo);
		orderIdBean.setuName(usersDAO.selectByNo(userNo).getuName());
		orderIdBean.setTotal(total);
		
		OrderIdBean result = orderIdDAO.insert(orderIdBean);
		if(result == null) {
			return null;
		}
		//3
		for(ProductCartViewBean bean : list) {
			OrdersBean ordersBean = new OrdersBean();
			
			ordersBean.setOrderID(orderIdBean.getoID());
			ordersBean.setProductID(bean.getId());
			ordersBean.setQuantity(bean.getQuantity());			
			ordersDAO.insert(ordersBean);
		}
		
		if(ordersDAO.selectByOID(result.getoID()).size() == 0) {
			return null;
		}else {
			return result;
		}
	}
	
	public List<OrderIdBean> getAllOrderIdByUser(Integer userNo){
		return orderIdDAO.selectByUno(userNo);
	}
	
	public List<OrdersBean> getAllOrdersByOrderID(Integer orderID){
		
		// need to modify
		
		return ordersDAO.selectByOID(orderID);
	}
	
	public List<OrdersBean> getAllOrdersByProductID(Integer productID){
		return ordersDAO.selectByPID(productID);
	}
	
	public List<OrdersViewBean> getAllOrdersViewByOrderID(Integer orderID){
		return ordersViewDAO.selectByOrderID(orderID);
	}
	
	public List<OrdersViewBean> getAllOrdersViewByProductID(Integer productID){
		return ordersViewDAO.selectByProductID(productID);
	}
}
