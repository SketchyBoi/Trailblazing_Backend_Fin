package com.nighthawk.spring_portfolio.mvc.dijkstra;


import java.util.HashMap;

public class GraphRequest {
    private int source;
    private int target;
    private int[][] adjacencyList;
    private HashMap<Integer,int[]> coordinates;

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

    public HashMap<Integer,int[]> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(HashMap<Integer,int[]> coordinates) {
        this.coordinates = coordinates;
    }
}
