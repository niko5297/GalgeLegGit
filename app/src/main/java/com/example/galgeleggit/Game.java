package com.example.galgeleggit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;


public class Game extends AppCompatActivity implements View.OnClickListener {

    Galgelogik galgelogik = new Galgelogik();
    EditText skriveFelt;
    ImageView billede;
    Button tjekBogstav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        tjekBogstav = findViewById(R.id.tjekBogstav);
        skriveFelt = findViewById(R.id.skriveFelt);
        billede = findViewById(R.id.galge);
        tjekBogstav.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view == tjekBogstav) {
            System.out.println(skriveFelt.getText());
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(Menu.NONE, 1, Menu.NONE, "Hjælp");

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == 1){
            System.out.println("Du har trykket");
        }

        return true;
    }
}
