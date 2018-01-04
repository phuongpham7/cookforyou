package com.pp.cookforyou.controllers;

import java.time.LocalDateTime;
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

import com.pp.cookforyou.models.UserComment;
import com.pp.cookforyou.repositories.UserCommentRepository;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class UserCommentController {
	
	@Autowired
    UserCommentRepository userCommentRepository;

    @GetMapping("/userComments")
    public List<UserComment> getAllUserComments() {
        Sort sortByDateCreatedAtDesc = new Sort(Sort.Direction.DESC, "dateCreated");
        return userCommentRepository.findAll(sortByDateCreatedAtDesc);
    }

    @PostMapping("/userComments")
    public UserComment createUserComment(@Valid @RequestBody UserComment userComment) {
    	userComment.setDateCreated(LocalDateTime.now());
        return userCommentRepository.save(userComment);
    }

    @GetMapping(value="/userComments/{id}")
    public ResponseEntity<UserComment> getUserCommentById(@PathVariable("id") String id) {
        UserComment userComment = userCommentRepository.findOne(id);
        if(userComment == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(userComment, HttpStatus.OK);
        }
    }

    @PutMapping(value="/userComments/{id}")
    public ResponseEntity<UserComment> updateUserComment(@PathVariable("id") String id,
                                           @Valid @RequestBody UserComment userComment) {
        UserComment userCommentData = userCommentRepository.findOne(id);
        if(userCommentData == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        userCommentData.setComment(userComment.getComment());
        userCommentData.setUser(userComment.getUser());
        userCommentData.setByUser(userComment.getByUser());
        UserComment updatedUserComment = userCommentRepository.save(userCommentData);
        return new ResponseEntity<>(updatedUserComment, HttpStatus.OK);
    }

    @DeleteMapping(value="/userComments/{id}")
    public void deleteUserComment(@PathVariable("id") String id) {
    	UserComment userCommentData = userCommentRepository.findOne(id);
        if(userCommentData == null) {
            return;
        }
        userCommentData.setDeleted(true);
        userCommentData.setDateDeleted(LocalDateTime.now());
        userCommentRepository.delete(id);
    }

}
