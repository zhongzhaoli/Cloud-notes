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
		List new_retu_list = new ArrayList();
		for(int i=0;i<list.size();i++){
			List new_retu_sm = new ArrayList();
			User user_list_zxc = (User) list.get(i);
			String user_id = user_list_zxc.getId();
			Share share_edit = (Share) sharedao.share_find_id(id, user_id, true).get(0);
			User zxc = new User();
			zxc.setAccount(user_list_zxc.getAccount());
			zxc.setId(user_list_zxc.getId());
			zxc.setPhoto(user_list_zxc.getPhoto());
			zxc.setPassword(share_edit.getEditable());
			new_retu_list.add(i, zxc);
		}
		return new_retu_list; 
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
