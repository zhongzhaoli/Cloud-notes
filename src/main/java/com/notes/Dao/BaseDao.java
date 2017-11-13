package com.notes.Dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.notes.Entity.Note;
import com.notes.Entity.User;

//组件
@Component
//事务管理器
@Repository //据访问层
@Transactional
public class BaseDao<T> {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public Session getSession(){
		return entityManager.unwrap(Session.class);
	}
	
	public List for_Note(final DetachedCriteria dc){
		 Criteria criteria = dc.getExecutableCriteria(getSession());
		 return criteria.list();
	}
	public List for_Share(final DetachedCriteria dc){
		 Criteria criteria = dc.getExecutableCriteria(getSession());
		 return criteria.list();
	}
	public List for_User(final DetachedCriteria dc){
		 Criteria criteria = dc.getExecutableCriteria(getSession());
		 return criteria.list();
	}
	public User get_list_user(List list){
		 if(list != null && list.size() > 0){
			 return (User)list.get(0);
		 }
		 else{
			 return null;
		 }
	}
	public List get_list_all(List list){
		 if(list != null && list.size() > 0){
			 return list;
		 }
		 else{
			 return null;
		 }
	}
}
