package com.nighthawk.spring_portfolio.mvc.dijkstra;

import java.util.HashMap;
import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Node {
    private int VerticeIndex;
    private HashMap<Integer, Integer> Edges;

    Node (int ProvidedIndexFromFrontend) {
        this.VerticeIndex = ProvidedIndexFromFrontend; 
        this.Edges = new HashMap<Integer, Integer>();
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
