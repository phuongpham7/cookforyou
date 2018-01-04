package com.pp.cookforyou.controllers;

import java.io.IOException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pp.cookforyou.models.User;
import com.pp.cookforyou.repositories.UserRepository;
import com.pp.cookforyou.utils.PasswordEncryptor;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class AuthenticationController {
	
	@Autowired
	UserRepository userRepository;
	
	@PostMapping("/authenticateUser")
    public ResponseEntity<User> authenticateUser(@Valid @RequestBody String credential) throws JsonProcessingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode node = mapper.readTree(credential);
		String email = mapper.convertValue(node.get("email"), String.class);
		String password = mapper.convertValue(node.get("password"), String.class);
		
		User user = userRepository.findByEmail(email);
		if(user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
        	if (!user.getPassword().equals(PasswordEncryptor.encryptPassword(password)))
        		return new ResponseEntity<>(user, HttpStatus.FORBIDDEN);//Wrong password
        	if (!user.isActive())
        		return new ResponseEntity<>(user, HttpStatus.LOCKED);//User is not active
        }
		return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
