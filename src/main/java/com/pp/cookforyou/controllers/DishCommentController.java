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

import com.pp.cookforyou.models.DishComment;
import com.pp.cookforyou.repositories.DishCommentRepository;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class DishCommentController {
	
	@Autowired
    DishCommentRepository dishCommentRepository;

    @GetMapping("/dishComments")
    public List<DishComment> getAllDishComments() {
        Sort sortByDateCreatedAtDesc = new Sort(Sort.Direction.DESC, "dateCreated");
        return dishCommentRepository.findAll(sortByDateCreatedAtDesc);
    }

    @PostMapping("/dishComments")
    public DishComment createDishComment(@Valid @RequestBody DishComment dishComment) {
    	dishComment.setDateCreated(LocalDateTime.now());
        return dishCommentRepository.save(dishComment);
    }

    @GetMapping(value="/dishComments/{id}")
    public ResponseEntity<DishComment> getDishCommentById(@PathVariable("id") String id) {
    	DishComment dishComment = dishCommentRepository.findOne(id);
        if(dishComment == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(dishComment, HttpStatus.OK);
        }
    }

    @PutMapping(value="/dishComments/{id}")
    public ResponseEntity<DishComment> updateDishComment(@PathVariable("id") String id,
                                           @Valid @RequestBody DishComment dishComment) {
        DishComment dishCommentData = dishCommentRepository.findOne(id);
        if(dishCommentData == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        dishCommentData.setComment(dishComment.getComment());
        dishCommentData.setDish(dishComment.getDish());
        dishCommentData.setByUser(dishComment.getByUser());
        DishComment updatedDishComment = dishCommentRepository.save(dishCommentData);
        return new ResponseEntity<>(updatedDishComment, HttpStatus.OK);
    }

    @DeleteMapping(value="/dishComments/{id}")
    public void deleteDishComment(@PathVariable("id") String id) {
    	DishComment dishCommentData = dishCommentRepository.findOne(id);
        if(dishCommentData == null) {
            return;
        }
        dishCommentData.setDeleted(true);
        dishCommentData.setDateDeleted(LocalDateTime.now());
        dishCommentRepository.delete(id);
    }

}
