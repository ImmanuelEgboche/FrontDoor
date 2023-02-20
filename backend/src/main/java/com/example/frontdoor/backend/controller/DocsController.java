package com.example.frontdoor.backend.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

/* Implement validation of request payloads
It is always a good idea to validate incoming request payloads before processing them. 
In this case, you can use the Bean Validation API to validate the incoming DocsModel object before saving it to the database. 
This can help prevent invalid data from being stored in the database.*/

public class DocsController {
    // We use @Autowired to inject DocsRepo bean to local variable.
    @Autowired
    docsRepo docsrepo;


    // returns all doctor information
    // returns name querys only
    @GetMapping("/docsApi") // Reorganize the routes to make them more organized and follow RESTful conventions.
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
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);          } // instead of returning 500 errors return 400 or 422
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

    // /docs/search?name=<name>&location=<location>&speciality=<speciality>&rating=<rating> for search queries.

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

// // @RestController
// @RequestMapping("/docs")
// public class DocsController {
//     private final DocsRepo docsRepo;

//     public DocsController(DocsRepo docsRepo) {
//         this.docsRepo = docsRepo;
//     }

//     @GetMapping
//     public ResponseEntity<List<DocsModel>> getAllDocs() {
//         List<DocsModel> docsModels = docsRepo.findAll();
//         if (docsModels.isEmpty()) {
//             return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//         }
//         return new ResponseEntity<>(docsModels, HttpStatus.OK);
//     }

//     @GetMapping("/{id}")
//     public ResponseEntity<DocsModel> getDocById(@PathVariable String id) {
//         return docsRepo.findById(id)
//                 .map(doc -> new ResponseEntity<>(doc, HttpStatus.OK))
//                 .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
//     }

//     @PostMapping
//     public ResponseEntity<DocsModel> createDoc(@RequestBody DocsModel docsModel) {
//         try {
//             DocsModel createdDoc = docsRepo.save(docsModel);
//             return new ResponseEntity<>(createdDoc, HttpStatus.CREATED);
//         } catch (Exception e) {
//             return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//         }
//     }

//     @PutMapping("/{id}")
//     public ResponseEntity<DocsModel> updateDoc(@PathVariable String id, @RequestBody DocsModel docsModel) {
//         return docsRepo.findById(id)
//                 .map(doc -> {
//                     doc.setName(docsModel.getName());
//                     doc.setLocation(docsModel.getLocation());
//                     doc.setSpeciality(docsModel.getSpeciality());
//                     doc.setRating(docsModel.getRating());
//                     return new ResponseEntity<>(docsRepo.save(doc), HttpStatus.OK);
//                 })
//                 .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
//     }

//     @DeleteMapping("/{id}")
//     public ResponseEntity<HttpStatus> deleteDoc(@PathVariable String id) {
//         try {
//             docsRepo.deleteById(id);
//             return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//         } catch (Exception e) {
//             return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//         }
//     }

//     @GetMapping("/names")
//     public ResponseEntity<List<DocsModel>> getDocsByName(@RequestParam(required = false) String name) {
//         List<DocsModel> docsModels = name != null ? docsRepo.findByName(name) : new ArrayList<>();
//         if (docsModels.isEmpty()) {
//             return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//         }
//         return new ResponseEntity<>(docsModels, HttpStatus.OK);
//     }

//     @GetMapping("/locations")
//     public ResponseEntity<List<DocsModel>> getDocsByLocation(@RequestParam(required = false) String location) {
//         List<DocsModel> docsModels = location != null ? docsRepo.findByLocation(location) : new ArrayList<>();
//         if (docsModels.isEmpty()) {
//             return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//         }
//         return new ResponseEntity<>(docsModels, HttpStatus.OK);
//     }

//     @GetMapping("/ratings")
//     public ResponseEntity<List<DocsModel>> getDocsByRating(@RequestParam(required = false) Integer rating) {
//         List<DocsModel> docsModels = rating != null ? docsRepo.findByRating(rating) : new ArrayList<>();
//         if (docsModels.isEmpty()) {
//             return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//         }
//         return new ResponseEntity<>(docsModels, HttpStatus.OK);
//     }

//     @GetMapping("/specialities")
//     public ResponseEntity<List<DocsModel>> getDocsBySpeciality(@RequestParam(required = false) String speciality) {
//         List<DocsModel> docsModels = speciality != null ? docsRepo.findBySpeciality(speciality) : new ArrayList<>();
//         if (docsModels.isEmpty()) {
//             return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//         }
//         return new ResponseEntity<>(docsModels, HttpStatus.OK);
//     }
// }
