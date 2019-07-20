package com.motozone.autobuy.model.service;


import java.util.List;

import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.motozone.autobuy.model.OrderIdBean;
import com.motozone.autobuy.model.OrdersBean;
import com.motozone.autobuy.model.ProductCartBean;
import com.motozone.autobuy.model.ProductCartViewBean;
import com.motozone.autobuy.model.ProductPostBean;
import com.motozone.autobuy.model.ProductQuestionBean;

public class AutoBuyServiceTest {
	ApplicationContext context;
	SessionFactory factory;
	AutoBuyService autoBuyService;
	
	@Before
	public void init() {
		context = new AnnotationConfigApplicationContext(com.motozone.config.SpringJavaConfig.class);

		autoBuyService = (AutoBuyService) context.getBean("autoBuyService");
		// get session factory
		factory = (SessionFactory) context.getBean("sessionFactory");
	}
	
	@Test
	public void publishProduct() {
		factory.getCurrentSession().beginTransaction();
		ProductPostBean bean = new ProductPostBean();
		bean.setCategory("測試");
		bean.setImg("http://ewjodwepqwo");
		bean.setName("Service測試");
		bean.setQuantity(55);
		bean.setPrice(55);
		bean.setArea("測試");
		bean.setStatus("測試");
		bean.setStatusDescription("測試");
		bean.setDescription("測試");
		bean.setPayment("測試");
		bean.setDelivery("測試");
		bean.setShipping("測試");
		bean.setAuctionDays(12);
		
		ProductPostBean result = autoBuyService.publishProductPost(bean);
		
		if(result != null) {
			System.out.println(result);
		} else {
			System.out.println("Publish fail");
		}
		
		factory.getCurrentSession().getTransaction().commit();
		
		
		
	}
	
	@Test
	public void updateProduct() {
		factory.getCurrentSession().beginTransaction();
		ProductPostBean bean = autoBuyService.getProductPost(14);
//		ProductPostBean bean = autoBuyService.getProductPost(2);
		if (bean != null) {
			
			bean.setName("Service修改");
			bean.setQuantity(55);
			bean.setPrice(55);
			bean.setArea("修改");
			bean.setStatus("修改");
			bean.setStatusDescription("修改");
			bean.setDescription("修改");
			bean.setAuctionDays(11);
			System.out.println(autoBuyService.updateProductPost(bean));
		} else {
			System.out.println("id不存在");
		}
		factory.getCurrentSession().getTransaction().commit();
	}
	
	@Test
	public void productQuery() {
		factory.getCurrentSession().beginTransaction();
		ProductPostBean bean = autoBuyService.getProductPost(15);
//		ProductPostBean bean = autoBuyService.getProductPost(2);
		factory.getCurrentSession().getTransaction().commit();
		
		System.out.println("bean = " + bean);

	}
	
	@Test
	public void productQueryByCategory() {
		//query by Category
		factory.getCurrentSession().beginTransaction();
		
//		List<ProductPostBean> list = autoBuyService.getCategoryProductPost("BBE");
		List<ProductPostBean> list = autoBuyService.getCategoryProductPost("123");
		factory.getCurrentSession().getTransaction().commit();
		
		if(list != null) {
			if(list.size() != 0) {
				for(ProductPostBean bean : list) {
					System.out.println("result = " + bean);
				}
			} else {
				System.out.println("No Result");
			}
			
		} else {
			System.out.println("NULL");
		}
	}
	
	@Test
	public void productQueryBySubCategory() {
		//query by Category
		factory.getCurrentSession().beginTransaction();
//		List<ProductPostBean> list = autoBuyService.getSubCategoryProductPost("BBECR");
		List<ProductPostBean> list = autoBuyService.getSubCategoryProductPost("123");
		factory.getCurrentSession().getTransaction().commit();
		
		if(list != null) {
			if(list.size() != 0) {
				for(ProductPostBean bean : list) {
					System.out.println("result = " + bean);
				}
			} else {
				System.out.println("No Result");
			}
			
		} else {
			System.out.println("NULL");
		}
	}
//	-------------------------------------------------------------------------
								//Question
	@Test
	public void addQuestionTest() {
		factory.getCurrentSession().beginTransaction();
		ProductQuestionBean bean = new ProductQuestionBean();
		bean.setUserNo(0);
		bean.setUserName("11號");
		bean.setId(1);
		bean.setContent("addQuestion");
		System.out.println(autoBuyService.addQuestion(bean));
		factory.getCurrentSession().getTransaction().commit();
	}
	
	@Test
	public void getQuestionByUserAndIdTest() {
		factory.getCurrentSession().beginTransaction();
		System.out.println(autoBuyService.getQuestionByUserAndId(0, 1));
		factory.getCurrentSession().getTransaction().commit();
	}
	
	@Test
	public void getQuestionByIdTest() {
		factory.getCurrentSession().beginTransaction();
		System.out.println(autoBuyService.getQuestionById(1));
		factory.getCurrentSession().getTransaction().commit();
	}
	
//	-------------------------------------------------------------------------
								//購物車
	
	
	//------------query productCartBean-----------
	//query by no productCartBean
	@Test  
	public void getCartProductByNo(){
		factory.getCurrentSession().beginTransaction();
		ProductCartBean bean = autoBuyService.getCartProductByNo(8);
//		ProductCartBean bean = autoBuyService.getCartProductByNo(1);
		
		factory.getCurrentSession().getTransaction().commit();
		
		if(bean != null) {
			System.out.println(bean);
		} else {
			System.out.println("No Result");
		}
		
	}
	
