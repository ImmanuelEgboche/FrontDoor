package com.example.frontdoor.backend.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.example.frontdoor.backend.model.docsModel;
import com.example.frontdoor.backend.repo.docsRepo;

@CrossOrigin(origins = "http://localhost:8081")
// @CrossOrigin is for configuring allowed origins.
@RestController
// @RestController annotation is used to define a controller and to indicate
// that the return value of the methods should be be bound to the web response
// body.
@RequestMapping("/docs")
// declares that all Apis url in the controller will start with /api.

/*
 * Implement validation of request payloads
 * It is always a good idea to validate incoming request payloads before
 * processing them.
 * In this case, you can use the Bean Validation API to validate the incoming
 * DocsModel object before saving it to the database.
 * This can help prevent invalid data from being stored in the database.
 */

public class DocsController {
    // We use @Autowired to inject DocsRepo bean to local variable.
    private final docsRepo docsRepo;

    public DocsController(docsRepo docsRepo) {
        this.docsRepo = docsRepo;
    }

    // returns all doctor information
    @GetMapping
    public ResponseEntity<List<docsModel>> getAllDocs() {
        List<docsModel> docsModels = docsRepo.findAll();
        if (docsModels.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(docsModels, HttpStatus.OK);
    }

    // return doctor by id
    @GetMapping("/{id}")
    public ResponseEntity<docsModel> getDocById(@PathVariable String id) {
        return docsRepo.findById(id)
                .map(doc -> new ResponseEntity<>(doc, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // creating a new entry
    @PostMapping
    public ResponseEntity<docsModel> create(@RequestBody docsModel docsModel) {
        try {
            docsModel savedDocsModel = docsRepo.save(docsModel);
            return new ResponseEntity<>(savedDocsModel, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // updating a entry
    @PutMapping("/{id}")
    public ResponseEntity<docsModel> updateDocDetails(@PathVariable("id") String id,
            @Valid @RequestBody docsModel docsModel) {
        Optional<docsModel> docData = docsRepo.findById(id);

        if (!docData.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        docsModel _docModel = docData.get();
        _docModel.setName(docsModel.getName());
        _docModel.setLocation(docsModel.getLocation());
        _docModel.setSpeciality(docsModel.getSpeciality());
        _docModel.setRating(docsModel.getRating());

        try {
            docsModel updatedDoc = docsRepo.save(_docModel);
            return new ResponseEntity<>(updatedDoc, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
public ResponseEntity<DocResponse> deleteDoc(@PathVariable("id") String id) {
    try {
        docsRepo.deleteById(id);
        DocResponse response = new DocResponse("Document with ID " + id + " has been deleted");
        return new ResponseEntity<>(response, HttpStatus.OK);
    } catch (EmptyResultDataAccessException e) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    } catch (Exception e) {
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

class DocResponse {
    private String message;

    public DocResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}



    // /docs/search?name=<name>&location=<location>&speciality=<speciality>&rating=<rating>
/**
 * Retrieves a list of docsModel objects from the MongoDB database based on the specified search criteria.
 * 
 * @param name (optional) the name of the doctor to search for
 * @param location (optional) the location of the doctor to search for
 * @param rating (optional) the minimum rating of the doctor to search for
 * @param speciality (optional) the speciality of the doctor to search for
 * @return a ResponseEntity object containing a list of docsModel objects if successful, or a HttpStatus code if there was an error
 */
    @Autowired
    private MongoTemplate mongoTemplate;

    @GetMapping("/docsApi")
public ResponseEntity<List<docsModel>> searchDocs(@RequestParam(required = false) String name,
                                                  @RequestParam(required = false) String location,
                                                  @RequestParam(required = false) String rating,
                                                  @RequestParam(required = false) String speciality) {
                                                    try {
            Criteria criteria = new Criteria();
        // Add search criteria to the query based on the provided parameters
            if (name != null) {
                criteria.and("name").is(name);
            }
            if (location != null) {
                criteria.and("location").is(location);
            }
            if (rating != null) {
                try {
                    int ratingValue = Integer.parseInt(rating);
                    criteria.and("rating").is(ratingValue);
                } catch (NumberFormatException e) {
                    // Return a 400 Bad Request status if the rating parameter is not a valid integer
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
            }
            if (speciality != null) {
                criteria.and("speciality").is(speciality);
            }

            Query query = new Query(criteria);
            // Retrieve docsModel objects from the database based on the query
            List<docsModel> result = mongoTemplate.find(query, docsModel.class);

            if (result.isEmpty()) {
                // Return a 204 No Content status if the result is empty
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            // Return a 200 OK status with the list of docsModel objects
            return new ResponseEntity<>(result, HttpStatus.OK);

        } catch (Exception e) {
                    // Return a 500 Internal Server Error status if there was an unexpected error
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
}}


