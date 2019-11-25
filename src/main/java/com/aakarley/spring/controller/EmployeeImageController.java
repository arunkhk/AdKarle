package com.aakarley.spring.controller;
import static org.springframework.web.servlet
.support.ServletUriComponentsBuilder.fromCurrentRequest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.model.ChecksumItem;
import com.util.ChecksumGeneration;
@Controller
public class EmployeeImageController {
	//Save the uploaded file to this folder
    private static String UPLOADED_FOLDER = "C://Temp//";


    //@RequestMapping(value = "/upload", method = RequestMethod.POST)
   
    
    @RequestMapping(value = "rest/uploadImageqwerty/{id}/photo", method = RequestMethod.POST)
	public @ResponseBody String   generatePaytmChecksum(@RequestParam("file")MultipartFile file,@PathVariable final String id) {


    	System.out.println("PathVariable "+id);
        if (file.isEmpty()) {
            return "Please select a file to upload";
        }

        try {

            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
            Files.write(path, bytes);

           
        } catch (IOException e) {
            e.printStackTrace();
        }

        return file.getOriginalFilename();
    } 
	
}
