package com.notes.Controller;

import java.util.UUID;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.notes.Dao.NotesDao;
import com.notes.Entity.Notes;
import com.notes.Service.NotesService;
import com.notes.Util.Time;

@EnableRedisHttpSession
@Controller
public class NotesController {
	
	@Autowired 
	private NotesDao notesdao;
	@Autowired
	private NotesService notesservice;
	
	Notes notes = new Notes();
	Time time = new Time();
	
	//创建 notes
	@ResponseBody
	@PostMapping("/notes")
	public String createnotes(String title,String content,HttpServletRequest req){
		Notes note = notesservice.setEntity_notes(title,content,time.gettime(),req);
		notesdao.createNotes(note);
		return note.getId();
	}
	
	//删除 notes
	@ResponseBody
	@DeleteMapping("/notes/{id}")
	public String savenotes(@PathVariable String id){
		List aaa = notesdao.findAllNotes_id(id);
		Notes bbb = (Notes) aaa.get(0);
		BeanUtils.copyProperties(bbb, notes);
		notesdao.deleteNotes(notes);
		return "success";
	}
}
