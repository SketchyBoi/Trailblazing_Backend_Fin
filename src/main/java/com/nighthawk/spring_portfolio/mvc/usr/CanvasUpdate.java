package com.nighthawk.spring_portfolio.mvc.usr;

public class CanvasUpdate {
    private String email;
    private int[][] adj;

    public CanvasUpdate(String email, int[][] adj) {
        this.email = email;
        this.adj = adj;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public void setAdj(int[][] adj) {
        this.adj = adj;
    }

    public String getEmail() {
        return this.email;
    }

    public int[][] getAdj() {
        return this.adj;
    }
}
