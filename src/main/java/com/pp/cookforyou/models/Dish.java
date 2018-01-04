package com.pp.cookforyou.models;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="dishes")
public class Dish extends Base {

	@NotBlank
    @Size(max=500)
	@Indexed
    private String name;
    
    private String photo;
    
    @NotBlank
    @Size(max=500)
    private String ingredient;
    
    @NotBlank
    @Size(max=100)
    @Indexed
    private String style;
    
    @NotBlank
    @Size(max=100)
    private String price;
    
    @Size(max=100)
    private String shippingFee;
    
    @Size(max=500)
    private String additionalDescription;
    
    @DBRef
    private User user;
    
    public Dish() {
        super();
    }
    
	public Dish(String name, String style, String photo, String ingredient, String price, 
			String shippingFee, String additionalDescription, User user) {
		super();
		this.name = name;
		this.style = style;
		this.photo = photo;
		this.ingredient = ingredient;
		this.price = price;
		this.shippingFee = shippingFee;
		this.additionalDescription = additionalDescription;
		this.user = user;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getIngredient() {
		return ingredient;
	}

	public void setIngredient(String ingredient) {
		this.ingredient = ingredient;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}
	
	public String getShippingFee() {
		return shippingFee;
	}

	public void setShippingFee(String shippingFee) {
		this.shippingFee = shippingFee;
	}
	
	public String getAdditionalDescription() {
		return additionalDescription;
	}

	public void setAdditionalDescription(String additionalDescription) {
		this.additionalDescription = additionalDescription;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
