package com.example.galgeleggit.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.galgeleggit.R;
import com.example.galgeleggit.model.Galgelogik;
import com.example.galgeleggit.model.Help;
import com.example.galgeleggit.model.Player;
import com.example.galgeleggit.model.Points;
import com.google.gson.Gson;

import java.util.HashSet;
import java.util.Set;


public class Game extends AppCompatActivity implements View.OnClickListener {

    //region Fields

    public static Galgelogik galgelogik;
    TextView ord, gættedeBogstaver, skiftetekst;
    EditText skriveFelt;
    ImageView billede;
    Button tjekBogstav, startNytSpil, points;
    private static int antalForkerteGæt = 0;
    private boolean brugtBogstav;
    private MediaPlayer mediaPlayer;
    private Player player = Player.getInstance();
    public static Points pointManager = new Points();
    private Help help = new Help();
    public static Set<String> lokalHighscore = new HashSet<>();
    public static final String prefsFile = "PrefsFile";

    //endregion

    //region onCreate / onPause
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        galgelogik = MainActivity.galgelogik;

        tjekBogstav = findViewById(R.id.tjekBogstav);
        ord = findViewById(R.id.ord);
        gættedeBogstaver = findViewById(R.id.gættedeBogstaver);
        skiftetekst = findViewById(R.id.skiftetekst);
        skriveFelt = findViewById(R.id.skriveFelt);
        billede = findViewById(R.id.galge);
        startNytSpil = findViewById(R.id.nytSpil);
        points = findViewById(R.id.points);
        startNytSpil.setOnClickListener(this);
        tjekBogstav.setOnClickListener(this);

        visGalge();
        if (galgelogik.erSpilletSlut() || MainActivity.nytSpil) {
            pointManager.nulstil();
            galgelogik.nulstil();
            antalForkerteGæt = 0;
            points.setText("Points: " + pointManager.getAntalPoints());
            billede.setImageDrawable(null);
            opdaterOrdOgGættedeBogstaver();
            visGalge();
        }


        ord.setText("Du skal gætte følgende ord: " + galgelogik.getSynligtOrd());

        gættedeBogstaver.setText("Du har gættet på følgende bogstaver: " + galgelogik.getBrugteBogstaver());

        points.setText("Points :" + pointManager.getAntalPoints());


