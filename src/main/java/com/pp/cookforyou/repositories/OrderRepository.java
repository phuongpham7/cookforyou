package com.pp.cookforyou.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.pp.cookforyou.models.Order;

@Repository
public interface OrderRepository extends MongoRepository<Order, String> {

}
