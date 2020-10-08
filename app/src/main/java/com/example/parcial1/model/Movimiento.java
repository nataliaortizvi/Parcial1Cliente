package com.example.parcial1.model;

public class Movimiento {

    //metadata
    private String type = "Movimiento";
    private float x,y;

    public Movimiento(){
    }

    public Movimiento(float x, float y){
        super();
        this.x = x;
        this.y = y;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


}
