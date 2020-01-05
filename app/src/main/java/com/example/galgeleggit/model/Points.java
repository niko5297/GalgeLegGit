package com.example.galgeleggit.model;

public class Points {

    private int antalRigtigeITraek;
    private boolean erSidsteKorrekt;
    private int antalPoints;
    private int sidsteAntalPointsGivet;


    public Points() {
    }


    public void givPoint() {

        //Hvis sidste var korrekt, skal antal af point der gives være dobbelt så stor som sidste point givet

        //Ellers skal der bare tilføjes et point


        erSidsteKorrekt = true;

    }

    public void tagPoint() {


        if (antalPoints<=0){
            antalPoints = 0;
        }

        erSidsteKorrekt = false;

    }

    public void nulstil() {
        sidsteAntalPointsGivet = 0;
        antalRigtigeITraek = 0;
        erSidsteKorrekt = false;
        antalPoints = 0;
    }
}
