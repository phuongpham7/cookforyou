package com.pp.cookforyou.controllers;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pp.cookforyou.models.Dish;
import com.pp.cookforyou.models.Location;
import com.pp.cookforyou.models.Role;
import com.pp.cookforyou.models.User;
import com.pp.cookforyou.repositories.LocationRepository;
import com.pp.cookforyou.repositories.UserRepository;
import com.pp.cookforyou.utils.CFUUtils;
import com.pp.cookforyou.utils.PasswordEncryptor;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class UserController {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	LocationRepository locationRepository;
	
	@GetMapping("/users")
    public List<User> getAllUsers() {
        Sort sortByEmailAtDesc = new Sort(Sort.Direction.DESC, "email");
        return userRepository.findAll(sortByEmailAtDesc);
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody String userInfo) {
    	try
    	{
	    	ObjectMapper mapper = new ObjectMapper();
			JsonNode node = mapper.readTree(userInfo);
			String email = mapper.convertValue(node.get("email"), String.class);
			String password = mapper.convertValue(node.get("password"), String.class);
			String name = mapper.convertValue(node.get("name"), String.class);
			String location = mapper.convertValue(node.get("location"), String.class);
			
			if (userRepository.findByEmail(email) != null)
	    	{
	    		return new ResponseEntity<>(HttpStatus.CONFLICT);
	    	}
			
			Location locationObj = locationRepository.findLocationByName(location);
			Location userLocation = null;
			if (locationObj == null)
			{
				userLocation = new Location(location);
				userLocation.setDeleted(false);
				userLocation.setDateCreated(LocalDateTime.now());
				locationRepository.save(userLocation);
			}
			else
			{
				userLocation = locationObj;
			}
			
	    	String token = CFUUtils.generateToken();
    		User user = new User();
    		user.setEmail(email);
    		user.setPassword(PasswordEncryptor.encryptPassword(password));
    		user.setName(name);
    		user.setRole(Role.USER);
    		user.setLocation(userLocation);
        	user.setNbrOfRate(0);
        	user.setSumOfRate(0);
        	user.setToken(token);
        	user.setActive(true);
            user.setDateCreated(LocalDateTime.now());
            user.setDeleted(false);
            userRepository.save(user);
            //CFUUtils.sendEmail(email, token);
    	}catch (Exception ex){
    		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    	}
    	
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @PostMapping("/users/createUserByHand")
    public User createUserByHand(@Valid @RequestBody String userInfo) throws JsonProcessingException, IOException {
    	ObjectMapper mapper = new ObjectMapper();
		JsonNode node = mapper.readTree(userInfo);
		String email = mapper.convertValue(node.get("email"), String.class);
		String password = mapper.convertValue(node.get("password"), String.class);
		String name = mapper.convertValue(node.get("name"), String.class);
		String phone = mapper.convertValue(node.get("phone"), String.class);
		String role = mapper.convertValue(node.get("role"), String.class);
		
    	User newUser = new User();
    	newUser.setEmail(email);
    	newUser.setPassword(PasswordEncryptor.generateHash(password));
    	newUser.setName(name);
    	newUser.setPhone(phone);
    	if (role.equals("user"))	newUser.setRole(Role.USER);
    	else	newUser.setRole(Role.ADMINISTRATOR);
    	newUser.setNbrOfRate(0);
    	newUser.setSumOfRate(0);
    	newUser.setActive(true);
    	newUser.setDateCreated(LocalDateTime.now());
    	newUser.setDeleted(false);
        return userRepository.save(newUser);
    }

    @GetMapping(value="/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") String id) {
        User user = userRepository.findOne(id);
        if(user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
    }

    @PutMapping(value="/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") String id,
    		@Valid @RequestBody String userInfo) throws JsonProcessingException, IOException {
    	ObjectMapper mapper = new ObjectMapper();
		JsonNode node = mapper.readTree(userInfo);
		String name = mapper.convertValue(node.get("name"), String.class);
		String locationName = mapper.convertValue(node.get("location"), String.class);
        User userData = userRepository.findOne(id);
        if(userData == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        Location location = locationRepository.findLocationByName(locationName);
        Location userLocation = null;
        if (location == null)
        {
        	userLocation = new Location(locationName);
			userLocation.setDeleted(false);
			userLocation.setDateCreated(LocalDateTime.now());
			locationRepository.save(userLocation);
        }
        else {
        	userLocation = location;
        }
        
        userData.setName(name);
        userData.setLocation(userLocation);
        
        //userData.setPassword(user.getPassword());
        //userData.setPhone(user.getPhone());
        userData.setDateUpdated(LocalDateTime.now());
        User updatedUser = userRepository.save(userData);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }
    
    @PutMapping(value="/users/activateUser")
    public ResponseEntity<User> activateUser(@Valid @RequestBody String userInfo) throws JsonProcessingException, IOException {
    	ObjectMapper mapper = new ObjectMapper();
		JsonNode node = mapper.readTree(userInfo);
		String email = mapper.convertValue(node.get("email"), String.class);
		String token = mapper.convertValue(node.get("token"), String.class);
		
        User userData = userRepository.findByEmail(email);
        if(userData == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (!userData.getToken().equals(token))	return new ResponseEntity<>(HttpStatus.CONFLICT);
        userData.setActive(true);
        User updatedUser = userRepository.save(userData);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }
    
    @PutMapping(value="/users/updateLocation/{id}")
    public ResponseEntity<User> updateUserLocation(@PathVariable("id") String id,
                                           @Valid @RequestBody Location newLocation) {
        User userData = userRepository.findOne(id);
        if(userData == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        userData.setLocation(newLocation);
        userData.setDateUpdated(LocalDateTime.now());
        
        User updatedUser = userRepository.save(userData);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping(value="/users/{id}")
    public void deleteUser(@PathVariable("id") String id) {
    	User userData = userRepository.findOne(id);
        if(userData == null) {
            return;
        }
        userData.setDeleted(true);
        userData.setDateDeleted(LocalDateTime.now());
        userRepository.delete(id);
    }
}
