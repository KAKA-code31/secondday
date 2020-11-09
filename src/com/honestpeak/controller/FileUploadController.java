package com.honestpeak.controller;


import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.honestpeak.utils.ImageUploadUtil;




@Controller
@RequestMapping("/back/images")
public class FileUploadController {
	
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
    /**
     * ckeditor图片上传
     * 
     * @Title imageUpload
     * @param request
     * @param response
     */
    @RequestMapping("/fileUpload")
    public void imageUpload(HttpServletRequest request, HttpServletResponse response) {
        String DirectoryName = "upload/img/";
        try {
            ImageUploadUtil.ckeditor(request, response, DirectoryName);
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}