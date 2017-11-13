package com.notes.Dao;

import java.rmi.ServerException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.notes.Entity.Note;
import com.notes.Entity.Share;
import com.notes.Util.ServiceException;
import com.notes.Util.Time;

import ch.qos.logback.core.joran.util.beans.BeanUtil;

//import antlr.collections.List;

//组件
@Component
//事务管理器
@Transactional
public class NoteDao {

	@Autowired
	private BaseDao basedao;
	@Autowired
	private ShareDao sharedao;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public Session getSession(){
		return entityManager.unwrap(Session.class);
	}
	
	Note notes = new Note();
	//查找所有的笔记(通过用户名)
	public List findAllNotes(String a){
		 DetachedCriteria dc = DetachedCriteria.forClass(Note.class);
		 dc.add(Property.forName("username").eq(a));
		 dc.addOrder(Order.desc("time"));
		 List list = basedao.for_Note(dc);
		 if(list != null && list.size() > 0){
			 return list;
		 }
		 else{
			 return null;
		 }
	}
	//查找所有笔记(通过ID)
	public List findNote_id(String id){
		if(!id.equals(null)){
			DetachedCriteria dc = DetachedCriteria.forClass(Note.class);
			dc.add(Property.forName("id").eq(id));
			List list = basedao.for_Note(dc);
			return basedao.get_list_all(list);
		}else{
			throw new ServiceException("note","noteId.is.null");
		}
	}
	
	public void copyNote(Note note){
		List getnote = findNote_id(note.getId());
		BeanUtils.copyProperties((Note) getnote.get(0), notes);
	}
	
	public void createNote(Note note){
		getSession().save(note);
	}
	//删除笔记
	public void deleteNote(Note notes){
		getSession().remove(getSession().merge(notes));
	}
	//修改笔记
	public void updateNote(Note note, String title, String content){
		copyNote(note);
		notes.setTitle(title);
		notes.setContent(content);
		notes.setTime(Time.timestamp());
		getSession().merge(notes);
	}
	//修改笔记2
	public void updataNote2(Note note){
		getSession().merge(note);
	}
}
