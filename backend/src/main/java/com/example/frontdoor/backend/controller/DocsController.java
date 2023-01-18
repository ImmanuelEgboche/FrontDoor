package com.example.frontdoor.backend.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
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

import com.example.frontdoor.backend.model.docsModel;
import com.example.frontdoor.backend.repo.docsRepo;

@CrossOrigin(origins = "http://localhost:8081")
// @CrossOrigin is for configuring allowed origins.
@RestController
//@RestController annotation is used to define a controller and to indicate 
// that the return value of the methods should be be bound to the web response body.
@RequestMapping("/")
// declares that all Apis url in the controller will start with /api.

public class DocsController {
    // We use @Autowired to inject DocsRepo bean to local variable.
    @Autowired
    docsRepo docsrepo;


    // returns all doctor information
    // returns name querys only
    @GetMapping("/docsApi") // rejig so /docs api is only a get all
    public ResponseEntity<List<docsModel>> getAll(@RequestParam(required = false) String name){
        try{
            List<docsModel> docsModels = new ArrayList<>();

            if(name == null){
                docsrepo.findAll().forEach(docsModels::add);
            }else{
                return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
            }
            if(docsModels.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } 
            return new ResponseEntity<>(docsModels, HttpStatus.OK);
        } catch(Exception e ){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("docsApi/{id}")
    public ResponseEntity<docsModel> getNameById(@PathVariable("id") String id) {
        Optional<docsModel> docsmodels = docsrepo.findById(id);

        if(docsmodels.isPresent()){
            return new ResponseEntity<>(docsmodels.get(),HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    // creating a new entry
    @PostMapping("docsApi")
    public ResponseEntity<docsModel> createDocName(@RequestBody docsModel docsmodel){
        try {
            docsModel _docsmodel = docsrepo.save(new docsModel(docsmodel.getName(), docsmodel.getlocation(), docsmodel.getSpeciality(), docsmodel.getRating()));
            return new ResponseEntity<>(_docsmodel, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);          }
    }

    // updating a entry 
    @PutMapping("docsApi/{id}")
    public ResponseEntity<docsModel> updateDocName(@PathVariable("id") String id, @RequestBody docsModel docsmodel) {
        Optional<docsModel> docData = docsrepo.findById(id);
        
        if(docData.isPresent()){
            docsModel _docModel = docData.get();
            _docModel.setName(docsmodel.getName());
            _docModel.setlocation(docsmodel.getlocation());
            _docModel.setspeciality(docsmodel.getSpeciality());
            _docModel.setRating(docsmodel.getRating());
            return new ResponseEntity<>(docsrepo.save(_docModel), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }

    }

    @DeleteMapping("docsApi/{id}")
    public ResponseEntity<HttpStatus> deleteDoc(@PathVariable("id")String id){
        try {
            docsrepo.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("docsApi")
    public ResponseEntity<HttpStatus> deleteAllDocs(){
        try {
            docsrepo.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // create get all for names
    @GetMapping("/docsApi/names")
    public ResponseEntity<List<docsModel>> getNames(@RequestParam(required = false) String name){
        try{
            List<docsModel> docsModels = new ArrayList<>();

            if(name == null){
                return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
            }else{
                docsrepo.findByName(name).forEach(docsModels::add);
            }
            if(docsModels.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } 
            return new ResponseEntity<>(docsModels, HttpStatus.OK);
        } catch(Exception e ){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/docsApi/location")
    public ResponseEntity<List<docsModel>> getLocation(@RequestParam(required = false) String location){
        try{
            List<docsModel> docsModels = new ArrayList<>();

            if(location == null){
                return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
            }else{
                docsrepo.findByLocation(location).forEach(docsModels::add);
            }
            if(docsModels.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } 
            return new ResponseEntity<>(docsModels, HttpStatus.OK);
        } catch(Exception e ){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/docsApi/rating")
    public ResponseEntity<List<docsModel>> getRating(@RequestParam(required = false) Integer rating){
        try{
            List<docsModel> docsModels = new ArrayList<>();

            if(rating == null){
                return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
            }else{
                docsrepo.findByRating(rating).forEach(docsModels::add);
            }
            if(docsModels.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } 
            return new ResponseEntity<>(docsModels, HttpStatus.OK);
        } catch(Exception e ){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
// speciality
@GetMapping("/docsApi/speciality")
public ResponseEntity<List<docsModel>> getSpeciality(@RequestParam(required = false) String speciality){
    try{
        List<docsModel> docsModels = new ArrayList<>();

        if(speciality == null){
            return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
        }else{
            docsrepo.findBySpeciality(speciality).forEach(docsModels::add);
        }
        if(docsModels.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } 
        return new ResponseEntity<>(docsModels, HttpStatus.OK);
    } catch(Exception e ){
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}


}

