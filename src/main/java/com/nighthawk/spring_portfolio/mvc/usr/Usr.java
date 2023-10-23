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

    private ArrayList<int[][]> canvasHistory;

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
        this.canvasHistory = new ArrayList<int[][]>();
        // this.highScore = highScore;
        // this.totalOfAllScores = totalOfAllScores;
        // this.numberOfScores = numberOfScores;
    }

    public void addCanvasHistory(int[][] newCanvasHistory) {
        this.canvasHistory.add(newCanvasHistory);
    }

    public void addCanvasHistoryThruJSON(String newCanvasHistoryJSON) {
        try {
            this.addCanvasHistory(convertJsonToData(newCanvasHistoryJSON));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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
        int[][] data = {
            {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11},
            {1, 0, 3, 6, 0, 0, 0, 0, 0, 0, 50, 0},
            {2, 3, 0, 2, 6, 0, 0, 0, 0, 0, 40, 0},
            {3, 6, 2, 0, 7, 8, 0, 0, 0, 0, 0, 0},
            {4, 0, 6, 7, 0, 1, 8, 0, 0, 0, 0, 0},
            {5, 0, 0, 8, 1, 0, 4, 10, 0, 0, 0, 0},
            {6, 0, 0, 0, 8, 4, 0, 6, 10, 0, 0, 0},
            {7, 0, 0, 0, 0, 10, 6, 0, 8, 2, 0, 0},
            {8, 0, 0, 0, 0, 0, 10, 8, 0, 9, 20000, 0},
            {9, 0, 0, 0, 0, 0, 0, 2, 9, 0, 1000, 0},
            {10, 50, 40, 0, 0, 0, 0, 0, 20000, 1000, 0, 0},
            {11, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        };  
        p3.addCanvasHistory(data);
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
