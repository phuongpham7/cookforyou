package com.pp.cookforyou.controllers;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class PhotoController {
	
	@SuppressWarnings("unchecked")
	@PostMapping("/uploadPhoto/{photoName}")
    public ResponseEntity<Map<Object, Object>> uploadPhoto(@Valid @RequestBody String photoInfo) throws JsonProcessingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode node = mapper.readTree(photoInfo);
		String url = mapper.convertValue(node.get("url"), String.class);
		
		Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
				  "cloud_name", "dseieldd7",
				  "api_key", "163983422959623",
				  "api_secret", "CcGFfCDxLAImaVi1usXhEN1SxA0"));
		File toUpload = new File(url);
		Map<Object, Object> uploadResult = cloudinary.uploader().upload(toUpload, ObjectUtils.emptyMap());
		return new ResponseEntity<>(uploadResult, HttpStatus.OK);
	}
}
