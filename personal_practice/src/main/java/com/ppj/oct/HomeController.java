package com.ppj.oct;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ppj.oct.login.LoginDAO;

@Controller
public class HomeController {
	
	@Autowired
	private LoginDAO ldao;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(HttpServletRequest req) {
		System.out.println("Home");
		ldao.loginCheck(req);
		req.setAttribute("subPage", "blank.jsp");
		return "index";
	}
	
	@RequestMapping(value="/oct.home" , method = RequestMethod.GET)
	public String goHome(HttpServletRequest req) {
		System.out.println("Home by HeaderImage");
		ldao.loginCheck(req);
		req.setAttribute("subPage", "blank.jsp");
		
		return "index";
	}
	
}
