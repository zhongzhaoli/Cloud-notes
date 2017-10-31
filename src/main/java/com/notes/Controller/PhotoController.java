package com.notes.Controller;

import java.io.FileInputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@EnableRedisHttpSession
@Controller
public class PhotoController {
	
	@GetMapping(value = "/photo/{id}")
	public void get(@PathVariable String id, HttpServletResponse response){
		System.out.println(id);
        FileInputStream fis = null;  
        OutputStream os = null;  
        try {  
            fis = new FileInputStream("src/main/resources/static/images/upload/"+id+".jpg");  
            os = response.getOutputStream();  
            int count = 0;  
            byte[] buffer = new byte[1024 * 8];  
            while ((count = fis.read(buffer)) != -1) {  
                os.write(buffer, 0, count);  
                os.flush();  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        } 
	}
	
	@GetMapping(value = "/photonote/{id}")
	public void photoget(@PathVariable String id, HttpServletResponse response){
		System.out.println(id);
        FileInputStream fis = null;  
        OutputStream os = null;  
        try {  
            fis = new FileInputStream("src/main/resources/static/images/note/"+id+".jpg");  
            os = response.getOutputStream();  
            int count = 0;  
            byte[] buffer = new byte[1024 * 8];  
            while ((count = fis.read(buffer)) != -1) {  
                os.write(buffer, 0, count);  
                os.flush();  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        } 
	}
}
