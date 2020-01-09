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

    //region Fields

    private String name;
    private int score;

    //endregion

    //region Constructors

    public Highscore() {

    }

    public Highscore (String name, int highscore) {
        this.name = name;
        this.score = highscore;
    }

    //endregion

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

    //region Interface methods

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
