package com.example.galgeleggit.model;

/**
 * @date 08/01/2020
 *
 * @description This class defines the singleton Player object.
 *
 * @source https://www.baeldung.com/java-singleton
 *
 */

public final class Player {

    //region Fields

    private String name;
    private static Player instance;

    //endregion

    //region Constructor

    private Player (){
    }

    //endregion

    //region Singleton Instance

    public static Player getInstance(){
        if (instance==null){
            instance = new Player();
        }
        return instance;
    }

    //endregion

    //region getters/setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //endregion

}
