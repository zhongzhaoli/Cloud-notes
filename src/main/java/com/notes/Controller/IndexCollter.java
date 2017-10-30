package com.notes.Controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.notes.Entity.Note;
import com.notes.Entity.User;
import com.notes.Service.NoteService;
import com.notes.Dao.NoteDao;
import com.notes.Dao.UserDao;

@EnableRedisHttpSession
@Controller
public class IndexCollter {

	@Autowired 
	private UserDao userdao;
	
	@Autowired
	private NoteService noteService;
	
	User user=new User();
	Note notes = new Note();
	
	//首页
	@GetMapping("/index")
	public String index(Model model,HttpServletRequest req){
		String a = (String) req.getSession().getAttribute("user_name");
		String b = (String) req.getSession().getAttribute("user_id");
		user.setAccount(a);
		if(a != null && b != null){
			User he = userdao.findUser(a);
			List not = noteService.findNode_create(a, req);
			model.addAttribute("item", he);
			model.addAttribute("sessionuser", user.getAccount());
			model.addAttribute("notes",not);
			return "index";
		}
		else{
			return "noLogin";
		}
	}
	
}
