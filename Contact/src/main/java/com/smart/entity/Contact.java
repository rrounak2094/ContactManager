package com.smart.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.smart.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name="cgroup")
public class Contact{
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int conid;
	private String name;
	private String secondname;
	private String work;
	//@Column(unique=true)
	@NotNull
	private String email;
	private String phone;
	private String profile_image;
	
	private String about;
	
	@ManyToOne
	@JsonIgnore
	private User u;

	
	
	public int getConid() {
		return conid;
	}

	public void setConid(int conid) {
		this.conid = conid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSecondname() {
		return secondname;
	}

	public void setSecondname(String secondname) {
		this.secondname = secondname;
	}

	public String getWork() {
		return work;
	}

	public void setWork(String work) {
		this.work = work;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getprofileImage() {
		return profile_image;
	}

	public void setprofileImage(String profile_image) {
		this.profile_image = profile_image;
	}



	
	  public String getProfile_image() {
		return profile_image;
	}

	public void setProfile_image(String profile_image) {
		this.profile_image = profile_image;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public User getU() { return u; }
	  
	  public void setU(User u) { this.u = u; }

		/*
		 * @Override public String toString() { return "Contact [conid=" + conid +
		 * ", name=" + name + ", secondname=" + secondname + ", work=" + work +
		 * ", email=" + email + ", phone=" + phone + ", image=" + image + ", about=" +
		 * about + ", u=" + u + "]"; }
		 */
	
	  @Override
	  public boolean equals(Object obj)
	  {
		  return this.conid==((Contact)obj).getConid();
	  }
	
}
