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

import com.pp.cookforyou.models.Order;
import com.pp.cookforyou.models.OrderStatus;
import com.pp.cookforyou.repositories.OrderRepository;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class OrderController {

	@Autowired
	OrderRepository orderRepository;
	
	@GetMapping("/orders")
    public List<Order> getAllOrders() {
        Sort sortByCreatedDateAtDesc = new Sort(Sort.Direction.DESC, "DateCreated");
        return orderRepository.findAll(sortByCreatedDateAtDesc);
    }

    @PostMapping("/orders")
    public Order createOrder(@Valid @RequestBody Order order) {
    	order.setStatus(OrderStatus.PLACED);
    	order.setDateCreated(LocalDateTime.now());
        return orderRepository.save(order);
    }

    @GetMapping(value="/orders/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable("id") String id) {
        Order order = orderRepository.findOne(id);
        if(order == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(order, HttpStatus.OK);
        }
    }

    @PutMapping(value="/orders/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable("id") String id,
                                           @Valid @RequestBody Order order) {
        Order orderData = orderRepository.findOne(id);
        if(orderData == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        orderData.setDish(order.getDish());
        orderData.setQuantity(order.getQuantity());
        orderData.setAddress(order.getAddress());
        orderData.setDeliveryPickupDate(order.getDeliveryPickupDate());
        orderData.setStatus(order.getStatus());
        orderData.setDateUpdated(LocalDateTime.now());
        Order updatedOrder = orderRepository.save(orderData);
        return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
    }

    @DeleteMapping(value="/orders/{id}")
    public void deleteOrder(@PathVariable("id") String id) {
    	Order orderData = orderRepository.findOne(id);
        if(orderData == null) {
            return;
        }
        orderData.setDeleted(true);
        orderData.setDateDeleted(LocalDateTime.now());
        orderRepository.delete(id);
    }	
}
