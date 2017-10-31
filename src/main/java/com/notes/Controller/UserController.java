package com.notes.Controller;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.notes.Dao.UserDao;
import com.notes.Entity.User;
import com.notes.Service.PhotoService;
import com.notes.Util.BasePhoto;
import com.notes.Util.UTF8;
import com.notes.Util.ServiceException;

@EnableRedisHttpSession
@Controller
public class UserController {
	
	
	@Autowired 
	private UserDao userdao;
	@Autowired
	private PhotoService photoservice;
	User user=new User();
	
	@ResponseBody
	@PostMapping("/outLogin")
	public String outLogin(HttpServletRequest req){
		req.getSession().removeAttribute("user_name");
		return "ok";
	}
	
	//修改信息 包括头像 看关键词change
	@ResponseBody
    @RequestMapping(value="/user", method = RequestMethod.PUT)
    public String uploadimg(String base_url,String change,HttpServletRequest req,String user_nickname,String user_province,String user_city,String user_mood) throws UnsupportedEncodingException{
		String sessio_username = (String) req.getSession().getAttribute("user_name");
		String sessio_userid = (String) req.getSession().getAttribute("user_id");
		//如果是修改头像
		if(change.equals("photo")){
			String file_name = UUID.randomUUID().toString();
			String file_sql_url = "/photo/"+file_name+".jpg";
			boolean c = photoservice.savephoto(base_url, "upload",file_name);
			if(c){
				user.setAccount(sessio_username);
				user.setId(sessio_userid);
				userdao.changePhoto(file_sql_url, user);
				return "success";
			}
			throw new ServiceException("photo.create.error");
		}
		//如果是修改信息
		if(change.equals("message")){
			user.setAccount(sessio_username);
			user.setId(sessio_userid);
			userdao.changeMessage(user_nickname,user_province,user_city,user_mood,user);
			return "success";
		}
		throw new ServiceException("message.create.error");
    }
	
}
