package com.lp.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.lp.util.ValidPassword;

@Entity
@Table(name = "USERS")
public class Actor implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6047148473727575924L;

	@Id
	@Column(name = "ACTOR_ID")
	@SequenceGenerator(sequenceName = "actor_id_seq", name = "actorIdSeq" ,allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "actorIdSeq")
	private Long actorId;
	
	@Column(name = "USER_NAME")
	private String userName;
	
	@Column(name = "PASSWORD")
	@ValidPassword
	private String password;
	
	public Long getActorId() {
		return actorId;
	}
	
	public void setActorId(Long actorId) {
		this.actorId = actorId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	

}
