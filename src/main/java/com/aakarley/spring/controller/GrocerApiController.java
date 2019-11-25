package com.aakarley.spring.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dao.daoimpl.Dao;
import com.model.AddCartItem;
import com.model.AppUpdate;
import com.model.CartItem;
import com.model.ChecksumItem;
import com.model.CityItem;
import com.model.ConstantItem;
import com.model.GetCategoryItem;
import com.model.GrocerProductItem;
import com.model.LoginItem;
import com.model.OrderItem;
import com.model.OrderProductItem;
import com.model.SaveOrderitem;
import com.model.SearchProductItem;
import com.model.WalletItem;
import com.util.AppConstant;
import com.util.ChecksumGeneration;
import com.util.DownloadImage;

@Controller
public class GrocerApiController {

	@RequestMapping(value ="grocerTest", method = RequestMethod.GET)
	public  @ResponseBody String test() throws SQLException {
		System.out.print("hello123");
	    return "GROCER API WORKING";
	}
	
	
	@RequestMapping (value= "rest/parentGetCategory",method=RequestMethod.GET)
	public@ResponseBody List<GetCategoryItem> getCategoryItem()  {
		
		Dao dao= new Dao();
		List<GetCategoryItem> getCategory = null;
		try {
			getCategory = dao.getGrocersCategory(AppConstant.GET_IMAGE_PATH);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
		
		return getCategory;
	}
	
	 @RequestMapping(value="rest/getImage", method = RequestMethod.GET)
	 public @ResponseBody void downloadFile(HttpServletResponse response,@RequestParam Map<String, String> customQuery) throws IOException {
	     
	        final String EXTERNAL_FILE_PATH=AppConstant.PARENT_CATEGORY_IMAGE_FOLDER_PATH+customQuery.get("image");
            final String FILE_NOT_FOUND=AppConstant.PARENT_CATEGORY_IMAGE_FOLDER_PATH+"404.png";    
            DownloadImage.downloadImage(response, EXTERNAL_FILE_PATH, FILE_NOT_FOUND);
        
         
	 }
	
		
	 @RequestMapping(value="rest/getSubCategory", method = RequestMethod.GET)
	 public @ResponseBody List<GetCategoryItem> getSubCategoryItem(@RequestParam Map<String, String> customQuery) throws IOException { 
			Dao dao= new Dao();
			List<GetCategoryItem> getSubCategoryItem = null;
			try {
				getSubCategoryItem = dao.getSubGrocersCategory(customQuery.get("catId"),AppConstant.GET_IMAGE_PATH);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			};
			return getSubCategoryItem;
			
        
         
	 }
	 
	 @RequestMapping(value="rest/getProduct", method = RequestMethod.GET)
	 public @ResponseBody List<GrocerProductItem> getProduct(@RequestParam Map<String, String> customQuery) throws IOException { 
			Dao dao= new Dao();
			List<GrocerProductItem> grocerProductItems = null;
			try {
				grocerProductItems = dao.getGrocerProduct(customQuery.get("subCatId"),AppConstant.GET_PRODUCT_IMAGE_PATH);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			};
			return grocerProductItems;
			   
	 }
		
	 @RequestMapping(value="rest/getProductImage", method = RequestMethod.GET)
	 public @ResponseBody void getProductImage(HttpServletResponse response,@RequestParam Map<String, String> customQuery) throws IOException {
	     
	        final String EXTERNAL_FILE_PATH=AppConstant.PRODUCT_IMAGE_FOLDER_PATH+customQuery.get("image");
            final String FILE_NOT_FOUND=AppConstant.PARENT_CATEGORY_IMAGE_FOLDER_PATH+"404.png";    
            DownloadImage.downloadImage(response, EXTERNAL_FILE_PATH, FILE_NOT_FOUND);
        
         
	 }
	 
	 @RequestMapping(value = "rest/grocerDoLogin", method = RequestMethod.POST)
		public @ResponseBody List<LoginItem>  createUser(@RequestBody LoginItem item) {
			 Dao dao= new Dao();
			 System.out.println("getItem : "+item.getMobileNumber()); 
			 
	
			 List<LoginItem> loginItems = new ArrayList<LoginItem>();
			 dao.grocerDoLogin(item,AppConstant.GET_USER_IMAGE_PATH);
			 
			 if( dao.grocerDoLogin(item,AppConstant.GET_USER_IMAGE_PATH).size()==0 || dao.grocerDoLogin(item,AppConstant.GET_USER_IMAGE_PATH).isEmpty()) {
				 LoginItem loginItem= new LoginItem();
	    		 loginItem.setResCode(false);
	    		 loginItems.add(loginItem);	 
			 }else {
				  loginItems=dao.grocerDoLogin(item,AppConstant.GET_USER_IMAGE_PATH); 
			 }
			return loginItems;
				
		}
	 @RequestMapping(value="rest/getUserPic", method = RequestMethod.GET)
	 public @ResponseBody void getUserPic(HttpServletResponse response,@RequestParam Map<String, String> customQuery) throws IOException {
	     
	        final String EXTERNAL_FILE_PATH=AppConstant.USER_IMAGE_FOLDER_PATH+customQuery.get("image");
            final String FILE_NOT_FOUND=AppConstant.PARENT_CATEGORY_IMAGE_FOLDER_PATH+"404.png";    
           
            DownloadImage.downloadImage(response, EXTERNAL_FILE_PATH, FILE_NOT_FOUND);  
         
	 }
	 
	 @RequestMapping(value = "rest/addCartItem", method = RequestMethod.POST)
		public @ResponseBody List<ConstantItem>  addCartItem(@RequestBody AddCartItem item) {
			 Dao dao= new Dao();
			 System.out.println("getItem : "+item.getProductName()); 
			return dao.addCartItem(item);
				
		}
	 
	 @RequestMapping(value = "rest/getCartItem", method = RequestMethod.GET)
		public @ResponseBody List<CartItem>  getCartItem(@RequestParam Map<String, String> customQuery) {
			 Dao dao= new Dao();
			 System.out.println("getItem : "+customQuery.get("userId")); 
			 
			 
			 List<CartItem> getCartItems = new ArrayList<CartItem>();

			 if(dao.getCartItem(customQuery.get("userId"),AppConstant.GET_PRODUCT_IMAGE_PATH).size()==0 || dao.getCartItem(customQuery.get("userId"),AppConstant.GET_PRODUCT_IMAGE_PATH).isEmpty()) {
				 CartItem productItem= new CartItem();
	        	 productItem.setResCode(false);
	        	 productItem.setMsg("No Item in Added in Cart");
	        	 getCartItems.add(productItem);	
	    		
			 }else {
				 getCartItems= dao.getCartItem(customQuery.get("userId"),AppConstant.GET_PRODUCT_IMAGE_PATH);
			 }
			 
			return getCartItems;
				
		}
	 
	 @RequestMapping(value = "rest/removeCartItem", method = RequestMethod.POST)
		public @ResponseBody List<ConstantItem>  removeCartItem(@RequestBody AddCartItem item) {
			 Dao dao= new Dao();
			 System.out.println("getItem : "+item.getProductID()); 
			return dao.removeCartItem(item,0);
				
		}
	 
	 @RequestMapping(value = "rest/updateItemQuantity", method = RequestMethod.POST)
		public @ResponseBody List<ConstantItem>  updateItemQuantity(@RequestBody AddCartItem item) {
			 Dao dao= new Dao();
			 System.out.println("getItem : "+item.getProductQty()); 
			return dao.updateItemQuantity(item);
				
		}
	 
	 
	 @RequestMapping(value = "rest/getCityGro", method = RequestMethod.GET)
		public @ResponseBody List<CityItem> getCityGro(@RequestParam Map<String, String> customQuery) {
	
		 System.out.println("getItem : "+customQuery.get("city")); 
		 
		
			Dao dao= new Dao();
			List<CityItem> getCityItems = null;
			try {
				getCityItems = dao.getCity(customQuery.get("city"));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			};
			return getCityItems;
		}
		
	 @RequestMapping(value = "rest/addUpdateUserGrocer", method = RequestMethod.POST)
		public @ResponseBody List<ConstantItem>  addUpdateUserGrocer(@RequestBody LoginItem item) {
			 Dao dao= new Dao();
			 System.out.println("getItem : "+item.getPassword());  
			return dao.addUpdateUser(item);
				
		}
	 
	 @RequestMapping(value = "rest/addChangeAddressGrocer", method = RequestMethod.POST)
		public @ResponseBody List<ConstantItem>  addChangeAddressGrocer(@RequestBody LoginItem item) {
			 Dao dao= new Dao();
			 System.out.println("getItem : "+item.getUserID()); 
			return dao.addChangeAddressGrocer(item);
				
		}
	 
	 @RequestMapping(value = "rest/searchProduct", method = RequestMethod.GET)
		public @ResponseBody List<SearchProductItem>  searchProduct(@RequestParam Map<String, String> customQuery) {
			 Dao dao= new Dao();
			 System.out.println("getItem product : "+customQuery.get("productName")); 
			return dao.getSearchProduct(customQuery.get("productName"), AppConstant.GET_PRODUCT_IMAGE_PATH);
				
		}
	 
	 
	 @RequestMapping(value = "rest/saveOrder", method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
		public @ResponseBody ConstantItem saveOrder( HttpEntity<String> httpEntity) {
			  String json = httpEntity.getBody();
			  SaveOrderitem orderitem= new SaveOrderitem();
			  System.out.println("json body : "+json); 
			 List<SaveOrderitem> list= new ArrayList<SaveOrderitem>();
			 
			 try { 
				 JSONObject jsonObject= new JSONObject(json);
				 orderitem.setUseId(jsonObject.getString("userId"));
				  orderitem.setProductId(jsonObject.getString("productId"));
				orderitem.setOrderNo(jsonObject.getString("orderNo"));
				orderitem.setPaymentMode(jsonObject.getString("paymentMode"));
				orderitem.setTotalAmt(jsonObject.getString("totalAmt"));
				 orderitem.setDiscountAmt(jsonObject.getString("discountAmt"));
				orderitem.setNetpayableAmt(jsonObject.getString("netpayableAmt"));
				orderitem.setNoOfItem(jsonObject.getString("noOfItem"));
				orderitem.setPaymentStatus(jsonObject.getString("paymentStatus"));
			    orderitem.setOrderStatus(jsonObject.getString("orderStatus"));
			    
			    
			    JSONArray array=jsonObject.getJSONArray("productDetails"); 
			   
			    for(int i=0;i<array.length();i++) {
			    JSONObject	jsonObject1=array.getJSONObject(i);
					   SaveOrderitem saveOrderitem= new SaveOrderitem();	  
						saveOrderitem.setProductId(jsonObject1.getString("productId"));
						saveOrderitem.setUnit(jsonObject1.getString("unit"));
						saveOrderitem.setUnitName(jsonObject1.getString("unitName"));
						saveOrderitem.setPayablePrice(jsonObject1.getString("payablePrice"));
						 saveOrderitem.setDiscount(jsonObject1.getString("discount"));
						 saveOrderitem.setOrderNo(jsonObject.getString("orderNo"));
						 list.add(saveOrderitem);
				 }		 
			 }catch(Exception e) {
				 
			 }
			 
			Dao dao= new Dao();
		    return dao.saveOrder(orderitem,list);
				
		}
	 
	 @RequestMapping(value = "rest/getOrders", method = RequestMethod.GET)
	 public @ResponseBody List<OrderItem>  getOrders(@RequestParam Map<String, String> customQuery) {
			 System.out.println("custom Query : "+customQuery.get("userId")); 
			 List<OrderItem> orderItemList = new ArrayList<OrderItem>();
			 Dao dao= new Dao();
			 
			 if(dao.getOrder(customQuery.get("userId")).size()==0 || dao.getOrder(customQuery.get("userId")).isEmpty()) {
				 OrderItem productItem= new OrderItem();
	        	 productItem.setResCode(false);
	        	 productItem.setMsg("No order found");
	        	 orderItemList.add(productItem);		
			 }else {
				 orderItemList=dao.getOrder(customQuery.get("userId"));
			 }	
			 return orderItemList;
		}
	 
	 
	 
	 
	 
	 @RequestMapping(value = "rest/doOrderCancel", method = RequestMethod.GET)
		public @ResponseBody List<ConstantItem>  doOrderCancel(@RequestParam Map<String, String> customQuery) {
			 Dao dao= new Dao();
			 System.out.println("custom Query order No : "+customQuery.get("orderNo")); 
			return dao.orderCancelled(customQuery.get("orderNo"));
				
		}
	 
	 
	 
	 
	 
	 @RequestMapping(value = "rest/orderItem", method = RequestMethod.GET)
		public @ResponseBody List<OrderProductItem>  orderItem(@RequestParam Map<String, String> customQuery) {
			 Dao dao= new Dao();
			 System.out.println("custom Query order No : "+customQuery.get("orderNo")); 
			return dao.getOrderitem(AppConstant.GET_PRODUCT_IMAGE_PATH,customQuery.get("orderNo"));
				
		}
	 
	 
	 @RequestMapping(value = "rest/doForgetPassword", method = RequestMethod.GET)
		public @ResponseBody ConstantItem  doForgetPassword(@RequestParam Map<String, String> customQuery) {
			 Dao dao= new Dao();
			 System.out.println("custom Query mail : "+customQuery.get("mailId")); 
			 return dao.forgetPassword(customQuery.get("mailId"));
				
		}
	 
	 @RequestMapping(value = "rest/appVersion", method = RequestMethod.GET)
		public @ResponseBody AppUpdate  appVesrion(@RequestParam Map<String, String> customQuery) {
			 Dao dao= new Dao();
			 System.out.println("custom Query app : "+customQuery.get("app")); 
				return dao.getAppVersion(customQuery.get("app"));
			
		
		}
	 
	 @RequestMapping(value = "rest/addWalletMoney", method = RequestMethod.POST)
		public @ResponseBody ConstantItem  addUpdateMoney(@RequestBody WalletItem walletItem ) {
			 Dao dao= new Dao();
			 System.out.println("Customer Id app : "+walletItem.getCustomerId()); 
				return dao.addWalletMoney(walletItem);
			
		
		}
	 
	 @RequestMapping(value = "rest/getWallet", method = RequestMethod.GET)
		public @ResponseBody WalletItem  getWallet(@RequestParam Map<String, String> customQuery) {
			 Dao dao= new Dao();
			 System.out.println("Customer Id  : "+customQuery.get("customerId")); 
				return dao.getWallet(customQuery.get("customerId"));
			
		
		}
	 
	 @RequestMapping(value = "rest/getWalletDetail", method = RequestMethod.GET)
		public @ResponseBody List<WalletItem>  getWalletDetail(@RequestParam Map<String, String> customQuery) {
			 Dao dao= new Dao();
			 System.out.println("Customer Id  : "+customQuery.get("walletId")); 
				return dao.getWalletDetails(customQuery.get("walletId"));
			
		
		}
	 
	 @RequestMapping(value = "rest/generatePaytmChecksum", method = RequestMethod.POST)
		public @ResponseBody String   generatePaytmChecksum(@RequestBody ChecksumItem checksumItem) {

			  System.out.println("TXN_AMOUNT : "+checksumItem.getTxnAmt()); 
				return ChecksumGeneration.generateChecksum(checksumItem);
			
		
		}
}
