package com.pp.cookforyou.models;

import java.util.List;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="users")
public class User extends Base {
	
	@NotBlank
    @Size(max=500)
	@Indexed
    private String email;
	
	@NotBlank
    @Size(max=100)
    private String password;
    
    @NotBlank
    @Size(max=500)
    @Indexed
    private String name;
    
    @DBRef
    @NotBlank
    private Location location;
    
    @Size(max=20)
    private String phone;
    
    @NotBlank
    private Role role;
    
    private int nbrOfRate;
    
    private int sumOfRate;
    
    @DBRef
    private List<Dish> dishes;
    
    //@DBRef
    //private List<Location> locationServed;
    
    @Size(max=20)
    private String token;
    
    private boolean isActive;
    
    public User() {
        super();
        String token = "";
        this.isActive = false;
    }

	public User(String email, String password, String name, String phone, Role role, int nbrOfRate, int sumOfRate,
			List<Dish> dishes, Location location, String token, boolean isActive) {
		super();
		this.email = email;
		this.password = password;
		this.name = name;
		this.phone = phone;
		this.role = role;
		this.nbrOfRate = nbrOfRate;
		this.sumOfRate = sumOfRate;
		this.dishes = dishes;
		this.location = location;
		this.token = token;
		this.isActive = isActive;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public int getNbrOfRate() {
		return nbrOfRate;
	}

	public void setNbrOfRate(int nbrOfRate) {
		this.nbrOfRate = nbrOfRate;
	}

	public int getSumOfRate() {
		return sumOfRate;
	}

	public void setSumOfRate(int sumOfRate) {
		this.sumOfRate = sumOfRate;
	}

	public List<Dish> getDishes() {
		return dishes;
	}

	public void setDishes(List<Dish> dishes) {
		this.dishes = dishes;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	
}
