package com.example.galgeleggit.model;

/**
 * @date 08/01/2020
 *
 * @description
 *
 * @source https://www.baeldung.com/java-singleton
 *
 */

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
