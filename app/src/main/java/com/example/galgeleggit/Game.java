package com.example.galgeleggit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


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
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

    }

    @Override
    public void onClick(View view) {
        if (view == tjekBogstav) {
            System.out.println(skriveFelt.getText());
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //menu.add(Menu.NONE, 1, Menu.NONE, "Hjælp").setIcon(R.drawable.baseline_help_outline_black_18dp);

        getMenuInflater().inflate(R.menu.game_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

       /* if (item.getItemId() == 1){
            System.out.println("Du har trykket");
        }
        */
        int id = item.getItemId();
        switch (id){
            case R.id.hjælp:
                Toast.makeText(this, item.getTitle().toString(), Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
