package com.notes.Entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity  //表明该类是ejb的实体bean
public class User {
	//账号
	@Id
	private String id;
	private String account;
	private String password;
	//个人信息
	private String nickname;
	private String photo;
	//省
	private String province;
	//城市
	private String city;
	private String mood;
	//是否是第三方登录
	private String wxine;
	@Transient
	private String qx;
	
	
	public String getQx() {
		return qx;
	}
	public void setQx(String qx) {
		this.qx = qx;
	}
	public String getWxine() {
		return wxine;
	}
	public void setWxine(String wxine) {
		this.wxine = wxine;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getMood() {
		return mood;
	}
	public void setMood(String mood) {
		this.mood = mood;
	}
}
