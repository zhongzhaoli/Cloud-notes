package com.notes.Service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.notes.Controller.NoteForm;
import com.notes.Dao.NoteDao;
import com.notes.Entity.Note;
import com.notes.Util.ServiceException;

@Component
@Transactional
public class NoteService {

	@Autowired
	private NoteDao noteDao;
	
	Note note = new Note();
	
	public void create(NoteForm noteForm){
		if(!noteForm.getTitle().equals(null)){
			note.setId(UUID.randomUUID().toString().replace("-", ""));
			note.setTitle(noteForm.getTitle());
			note.setContent(noteForm.getContent());
			noteDao.save(note);
		}else{
			throw new ServiceException("note.title","title.is.null");
		}
	}
	
	public void update(String id, String title, String content){
		if(title.length() > 1){
			note.setId(id);
			note.setTitle(title);
			note.setContent(content);
			noteDao.update(note, title, content);
		}else{
			throw new ServiceException("note.title","title.length.short");
		}
	}
}
