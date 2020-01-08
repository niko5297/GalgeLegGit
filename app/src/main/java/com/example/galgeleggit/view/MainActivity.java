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
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.galgeleggit.R;
import com.example.galgeleggit.model.Galgelogik;
import com.example.galgeleggit.model.Help;
import com.example.galgeleggit.model.Player;
import com.shreyaspatil.MaterialDialog.MaterialDialog;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    //region Fields

    Button start, fortsaet, helpbutton, playername;
    Spinner spinner;
    ProgressDialog dialog;
    private int spilType;
    private AsyncTask asyncTask;
    private Help help = new Help();
    private String mPlayerName;
    private Player player = Player.getInstance();
    public static boolean erSpilletIGang;
    public static boolean nytSpil;
    public static Galgelogik galgelogik;
    public static Galgelogik almindeligGalgeLogik = new Galgelogik();
    public static Galgelogik drGalgeLogik = new Galgelogik();

    //endregion
    //TODO: Tilføj fortsæt spil til Shared preferences, så man stadig kan fortsætte sit spil, selvom man har lukket appen

    //TODO: Lav alt kode om fra dansk til engelsk

    //TODO: Lav måske en mulighed for at vælge et navn.

    //TODO: Lav popup ved start for at vælge et navn.

    //TODO: Overvej hvorvidt der er brug for en controller til f.eks. player, help, point osv osv.
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
        playername = findViewById(R.id.playername);
        helpbutton = findViewById(R.id.help);
        playername.setOnClickListener(this);
        helpbutton.setOnClickListener(this);
        fortsaet.setOnClickListener(this);
        start.setOnClickListener(this);

        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        if (player.getName()==null) {
            choosePlayerName();
        }
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
            player.setName(mPlayerName);
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

        if (view == helpbutton){
            help.inflateHelp(this);
        }

        if (view == playername){
            choosePlayerName();
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
                help.inflateHelp(this);
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

    /**
     * https://stackoverflow.com/questions/10903754/input-text-dialog-android
     */

    private void choosePlayerName(){


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Vælg dit spiller navn");

        // Set up the input
        final EditText input = new EditText(this);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("Vælg navn", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mPlayerName = input.getText().toString();
            }
        });
        builder.show();




    }

    //endregion


}
