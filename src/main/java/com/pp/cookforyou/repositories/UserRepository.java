package com.pp.cookforyou.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pp.cookforyou.models.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
	
	@Query("{email: ?0}")
	User findByEmail(@Param("email") String email);
	
}
