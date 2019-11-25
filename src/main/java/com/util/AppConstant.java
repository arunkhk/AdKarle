package com.util;

public class AppConstant {

	public static String SQL_SERVER_DRIVER = "com.mysql.cj.jdbc.Driver";

	
	 public static String BASE_PATH="http://192.168.0.24:8081"; public static
	  String DATABASE_NAME="jdbc:mysql://192.168.0.24/le_ad_db?"+
	  "user=root&password=root";
	
	 

	
	/*
	 * public static String BASE_PATH="http://122.176.74.194:8080/"; public static
	 * String DATABASE_NAME="jdbc:mysql://192.168.1.110/adkarlebazar?"+
	 * "user=arun&password=123Jgamja";
	 */
	 

	public static String GET_IMAGE_PATH = BASE_PATH + "/AdKarle/rest/getImage?image=";
	public static String PARENT_CATEGORY_IMAGE_FOLDER_PATH = "E:/AdkarleImages/CategoryImage/";

	
	
	public static String PRODUCT_IMAGE_FOLDER_PATH = "E:/AdkarleImages/ProductImage/";
	public static String GET_PRODUCT_IMAGE_PATH = BASE_PATH + "/AdKarle/rest/getProductImage?image=";

	public static String USER_IMAGE_FOLDER_PATH = "E:/AdkarleImages/UserImage/";
	public static String GET_USER_IMAGE_PATH = BASE_PATH + "/AdKarle/rest/getUserPic?image=";

	/* connect to the devetach server */
	public static String IMAGE_URL = "http://adkarle.com/uploads/";
	public static String COUPON_URL = "http://adkarle.com/uploads/";

	public static String GET_FROM_USER_URL = "";
	public static boolean S_CODE = true;
	public static boolean F_CODE = false;

}
