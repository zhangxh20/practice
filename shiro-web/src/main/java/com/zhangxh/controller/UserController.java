package com.zhangxh.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {

	@RequestMapping("toLogin")
	public String toLogin() {
		return "login";
	}
	
	@RequestMapping("login")
	public String login(String username,String password) {
	    Subject subject = SecurityUtils.getSubject();
	    UsernamePasswordToken token = new UsernamePasswordToken(username, password);
	    subject.login(token);
	    if (subject.isAuthenticated()) {
	        return "index";
	    }
	    return "unauth";
	}
	
	@RequestMapping("/unauth")
	public String unauth() {
	    return "unauth";
	}
}
