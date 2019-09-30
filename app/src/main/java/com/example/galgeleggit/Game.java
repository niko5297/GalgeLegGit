package com.example.galgeleggit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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
        if (view == tjekBogstav){
            System.out.println(skriveFelt.getText());
        }

    }
}
