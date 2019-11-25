package com.util;

import java.util.Map;
import java.util.TreeMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.model.ChecksumItem;
import com.paytm.pg.merchant.CheckSumServiceHelper;

public class ChecksumGeneration  {
	private static String MID = "rRDANX25872613208895"; 
	private static String MercahntKey = "O0jAIrE4Wt#TRLyv";
	private static String INDUSTRY_TYPE_ID = "Retail";
	private static String CHANNLE_ID = "WAP";
	private static String WEBSITE = "DEFAULT";
	private static String CALLBACK_URL = "https://securegw.paytm.in/theia/paytmCallback?ORDER_ID=";
	
	
	public static void main(String[] args) {
		String checkSum="";
		
		TreeMap<String,String> paramMap = new TreeMap<String,String>();
		paramMap.put("MID" , MID);
		paramMap.put("ORDER_ID" , "ORDER00012");
		paramMap.put("CUST_ID" , "CUST00011");
		paramMap.put("INDUSTRY_TYPE_ID" , INDUSTRY_TYPE_ID);
		paramMap.put("CHANNEL_ID" , CHANNLE_ID);
		paramMap.put("TXN_AMOUNT" , "1.00");
		paramMap.put("WEBSITE" , WEBSITE);
		paramMap.put("EMAIL" , "test@gmail.com");
		paramMap.put("MOBILE_NO" , "9958126563");
		paramMap.put("CALLBACK_URL" , CALLBACK_URL);
		
		try{
		checkSum =  CheckSumServiceHelper.getCheckSumServiceHelper().genrateCheckSum(MercahntKey, paramMap);
		
		paramMap.put("CHECKSUMHASH" , checkSum);
	
		
		System.out.println("Paytm Payload: "+ paramMap);
		
		
		}catch(Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
}
	
	
public static String generateChecksum(ChecksumItem checksumItem) {

	String checkSum="";
	TreeMap<String,String> paramMap = new TreeMap<String,String>();
	paramMap.put("MID" , MID);
	paramMap.put("ORDER_ID" , checksumItem.getOrderId());
	paramMap.put("CUST_ID" , checksumItem.getCustomerId());
	paramMap.put("INDUSTRY_TYPE_ID" , INDUSTRY_TYPE_ID);
	paramMap.put("CHANNEL_ID" , CHANNLE_ID);
	paramMap.put("TXN_AMOUNT" , checksumItem.getTxnAmt());
	paramMap.put("WEBSITE" , WEBSITE);
	paramMap.put("EMAIL" , checksumItem.getEmail());
	paramMap.put("MOBILE_NO" , checksumItem.getMobile());
	paramMap.put("CALLBACK_URL" , CALLBACK_URL+checksumItem.getOrderId());
	
	try{
	checkSum =  CheckSumServiceHelper.getCheckSumServiceHelper().genrateCheckSum(MercahntKey, paramMap);
	
	paramMap.put("CHECKSUMHASH" , checkSum);

	
	System.out.println("Paytm Payload: "+ paramMap);
	Gson gson = new GsonBuilder().disableHtmlEscaping().create();
	checkSum = gson.toJson(paramMap);
	System.out.println("Paytm GSON : "+ checkSum);
	
	}catch(Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	return checkSum;
}
}


