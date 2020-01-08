package com.example.galgeleggit.model;

import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;

import com.example.galgeleggit.R;

public class Help {

    public Help () {

    }


    public void inflateHelp(Context context) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle("Hjælp");
        dialog.setIcon(R.drawable.ic_help_black_24dp);
        dialog.setMessage("Spillet går ud på at du skal gætte det ord som maskinen tænker på. \n" +
                "Dette gøres ved at skrive et bogstav. For hvert rigtigt svar vises det bogstav i ordet. " +
                "For hvert forkert svar tegnes noget af galgen. Hele galgen vil være tegnet ved 6 forkerte gæt. \n" +
                "Det gælder om at gætte hele ordet før galgen er blevet tegnet.\n\n" +
                "Man scorer point for hvert rigtigt gæt. Det første rigtige gæt giver 1 point," +
                "og for hvert rigtig gæt i træk, fordobles pointantallet.\n" +
                "For et forkert svar, mister du 2 point. \n\n" +
                "Held og lykke.");
        dialog.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
            }
        });
        dialog.show();
    }
}
