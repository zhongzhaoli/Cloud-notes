package com.notes.Service;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.notes.Controller.NoteForm;
import com.notes.Dao.NoteDao;
import com.notes.Entity.Note;
import com.notes.Util.ServiceException;
import com.notes.Util.Time;

@Component
@Transactional
public class NoteService {

	@Autowired
	private NoteDao noteDao;
	
	Note note = new Note();
	Time time = new Time();
	NoteForm notesForm = new NoteForm();
	
	public Note create(NoteForm noteForm, HttpServletRequest req){
		if(noteForm.getTitle().equals(null)){
			note.setTitle("无标题笔记");
		}else{
			note.setTitle(noteForm.getTitle());
		}
		note.setId(UUID.randomUUID().toString().replace("-", ""));
		note.setContent(noteForm.getContent());
		note.setUsername((String) req.getSession().getAttribute("user_name"));
		note.setUserid((String) req.getSession().getAttribute("user_id"));
		note.setTime(time.gettime());
		noteDao.createNote(note);
		return note;
	}
	public Note create_node(String title, String content, HttpServletRequest req){
		note.setId(UUID.randomUUID().toString().replace("-", ""));
		note.setTitle(title);
		note.setContent(content);
		note.setTime(time.gettime());
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
	//删除 notes
	@ResponseBody
	@DeleteMapping("/notes/{id}")
	public String savenotes(@PathVariable String id){
		List aaa = (List) noteDao.findNote_id(id);
		Note bbb = (Note) aaa.get(0);
		BeanUtils.copyProperties(bbb, note);
		noteDao.deleteNote(note);
		return "success";
	}
}
