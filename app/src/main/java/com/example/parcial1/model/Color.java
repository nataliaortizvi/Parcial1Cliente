package com.example.parcial1.model;

public class Color {

    //metadata
    private String type = "Color";
    private int r,g,b;

    public Color(){
    }

    public Color(int r, int g, int b){
        super();
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getR() {
        return r;
    }

    public void setR(int r) {
        this.r = r;
    }

    public int getG() {
        return g;
    }

    public void setG(int g) {
        this.g = g;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }
}
