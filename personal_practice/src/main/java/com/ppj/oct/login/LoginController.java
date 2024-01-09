package com.ppj.oct.login;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {
	
	@Autowired
	private LoginDAO ldao;
	
	@RequestMapping(value="/oct.login", method = RequestMethod.GET)
	public String Login(HttpServletRequest req) {
		req.setAttribute("loginPage", "login.jsp");
		return "index";
	}

	
	@RequestMapping(value="/oct.login", method = RequestMethod.POST)
	public String tryLogin(HttpServletRequest req) {
		if(ldao.userLogin(req)) {
			req.setAttribute("loginPage", "User.jsp");

		}else {
			req.setAttribute("loginPage", "login.jsp");
		}

		req.setAttribute("subPage", "blank.jsp");
		return "index";
	}
	
	@RequestMapping(value="/oct.signUp", method = RequestMethod.GET)
	public String toSignUp(HttpServletRequest req) {
		req.setAttribute("loginPage", "blank.jsp");
		req.setAttribute("subPage", "signUp.jsp");
		return "index";
	}
	
	@RequestMapping(value="/oct.register", method = RequestMethod.POST)
	public String regist(HttpServletRequest req) {
		
		req.setAttribute("loginPage", "blank.jsp");
		
		if(ldao.registUser(req)) {
			req.setAttribute("subPage", "SignUpSuccess.jsp");
		}else {
			req.setAttribute("subPage", "SignUpFailed.jsp");
		}

		return "index";
	}
	
	
	@RequestMapping(value="/oct.logout", method=RequestMethod.POST)
	public String logout(HttpServletRequest req) {
		System.out.println("/oct.logout");
		ldao.logout(req);
		ldao.loginCheck(req);
		req.setAttribute("subPage", "blank.jsp");
		return "index";
	}

}
