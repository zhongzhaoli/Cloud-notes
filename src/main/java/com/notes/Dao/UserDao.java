package com.notes.Dao;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

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
	
	User user =new User();
	UTF8 utf8=new UTF8();
	
	//找个人信息
	public User findUser(String account){
		if(account!=null){
			 DetachedCriteria dc=DetachedCriteria.forClass(User.class); //离线查询
			 dc.add(Property.forName("account").eq(account));
			 Criteria criteria=dc.getExecutableCriteria(getSession());
			 List list=criteria.list();
			 if(list!=null&&list.size()>0){
				 return (User)list.get(0);
			 }
			 else{
				 return null;
			 }
		}
		else{
			throw new ServiceException("account","not.get.account");
		}
	}
	//修改头像
	public void changePhoto(String photo_url,User user){
		User getuser = findUser(user.getAccount());
		BeanUtils.copyProperties(getuser, user);
		user.setPhoto(photo_url);
		System.out.println(user);
		getSession().merge(user);
	}
	//修改信息
	public void changeMessage(String user_nickname,String user_province,String user_city,String user_mood,User user) throws UnsupportedEncodingException{
		User getuser = findUser(user.getAccount());
		BeanUtils.copyProperties(getuser, user);
		user.setNickname(user_nickname);
		user.setProvince(user_province);
		user.setCity(user_city);
		user.setMood(user_mood);
		getSession().merge(user);
	}
}
