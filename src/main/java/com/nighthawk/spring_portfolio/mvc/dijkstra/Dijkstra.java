package com.nighthawk.spring_portfolio.mvc.dijkstra;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.Stack;

import org.hibernate.mapping.Array;

public class Dijkstra {
    private static final int inf = Integer.MAX_VALUE;
    private static HashMap<Node, Integer> ShortestDistanceMap;
    private static HashMap<Integer, Integer> ParentVerticesMap;
    private static ArrayList<Node> NodeArrayList;
    private static Node CurrentNodeToTraverse;
    private static int alt;
    private static WeightedGraph InputGraph;
    
    
    public static HashMap<Node, Integer> dijkstra(WeightedGraph Graph, int SourceVertexLabel) {
        InputGraph = Graph;
        ShortestDistanceMap = new HashMap<Node, Integer>();
        ParentVerticesMap = new HashMap<Integer, Integer>();
        NodeArrayList = new ArrayList<Node>();
        Node SourceVertextNodeObject = InputGraph.getVerticeFromLabel(SourceVertexLabel);
        Node CurrentNodeToTraverse;
        for (Node vertice : InputGraph.retrieveGraph()) {
            ShortestDistanceMap.put(vertice, inf-1);
            ParentVerticesMap.put(vertice.getIndex(), null);
            NodeArrayList.add(vertice);
        }

        ShortestDistanceMap.put(SourceVertextNodeObject, 0);

        while (NodeArrayList.size() != 0) {
            CurrentNodeToTraverse = FindMinDistInNodesArrayList();
            NodeArrayList.remove(CurrentNodeToTraverse);

            for (int verticeLabel : CurrentNodeToTraverse.getNeighborsOfVertice()) {
                Node vertice = InputGraph.getVerticeFromLabel(verticeLabel);
                if (NodeArrayList.contains(vertice)) {
                    alt = ShortestDistanceMap.get(CurrentNodeToTraverse) + vertice.getDistanceBetweenTwoVertices(CurrentNodeToTraverse);
                    if (alt < ShortestDistanceMap.get(vertice)) {
                        ShortestDistanceMap.put(vertice, alt);
                        ParentVerticesMap.put(vertice.getIndex(), CurrentNodeToTraverse.getIndex());
                    }
                }
            } 
        }
        return ShortestDistanceMap;
    }

    private static Node FindMinDistInNodesArrayList () {
        int minimumDistance = inf; 
        int newDistance;
        Node optimalNode = null;
        for (Node vertice : NodeArrayList) {
            newDistance = ShortestDistanceMap.get(vertice);
            if (newDistance < minimumDistance) {
                minimumDistance = newDistance;
                optimalNode = vertice;
            }
        }
        return optimalNode;
    }

    public static ArrayList<Integer> ReverseIteratePath (int sourceNodetoIterate, int targetNodetoIterate) {
        Stack<Integer> ShortestPathStack = new Stack<Integer>();
        System.out.println(ParentVerticesMap.get(targetNodetoIterate));
        Integer CurrentNodeToTrace = targetNodetoIterate;
        if (ParentVerticesMap.get(CurrentNodeToTrace)!=null || CurrentNodeToTrace == sourceNodetoIterate) {
            System.out.println("HELLLLLLLLLO IF THIS RUNS I THINK JAVA IS BROKEN");
            while (CurrentNodeToTrace!=null) {
                ShortestPathStack.push(CurrentNodeToTrace);
                CurrentNodeToTrace = ParentVerticesMap.get(CurrentNodeToTrace);
            }
        }
        ArrayList<Integer> shortestPath = new ArrayList<Integer>();
        while (!ShortestPathStack.isEmpty()) {
            shortestPath.add(ShortestPathStack.pop());
        }
        return shortestPath;
    }
    

    public static void main(String args[]) {
        dijkstra(test, 9);
        for (Map.Entry<Node, Integer> optimalDistance : ShortestDistanceMap.entrySet()) {
            System.out.println("Shortest distance from vertex 9 to vertex " + 
            optimalDistance.getKey().getIndex() + 
            " is: " 
            + optimalDistance.getValue());
        }
        Stack<Integer> OptimalDistanceToTarget = ReverseIteratePath(9, 10);
        System.out.println(OptimalDistanceToTarget);
        while (!OptimalDistanceToTarget.isEmpty()) {
            System.out.println(OptimalDistanceToTarget.pop());
        }
    }

}