package com.pp.cookforyou.models;

import java.util.List;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="locations")
public class Location extends Base {
	
	@NotBlank
    @Size(max=500)
    @Indexed
    private String name;
    
	/*
    @DBRef
    private List<User> users;
    */
    
    public Location() {
        super();
    }

	public Location(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
    
}
