package com.notes.Controller;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.notes.Dao.OpinionDao;
import com.notes.Entity.Opinion;
import com.notes.Util.ServiceException;


@EnableRedisHttpSession
@Controller
public class OpintionController {
	
	@Autowired 
	private OpinionDao opiniondao;
	
	Opinion opinion = new Opinion();
	
	@ResponseBody
	@PostMapping("/opinion")
	public String opinion(String opinions,HttpServletRequest req){
		if(opinions != null && opinions != ""){
			String user_name = (String) req.getSession().getAttribute("user_name");
			opinion.setId(UUID.randomUUID().toString().replace("-", ""));
			opinion.setOpinion(opinions);
			opinion.setUsername(user_name);
			opiniondao.save(opinion);
			return "success";
		}
		else{
			throw new ServiceException("opinion.text.null");
		}
	}
}
