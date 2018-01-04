package com.pp.cookforyou.models;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="dish_comments")
public class DishComment extends Comment {

	@DBRef
	private Dish dish;
	
	public DishComment() {
		super();
	}

	public DishComment(Dish dish) {
		super();
		this.dish = dish;
	}

	public Dish getDish() {
		return dish;
	}

	public void setDish(Dish dish) {
		this.dish = dish;
	}
}
