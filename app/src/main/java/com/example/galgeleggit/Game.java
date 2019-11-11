package com.example.galgeleggit;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


public class Game extends AppCompatActivity implements View.OnClickListener {

    public static Galgelogik galgelogik;
    TextView ord, gættedeBogstaver;
    EditText skriveFelt;
    ImageView billede;
    Button tjekBogstav, startNytSpil;
    static int antalForkerteGæt = 0;
    public static Set<String> lokalHighscore = new HashSet<>();
    public static final String prefsFile = "PrefsFile";

    /**
     * Lav aktivitet for vinder og taber og før informationer over i den aktivitet
     * @param savedInstanceState
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        galgelogik = MainActivity.galgelogik;

        tjekBogstav = findViewById(R.id.tjekBogstav);
        ord = findViewById(R.id.ord);
        gættedeBogstaver = findViewById(R.id.gættedeBogstaver);
        skriveFelt = findViewById(R.id.skriveFelt);
        billede = findViewById(R.id.galge);
        startNytSpil = findViewById(R.id.nytSpil);
        startNytSpil.setOnClickListener(this);
        tjekBogstav.setOnClickListener(this);

        visGalge();
        if (galgelogik.erSpilletSlut()){
            galgelogik.nulstil();
            antalForkerteGæt=0;
            billede.setImageDrawable(null);
            opdaterOrdOgGættedeBogstaver();
        }


        ord.setText("Du skal gætte følgende ord: " + galgelogik.getSynligtOrd());

        gættedeBogstaver.setText("Du har gættet på følgende bogstaver: " + galgelogik.getBrugteBogstaver());





        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

    }

    @Override
    public void onClick(View view) {
        if (view == tjekBogstav && skriveFelt.getText().toString().length() < 1){
            skriveFelt.setError("Du har skrevet for få bogstaver. Skriv et bogstav for at gætte");
        }

        if (view == tjekBogstav && skriveFelt.getText().toString().length() > 1){
            skriveFelt.setError("Du har skrevet for mange bogstaver. Skriv et bogstav for at gætte");
        }

        if (view == tjekBogstav && skriveFelt.getText().toString().length() ==1) {
            opdaterGalge();
            opdaterOrdOgGættedeBogstaver();


        }

        if (view == startNytSpil){
            galgelogik.nulstil();
            antalForkerteGæt = 0;
            billede.setImageDrawable(null);
            opdaterOrdOgGættedeBogstaver();
            AlertDialog.Builder tabtDialog = new AlertDialog.Builder(this);
            tabtDialog.setTitle("Nyt spil");
            tabtDialog.setMessage("Du har startet et nyt spil");
            tabtDialog.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {
                }
            });
            tabtDialog.show();

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
        switch (id){
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
                Intent i = new Intent(this,HighScore.class);
                startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }
    private void opdaterOrdOgGættedeBogstaver() {
        ord.setText("Du skal gætte følgende ord: " + galgelogik.getSynligtOrd() + "\n\n");
        if (galgelogik.getBrugteBogstaver().size()>0) {
            if (galgelogik.erSidsteBogstavKorrekt()) {
                gættedeBogstaver.setText("Du gættede rigigt! \n\nDu har gættet på følgende bogstaver: " + galgelogik.getBrugteBogstaver());

            } else {
                gættedeBogstaver.setText("Du gættede desværre forkert... prøv igen \n\nDu har gættet på følgende bogstaver: " + galgelogik.getBrugteBogstaver());
            }

            if (galgelogik.erSpilletVundet()) {
                lokalHighscore.add(galgelogik.getAntalForkerteBogstaver() + " forkerte bogstaver på ordet " + galgelogik.getOrdet());
                SharedPreferences.Editor editor = getSharedPreferences(prefsFile, MODE_PRIVATE).edit();
                editor.putStringSet("highscore", lokalHighscore);
                editor.apply();
                Intent i = new Intent(this,VinderAktivitet.class);
                startActivity(i);
                finish();

            }
            if (galgelogik.erSpilletTabt()) {
                Intent i = new Intent(this,TaberAktivitet.class);
                startActivity(i);
                finish();
            }
        }
        else {
            gættedeBogstaver.setText(getString(R.string.gættedeBogstaverStart) + " " + galgelogik.getBrugteBogstaver());
        }

    }

    private void opdaterGalge(){
        boolean brugtBogstav = false;
        if (galgelogik.getBrugteBogstaver().contains(skriveFelt.getText().toString())){
            brugtBogstav = true;
        }
        galgelogik.gætBogstav(skriveFelt.getText().toString());
        if(!galgelogik.erSidsteBogstavKorrekt() && !brugtBogstav) {
            antalForkerteGæt++;

            switch (antalForkerteGæt){
                case 1: billede.setImageResource(R.drawable.galge);
                    break;
                case 2: billede.setImageResource(R.drawable.forkert1);
                    break;
                case 3: billede.setImageResource(R.drawable.forkert2);
                    break;
                case 4: billede.setImageResource(R.drawable.forkert3);
                    break;
                case 5: billede.setImageResource(R.drawable.forkert4);
                    break;
                case 6: billede.setImageResource(R.drawable.forkert5);
                    break;
                case 7: billede.setImageResource(R.drawable.forkert6);
                    break;
            }

        }
    }

    private void visGalge(){
        switch (antalForkerteGæt){
            case 1: billede.setImageResource(R.drawable.galge);
                break;
            case 2: billede.setImageResource(R.drawable.forkert1);
                break;
            case 3: billede.setImageResource(R.drawable.forkert2);
                break;
            case 4: billede.setImageResource(R.drawable.forkert3);
                break;
            case 5: billede.setImageResource(R.drawable.forkert4);
                break;
            case 6: billede.setImageResource(R.drawable.forkert5);
                break;
            case 7: billede.setImageResource(R.drawable.forkert6);
                break;
        }
    }

    public static void setAntalForkerteGæt(int antalForkerteGæt) {
        Game.antalForkerteGæt = antalForkerteGæt;
    }
}
