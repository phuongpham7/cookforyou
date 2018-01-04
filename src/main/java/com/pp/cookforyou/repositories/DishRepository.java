package com.pp.cookforyou.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.pp.cookforyou.models.Dish;

@Repository
public interface DishRepository extends MongoRepository<Dish, String> {

}
