package com.notes.Controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.notes.Dao.ShareDao;
import com.notes.Dao.UserDao;
import com.notes.Entity.Share;
import com.notes.Entity.User;

@EnableRedisHttpSession
@Controller
public class ShareController {
	
	@Autowired
	private UserDao userdao;
	@Autowired
	private ShareDao sharedao;
	
	Share share = new Share();

	@ResponseBody
	@PostMapping("/share/{keyword}")
	public List outLogin(HttpServletRequest req,@PathVariable String keyword){
		if(keyword == null||keyword == ""){
			return null;
		}
		List list = userdao.findUser_vague(keyword);
		return list;
	}
	
	@PostMapping("/share/{id}/user/{userid}")
	@ResponseBody
	public String inShare(HttpServletRequest req,@PathVariable String id,@PathVariable String userid){
		String re_str = sharedao.add_share_user(id, userid,req);
		return re_str;
	}
	@DeleteMapping("/share/{id}/user/{userid}")
	@ResponseBody
	public String delShare(HttpServletRequest req,@PathVariable String id,@PathVariable String userid){
		String re_str = sharedao.dele_share_user(id,userid);
		return re_str;
	}
	
	@PostMapping("/share/{id}/find")
	@ResponseBody
	public List findShare(HttpServletRequest req,@PathVariable String id){
		List list = sharedao.findStaredao(id);
		if(list.size() <= 0||list==null){
			return null;
		}
		return list; 
	}
	@PutMapping("/share/{id}/editable/{userid}")
	@ResponseBody
	public Share editabledShare(@PathVariable String id,@PathVariable String userid){
		Share edit_share = (Share) sharedao.share_find_id(id, userid,true).get(0);
		BeanUtils.copyProperties(edit_share, share);
		if(share.getEditable().equals("false")){
			share.setEditable("true");
		}
		else{
			share.setEditable("false");
		}
		sharedao.updata_share(share);
		return share;
	}
}
