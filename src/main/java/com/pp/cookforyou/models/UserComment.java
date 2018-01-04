package com.pp.cookforyou.models;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="user_comments")
public class UserComment extends Comment {

	@DBRef
	private User user;
	
	public UserComment() {
		super();
	}
	
	public UserComment(User user) {
		super();
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
}
