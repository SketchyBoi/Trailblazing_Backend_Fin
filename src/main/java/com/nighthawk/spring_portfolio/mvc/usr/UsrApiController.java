package com.nighthawk.spring_portfolio.mvc.usr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nighthawk.spring_portfolio.mvc.usr.CanvasUpdate;

import java.util.*;

@RestController
@RequestMapping("/api/usr")
public class UsrApiController {
    //     @Autowired
    // private JwtTokenUtil jwtGen;
    /*
    #### RESTful API ####
    Resource: https://spring.io/guides/gs/rest-service/
    */
    private static final Logger logger = LoggerFactory.getLogger(UsrApiController.class);

    // Autowired enables Control to connect POJO Object through JPA
    @Autowired
    private UsrJpaRepository repository;

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
                                            // @RequestParam("highScore") double highScore,
                                            // @RequestParam("totalOfAllScores") double totalOfAllScores,
                                            // @RequestParam("numerOfScores") int numberOfScores) {
        // A person object WITHOUT ID will create a new record with default roles as student
        Usr usr = new Usr(email, password, name); //highScore, totalOfAllScores, numberOfScores);
        usr.setPassword(password);
        repository.save(usr);
        return new ResponseEntity<>(email +" is created successfully", HttpStatus.CREATED);
    }

    /*
    PUT Aa record by Requesting Parameters from BODY
    */
    @PutMapping("/update")
    public ResponseEntity<Object> updateUsr(@RequestBody CanvasUpdate canvasUpdate) {
        try {
            logger.debug("Received PUT request for updating user data: {}", canvasUpdate);

            // Find the user by email
            Usr usr = repository.findByEmail(canvasUpdate.getEmail());
            logger.debug("Found user: {}", usr);

            if (usr != null) {
                // Convert the JSON string to a 2D int array and add it to the user's canvasHistory
                usr.addCanvasHistory(canvasUpdate.getHistory());

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

    /*
    The personSearch API looks across database for partial match to term (k,v) passed by RequestEntity body
     */
    @PostMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> usrSearch(@RequestBody final Map<String,String> map) {
        // extract term from RequestEntity
        String term = (String) map.get("term");

        // JPA query to filter on term
        List<Usr> list = repository.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(term, term);

        // return resulting list and status, error checking should be added
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    /*
    The personStats API adds stats by Date to Person table 
    */
    @PostMapping(value = "/setStats", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Usr> usrStats(@RequestBody final Map<String,Object> stat_map) {
        // find ID
        long id=Long.parseLong((String)stat_map.get("id"));  
        Optional<Usr> optional = repository.findById((id));
        if (optional.isPresent()) {  // Good ID
            Usr usr = optional.get();  // value from findByID

            // Extract Attributes from JSON
            Map<String, Object> attributeMap = new HashMap<>();
            for (Map.Entry<String,Object> entry : stat_map.entrySet())  {
                // Add all attribute other thaN "date" to the "attribute_map"
                if (!entry.getKey().equals("date") && !entry.getKey().equals("id"))
                    attributeMap.put(entry.getKey(), entry.getValue());
            }

            // Set Date and Attributes to SQL HashMap
            Map<String, Map<String, Object>> date_map = new HashMap<>();
            date_map.put( (String) stat_map.get("date"), attributeMap );
            usr.setStats(date_map);  // BUG, needs to be customized to replace if existing or append if new
            repository.save(usr);  // conclude by writing the stats updates

            // return Person with update Stats
            return new ResponseEntity<>(usr, HttpStatus.OK);
        }
        // return Bad ID
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST); 
    }
}