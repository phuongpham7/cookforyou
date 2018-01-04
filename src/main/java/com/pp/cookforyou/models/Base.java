package com.pp.cookforyou.models;

import java.time.LocalDateTime;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.Id;

public abstract class Base {
	
	@Id
    private String id;
	
	private boolean isDeleted;
	
	@NotBlank
	private LocalDateTime dateCreated;
	
	private LocalDateTime dateUpdated;
	
	private LocalDateTime dateDeleted;

	public Base()
	{
		super();
	}
	
	public Base(String id, boolean isDeleted, LocalDateTime dateCreated, LocalDateTime dateUpdated, LocalDateTime dateDeleted) {
		super();
		this.id = id;
		this.isDeleted = isDeleted;
		this.dateCreated = dateCreated;
		this.dateUpdated = dateUpdated;
		this.dateDeleted = dateDeleted;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public LocalDateTime getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(LocalDateTime dateCreated) {
		this.dateCreated = dateCreated;
	}

	public LocalDateTime getDateUpdated() {
		return dateUpdated;
	}

	public void setDateUpdated(LocalDateTime dateUpdated) {
		this.dateUpdated = dateUpdated;
	}

	public LocalDateTime getDateDeleted() {
		return dateDeleted;
	}

	public void setDateDeleted(LocalDateTime dateDeleted) {
		this.dateDeleted = dateDeleted;
	}
}
