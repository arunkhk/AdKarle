package com.aakarley.spring.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.dao.daoimpl.Dao;
import com.model.CityItem;
import com.model.ConstantItem;
import com.model.CouponItem;
import com.model.Employee;
import com.model.GetCategoryItem;
import com.model.LoginItem;
import com.model.MenuImage;
import com.model.RegisterUserItem;
import com.model.UploadItem;
import com.model.VendorDetailItem;
import com.mysql.cj.util.Util;
import com.util.AppConstant;



import javax.servlet.ServletContext;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import java.net.BindException;

/**
 * Handles requests for the Employee service.
 */
@Controller
public class ApiController {
	 @Autowired
	    private Environment environment;
	
	private static final Logger logger = LoggerFactory.getLogger(ApiController.class);
	

	

/*	View reslover url for JSP Page*/
	
	
	@RequestMapping(value = { IConstants.View }, method = RequestMethod.GET)
	public String login(HttpServletRequest request, HttpServletResponse response,ModelMap model,HttpSession httpSession) throws SQLException {
		System.out.print("hello");
	return "home";
	}
	
	@RequestMapping(value =IConstants.FOR_TEST, method = RequestMethod.GET)
	public  @ResponseBody String test() throws SQLException {
		System.out.print("hello123");
	return "API WORKING";
	}
	

	@RequestMapping(value = IConstants.GET_CATEGORY, method = RequestMethod.GET)
	public @ResponseBody List<GetCategoryItem> getCategoryItem() {
		logger.info("Get Category");
		Dao dao= new Dao();
		List<GetCategoryItem> getCategoryItems = null;
		try {
			getCategoryItems = dao.getCategory();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
		return getCategoryItems;
	}
	
	@RequestMapping(value = IConstants.GET_CITY, method = RequestMethod.GET)
	public @ResponseBody List<CityItem> getCity() {
		logger.info("Get City");
		Dao dao= new Dao();
		List<CityItem> getCityItems = null;
		try {
			getCityItems = dao.getCity("");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
		return getCityItems;
	}
	

	@RequestMapping(value = IConstants.GET_VENDOR_DETAILS, method = RequestMethod.GET)
	public @ResponseBody  List<VendorDetailItem> getVendorDetail(@RequestParam Map<String, String> customQuery) {
		Dao dao= new Dao();
		List<VendorDetailItem> getVendorItem=null;
		System.out.println("query String Data"+ customQuery.get("cityId"));
		try {
			getVendorItem = dao.getVendorDetail(Integer.parseInt(customQuery.get("categoryId")),Integer.parseInt(customQuery.get("cityId")));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
		return getVendorItem;
	}
	
	
	
	 @RequestMapping(value=IConstants.DOWNLOAD_IVENDOR_IMAGE, method = RequestMethod.GET)
	 public @ResponseBody void downloadFile(HttpServletResponse response,@RequestParam Map<String, String> customQuery) throws IOException {
	     
	        File file = null;
	        
	        
	    
	          /*final String EXTERNAL_FILE_PATH=AppConstant.GET_FROM_USER_URL+customQuery.get("image");
	          final String FILE_NOT_FOUND=AppConstant.GET_FROM_USER_URL+"404.jpg";*/
	        
	        final String EXTERNAL_FILE_PATH="E:/AdkarleImages/"+customQuery.get("image");
            final String FILE_NOT_FOUND="E:/AdkarleImages/CouponImages/404.jpg";
             
	            file = new File(EXTERNAL_FILE_PATH);
	      
	        if(!file.exists()){
	           // String errorMessage = "Sorry. The file you are looking for does not exist";
	           // System.out.println(errorMessage);
	            
	            String mimeType= URLConnection.guessContentTypeFromName(file.getName());
		        if(mimeType==null){
		            System.out.println("mimetype is not detectable, will take default");
		            mimeType = "application/octet-stream";
		        }    
		        file = new File(FILE_NOT_FOUND);
		       
		        System.out.println("mimetype : "+mimeType);  
		        response.setContentType(mimeType);  
		        response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() +"\""));  
		        response.setContentLength((int)file.length());
		        InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
		        FileCopyUtils.copy(inputStream, response.getOutputStream());
	         
		 
	            return ;
	        }
	         
	        String mimeType= URLConnection.guessContentTypeFromName(file.getName());
	        if(mimeType==null){
	            System.out.println("mimetype is not detectable, will take default");
	            mimeType = "application/octet-stream";
	        }    
	        System.out.println("mimetype : "+mimeType);  
	        response.setContentType(mimeType);  
	        response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() +"\""));  
	     
