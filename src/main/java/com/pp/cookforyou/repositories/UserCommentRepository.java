package com.pp.cookforyou.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.pp.cookforyou.models.UserComment;

@Repository
public interface UserCommentRepository extends MongoRepository<UserComment, String> {

}
