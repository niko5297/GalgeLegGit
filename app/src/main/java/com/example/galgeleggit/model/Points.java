package com.example.galgeleggit.model;

import android.widget.Button;

import com.example.galgeleggit.R;

public class Points {

    private boolean erSidsteKorrekt;
    private int antalPoints;
    private int sidsteAntalPointsGivet;

    public Points() {
    }


    public int givPoint() {

        if (erSidsteKorrekt){
            antalPoints += sidsteAntalPointsGivet * 2;
            sidsteAntalPointsGivet = sidsteAntalPointsGivet * 2;
        } else {
            antalPoints++;
            sidsteAntalPointsGivet = 1;
        }
        erSidsteKorrekt = true;

        return antalPoints;

    }

    public int tagPoint() {

        antalPoints = antalPoints - 2;

        if (antalPoints<=0){
            antalPoints = 0;
        }

        erSidsteKorrekt = false;

        return antalPoints;

    }

    public void nulstil() {
        sidsteAntalPointsGivet = 0;
        erSidsteKorrekt = false;
        antalPoints = 0;
    }

    //region getters/setters


    public boolean isErSidsteKorrekt() {
        return erSidsteKorrekt;
    }

    public void setErSidsteKorrekt(boolean erSidsteKorrekt) {
        this.erSidsteKorrekt = erSidsteKorrekt;
    }

    public int getAntalPoints() {
        return antalPoints;
    }

    public void setAntalPoints(int antalPoints) {
        this.antalPoints = antalPoints;
    }

    public int getSidsteAntalPointsGivet() {
        return sidsteAntalPointsGivet;
    }

    public void setSidsteAntalPointsGivet(int sidsteAntalPointsGivet) {
        this.sidsteAntalPointsGivet = sidsteAntalPointsGivet;
    }

    //endregion
}
