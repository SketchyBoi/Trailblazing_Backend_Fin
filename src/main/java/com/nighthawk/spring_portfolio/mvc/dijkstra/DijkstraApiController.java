package com.nighthawk.spring_portfolio.mvc.dijkstra;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Date;
import java.util.HashMap;


import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.nighthawk.spring_portfolio.mvc.usr.Usr;
import com.nighthawk.spring_portfolio.mvc.usr.UsrJpaRepository;

public class graphRequest {
    private int source;
    private int target;
    private int[][] adjacencyList;
    private Hashmap<Integer,int[]> coordinates;

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public int getTarget() {
        return target;
    }

    public void setTarget(int target) {
        this.target = target;
    }

    public int[][] getAdjacencyList() {
        return adjacencyList;
    }

    public void setAdjacencyList(int[][] adjacencyList) {
        this.adjacencyList = adjacencyList;
    }

    public int[][] getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Hashmap<Integer,int[]> coordinates) {
        this.coordinates = coordinates;
    }
}

@RestController
@RequestMapping("/api/dijkstra") // Sets the path to our dijkstra algorithm
public class DijkstraApiController {
    private JSONObject body; //last run result
    private HttpStatus status; //last run status
    String last_run = null; //last run day of month

    @Autowired
    private WeightedGraph userInputedGraph;

    @PostMapping("/")
    @ResponseBody
    public ResponseEntity<Object> findShortestPath(@RequestBody graphRequest request) {
        int[][] adjacencyList = request.getAdjacencyList();
        Hashmap<Integer,int[]> = request.getCoordinates();
        WeightedGraph weightedGraph = new WeightedGraph();
        weightedGraph.setGraphFromAdjacencyList(adjacencyList);

        Usr usr = new Usr(email, password, name); //highScore, totalOfAllScores, numberOfScores);
        repository.save(usr);
        return new ResponseEntity<>(email +" is created successfully", HttpStatus.CREATED);
    }
}
