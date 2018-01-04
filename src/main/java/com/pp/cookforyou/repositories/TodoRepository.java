package com.pp.cookforyou.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.pp.cookforyou.models.Todo;

@Repository
public interface TodoRepository extends MongoRepository<Todo, String> {
	
}
