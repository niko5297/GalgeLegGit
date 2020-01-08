package com.example.galgeleggit.model;

import java.util.ArrayList;
import java.util.List;

public final class Highscore {

    public List<String> name;
    public List<Integer> highscore;
    private static Highscore instance;

    private Highscore(List<String> name, List<Integer> highscore){
        this.highscore = new ArrayList<>(highscore);
        this.name = new ArrayList<>(name);

    }

    public static Highscore getInstance(){
        if (instance==null){
            instance = new Highscore(new ArrayList<String>(), new ArrayList<Integer>());
        }
        return instance;
    }

    //region getters/setters


    public List<String> getName() {
        return name;
    }

    public void setName(List<String> name) {
        this.name = name;
    }

    public List<Integer> getHighscore() {
        return highscore;
    }

    public void setHighscore(List<Integer> highscore) {
        this.highscore = highscore;
    }

    //endregion

    //region public methods

    public void addName(String stringName){
        name.add(stringName + ": ");
    }

    public void addHighscore (int score){
        highscore.add(score);
    }

    //endregion
}
