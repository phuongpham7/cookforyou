package com.pp.cookforyou.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.pp.cookforyou.models.Location;

@Repository
public interface LocationRepository extends MongoRepository<Location, String> {
	
	@Query("{name: ?0 }")
	Location findLocationByName(String name);
}
