package com.example.frontdoor.backend;
import com.example.frontdoor.backend.controller.DocsController;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;





// Doc models tests

/*
 * Test the constructor

Test that the constructor sets the fields correctly
Test that if any of the parameters passed to the constructor is null or empty, it throws an exception

* Test the getter and setter methods
Test that the getter methods return the correct values
Test that the setter methods set the fields correctly
Test that if any of the parameters passed to the setter methods is null or empty, it throws an exception

* Test the validation annotations
Test that if any of the fields annotated with @NotBlank is null or empty, it throws an exception
Test that if the rating field is set to a value less than 1 or greater than 5, it throws an exception

* Test the toString() method
Test that the toString() method returns a string that contains all the field names and their values

* Test the equals() and hashCode() methods
Test that the equals() method returns true when comparing two docsModel objects that have the same field values
Test that the hashCode() method returns the same value for two docsModel objects that have the same field values

* Test the interaction with the database
If the docsModel class is interacting with a database or persistence layer, then you should also write tests to ensure that the data is stored and retrieved correctly from the database.
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class ControllerTest {

    @Autowired 
    private DocsController controller;
        
    

    @Test
    public void contextLoads() throws Exception{
        assertThat(controller).isNotNull();

    }
}
