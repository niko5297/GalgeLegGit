package com.example.galgeleggit.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.galgeleggit.R;
import com.example.galgeleggit.model.Galgelogik;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    //region Fields

    Button start;
    Button fortsaet;
    Spinner spinner;
    ProgressDialog dialog;
    private int spilType;
    private AsyncTask asyncTask;
    public static boolean erSpilletIGang;
    public static boolean nytSpil;
    public static Galgelogik galgelogik;
    public static Galgelogik almindeligGalgeLogik = new Galgelogik();
    public static Galgelogik drGalgeLogik = new Galgelogik();

    //endregion
    //TODO: Tilføj fortsæt spil til Shared preferences, så man stadig kan fortsætte sit spil, selvom man har lukket appen

    //TODO: Fjern \n i TaberAktivtet.

    //TODO: Tilføj pointtavle
    /**
     * SE HER: https://stackoverflow.com/questions/7145606/how-android-sharedpreferences-save-store-object
     *
     * @param savedInstanceState
     */


    //region onCreate / onResume
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        start = findViewById(R.id.start);
        fortsaet = findViewById(R.id.fortsaet);
        fortsaet.setOnClickListener(this);
        start.setOnClickListener(this);

        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        addSpinner();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (erSpilletIGang) {
            fortsaet.setEnabled(true);
        } else fortsaet.setEnabled(false);
    }

    //endregion

    //region onClick and Menu

    @Override
    public void onClick(View view) {
        if (view == start) {
            erSpilletIGang = true;
            startSpilleType(spilType);
            nytSpil = true;
            Intent i = new Intent(this, Game.class);
            startActivity(i);
        }

        if (view == fortsaet) {
            Intent i = new Intent(this, Game.class);
            startActivity(i);
        }
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

    //endregion

    //region Spinner
    private void addSpinner() {
        String[] ordType = {"Almindelige ord", "Hent ord fra DR"};
        spinner = findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, android.R.id.text1, ordType);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setPrompt("Vælg din måde at spille på");
        spinner.setAdapter(adapter);

    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        //Spillemåde 0 = Almindelig, Spillemåde 1 = Ord fra DR
        spilType = position;

        if (position == 1) {
            asyncTask = new AsyncTask() {

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    dialog = new ProgressDialog(MainActivity.this);
                    dialog.setMessage("Loading...");
                    dialog.setIndeterminate(false);
                    dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    dialog.setCancelable(true);
                    dialog.show();
                }

                @Override
                protected Object doInBackground(Object[] objects) {
                    try {
                        drGalgeLogik.hentOrdFraDr();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return "Hentet ord fra DR gennemført";
                }

                @Override
                protected void onPostExecute(Object o) {
                    Toast.makeText(MainActivity.this, "Hentet ord fra DR gennemført", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    asyncTask = null;
                }

            };
            asyncTask.execute();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        Toast.makeText(this, "Intet valgt", Toast.LENGTH_SHORT).show();
    }

    //endregion

    //region Support methods

    private void startSpilleType(int spilType) {

        if (spilType == 0) {

            galgelogik = almindeligGalgeLogik;
        }

        if (spilType == 1) {

            galgelogik = drGalgeLogik;
        }

    }

    //endregion


}