	//query by id productCartBean
	@Test
	public void getCartProductById(){
		factory.getCurrentSession().beginTransaction();
		ProductCartBean bean = autoBuyService.getCartProductByIdAndUser(202, 2);
//		ProductCartBean bean = autoBuyService.getCartProductById(200, 1);
		factory.getCurrentSession().getTransaction().commit();
		
		if(bean != null) {
			System.out.println(bean);
		} else {
			System.out.println("No Result");
		}
	}
	
	//query all productCartBean
	@Test
	public void getCartProductAll(){
		factory.getCurrentSession().beginTransaction();
		List<ProductCartBean> list = autoBuyService.getCartProductAll(2);
//		List<ProductCartBean> list = autoBuyService.getCartProductAll(1);
		factory.getCurrentSession().getTransaction().commit();
		
		if(list != null) {
			if(list.size() != 0) {
				for(ProductCartBean bean : list) {
					System.out.println("result = " + bean);
				}
			} else {
				System.out.println("No Result");
			}
			
		} else {
			System.out.println("NULL");
		}
	}
	
	//----------------query productCartViewBean-----------------
	//query by Id productCartViewBean
	@Test
	public void getViewProductFromCartById() {
		factory.getCurrentSession().beginTransaction();
		ProductCartViewBean bean = autoBuyService.getViewProductFromCartByIdAndUser(202, 2);
//		ProductCartViewBean bean = autoBuyService.getViewProductFromCartByIdAndUser(200, 1);
		factory.getCurrentSession().getTransaction().commit();
		
		if(bean != null) {
			System.out.println(bean);
		} else {
			System.out.println("No Result");
		}
	}
	
	
	//query by No productCartViewBean
	@Test
	public void getViewProductFromCartByNo() {
		factory.getCurrentSession().beginTransaction();
//		ProductCartViewBean bean = autoBuyService.getViewProductFromCartByNo(8);
		ProductCartViewBean bean = autoBuyService.getViewProductFromCartByNo(1);
		factory.getCurrentSession().getTransaction().commit();
		
		if(bean != null) {
			System.out.println(bean);
		} else {
			System.out.println("No Result");
		}
	}
	
	//query AllProduct productCartViewBean
	@Test
	public void getAllViewProductFromCart() {
		factory.getCurrentSession().beginTransaction();
		List<ProductCartViewBean> list = autoBuyService.getAllViewProductFromCart(1); //userNo
//		List<ProductCartViewBean> list = autoBuyService.getAllViewProductFromCart(2); //userNo
		
		factory.getCurrentSession().getTransaction().commit();
		
		System.out.println(list);
		System.out.println("總共有 " + list.size() + " 筆商品"); //總數
	}
//	-----------insert update delete---------
	//insert
	@Test
	public void joinAutoBuyCart() {
		factory.getCurrentSession().beginTransaction();
		ProductPostBean bean = autoBuyService.getProductPost(195);
		System.out.println(bean);
		System.out.println(autoBuyService.joinAutoBuyCart(bean,123,1));
		factory.getCurrentSession().getTransaction().commit();
	}
	
	@Test
	public void update() {
		factory.getCurrentSession().beginTransaction();
		ProductCartBean result = autoBuyService.getCartProductByIdAndUser(195, 1);
		result.setQuantity(35);
		System.out.println(autoBuyService.updateProductFromCart(result));
		factory.getCurrentSession().getTransaction().commit();
	}
	
	//delete
	@Test
	public void removeFromCart() {
		factory.getCurrentSession().beginTransaction();
		ProductCartBean bean = autoBuyService.getCartProductByNo(8);
		boolean result = autoBuyService.deleteProductFromCart(bean);
		factory.getCurrentSession().getTransaction().commit();
		
		System.out.println(result);
	}
	
	//cart clean
	@Test
	public void cartClean() {
		factory.getCurrentSession().beginTransaction();
		System.out.println(autoBuyService.cartClean(1)); //userNo
//		System.out.println(autoBuyService.cartClean(2)); //userNo
		factory.getCurrentSession().getTransaction().commit();
	}
	
//	------------------------------orderId------------------------------
	@Test
	public void addOrder() {
		factory.getCurrentSession().beginTransaction();
		System.out.println(autoBuyService.addOrder(1));
		factory.getCurrentSession().getTransaction().commit();
	}
	@Test
	public void getAllOrderIdByUser() {
		factory.getCurrentSession().beginTransaction();
		List<OrderIdBean> list = autoBuyService.getAllOrderIdByUser(2);
		factory.getCurrentSession().getTransaction().commit();
		
		if(list.size() != 0) {
			for(OrderIdBean bean : list) {
				System.out.println(bean);
			}
		} else {
			System.out.println("No Result");
		}
	}
	@Test
	public void getAllOrdersByUser() {
		factory.getCurrentSession().beginTransaction();
		List<OrdersBean> list = autoBuyService.getAllOrdersByOrderID(3);
		factory.getCurrentSession().getTransaction().commit();
		
		if(list.size() != 0) {
			for(OrdersBean bean : list) {
				System.out.println(bean);
			}
		} else {
			System.out.println("No Result");
		}
	}
	
	@After
	public void destroy() {
		factory.close();
		
		((ConfigurableApplicationContext) context).close();
	}
}
