package com.pp.cookforyou.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.pp.cookforyou.models.Rating;

@Repository
public interface RatingRepository extends MongoRepository<Rating, String> {

}
