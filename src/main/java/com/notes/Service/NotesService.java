package com.notes.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.notes.Dao.NoteDao;
import com.notes.Entity.Note;
import com.notes.Util.Time;
@Component
@Transactional
public class NotesService {
	
	@Autowired 
	private NoteDao notedao;
	
	Note notes = new Note();
	Time time = new Time();
	
	//获取 用户的所有笔记 如果没有笔记顺便增加一条
	public List findNodes_create(String a,HttpServletRequest req){
		List note = notedao.findAllNotes(a);
		if(note == null){
			Note notelp =(Note) setEntity_notes("","写下你的第一个笔记吧！",time.gettime(),req);
			notedao.createNote(notelp);
		}
		List notes = notedao.findAllNotes(a);
		return notes;
	}
	
	//设置 notes实体类
	public Note setEntity_notes(String title,String content,String time,HttpServletRequest req){
		if(title == null||title == ""){
			title = "无标题笔记";
		}
		notes.setId(UUID.randomUUID().toString().replace("-", ""));
		notes.setTitle(title);
		notes.setContent(content);
		notes.setTime(time);
		notes.setUsername((String) req.getSession().getAttribute("user_name"));
		notes.setUserid((String) req.getSession().getAttribute("user_id"));
		return notes;
	}
}
