package com.util;


import com.paytm.pg.merchant.*;

import java.util.Map;
import java.util.TreeMap;

public class checksumVerification {
	
	private static String MID = "rRDANX25872613208895"; 
	private static String MercahntKey = "O0jAIrE4Wt#TRLyv";
	private static String INDUSTRY_TYPE_ID = "Retail";
	private static String CHANNLE_ID = "WAP";
	private static String WEBSITE = "DEFAULT";
	private static String CALLBACK_URL = "https://securegw.paytm.in/theia/paytmCallback?ORDER_ID=";
	
	public static void main(String[] a){
		
		String paytmChecksum = "AG8QhJ/hadVCMi1uizF4aT9Q6zbj0w6DUyzNz7Yg9qQzc1p7eJQgRBHUCfK+TkRvZqKBhTiR9GjRCjntSi/XAjqwJ1VVJP0i5eaOv00C96s=";
		
		Map<String, String> paramMap = new  TreeMap<String,String>();
		paramMap.put("MID" , MID);
		paramMap.put("ORDER_ID" , "ORDER00011");
		paramMap.put("CUST_ID" , "CUST00011");
		paramMap.put("INDUSTRY_TYPE_ID" , INDUSTRY_TYPE_ID);
		paramMap.put("CHANNEL_ID" , CHANNLE_ID);
		paramMap.put("TXN_AMOUNT" , "1.00");
		paramMap.put("WEBSITE" , WEBSITE);
		paramMap.put("EMAIL" , "test@gmail.com");
		paramMap.put("MOBILE_NO" , "9958126563");
		paramMap.put("CALLBACK_URL" , CALLBACK_URL);
		
		TreeMap<String, String> paytmParams = new  TreeMap<String,String>();
		
		for (Map.Entry<String, String> entry : paramMap.entrySet())
		{   
		    if(entry.getKey().equals("CHECKSUMHASH")){
				paytmChecksum = entry.getKey();
			}else{
				paytmParams.put(entry.getKey(), entry.getValue());
			}
		}
		
		
		boolean isValideChecksum = false;
		try{
			
			isValideChecksum = CheckSumServiceHelper.getCheckSumServiceHelper().verifycheckSum(MercahntKey, paytmParams, paytmChecksum);
			
			System.out.println( "My veryfication "+ isValideChecksum);
			
			// if checksum is validated Kindly verify the amount and status 
			// if transaction is successful 
			// kindly call Paytm Transaction Status API and verify the transaction amount and status.
			// If everything is fine then mark that transaction as successful into your DB.
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
