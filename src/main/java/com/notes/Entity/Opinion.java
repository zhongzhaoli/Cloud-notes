package com.notes.Entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Opinion {
	@Id
	private String id;
	private String username;
	private String opinion;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getOpinion() {
		return opinion;
	}
	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}
}
