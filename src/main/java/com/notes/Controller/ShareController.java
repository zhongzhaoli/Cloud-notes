package com.notes.Controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.notes.Dao.UserDao;
import com.notes.Entity.User;

@EnableRedisHttpSession
@Controller
public class ShareController {
	
	@Autowired
	private UserDao userdao;

	@ResponseBody
	@PostMapping("/share/{keyword}")
	public List outLogin(HttpServletRequest req,@PathVariable String keyword){
		if(keyword == null||keyword == ""){
			return null;
		}
		List list = userdao.findUser_vague(keyword);
		return list;
	}
}
