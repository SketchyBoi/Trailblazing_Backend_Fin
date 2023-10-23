package com.nighthawk.spring_portfolio.mvc.dijkstra;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.Stack;

public class WeightedGraph {
    private ArrayList<Node> Graph;

    WeightedGraph() {
        this.Graph = new ArrayList<Node>();
    }
    
    public ArrayList<Node> retrieveGraph(){
        return this.Graph;
    }

    private int getMaximumLabel() {
        return this.Graph.size();
    }

    private boolean isVerticeExist(int ProvidedLabel) {
        for (Node vertice : this.Graph) {
            if (vertice.getIndex() == ProvidedLabel) {
                return true;
            }
        }
        return false;
    }

    public void addVertice(Node ProvidedNewVertice) {
        for (Node vertice : this.Graph) {
            if (vertice.getIndex() == ProvidedNewVertice.getIndex()) {
                return ;
            }
        }
        this.Graph.add(ProvidedNewVertice);
    }

    public Node getVerticeFromLabel(int ProvidedLabel) {
        for (Node vertice : this.Graph) {
            if (vertice.getIndex() == ProvidedLabel) {
                return vertice;
            }
        }
        return null;
    }

    public void addEdgeToGraph(int ProvidedSourceVertice, int ProvidedDestinationVertice, int ProvidedEdgeWeight) {
        assert isVerticeExist(ProvidedSourceVertice);
        assert isVerticeExist(ProvidedDestinationVertice);
        Node SourceVerticeObj = getVerticeFromLabel(ProvidedSourceVertice);
        Node DestinationVerticeObj = getVerticeFromLabel(ProvidedDestinationVertice);
        SourceVerticeObj.addEdgeToVertex(ProvidedDestinationVertice, ProvidedEdgeWeight);   
        DestinationVerticeObj.addEdgeToVertex(ProvidedSourceVertice, ProvidedEdgeWeight);       
    }

    public void removeEdgeFromGraph(int ProvidedSourceVertice, int ProvidedDestinationVertice, int ProvidedEdgeWeight) {
        assert isVerticeExist(ProvidedSourceVertice);
        assert isVerticeExist(ProvidedDestinationVertice);
        Node SourceVerticeObj = getVerticeFromLabel(ProvidedSourceVertice);
        Node DestinationVerticeObj = getVerticeFromLabel(ProvidedDestinationVertice);
        SourceVerticeObj.removeEdgeFromVertex(ProvidedDestinationVertice);   
        DestinationVerticeObj.removeEdgeFromVertex(ProvidedSourceVertice);       
    }

    public int[][] getAdjacencyList() {
        int adjacencyList[][] = new int[Graph.size()+1][Graph.size()+1];
        for (Node vertice : Graph) {
            int source = vertice.getIndex();
            adjacencyList[0][source]=source;
            adjacencyList[source][0]=source;
            for (Map.Entry<Integer, Integer> entry : vertice.getEdges().entrySet()) {
                int destination = entry.getKey();
                int weight = entry.getValue();
                adjacencyList[source][destination] = weight;
            }
        }
        return adjacencyList;
    }
}

// WeightedGraph test = new WeightedGraph();

// Node vertice1 = new Node(1);
// Node vertice2 = new Node(2);
// Node vertice3 = new Node(3);
// Node vertice4 = new Node(4);
// Node vertice5 = new Node(5);
// Node vertice6 = new Node(6);
// Node vertice7 = new Node(7);
// Node vertice8 = new Node(8);
// Node vertice9 = new Node(9);
// Node vertice10 = new Node(10);
// Node vertice11 = new Node(11);


// test.addVertice(vertice1);
// test.addVertice(vertice2);
// test.addVertice(vertice3);
// test.addVertice(vertice4);
// test.addVertice(vertice5);
// test.addVertice(vertice6);
// test.addVertice(vertice7);
// test.addVertice(vertice8);
// test.addVertice(vertice9);
// test.addVertice(vertice10);
// test.addVertice(vertice11);

// test.addEdgeToGraph(10, 1, 50);
// test.addEdgeToGraph(1, 2, 3);
// test.addEdgeToGraph(2, 3, 2);
// test.addEdgeToGraph(3, 4, 7);
// test.addEdgeToGraph(4, 5, 1);
// test.addEdgeToGraph(5, 6, 4);
// test.addEdgeToGraph(6, 7, 6);
// test.addEdgeToGraph(7, 8, 8);
// test.addEdgeToGraph(8, 9, 9);
// test.addEdgeToGraph(9, 10, 1000);
// test.addEdgeToGraph(10, 2, 40);
// test.addEdgeToGraph(2, 4, 6);
// test.addEdgeToGraph(4, 6, 8);
// test.addEdgeToGraph(6, 8, 10);
// test.addEdgeToGraph(8, 10, 20000);
// test.addEdgeToGraph(1, 3, 6);
// test.addEdgeToGraph(3, 5, 8);
// test.addEdgeToGraph(5, 7, 10);
// test.addEdgeToGraph(7, 9, 2);


// ArrayList<Node> sampleList = test.retrieveGraph();
// for (Node node: sampleList) {
//     System.out.println(node.getIndex() + " : " + node.getEdges());
// }

// System.out.println(sampleList.size());
// int sampleAdjacencyList[][] = test.getAdjacencyList();
// for (int i = 0; i < sampleAdjacencyList.length; i++ ) {
//     for (int j = 0; j < sampleAdjacencyList.length; j++ ) {
//         System.out.print(sampleAdjacencyList[i][j] + " ");
//     }
//     System.out.println();
// }