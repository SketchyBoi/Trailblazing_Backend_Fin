package com.nighthawk.spring_portfolio.mvc.usr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nighthawk.spring_portfolio.mvc.usr.CounterUpdate;

import java.util.*;

@RestController
// @CrossOrigin(origins = "https://csa-tri-1.github.io")
@RequestMapping("/api/usr")
public class UsrApiController {
    @Autowired UsrDetailsService usrService;
    private static final Logger logger = LoggerFactory.getLogger(UsrApiController.class);

    @Autowired
    private UsrJpaRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /*
    GET List of People
     */
    @GetMapping("/")
    public ResponseEntity<List<Usr>> getPeople() {
        return new ResponseEntity<>(repository.findAllByOrderByNameAsc(), HttpStatus.OK);
    }

    /*
    GET individual Person using ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Usr> getUsr(@PathVariable long id) {
        Optional<Usr> optional = repository.findById(id);
        if (optional.isPresent()) {  // Good ID
            Usr usr = optional.get();  // value from findByID
            return new ResponseEntity<>(usr, HttpStatus.OK);  // OK HTTP response: status code, headers, and body
        }
        // Bad ID
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);       
    }

    /*
    DELETE individual Person using ID
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Usr> deleteUsr(@PathVariable long id) {
        Optional<Usr> optional = repository.findById(id);
        if (optional.isPresent()) {  // Good ID
            Usr usr = optional.get();  // value from findByID
            repository.deleteById(id);  // value from findByID
            return new ResponseEntity<>(usr, HttpStatus.OK);  // OK HTTP response: status code, headers, and body
        }
        // Bad ID
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST); 
    }

    /*
    POST Aa record by Requesting Parameters from URL
     */
    @PostMapping( "/post")
    public ResponseEntity<Object> postUsr(@RequestParam("email") String email,
                                            @RequestParam("password") String password,
                                            @RequestParam("name") String name) {
        // A person object WITHOUT ID will create a new record with default roles as student
        Usr usr = new Usr(email, password, name, 0); //highScore, totalOfAllScores, numberOfScores);
        usr.setPassword(passwordEncoder.encode(usr.getPassword())); // PASSWORD ENCRYPTION
        repository.save(usr);
        return new ResponseEntity<>(email +" is created successfully", HttpStatus.CREATED);
    }

    /*
    PUT Aa record by Requesting Parameters from BODY
    */
    @PutMapping("/update")
    public ResponseEntity<Object> updateUsr(@ModelAttribute CounterUpdate counterUpdate, @RequestParam("addition") int addition) {
        try {
            logger.debug("Received PUT request for updating user data: {}", counterUpdate);

            // Find the user by email
            Usr usr = repository.findByEmail(counterUpdate.getEmail());
            logger.debug("Found user: {}", usr);

            if (usr != null) {
                usr.setCounter(usr.getCounter() + addition);
                // Save the updated user
                repository.save(usr);

                return new ResponseEntity<>("User updated successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Failed to update user", e);
            return new ResponseEntity<>("Failed to update user", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}