	        response.setContentLength((int)file.length());
	        InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
	        FileCopyUtils.copy(inputStream, response.getOutputStream());
	   
	 }
	 
	 
	 @RequestMapping(value = IConstants.GET_COUPON, method = RequestMethod.GET)
		public @ResponseBody  List<CouponItem> getCouponItem(@RequestParam Map<String, String> customQuery) {
			Dao dao= new Dao();
			List<CouponItem> getVendorItem=null;
			System.out.println("query String Data"+ customQuery.get("vendorId"));
		
			try {
				getVendorItem = dao.getCoupon(Integer.parseInt(customQuery.get("vendorId")));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			};
			return getVendorItem;
		}
		
	
	 @RequestMapping(value=IConstants.DOWNLOAD_COUPON_IMAGE, method = RequestMethod.GET)
	 public @ResponseBody void downloadCoupon(HttpServletResponse response,@RequestParam Map<String, String> customQuery) throws IOException {
	        File file = null;
	         
	               final String EXTERNAL_FILE_PATH="E:/AdkarleImages/CouponImages/"+customQuery.get("image");
	              final String FILE_NOT_FOUND="E:/AdkarleImages/CouponImages/404.png";
	             
	             file = new File(EXTERNAL_FILE_PATH);
	      
	        if(!file.exists()){
	           // String errorMessage = "Sorry. The file you are looking for does not exist";
	           // System.out.println(errorMessage);
	            
	            String mimeType= URLConnection.guessContentTypeFromName(file.getName());
		        if(mimeType==null){
		            System.out.println("mimetype is not detectable, will take default");
		            mimeType = "application/octet-stream";
		        }    
		        file = new File(FILE_NOT_FOUND);
		       
		        System.out.println("mimetype : "+mimeType);  
		        response.setContentType(mimeType);  
		        response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() +"\""));  
		        response.setContentLength((int)file.length());
		        InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
		        FileCopyUtils.copy(inputStream, response.getOutputStream());
	         
		        /*  OutputStream outputStream = response.getOutputStream();
	            outputStream.write(errorMessage.getBytes(Charset.forName("UTF-8")));
	            outputStream.close();*/
	            return ;
	        }
	         
	        String mimeType= URLConnection.guessContentTypeFromName(file.getName());
	        if(mimeType==null){
	            System.out.println("mimetype is not detectable, will take default");
	            mimeType = "application/octet-stream";
	        }    
	        System.out.println("mimetype : "+mimeType);  
	        response.setContentType(mimeType);  
	        /* "Content-Disposition : inline" will show viewable types [like images/text/pdf/anything viewable by browser] right on browser 
	            while others(zip e.g) will be directly downloaded [may provide save as popup, based on your browser setting.]*/
	        response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() +"\""));  
	        /* "Content-Disposition : attachment" will be directly download, may provide save as popup, based on your browser setting*/
	        //response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName()));
	        response.setContentLength((int)file.length());
	        InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
	        //Copy bytes from source to destination(outputstream in this example), closes both streams.
	        FileCopyUtils.copy(inputStream, response.getOutputStream());
	   
	 }
	 
	 
	 
	
		@SuppressWarnings("resource")
		@RequestMapping(value=IConstants.UPLOAD_USER_IMAGE, method = RequestMethod.POST)
		public @ResponseBody String inserNewUserData( @RequestParam("file") MultipartFile multipartFile) {
			if (!multipartFile.isEmpty()) {
				try {
					byte[] bytes = multipartFile.getBytes();

					// Creating the directory to store file
					File dir = new File(AppConstant.GET_FROM_USER_URL+ File.separator + "tmpFiles");
					
					if (!dir.exists())
						dir.mkdirs();
					
					File serverFile = new File(dir.getAbsolutePath());
					BufferedOutputStream stream = new BufferedOutputStream(
							new FileOutputStream(serverFile));
					stream.write(bytes);
					stream.close();

					logger.info("Server File Location="
							+ serverFile.getAbsolutePath());

					return "You successfully uploaded file=";
				} catch (Exception e) {
					return "You failed to upload "+" => " + e.getMessage();
				}
			} else {
				return "You failed to upload "
						+ " because the file was empty.";
			}
		 
		 
	 }
	 
		@RequestMapping(value = IConstants.INSERT_USER, method = RequestMethod.POST)
		public @ResponseBody ConstantItem createUser(@RequestBody RegisterUserItem item) {
			Dao dao= new Dao();
			 System.out.println("getItem : "+item.getEmail_id());  
			return dao.insertUser(item);
		}
		
		@RequestMapping(value = IConstants.DO_LOGIN, method = RequestMethod.POST)
		public @ResponseBody List<LoginItem>  createUser(@RequestBody LoginItem item) {
			 Dao dao= new Dao();
			 System.out.println("getItem : "+item.getEmailId());  
			 return dao.doLogin(item);
		}
		
		@RequestMapping(value = IConstants.CHANGE_PASSWORD, method = RequestMethod.POST)
		public @ResponseBody ConstantItem changePassword(@RequestBody RegisterUserItem item) {
			Dao dao= new Dao();
			 System.out.println("getItem : "+item.getEmail_id());  
			return dao.changePassword(item);
		}
		
	/*	getNew Offers*/
		@RequestMapping(value = IConstants.NEW_OFFER, method = RequestMethod.GET)
		public @ResponseBody  List<VendorDetailItem> getNewOffers() {
			Dao dao= new Dao();
			List<VendorDetailItem> getVendorItem=null;
		
			try {
				getVendorItem = dao.getNewOffer();
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			};
			return getVendorItem;
		}
		
		
		/*	getNew Offers*/
		@RequestMapping(value = IConstants.TOP_RATED, method = RequestMethod.GET)
		public @ResponseBody  List<VendorDetailItem> getTopRated() {
			Dao dao= new Dao();
			List<VendorDetailItem> getVendorItem=null;
		
			try {
				getVendorItem = dao.getTopRated();
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			};
			return getVendorItem;
		}
		
		
		/*	getNew Offers*/
		@RequestMapping(value = IConstants.SEARCH_BY_AREA, method = RequestMethod.GET)
		public @ResponseBody  List<VendorDetailItem> getAreaData(@RequestParam Map<String, String> customQuery) {
			Dao dao= new Dao();
			List<VendorDetailItem> getVendorItem=null;
		
			try {		
				getVendorItem = dao.getByArea(Integer.parseInt(customQuery.get("cityId")),Integer.parseInt(customQuery.get("categoryId")),customQuery.get("area"));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			};
			return getVendorItem;
		}
		
		
		@RequestMapping (value="/uploadfile", method= RequestMethod.POST)
		public @ResponseBody String upload(UploadItem uploadItem, HttpServletRequest request,Object command, BindException errors){
			
			try {

				MultipartFile filea = uploadItem.getFileData();

				InputStream inputStream = null;
				OutputStream outputStream = null;
				if (filea.getSize() > 0) {
				inputStream = filea.getInputStream();
				outputStream = new FileOutputStream("C:\\AdkarleImages\\"
				+ filea.getOriginalFilename());
				System.out.println("====22=========");
				System.out.println(filea.getOriginalFilename());
				System.out.println("=============");
				int readBytes = 0;
				byte[] buffer = new byte[8192];
				while ((readBytes = inputStream.read(buffer, 0, 8192)) != -1) {
				System.out.println("===ddd=======");
				outputStream.write(buffer, 0, readBytes);
				}
				outputStream.close();
				inputStream.close();
				/*session.setAttribute("uploadFile", "C:\\test111\\"
				+ filea.getOriginalFilename());*/
				}
				} catch (Exception e) {
				e.printStackTrace();
				}
			
			return "";
		}
		/*	getMenu*/
		@RequestMapping(value = IConstants.MENU_IMAGES, method = RequestMethod.GET)
		public @ResponseBody  List<MenuImage> getMenuImage(@RequestParam Map<String, String> customQuery) {
			Dao dao= new Dao();
			
			System.out.println("query String Data"+ customQuery.get("vendorId"));
			List<MenuImage> menuImages=null;
			try {
				menuImages = dao.getMenuImage(Integer.parseInt(customQuery.get("vendorId")));;
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return menuImages;
	
}
		
		 @RequestMapping(value="/download")
		    public void getLogFile(HttpSession session,HttpServletResponse response) throws Exception {
		        try {

		            String fileName="V-Repair.ipa";
		            String filePathToBeServed = "E:\\";
		            File fileToDownload = new File(filePathToBeServed+fileName);

		            InputStream inputStream = new FileInputStream(fileToDownload);
		            response.setContentType("application/force-download");
		            response.setHeader("Content-Disposition", "attachment; filename="+fileName); 
		            IOUtils.copy(inputStream, response.getOutputStream());
		            response.flushBuffer();
		            inputStream.close();
		        } catch (Exception exception){
		            System.out.println(exception.getMessage());
		        }

		 }
		
		 
		 
}
