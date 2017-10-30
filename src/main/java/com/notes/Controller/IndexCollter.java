package com.notes.Controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.notes.Entity.User;
import com.notes.Dao.UserDao;

@EnableRedisHttpSession
@Controller
public class IndexCollter {

	@Autowired 
	private UserDao userdao;
	
	User user=new User();
	
	//首页
	@GetMapping("/index")
	public String index(Model model,HttpServletRequest req){
		String a = (String) req.getSession().getAttribute("user_name");
		String b = (String) req.getSession().getAttribute("user_id");
		user.setAccount(a);
		if(a!=null&&b!=null){
			User he = userdao.findUser(a);
			model.addAttribute("item", he);
			model.addAttribute("sessionuser", user.getAccount());
			return "index";
		}
		else{
			return "noLogin";
		}
	}
}
