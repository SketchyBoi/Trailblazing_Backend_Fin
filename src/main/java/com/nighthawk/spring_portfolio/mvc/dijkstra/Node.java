package com.nighthawk.spring_portfolio.mvc.dijkstra;

import java.util.HashMap;
import java.util.ArrayList;

public class Node {
    private int VerticeIndex;
    private HashMap<Integer, Integer> Edges;

    Node (int ProvidedIndexFromFrontend) {
        this.VerticeIndex = ProvidedIndexFromFrontend; 
        this.Edges = new HashMap<Integer, Integer>();
    }

    Node (int ProvidedIndexFromFrontend, HashMap<Integer, Integer> ProvidedEdgesFromFrontend) {
        this.VerticeIndex = ProvidedIndexFromFrontend; 
        this.Edges = ProvidedEdgesFromFrontend;
    }

    public int getIndex() {
        return this.VerticeIndex;
    }

    public HashMap<Integer, Integer> getEdges() {
        return this.Edges;
    }

    public void setIndex(int ProvidedIndex) {
        this.VerticeIndex = ProvidedIndex;
    }

    public void addEdgeToVertex(int ProvidedDestinationIndex, int ProvidedEdgeWeight) {
        this.Edges.put(ProvidedDestinationIndex, ProvidedEdgeWeight);
    }

    public void removeEdgeFromVertex(int ProvidedDestinationIndex) {
        this.Edges.remove(ProvidedDestinationIndex);
    }

    public int getDistanceBetweenTwoVertices(Node ProvidedDestinationVerticeNode) {
        return this.Edges.get(ProvidedDestinationVerticeNode.getIndex());
    }

    public ArrayList<Integer> getNeighborsOfVertice() {
        ArrayList<Integer> ListOfEdges = new ArrayList<Integer>();
        for ( int VerticeKey : this.Edges.keySet()) {
            ListOfEdges.add(VerticeKey);
        }
        return ListOfEdges;
    }
}
