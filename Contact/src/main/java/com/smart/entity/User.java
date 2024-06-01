package com.smart.entity;

import java.util.ArrayList;
import java.util.List;

import com.smart.entity.Contact;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
public class User {

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	@NotBlank(message="Name should not be blank")
	@Size(min=2,max=20,message="min 2 and max 20 character are allowed")
	private String name;
	@Column(unique=true)
	private String email;
	private String pwd;
	private String role;
	private boolean enabled;
	private String imageUrl;
	@Column(length=500)
	private String about;
	
	@OneToMany(cascade=CascadeType.ALL,fetch = FetchType.LAZY, mappedBy="u",orphanRemoval = true)
	private List<Contact> c=new ArrayList<>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public List<Contact> getC() { return c; }
	  
	 public void setC(List<Contact> c) { this.c = c; }

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", email=" + email + ", pwd=" + pwd + ", role=" + role
				+ ", enabled=" + enabled + ", imageUrl=" + imageUrl + ", about=" + about + ", c=" + c + "]";
	}
	 
	
	
}
