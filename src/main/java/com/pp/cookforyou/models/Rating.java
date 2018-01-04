package com.pp.cookforyou.models;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="ratings")
public class Rating extends Base {
	
	@NotBlank
	private int rate;
	
	@DBRef
	private User user; //user who is rated
	
	@DBRef
	private User fromUser; //user who rated
	
	public Rating() {
        super();
    }

	public Rating(int rate, User user, User fromUser) {
		super();
		this.rate = rate;
		this.user = user;
		this.fromUser = fromUser;
	}

	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getFromUser() {
		return fromUser;
	}

	public void setFromUser(User fromUser) {
		this.fromUser = fromUser;
	}
	
}
