package com.notes.Dao;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Property;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.notes.Entity.Note;
import com.notes.Entity.User;
import com.notes.Util.ServiceException;
import com.notes.Util.UTF8;

//组件
@Component
//事务管理器
@Repository //据访问层
@Transactional
public class UserDao {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public Session getSession() {
		return entityManager.unwrap(Session.class);
	}
	
	@Autowired
	private BaseDao basedao;
	
	User users = new User();
	UTF8 utf8 = new UTF8();
	Note notes = new Note();
	
	//找个人信息(account)
	public User findUser(String account){
		if(account != null){
			 DetachedCriteria dc = DetachedCriteria.forClass(User.class); //离线查询
			 dc.add(Property.forName("account").eq(account));
			 List list = basedao.for_User(dc);
			 return basedao.get_list_user(list);
		}
		else{
			throw new ServiceException("account","not.get.account");
		}
	}
	//找个人信息(id)
	public User findUser_id(String id){
		if(id != null){
			 DetachedCriteria dc = DetachedCriteria.forClass(User.class); //离线查询
			 dc.add(Property.forName("id").eq(id));
			 List list = basedao.for_User(dc);
			 return basedao.get_list_user(list);
		}
		else{
			throw new ServiceException("account","not.get.account");
		}
	}
	//找人(模糊查找)
	public List findUser_vague(String keyword){
		DetachedCriteria dc = DetachedCriteria.forClass(User.class); //离线查询
		//ANYWHERE 查出所有
		dc.add(Property.forName("account").like(keyword,MatchMode.ANYWHERE));
		 List list = basedao.for_User(dc);
		 return basedao.get_list_all(list);
	}
	//查找出所有信息 并 进行覆盖
	public void copyUser(User user){
		User getuser = findUser(user.getAccount());
		BeanUtils.copyProperties(getuser, users);
	}
	//修改头像
	public void changePhoto(String photo_url,User user){
		copyUser(user);
		users.setPhoto(photo_url);
		getSession().merge(users);
	}
	//修改信息
	public void changeMessage(String user_nickname,String user_province,String user_city,String user_mood,User user) throws UnsupportedEncodingException{
		copyUser(user);
		users.setNickname(user_nickname);
		users.setProvince(user_province);
		users.setCity(user_city);
		users.setMood(user_mood);
		getSession().merge(users);
	}

}
