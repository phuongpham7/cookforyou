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

import com.pp.cookforyou.models.OrderComment;
import com.pp.cookforyou.repositories.OrderCommentRepository;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class OrderCommentController {
	
	@Autowired
    OrderCommentRepository orderCommentRepository;

    @GetMapping("/orderComments")
    public List<OrderComment> getAllOrderComments() {
        Sort sortByDateCreatedAtDesc = new Sort(Sort.Direction.DESC, "dateCreated");
        return orderCommentRepository.findAll(sortByDateCreatedAtDesc);
    }

    @PostMapping("/orderComments")
    public OrderComment createOrderComment(@Valid @RequestBody OrderComment orderComment) {
    	orderComment.setDateCreated(LocalDateTime.now());
        return orderCommentRepository.save(orderComment);
    }

    @GetMapping(value="/orderComments/{id}")
    public ResponseEntity<OrderComment> getOrderCommentById(@PathVariable("id") String id) {
    	OrderComment orderComment = orderCommentRepository.findOne(id);
        if(orderComment == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(orderComment, HttpStatus.OK);
        }
    }

    @PutMapping(value="/orderComments/{id}")
    public ResponseEntity<OrderComment> updateOrderComment(@PathVariable("id") String id,
                                           @Valid @RequestBody OrderComment orderComment) {
    	OrderComment orderCommentData = orderCommentRepository.findOne(id);
        if(orderCommentData == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        orderCommentData.setComment(orderComment.getComment());
        orderCommentData.setOrder(orderComment.getOrder());
        orderCommentData.setByUser(orderComment.getByUser());
        OrderComment updatedOrderComment = orderCommentRepository.save(orderCommentData);
        return new ResponseEntity<>(updatedOrderComment, HttpStatus.OK);
    }

    @DeleteMapping(value="/orderComments/{id}")
    public void deleteOrderComment(@PathVariable("id") String id) {
    	OrderComment orderCommentData = orderCommentRepository.findOne(id);
        if(orderCommentData == null) {
            return;
        }
        orderCommentData.setDeleted(true);
        orderCommentData.setDateDeleted(LocalDateTime.now());
        orderCommentRepository.delete(id);
    }

}
