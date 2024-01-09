package com.ppj.oct.item;

import java.io.File;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class MySessionListener implements HttpSessionListener{
	
	@Override
    public void sessionCreated(HttpSessionEvent se) {
    }

    @SuppressWarnings("unchecked")
	@Override
    public void sessionDestroyed(HttpSessionEvent se) {
    	 System.out.println("세션 종료됨");
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attributes.getRequest().getSession();
        
        ArrayList<String> fileList;
        ArrayList<String> ZipFileList;
        
        if(session.getAttribute("ImageFileList") == null) {
        	fileList = new ArrayList<String>();
        }else {
        	fileList = (ArrayList<String>) session.getAttribute("ImageFileList");
        }
        
        if(session.getAttribute("ZipFileList") == null) {
        	ZipFileList = new ArrayList<String>();
        }else {
        	ZipFileList = (ArrayList<String>) session.getAttribute("ZipFileList");
        }
       
        ServletContext sc = se.getSession().getServletContext();

        
        String fileDirectory = sc.getRealPath("fileUpDown")+"/";
        System.out.println(fileDirectory);
        File file = null;
        int len = fileList.size();
        int len2 = ZipFileList.size();
        for(int i=0; i<len; i++) {
        	file = new File(fileDirectory+fileList.get(i));
        	if (file.exists() && file.isFile()) {
                file.delete(); // 파일 삭제
            }
        }
        
        for(int i=0; i<len2; i++) {
        	file = new File(ZipFileList.get(i));
  	      if (file.exists() && file.isFile()) {
  	          file.delete(); // 파일 삭제
  	      }
        }
    }
    
    
}//class
