package com.notes.Controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@EnableRedisHttpSession
@Controller
public class UserController {
	
	@ResponseBody
	@PostMapping("/outLogin")
	public String outLogin(HttpServletRequest req){
		req.getSession().removeAttribute("user_name");
		return "ok";
	}
}
