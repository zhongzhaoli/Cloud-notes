package com.notes.Controller;


import org.hibernate.validator.constraints.NotEmpty;

public class NoteForm {

	@NotEmpty
	private String title;
	private String content;


	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