        System.out.println(galgelogik.getOrdet());
        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

    }

    @Override
    protected void onPause() {
        super.onPause();
        MainActivity.nytSpil = false;
    }

    //endregion

    //region onClick and Menu

    @Override
    public void onClick(View view) {
        if (view == tjekBogstav && skriveFelt.getText().toString().length() < 1) {
            skriveFelt.setError("Du har skrevet for få bogstaver. Skriv et bogstav for at gætte");
        }

        if (view == tjekBogstav && skriveFelt.getText().toString().length() > 1) {
            skriveFelt.setError("Du har skrevet for mange bogstaver. Skriv et bogstav for at gætte");
        }

        if (view == tjekBogstav && skriveFelt.getText().toString().length() == 1) {
            opdaterGalge();
            opdaterOrdOgGættedeBogstaver();


        }

        if (view == startNytSpil) {
            skiftetekst.setText("");
            galgelogik.nulstil();
            pointManager.nulstil();
            antalForkerteGæt = 0;
            points.setText("Points: " + pointManager.getAntalPoints());
            billede.setImageDrawable(null);
            opdaterOrdOgGættedeBogstaver();
        }
        skriveFelt.getText().clear();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.game_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id) {
            case R.id.hjælp:
                help.inflateHelp(this);
                break;
            case R.id.highscore:
                Intent i = new Intent(this, HighScore.class);
                startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

    //endregion

    //region Support Methods

    private void opdaterOrdOgGættedeBogstaver() {
        gemProcess();
        ord.setText("Du skal gætte følgende ord: " + galgelogik.getSynligtOrd());
        if (galgelogik.getBrugteBogstaver().size() > 0) {
            if (galgelogik.erSidsteBogstavKorrekt() && !brugtBogstav) {
                mediaPlayer = MediaPlayer.create(this, R.raw.points);
                mediaPlayer.start();
                points.setText("Points: " + pointManager.givPoint());
                skiftetekst.setText("Tillykke!! Du gættede rigigt!");
                gættedeBogstaver.setText("Dine brugte bogstaver er: " + galgelogik.getBrugteBogstaver());

            } else {
                skiftetekst.setText("Du gættede desværre forkert... Prøv igen.");
                gættedeBogstaver.setText("Dine brugte bogstaver er: " + galgelogik.getBrugteBogstaver());
            }

            if (galgelogik.erSpilletVundet()) {
                mediaPlayer = MediaPlayer.create(this, R.raw.victory);
                mediaPlayer.setVolume(4F, 4F);
                mediaPlayer.start();
                lokalHighscore.add(player.getName() + ": " + pointManager.getAntalPoints());
                SharedPreferences.Editor editor = getSharedPreferences(prefsFile, MODE_PRIVATE).edit();
                editor.putStringSet("highscore", lokalHighscore);
                editor.apply();
                Intent i = new Intent(this, VinderAktivitet.class);
                startActivity(i);
                finish();

            }
            if (galgelogik.erSpilletTabt()) {
                mediaPlayer = MediaPlayer.create(this, R.raw.lost);
                mediaPlayer.start();
                Intent i = new Intent(this, TaberAktivitet.class);
                startActivity(i);
                finish();
            }
        } else {
            gættedeBogstaver.setText(getString(R.string.gættedeBogstaverStart) + " " + galgelogik.getBrugteBogstaver());
        }

    }

    private void opdaterGalge() {
        brugtBogstav = false;
        if (galgelogik.getBrugteBogstaver().contains(skriveFelt.getText().toString())) {
            brugtBogstav = true;
        }
        galgelogik.gætBogstav(skriveFelt.getText().toString());
        if (!galgelogik.erSidsteBogstavKorrekt() && !brugtBogstav) {
            points.setText("Points: " + pointManager.tagPoint());
            antalForkerteGæt++;

            switch (antalForkerteGæt) {
                case 1:
                    billede.setImageResource(R.drawable.galge);
                    break;
                case 2:
                    billede.setImageResource(R.drawable.forkert1);
                    break;
                case 3:
                    billede.setImageResource(R.drawable.forkert2);
                    break;
                case 4:
                    billede.setImageResource(R.drawable.forkert3);
                    break;
                case 5:
                    billede.setImageResource(R.drawable.forkert4);
                    break;
                case 6:
                    billede.setImageResource(R.drawable.forkert5);
                    break;
                case 7:
                    billede.setImageResource(R.drawable.forkert6);
                    break;
            }

        }
    }

    private void visGalge() {
        switch (antalForkerteGæt) {
            case 1:
                billede.setImageResource(R.drawable.galge);
                break;
            case 2:
                billede.setImageResource(R.drawable.forkert1);
                break;
            case 3:
                billede.setImageResource(R.drawable.forkert2);
                break;
            case 4:
                billede.setImageResource(R.drawable.forkert3);
                break;
            case 5:
                billede.setImageResource(R.drawable.forkert4);
                break;
            case 6:
                billede.setImageResource(R.drawable.forkert5);
                break;
            case 7:
                billede.setImageResource(R.drawable.forkert6);
                break;
        }
    }


    private void gemProcess() {
        SharedPreferences.Editor editor = getPreferences(MODE_PRIVATE).edit();
        Gson gson = new Gson();
        String json = gson.toJson(galgelogik);
        editor.putString("MyObject", json);
        editor.apply();
    }

    //endregion

    //region Public methods

    public static void setAntalForkerteGæt(int antalForkerteGæt) {
        Game.antalForkerteGæt = antalForkerteGæt;
    }

    //endregion


}
