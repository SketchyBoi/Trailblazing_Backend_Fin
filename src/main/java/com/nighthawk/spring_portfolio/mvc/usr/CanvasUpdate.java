package com.nighthawk.spring_portfolio.mvc.usr;

import java.util.HashMap;

public class CanvasUpdate {
    private String email;
    private Integer[][] adj;
    private HashMap<Integer, Integer[]> coords;

    public CanvasUpdate(String email, Integer[][] adj, HashMap<Integer, Integer[]> coords) {
        this.email = email;
        this.adj = adj;
        this.coords = coords;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public void setAdj(Integer[][] adj) {
        this.adj = adj;
    }

    public void setCoords(HashMap<Integer, Integer[]> coords) {
        this.coords = coords;
    }

    // public void setCoords(Integer[][] coords) {
    //     for (Integer i = 0; i < coords.length; i++) {
    //       Integer coord[] = new Integer[2];
    //       coord[0] = coords[i][0];
    //       coord[1] = coords[i][1];
    //       Integer nodeID = i + 1;
    //       this.coords.put(nodeID, coord);
    //     }  
    //   }

    public String getEmail() {
        return this.email;
    }

    public Integer[][] getAdj() {
        return this.adj;
    }

    public HashMap<Integer, Integer[]> getCoords() {
        return this.coords;
    }
}
