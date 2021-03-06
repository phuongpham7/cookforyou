package com.pp.cookforyou.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.pp.cookforyou.models.DishComment;

@Repository
public interface DishCommentRepository extends MongoRepository<DishComment, String> {

}
