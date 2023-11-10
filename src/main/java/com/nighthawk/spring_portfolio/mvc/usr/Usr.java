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

    @NotEmpty
    @Size(min=5)
    @Column(unique=true)
    @Email
    private String email;

    @NotEmpty
    private String password;

    @NonNull
    @Size(min = 2, max = 30, message = "Name (2 to 30 chars)")
    private String name;

    private int counter;

    @Column
    private int addition;

    // To be implemented
    @ManyToMany(fetch = EAGER)
    private Collection<UsrRole> roles = new ArrayList<>();

    @Type(type="json")
    @Column(columnDefinition = "jsonb")
    private Map<String,Map<String, Object>> stats = new HashMap<>(); 

    // Constructor used when building object from an API
    public Usr(String email, String password, String name, int counter) { //double highScore, double totalOfAllScores, int numberOfScores) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.counter = counter;
    }
    // Initialize static test data 
    public static Usr[] init() {

        Usr p1 = new Usr("hi@test.com", "123Toby!", "Thomas Edison", 0);
        Usr p2 = new Usr("test2@gmail.com", "kendallsImpossibleToGuessPassw0rd", "Kendall Reist", 0);
        Usr p3 = new Usr("drewreedyo@gmail.com", "insecure", "Drew Reed", 0);
        Usr p4 = new Usr("js@test.com", "pass", "js", 0);

        Usr usrs[] = {p1, p2, p3, p4};
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
