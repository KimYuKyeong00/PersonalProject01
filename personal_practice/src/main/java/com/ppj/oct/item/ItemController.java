package com.ppj.oct.item;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;



@Controller
public class ItemController {
	
	@Autowired
	IDAO idao;
	
	@RequestMapping(value = "/oct.item", method = RequestMethod.GET,produces = "application/json; charset=UTF-8" )
	public @ResponseBody ImageURLs goIndex(HttpServletRequest req) throws Exception {
		System.out.println("goIndex");
		
		return idao.getImages(req);
	}
	
	@RequestMapping(value="/oct.zip", method=RequestMethod.GET)
	public @ResponseBody String getZip(HttpServletRequest req,HttpServletResponse res) {
		System.out.println("getZip");
		idao.imgToZip(req);
		String zipFileName = (String)req.getSession().getAttribute("zipFileName");
		System.out.println("세션에 저장된 zipFileName"+zipFileName);
		return zipFileName;
	}
	
	@RequestMapping(value="/oct.toStorage", method=RequestMethod.GET)
	public String toStorage(HttpServletRequest req) {
		System.out.println("oct.toStorage");
		idao.stackToStorage(req);
		idao.loginYes(req);
		req.setAttribute("subPage", "blank.jsp");
		req.setAttribute("loginPage", "User.jsp");
		return "index";
		
	}
	
	@RequestMapping(value="/oct.storage", method = RequestMethod.POST)
	public @ResponseBody String getStorage(HttpServletRequest req) {
		idao.getStorageFiles(req);
		return "index";
	}
	
	
	@RequestMapping(value="/oct.out",method=RequestMethod.GET)
	public void outOfPage(HttpServletRequest req) {
		System.out.println("outOfPage");
		
		if(req.getSession().getAttribute("login")!=null) {
			if(req.getSession().getAttribute("login").equals("y")) {
				System.out.println("로그인 된 상태에서 페이지 나감");
				req.getSession().setMaxInactiveInterval(1800);//세션 30분 갱신
			}else {
				req.getSession().invalidate();
			}
		}else {
			System.out.println("로그인 상태 아닐때 페이지 나감, 세션종료");

			req.getSession().invalidate();
		}
		
		
	}
	
}
