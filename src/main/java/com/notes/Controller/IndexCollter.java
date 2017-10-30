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

import com.notes.Entity.User;
import com.notes.Service.NoteService;
import com.notes.Dao.UserDao;

@EnableRedisHttpSession
@Controller
public class IndexCollter {

	@Autowired 
	private UserDao userdao;
	
	@Autowired
	private NoteService noteService;
	
	User user=new User();
	
	//首页
	@GetMapping("/index")
	public String index(Model model,HttpServletRequest req){
		String a = (String) req.getSession().getAttribute("user_name");
		String b = (String) req.getSession().getAttribute("user_id");
		user.setAccount(a);
		if(a != null && b != null){
			User he = userdao.findUser(a);
			model.addAttribute("item", he);
			model.addAttribute("sessionuser", user.getAccount());
			return "index";
		}
		else{
			return "noLogin";
		}
	}
	//创建笔记
	@PostMapping("/create")
	@ResponseBody
	public String create(NoteForm noteForm){
		noteService.create(noteForm);
		return null;
	}
	
	@RequestMapping(value = "/note/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public String update(HttpServletRequest request,@PathVariable String id, String title, String content){
		noteService.update(id, title, content);
		return null;
	}
}
