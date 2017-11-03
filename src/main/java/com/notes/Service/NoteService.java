package com.notes.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.notes.Controller.NoteForm;
import com.notes.Dao.NoteDao;
import com.notes.Dao.ShareDao;
import com.notes.Entity.Note;
import com.notes.Entity.Share;
import com.notes.Util.ServiceException;
import com.notes.Util.Time;

import ch.qos.logback.core.pattern.parser.Node;

@Component
@Transactional
public class NoteService {

	@Autowired
	private NoteDao noteDao;
	@Autowired
	private ShareDao sharedao;
	@Autowired
	private NoteDao notedao;
	
	Note note = new Note();
	Time time = new Time();
	NoteForm notesForm = new NoteForm();
	
	public Note create(NoteForm noteForm, HttpServletRequest req){
		if(noteForm.getTitle().equals(null)||noteForm.getTitle().equals("")){
			note.setTitle("无标题笔记");
		}else{
			note.setTitle(noteForm.getTitle());
		}
		note.setId(UUID.randomUUID().toString().replace("-", ""));
		note.setContent(noteForm.getContent());
		note.setUsername((String) req.getSession().getAttribute("user_name"));
		note.setUserid((String) req.getSession().getAttribute("user_id"));
		note.setTime(time.timestamp().substring(0,19));
		note.setEdit_time(time.timestamp());
		noteDao.createNote(note);
		return note;
	}
	public Note create_node(String title, String content, HttpServletRequest req){
		note.setId(UUID.randomUUID().toString().replace("-", ""));
		note.setTitle(title);
		note.setContent(content);
		note.setTime(time.timestamp());
		note.setUsername((String) req.getSession().getAttribute("user_name"));
		note.setUserid((String) req.getSession().getAttribute("user_id"));
		noteDao.createNote(note);
		return note;
	}
	
	//获取 用户的所有笔记 如果没有笔记顺便增加一条
	public List findNode_create(String a,HttpServletRequest req){
		List note = noteDao.findAllNotes(a);
		if(note == null){
			Note notelp =create_node("无标题笔记", "写下你第一篇笔记",req);
		}
		List notes = noteDao.findAllNotes(a);
		return notes;
	}
	//更新note
	public void update(String id, String title, String content){
		if(title.length() > 1){
			note.setId(id);
			note.setTitle(title);
			note.setContent(content);
			noteDao.updateNote(note, title, content);
		}else{
			throw new ServiceException("note.title","title.length.short");
		}
	}
	//查找受分享的note
	public List findShare(String user_name){
		List note_id_list = sharedao.share_find_noteid(user_name);
		if(note_id_list!=null){
			List note = new ArrayList();
			for(int i=0;i<note_id_list.size();i++){
				Share note_id_ty_share = (Share) note_id_list.get(i);
				String note_id = note_id_ty_share.getNode();
				Note notesz= (Note) notedao.findNote_id(note_id).get(0);
				//share_list 充当 editable
				Note uio = new Note();
				BeanUtils.copyProperties(notesz,uio);
 				uio.setSharelist(note_id_ty_share.getEditable());
				note.add(i, uio);
			}
			return note;
		}
		else{
			return null;
		}
	}
}
