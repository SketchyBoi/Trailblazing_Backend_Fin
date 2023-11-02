package com.nighthawk.spring_portfolio.mvc.usr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Collection;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import static javax.persistence.FetchType.EAGER;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import com.vladmihalcea.hibernate.type.json.JsonType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/*
Usr is a POJO, Plain Old Java Object.
First set of annotations add functionality to POJO
--- @Setter @Getter @ToString @NoArgsConstructor @RequiredArgsConstructor
The last annotation connect to database
--- @Entity
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@TypeDef(name="json", typeClass = JsonType.class)
public class Usr {

    // automatic unique identifier for Usr record
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // email, password, roles are key attributes to login and authentication
    @NotEmpty
    @Size(min=5)
    @Column(unique=true)
    @Email
    private String email;

    @NotEmpty
    private String password;

    // @NonNull, etc placed in params of constructor: "@NonNull @Size(min = 2, max = 30, message = "Name (2 to 30 chars)") String name"
    @NonNull
    @Size(min = 2, max = 30, message = "Name (2 to 30 chars)")
    private String name;

    private ArrayList<HashMap<String, Object>> canvasHistory;

    // private double highScore;

    // private double totalOfAllScores;

    // private int numberOfScores;

    // To be implemented
    @ManyToMany(fetch = EAGER)
    private Collection<UsrRole> roles = new ArrayList<>();

    /* HashMap is used to store JSON for daily "stats"
    "stats": {
        "2022-11-13": {
            "calories": 2200,
            "steps": 8000
        }
    }
    */
    @Type(type="json")
    @Column(columnDefinition = "jsonb")
    private Map<String,Map<String, Object>> stats = new HashMap<>(); 

    // Constructor used when building object from an API
    public Usr(String email, String password, String name) { //double highScore, double totalOfAllScores, int numberOfScores) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.canvasHistory = new ArrayList<HashMap<String, Object>>();
        // this.highScore = highScore;
        // this.totalOfAllScores = totalOfAllScores;
        // this.numberOfScores = numberOfScores;
    }

    public void addCanvasHistory(HashMap<String, Object> newCanvasHistory) {
        this.canvasHistory.add(newCanvasHistory);
    }

    /* IF NEEDED FOR JSON CONVERSION, CURRENTLY OBSOLETE
    public void addCanvasHistoryThruJSON(String newCanvasHistoryJSON) {
        try {
            this.addCanvasHistory(convertJsonToData(newCanvasHistoryJSON));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    */

    // a custom getter to return the average score of a usr
    // public double getAverageScore() {
    //     // check if the usr has played at all and that score total is valid compared to number of games
    //     if ((this.numberOfScores != 0) && ((this.numberOfScores * 100) > this.totalOfAllScores)) {
    //         return this.totalOfAllScores / this.numberOfScores;
    //     }
    //     return 0.0;
    // }

    public static int[][] convertJsonToData(String jsonData) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(jsonData, int[][].class);
    }

    // Initialize static test data 
    public static Usr[] init() {

        // basics of class construction
        Usr p1 = new Usr("toby@gmail.com", "123Toby!", "Thomas Edison");
        // p1.setHighScore(88.3);
        // p1.setTotalOfAllScores(238.2);
        // p1.setNumberOfScores(3);

        // basics of class construction
        Usr p2 = new Usr("kendallreistemail@icloud.com", "kendallsImpossibleToGuessPassw0rd", "Kendall Reist");
        // p2.setHighScore(94.1);
        // p2.setTotalOfAllScores(1220.9);
        // p2.setNumberOfScores(14);

        Usr p3 = new Usr("drewreedyo@gmail.com", "insecure", "Drew Reed");
        // p3.setName("Drew Reed");
        // p3.setEmail("drewreedyo@gmail.com");
        // p3.setPassword("notMyActualPassw0rd");
        Integer[][] adj = {
            {10000, 3, 3, 10000, 3},
            {3, 10000, 3, 4},
            {3, 3, 10000, 10000, 5},
            {10000, 4, 10000, 10000},
            {3, 10000, 5, 10000, 10000}
        };
        Integer[][] coords = new Integer[][] {
            new Integer[] {512, 125},
            new Integer[] {620, 197},
            new Integer[] {512, 251},
            new Integer[] {432, 207},
            new Integer[] {404, 81}
        };
        HashMap<String, Object> history = new HashMap<String, Object>();
        history.put("adjacencyList", adj);
        history.put("coords", coords);
        p3.addCanvasHistory(history);
        // p3.setHighScore(84.9);
        // p3.setTotalOfAllScores(500.0);
        // p3.setNumberOfScores(6);
        // Array definition and data initialization
        Usr usrs[] = {p1, p2, p3};
        return(usrs);
    }

    public static void main(String[] args) {
        // obtain Usr from initializer
        Usr usrs[] = init();

        // iterate using "enhanced for loop"
        for (Usr usr : usrs) {
            System.out.println(usr);  // print object
        }
    }

}
