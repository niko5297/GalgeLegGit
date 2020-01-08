package com.example.galgeleggit.model;

public final class Player {

    private String name;
    private static Player instance;

    private Player (){
    }

    public static Player getInstance(){
        if (instance==null){
            instance = new Player();
        }
        return instance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
