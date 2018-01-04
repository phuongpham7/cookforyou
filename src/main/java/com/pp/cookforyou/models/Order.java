package com.pp.cookforyou.models;

import java.time.LocalDateTime;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="orders")
public class Order extends Base {

	@DBRef
	@NotBlank
	private User user;	//user who maked dish
	
	@DBRef
	@NotBlank
	private Dish dish;
	
	@DBRef
	@NotBlank
	private User userOrdered; //user who ordered
	
	@NotBlank
	private int quantity;
	
	@NotBlank
	@Size(max=500)
	private int address;
	
	@NotBlank
	@Indexed
	private LocalDateTime deliveryPickupDate;
	
	@NotBlank
	@Indexed
	private LocalDateTime orderedDate;
	
	@NotBlank
	private OrderStatus status;
	
	public Order() {
        super();
    }

	public Order(User user, Dish dish, User userOrdered, int quantity, int address, LocalDateTime deliveryPickupDate,
			LocalDateTime orderedDate, OrderStatus status) {
		super();
		this.user = user;
		this.dish = dish;
		this.userOrdered = userOrdered;
		this.quantity = quantity;
		this.address = address;
		this.deliveryPickupDate = deliveryPickupDate;
		this.orderedDate = orderedDate;
		this.status = status;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Dish getDish() {
		return dish;
	}

	public void setDish(Dish dish) {
		this.dish = dish;
	}

	public User getUserOrdered() {
		return userOrdered;
	}

	public void setUserOrdered(User userOrdered) {
		this.userOrdered = userOrdered;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getAddress() {
		return address;
	}

	public void setAddress(int address) {
		this.address = address;
	}

	public LocalDateTime getDeliveryPickupDate() {
		return deliveryPickupDate;
	}

	public void setDeliveryPickupDate(LocalDateTime deliveryPickupDate) {
		this.deliveryPickupDate = deliveryPickupDate;
	}

	public LocalDateTime getOrderedDate() {
		return orderedDate;
	}

	public void setOrderedDate(LocalDateTime orderedDate) {
		this.orderedDate = orderedDate;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}
}
