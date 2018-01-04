package com.pp.cookforyou.models;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="comments")
public class Comment extends Base {

	@DBRef
	private User byUser;
	
	@NotBlank
	@Size(max=1000)
	private String comment;
	
	public Comment() {
        super();
    }
	
	public Comment(User byUser, String comment) {
		super();
		this.byUser = byUser;
		this.comment = comment;
	}

	public User getByUser() {
		return byUser;
	}

	public void setByUser(User byUser) {
		this.byUser = byUser;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
}
