package com.aakarley.spring.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.dao.daoimpl.Dao;
import com.model.ConstantItem;
import com.model.LoginItem;
import com.util.AppConstant;
import com.model.AdminModel;

@Controller
public class AdminController {
	
	@RequestMapping(value="admin/CreateStore",method=RequestMethod.POST)
	public @ResponseBody ConstantItem  addStore(@RequestBody AdminModel item) {
		Dao dao= new Dao();
		return dao.addStore(item);
		
	}
	

@RequestMapping (value="admin/creatCategory",method=RequestMethod.POST)
public @ResponseBody ConstantItem addParentCategory(@RequestBody AdminModel item) {
	
	Dao dao= new Dao();
	return dao.addCategory(item);
	
}

@RequestMapping (value="admin/uploadImage/{filefor}",method=RequestMethod.POST)
public @ResponseBody ConstantItem uploadImage(@RequestParam ("file")MultipartFile file,@PathVariable String filefor) {
	ConstantItem constantItem= new ConstantItem();
	
	System.out.println("PathVariable "+filefor);
	System.out.println("PathVariable 123 "+filefor);
    if (file.isEmpty()) {
    	constantItem.setResCode(false);
    	constantItem.setMsg("Empty file, Please select a file to upload");
    	return constantItem;
    }
    	try {

            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();
           Path path=null;
            if(filefor.trim().equals("categoryImage")) {
             path = Paths.get(AppConstant.PARENT_CATEGORY_IMAGE_FOLDER_PATH + file.getOriginalFilename());
            	  Files.write(path, bytes);
            }
           
          
        	constantItem.setResCode(true);
        	constantItem.setMsg("File uploaded successfully");
           
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Exception "+e.toString());
            constantItem.setResCode(false);
        	constantItem.setMsg("server error");
        }
 

	return constantItem;
	
}
}
