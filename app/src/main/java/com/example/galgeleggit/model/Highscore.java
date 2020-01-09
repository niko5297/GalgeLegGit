package com.example.galgeleggit.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @date 08/01/2020
 *
 * @description This class defines a highscore object. Comparable have been implemented
 * to allow Collection.sort to be used to sort the highscore list.
 *
 */

public class Highscore implements Comparable<Highscore> {

    private String name;
    private int score;

    public Highscore() {

    }

    public Highscore (String name, int highscore) {
        this.name = name;
        this.score = highscore;
    }

    //region getters/setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    //endregion

    //region public methods

    /*
    public void addName(String stringName){
        name.add(stringName + ": ");
    }

    public void addHighscore (int score){
        highscore.add(score);
    }


     */
    @Override
    public int compareTo(Highscore highscore) {
        if (highscore.getScore()>score) {
            return 1;
        }
        else if (highscore.getScore()==score){
            return 0;
        }
        else return -1;
    }

    //endregion
}
