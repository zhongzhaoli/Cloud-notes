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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.notes.Dao.NoteDao;
import com.notes.Entity.Note;
import com.notes.Service.NoteService;
import com.notes.Util.Time;

@EnableRedisHttpSession
@Controller
public class NotesController {
	
	@Autowired 
	private NoteDao notedao;
	@Autowired
	private NoteService noteService;
	
	Note notes = new Note();
	Time time = new Time();
	
	//创建 notes
	@ResponseBody
	@PostMapping("/notes")
	public String createnotes(NoteForm noteForm, HttpServletRequest req){
		Note note = noteService.create(noteForm,req);
		return note.getId();
	}
	
	//删除 notes
	@ResponseBody
	@DeleteMapping("/notes/{id}")
	public String savenotes(@PathVariable String id){
		List aaa = (List) notedao.findNote_id(id);
		Note bbb = (Note) aaa.get(0);
		BeanUtils.copyProperties(bbb, notes);
		notedao.deleteNote(notes);
		return "success";
	}
	//更改
	@PutMapping("/note/{id}")
	@ResponseBody
	public String update(HttpServletRequest request,@PathVariable String id, String title, String content){
		noteService.update(id, title, content);
		return null;
	}
}
