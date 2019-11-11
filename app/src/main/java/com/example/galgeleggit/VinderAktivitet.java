package com.example.galgeleggit;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class VinderAktivitet extends AppCompatActivity implements View.OnClickListener {

    MainActivity mainActivity;
    Button nytspil, tilbage;
    TextView vinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vinder_aktivitet);
        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        nytspil = findViewById(R.id.nytspil2);
        tilbage = findViewById(R.id.tilStart);
        vinder = findViewById(R.id.vinder);

        vinder.setText("DU VANDT SPILLET!! \n\nStort tillykke med sejren.\nDin score er nu lokalt gemt i Highscore!\n\n" +
                "Ordet du har gættet er: " + MainActivity.galgelogik.getOrdet() +
        "\nog du fik kun " +MainActivity.galgelogik.getAntalForkerteBogstaver() +" forkerte bogstaver");

        nytspil.setOnClickListener(this);
        tilbage.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.game_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id) {
            case R.id.hjælp:
                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                dialog.setTitle("Hjælp");
                dialog.setIcon(R.drawable.ic_help_black_24dp);
                dialog.setMessage("Spillet går ud på at du skal gætte det ord som maskinen tænker på. \n" +
                        "Dette gøres ved at skrive et bogstav. For hvert rigtigt svar vises det bogstav i ordet. " +
                        "For hvert forkert svar tegnes noget af galgen. Hele galgen vil være tegnet ved 6 forkerte gæt. \n" +
                        "Det gælder om at gætte hele ordet før galgen er blevet tegnet. \n\n" +
                        "Held og lykke.");
                dialog.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                    }
                });
                dialog.show();
                break;
            case R.id.highscore:
                Intent i = new Intent(this, HighScore.class);
                startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {

        if (view == nytspil){
            mainActivity.galgelogik.nulstil();
            Intent i = new Intent(this,Game.class);
            startActivity(i);
        }

        if (view == tilbage){
            Intent i = new Intent(this,MainActivity.class);
            startActivity(i);
        }

    }
}
