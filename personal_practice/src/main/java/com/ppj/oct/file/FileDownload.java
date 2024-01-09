package com.ppj.oct.file;

import java.io.File;
import java.io.FileInputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

@Service
public class FileDownload {
	
	public String[] fileList(String realPath,HttpServletRequest req) { //파일 저장directory에 있는 모든 파일
		File dir = new File(realPath);
		return dir.list();
	}
	
	
	public void fileDown(String realPath,HttpServletRequest req, HttpServletResponse res) throws Exception {
		String fileName = req.getParameter("fileName");
		
		String saveDir = realPath;
		
		File file = new File(saveDir+"/"+fileName);
		System.out.println("파일명"+fileName);
		System.out.println(saveDir);
		String fileExtension = fileName.substring(fileName.lastIndexOf(".")+1);
		
		String mimeType = "application/octet-stream"; 

		if (fileExtension.equalsIgnoreCase("jpg") || fileExtension.equalsIgnoreCase("jpeg")) {
		    mimeType = "image/jpeg";
		} else if (fileExtension.equalsIgnoreCase("png")) {
		    mimeType = "image/png";
		} else if (fileExtension.equalsIgnoreCase("pdf")) {
		    mimeType = "application/pdf";
		}else if(fileExtension.equalsIgnoreCase("zip")) {
			mimeType = "application/zip";
		}else if(fileExtension.equalsIgnoreCase("txt")) {
			mimeType = "application/txt";
		}
		
		System.out.println(mimeType);
		//-----------------------------------------------
		String downName = null;
		System.out.println("request.getHeader: " + req.getHeader("user-agent"));
		if (req.getHeader("user-agent").toLowerCase().contains("trident")) {
		    // Internet Explorer
		    downName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
		} else {
		    // 다른 브라우저
		    downName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
		}
		
		System.out.println("downName ::"+downName);
		
		res.setHeader("Content-Disposition", "attachment;filename=\"" + downName + "\";");
		
		FileInputStream fileInputStream = new FileInputStream(file);
		ServletOutputStream servletOutputStream = res.getOutputStream();
		byte[] b = new byte[1024];
		int data = 0;
		while((data=(fileInputStream.read(b,0,b.length))) != -1) {
			servletOutputStream.write(b,0,data);
		}
		servletOutputStream.close();
		fileInputStream.close();
	}
	
	
	public void downloadZIP(HttpServletRequest req,HttpServletResponse res) throws Exception{
		System.out.println("downloadZIP");
		String zipFileName = (String)req.getSession().getAttribute("zipFileName");
		String fileName = zipFileName.substring(zipFileName.lastIndexOf("/")+1);
		System.out.println(fileName);
		
		try {
			
			String downName = null;
			System.out.println("request.getHeader: " + req.getHeader("user-agent"));
			if (req.getHeader("user-agent").toLowerCase().contains("trident")) {
			    // Internet Explorer
			    downName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
			} else {
			    // 다른 브라우저
			    downName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
			}
			
			System.out.println("downName: "+downName);
			res.setHeader("Content-Disposition", "attachment;filename=\"" + downName + "\";");
			
			FileInputStream fileInputStream = new FileInputStream(zipFileName);
			ServletOutputStream servletOutputStream = res.getOutputStream();
			byte[] b = new byte[1024];
			int data = 0;
			while((data=(fileInputStream.read(b,0,b.length))) != -1) {
				servletOutputStream.write(b,0,data);
			}
			servletOutputStream.close();
			fileInputStream.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
