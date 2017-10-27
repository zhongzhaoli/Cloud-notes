package com.notes.Controller;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.notes.Util.FileUtil;

@Controller
public class FileCollter {
	//跳转到上传文件的页面
    @RequestMapping(value="/gouploadimg", method = RequestMethod.GET)
    public String goUploadImg() {
        //跳转到 templates 目录下的 uploadimg.html
        return "uploadimg";
    }

    //处理文件上传
    @RequestMapping(value="/testuploadimg", method = RequestMethod.POST)
    public @ResponseBody String uploadImg(@RequestParam("file") MultipartFile file,
            HttpServletRequest request) {
        String contentType = file.getContentType();
        String fileName = file.getOriginalFilename();
        String temp = "images" + File.separator + "upload" + File.separator;
        //String filePath = request.getSession().getServletContext().getRealPath("imgupload/");
        String datdDirectory = temp.concat(String.valueOf("asdas")).concat(File.separator);
        try {
            FileUtil.uploadFile(file.getBytes(), datdDirectory, fileName);
        } catch (Exception e) {
            // TODO: handle exception
        }
        //返回json
        return "success";
    }
}
