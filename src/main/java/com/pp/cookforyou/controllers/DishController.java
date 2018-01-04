package com.pp.cookforyou.controllers;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pp.cookforyou.models.Dish;
import com.pp.cookforyou.models.User;
import com.pp.cookforyou.repositories.DishRepository;
import com.pp.cookforyou.repositories.UserRepository;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class DishController {
	
	@Autowired
    DishRepository dishRepository;
	
	@Autowired
	UserRepository userRepository;

    @GetMapping("/dishes")
    public List<Dish> getAllDishes() {
        Sort sortByNameAtDesc = new Sort(Sort.Direction.DESC, "name");
        return dishRepository.findAll(sortByNameAtDesc);
    }

    @SuppressWarnings("unchecked")
	@PostMapping("/dishes")
    public ResponseEntity<Dish> createDish(@Valid @RequestBody String dishInfo) {
    	try
    	{
    		String name="", style="", photo="", ingredient="", price="", shippingFee="",
    				additionalDescription="", userId="", uploadedPhotoUrl="";
	    	ObjectMapper mapper = new ObjectMapper();
			JsonNode dishNode = mapper.readTree(dishInfo);
			name = dishNode.path("name").asText();
			style = dishNode.path("style").asText();
			photo = dishNode.path("photo").asText();
			ingredient = dishNode.path("ingredient").asText();
			price = dishNode.path("price").asText();
			shippingFee = dishNode.path("shippingFee").asText();
			additionalDescription = dishNode.path("additionalDescription").asText();
			JsonNode userNode = dishNode.path("user");
			if (userNode.isMissingNode())
			{
			}
			else
			{
				userId = userNode.path("id").asText();
			}
			
			if (!photo.equals(""))
			{
				Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
						  "cloud_name", "dseieldd7",
						  "api_key", "163983422959623",
						  "api_secret", "CcGFfCDxLAImaVi1usXhEN1SxA0"));
				//File toUpload = new File(photo);
				Map<Object, Object> uploadResult = cloudinary.uploader().upload(photo, ObjectUtils.emptyMap()); 
				uploadedPhotoUrl = uploadResult.get("url").toString();
			}
			
			User user = userRepository.findOne(userId);
			
			Dish dish = new Dish(name, style, uploadedPhotoUrl, ingredient, price, shippingFee, 
					additionalDescription, user);
    		dish.setDateCreated(LocalDateTime.now());
            dish.setDeleted(false);
            dishRepository.save(dish);
    	}catch (Exception ex){
    		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    	}
    	
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value="/dishes/{id}")
    public ResponseEntity<Dish> getDishById(@PathVariable("id") String id) {
        Dish dish = dishRepository.findOne(id);
        if(dish == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(dish, HttpStatus.OK);
        }
    }

    @PutMapping(value="/dishes/{id}")
    public ResponseEntity<Dish> updateDish(@PathVariable("id") String id,
                                           @Valid @RequestBody Dish dish) {
        Dish dishData = dishRepository.findOne(id);
        if(dishData == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        dishData.setName(dish.getName());
        dishData.setPhoto(dish.getPhoto());
        dishData.setIngredient(dish.getIngredient());
        dishData.setStyle(dish.getStyle());
        dishData.setPrice(dish.getPrice());
        dishData.setShippingFee(dish.getShippingFee());
        dishData.setAdditionalDescription(dish.getAdditionalDescription());
        dishData.setDateUpdated(LocalDateTime.now());
        Dish updatedDish = dishRepository.save(dishData);
        return new ResponseEntity<>(updatedDish, HttpStatus.OK);
    }

    @DeleteMapping(value="/dishes/{id}")
    public void deleteDish(@PathVariable("id") String id) {
    	Dish dishData = dishRepository.findOne(id);
        if(dishData == null) {
            return;
        }
        dishData.setDeleted(true);
        dishData.setDateDeleted(LocalDateTime.now());
        dishRepository.delete(id);
    }
}
