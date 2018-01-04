package com.pp.cookforyou.models;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="order_comments")
public class OrderComment extends Comment {
	
	@DBRef
	private Order order;
	
	public OrderComment() {
		super();
	}

	public OrderComment(Order order) {
		super();
		this.order = order;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}
}
