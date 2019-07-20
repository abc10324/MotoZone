package com.motozone.general.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.motozone.autobuy.model.ProductCartViewBean;
import com.motozone.autobuy.model.service.AutoBuyService;
import com.motozone.general.model.UsersBean;

import ecpay.payment.integration.AllInOne;
import ecpay.payment.integration.domain.AioCheckOutOneTime;
import ecpay.payment.integration.domain.InvoiceObj;
import ecpay.payment.integration.exception.EcpayException;

@Controller
public class EcPayController {
	@Autowired
	private AutoBuyService autoBuyService;
	
	private final String AUTH_UPDATE_PATH = "/Authority/";
	private final String ORDER_GEN_PATH = "/Orders/";

	@PostMapping(value = "/EcPay/Authority/{authID}", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String authorityEcPayPM(@PathVariable String authID,
			     				   @RequestBody UsersBean bean, 
			     				   HttpServletRequest req) {
		
		String baseURL = req.getScheme() + "://" 
					   + req.getServerName() + ":"
					   + req.getServerPort() 
					   + req.getContextPath();
		
		AioCheckOutOneTime aio = new AioCheckOutOneTime(); 
		AllInOne all = new AllInOne("");
		
		// without invoice
		InvoiceObj invoice = null;
		
		aio.setMerchantTradeNo(UUID.randomUUID().toString().replaceAll("-", "").substring(0, 15));
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
		aio.setMerchantTradeDate(sdf.format(date));
		
		if("PM".equals(authID)) {
			aio.setItemName("MotoZone白金會員升級");
			aio.setTotalAmount("500");
		} else if("DM".equals(authID)){
			aio.setItemName("MotoZone鑽石會員升級");
			aio.setTotalAmount("2000");
		}
		
		
		aio.setTradeDesc("MotoZone");
		aio.setReturnURL("https://joycart.azurewebsites.net/testAIO"); // need to modify
		aio.setClientBackURL(baseURL + AUTH_UPDATE_PATH + authID);

		// update loginUser's parameter in session scope
		bean.setAuthID(authID);
		req.getSession().setAttribute("userDataBean", bean);
		
		try {
			String html = all.aioCheckOut(aio, invoice);
			
			return html;
		} catch (EcpayException e) {
			throw new Error(e.getNewExceptionMessage());
		}

	}
	
	@PostMapping(value = "/EcPay/Orders/{userNo}", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String payment(@PathVariable Integer userNo,
			 				 HttpServletRequest req) {
		String baseURL = req.getScheme() + "://" 
				   + req.getServerName() + ":"
				   + req.getServerPort() 
				   + req.getContextPath();
		
		// get specific cart list by userNo
		List<ProductCartViewBean> list = autoBuyService.getAllViewProductFromCart(userNo);
		Integer sum = 0;

		// build string for multiple items
		StringBuilder stb = new StringBuilder();
		for(ProductCartViewBean bean : list) {
			stb.append(bean.getName());
			stb.append(" x ");
			stb.append(bean.getQuantity());
			stb.append(" = ");
			stb.append(bean.getTotal());
			stb.append(" 元#");
			
			sum += bean.getTotal();
		}
		
		// remove last # mark
		stb.deleteCharAt(stb.length() - 1);
		
		
		AioCheckOutOneTime aio = new AioCheckOutOneTime();
		AllInOne all = new AllInOne("");
		InvoiceObj invoice = null;
		
		
		aio.setMerchantTradeNo(UUID.randomUUID().toString().replaceAll("-", "").substring(0, 15));
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
		aio.setMerchantTradeDate(sdf.format(date));
		
		aio.setItemName(stb.toString());
		aio.setTotalAmount(sum.toString());
		aio.setTradeDesc("MotoZone");
		aio.setReturnURL("https://joycart.azurewebsites.net/testAIO"); // need to modify
		aio.setClientBackURL(baseURL + ORDER_GEN_PATH + userNo);
		
		try {
			String html = all.aioCheckOut(aio, invoice);
			System.out.println(html);
			return html;
		} catch (EcpayException e) {
			throw new Error(e.getNewExceptionMessage());
		}
	}
}
