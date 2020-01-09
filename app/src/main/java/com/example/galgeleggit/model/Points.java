package com.example.galgeleggit.model;

/**
 * @date 08/01/2020
 *
 * @description This class defines the point system for the game
 *
 */

public class Points {

    //region Fields

    private boolean lastCorrect;
    private int numberOfPoints;
    private int lastPointsGiven;

    //endregion

    //region Constructor

    public Points() {
    }

    //endregion

    //region public methods

    public int givPoint() {

        if (lastCorrect){
            numberOfPoints += lastPointsGiven * 2;
            lastPointsGiven = lastPointsGiven * 2;
        } else {
            numberOfPoints++;
            lastPointsGiven = 1;
        }
        lastCorrect = true;

        return numberOfPoints;

    }

    public int tagPoint() {

        numberOfPoints = numberOfPoints - 2;

        if (numberOfPoints <=0){
            numberOfPoints = 0;
        }

        lastCorrect = false;

        return numberOfPoints;

    }

    public void nulstil() {
        lastPointsGiven = 0;
        lastCorrect = false;
        numberOfPoints = 0;
    }

    //endregion

    //region getters/setters


    public boolean isLastCorrect() {
        return lastCorrect;
    }

    public void setLastCorrect(boolean lastCorrect) {
        this.lastCorrect = lastCorrect;
    }

    public int getNumberOfPoints() {
        return numberOfPoints;
    }

    public void setNumberOfPoints(int numberOfPoints) {
        this.numberOfPoints = numberOfPoints;
    }

    public int getLastPointsGiven() {
        return lastPointsGiven;
    }

    public void setLastPointsGiven(int lastPointsGiven) {
        this.lastPointsGiven = lastPointsGiven;
    }

    //endregion
}
