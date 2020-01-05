package com.example.galgeleggit.model;

public class Points {

    private boolean erSidsteKorrekt;
    private int antalPoints;
    private int sidsteAntalPointsGivet;


    public Points() {
    }


    public void givPoint() {

        if (erSidsteKorrekt){
            antalPoints = sidsteAntalPointsGivet * 2;
            sidsteAntalPointsGivet = sidsteAntalPointsGivet * 2;
        } else {
            antalPoints++;
            sidsteAntalPointsGivet = 1;
        }
        erSidsteKorrekt = true;

    }

    public void tagPoint() {

        antalPoints = antalPoints - 5;

        if (antalPoints<=0){
            antalPoints = 0;
        }

        erSidsteKorrekt = false;

    }

    public void nulstil() {
        sidsteAntalPointsGivet = 0;
        erSidsteKorrekt = false;
        antalPoints = 0;
    }
}
