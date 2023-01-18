package com.example.frontdoor.backend.repo;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.frontdoor.backend.model.docsModel;



public interface docsRepo extends MongoRepository<docsModel, String>{
    List<docsModel> findByLocation(String location);
    List<docsModel> findByName(String name);
    List<docsModel> findByRating(Integer rating);
    List<docsModel> findBySpeciality(String speciality);

}
