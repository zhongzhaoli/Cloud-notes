package com.notes.Entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Share {
	@Id
	private String id;
	private String node;
	private String username;
	private String Editable;
	public String getEditable() {
		return Editable;
	}
	public void setEditable(String string) {
		Editable = string;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNode() {
		return node;
	}
	public void setNode(String node) {
		this.node = node;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
}
