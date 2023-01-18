package com.example.frontdoor.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="Docs")
public class docsModel {
    @Id

    private String id;
    private String name;
    private String location;
    private String speciality;
    private Integer rating;

    public docsModel(){
        // empty because thats how it is on the tutorial
    }

    public docsModel(String name, String location, String speciality, Integer rating){
        this.name = name;
        this.location = location;
        this.speciality = speciality;
        this.rating = rating;
    }

    public String getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getlocation() {
        return location;
    }
    
    public void setlocation(String location) {
        this.location = location;
    }
    
    public String getSpeciality() {
        return speciality;
    }
    
    public void setspeciality(String speciality) {
        this.speciality = speciality;
    }

    public Integer getRating() {
        return rating;
    }
    
    public void setRating(Integer rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
    return "Doctor [id=" + id + ", name=" + name + ", loco=" + location + ", speciality=" + speciality + ", rating=" + rating + "]";
}
}
