package com.notes.Entity;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Notes {
	@Id
	private String id;
	private String title;
	private String content;
	private String username;
	private String userid;
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	private String sharelist;
	private String time;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getSharelist() {
		return sharelist;
	}
	public void setSharelist(String sharelist) {
		this.sharelist = sharelist;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time2) {
		this.time = time2;
	}
	
}
