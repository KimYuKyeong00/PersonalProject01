package com.ppj.oct.login;

import java.io.File;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginDAO {
	
	@Autowired
	private SqlSession ss;
	
	public boolean registUser(HttpServletRequest req) {
		String squeeze_id = req.getParameter("squeeze_id");
		String squeeze_pw = req.getParameter("squeeze_pw");
		String squeeze_email = req.getParameter("squeeze_email");
		String squeeze_folder = generateFolderName();
		
		String dir = req.getParameter("folder");//유저의 저장소 폴더 생성
		System.out.println(dir);
		File folder = new File(dir+"/"+squeeze_folder);
		if(!folder.exists()) {
			folder.mkdir();
		}
		
		try {
			SqueezeUser su = new SqueezeUser(squeeze_id, squeeze_pw, squeeze_email);
			su.setSqueeze_folder(squeeze_folder);
			if(ss.getMapper(LoginMapper.class).registUser(su)== 1) {
				System.out.println("signUp 성공");
				return true;
			}else {
				System.out.println("signUp 실패");
				return false;
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return false;
	}
	
	private String generateFolderName() {
		String uuid = UUID.randomUUID().toString();
		return uuid;
	}
	
	public boolean userLogin(HttpServletRequest req) {
		try {
			req.setCharacterEncoding("UTF-8");
			String squeeze_id = req.getParameter("squeeze_id");
			String squeeze_pw = req.getParameter("squeeze_pw");
			
			SqueezeUser su = ss.getMapper(LoginMapper.class).getUserById(squeeze_id).get(0);
			if(squeeze_pw.equals(su.getSqueeze_pw())) {
				System.out.println("로그인 성공");
				req.getSession().setAttribute("login", "y");
				req.getSession().setAttribute("user", su);
				return true;
				
			}else {
				System.out.println("로그인 실패!");
				req.getSession().setAttribute("login", "n");
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return false;

	}
	
	public boolean loginYes(HttpServletRequest req) {
		if(req.getSession().getAttribute("login")!=null) {
			if(req.getSession().getAttribute("login").equals("y")) {
				return true;
			}
		}
		return false;
	}
	
	public void loginCheck(HttpServletRequest req) {
		if(loginYes(req)) {
			req.setAttribute("loginPage", "User.jsp");
		}else {
			req.setAttribute("loginPage", "login.jsp");
		}
	}
	
	public void logout(HttpServletRequest req) {
		if(loginYes(req)) {
			req.getSession().invalidate();
		}
	}

}
