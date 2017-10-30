package com.notes.Dao;

import java.rmi.ServerException;
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

import com.notes.Entity.Note;
import com.notes.Util.ServiceException;

import ch.qos.logback.core.joran.util.beans.BeanUtil;

//import antlr.collections.List;

//组件
@Component
//事务管理器
@Repository //据访问层
@Transactional
public class NoteDao {

	@PersistenceContext
	private EntityManager entityManager;
	
	public Session getSession(){
		return entityManager.unwrap(Session.class);
	}
	
	Note notes = new Note();
	
	public Note findNote(String id){
		if(!id.equals(null)){
			DetachedCriteria dc = DetachedCriteria.forClass(Note.class);
			dc.add(Property.forName("id").eq(id));
			Criteria criteria = dc.getExecutableCriteria(getSession());
			List list = criteria.list();
			if(list != null && list.size() > 0){
				return (Note)list.get(0);
			}else{
				return null;	
			}
		}else{
			throw new ServiceException("note","noteId.is.null");
		}
	}
	
	public void copyNote(Note note){
		Note getnote = findNote(note.getId());
		BeanUtils.copyProperties(getnote, notes);
	}
	
	public void save(Note note){
		getSession().save(note);
	}
	
	public void update(Note note, String title, String content){
		copyNote(note);
		notes.setTitle(title);
		notes.setContent(content);
		getSession().merge(notes);
	}
}
