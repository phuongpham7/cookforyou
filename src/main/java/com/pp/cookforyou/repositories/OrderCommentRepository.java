package com.pp.cookforyou.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.pp.cookforyou.models.OrderComment;

@Repository
public interface OrderCommentRepository extends MongoRepository<OrderComment, String> {

}
