package com.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;

import javax.servlet.http.HttpServletResponse;

import org.springframework.util.FileCopyUtils;

public class DownloadImage {
	
	
	public static void downloadImage(HttpServletResponse response,String folderFilePath,String fileNotFoundPath) {
		
		  File file = null;
		  
		  file = new File(folderFilePath);
	        if(!file.exists()){
	           // String errorMessage = "Sorry. The file you are looking for does not exist";
	           // System.out.println(errorMessage);
	            
	            String mimeType= URLConnection.guessContentTypeFromName(file.getName());
		        if(mimeType==null){
		            System.out.println("mimetype is not detectable, will take default");
		            mimeType = "application/octet-stream";
		        }    
		        file = new File(fileNotFoundPath);
		       
		        System.out.println("mimetype : "+mimeType);  
		        response.setContentType(mimeType);  
		        response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() +"\""));  
		        response.setContentLength((int)file.length());
		        InputStream inputStream = null;
				try {
					inputStream = new BufferedInputStream(new FileInputStream(file));
					FileCopyUtils.copy(inputStream, response.getOutputStream());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		      
	         
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
	      
	        try {
	        	 InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
	 	         FileCopyUtils.copy(inputStream, response.getOutputStream());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
	       
		
	}

}
