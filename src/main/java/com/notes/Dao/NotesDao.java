package com.notes.Dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.notes.Entity.Notes;
import com.notes.Entity.User;

//组件
@Component
//事务管理器
@Repository //据访问层
@Transactional
public class NotesDao {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public Session getSession() {
		return entityManager.unwrap(Session.class);
	}
	
	Notes notesa = new Notes();
	//查找所有的笔记(通过用户名)
	public List findAllNotes(String a){
		 DetachedCriteria dc = DetachedCriteria.forClass(Notes.class); //离线查询
		 dc.add(Property.forName("username").eq(a));
		 Criteria criteria = dc.getExecutableCriteria(getSession());
		 List list = criteria.list();
		 if(list != null && list.size() > 0){
			 return list;
		 }
		 else{
			 return null;
		 }
	}
	//查找所有的笔记(通过ID)
	public List findAllNotes_id(String a){
		 DetachedCriteria dc = DetachedCriteria.forClass(Notes.class); //离线查询
		 dc.add(Property.forName("id").eq(a));
		 Criteria criteria = dc.getExecutableCriteria(getSession());
		 List list = criteria.list();
		 if(list != null && list.size() > 0){
			 return list;
		 }
		 else{
			 return null;
		 }
	}
	//创建笔记
	public void createNotes(Notes notes){
		getSession().save(notes);
	}
	//删除笔记
	public void deleteNotes(Notes notes){
		getSession().remove(getSession().merge(notes));
	}
}
