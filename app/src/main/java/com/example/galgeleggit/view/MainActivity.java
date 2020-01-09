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

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    //region Fields

    Button start, continueButton, helpbutton, playername;
    Spinner spinner;
    ProgressDialog dialog;

    private int gameType;
    private AsyncTask asyncTask;
    private Help help = new Help();
    private String mPlayerName;
    private Player player = Player.getInstance();

    public static boolean isGameRunning;
    public static boolean newGame;
    public static Galgelogik galgelogik;
    public static Galgelogik almindeligGalgeLogik = new Galgelogik();
    public static Galgelogik drGalgeLogik = new Galgelogik();

    //endregion

    //region onCreate / onResume
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize view
        start = findViewById(R.id.start);
        continueButton = findViewById(R.id.continueButton);
        playername = findViewById(R.id.playername);
        helpbutton = findViewById(R.id.help);
        playername.setOnClickListener(this);
        helpbutton.setOnClickListener(this);
        continueButton.setOnClickListener(this);
        start.setOnClickListener(this);

        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        //If the player havent chosen a name, alert him and show dialog again
        if (player.getName()==null) {
            choosePlayerName();
        }

        //Adds spinner for game selection
        addSpinner();

    }

    /**
     * onResume is called when you return to the Activity.
     * For this sake, we use onResume, to check if a game is currently running
     */
    @Override
    protected void onResume() {
        super.onResume();
        if (isGameRunning) {
            continueButton.setEnabled(true);
        } else continueButton.setEnabled(false);
    }

    //endregion

    //region onClick and Menu

    @Override
    public void onClick(View view) {
        if (view == start ) {
            if (player.getName()==null){
                Toast.makeText(this, R.string.chooseName, Toast.LENGTH_SHORT).show();
                choosePlayerName();
            }
            else {
                isGameRunning = true;
                startGameType(gameType);
                newGame = true;
                Intent i = new Intent(this, Game.class);
                startActivity(i);
            }
        }

        if (view == continueButton) {
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

    /**
     * Inflates the help and highscore menu, top right corner of the toolbar
     * @param menu menu
     * @return true or false if it is successful.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.game_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Lets the user select the options in the inflated menu.
     * @param item clicked on
     * @return true or false if it is successful
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id) {
            case R.id.helpMenu:
                help.inflateHelp(this);
                break;
            case R.id.highscore:
                Intent i = new Intent(this, HighScoreActivity.class);
                startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

    //endregion

    //region Spinner

    /**
     * This method adds the spinner/user game selection.
     * The user can select between two types of game.
     */
    private void addSpinner() {
        String[] gameTypeSpinner = {"Almindelige ord", "Hent ord fra DR"};
        spinner = findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, android.R.id.text1, gameTypeSpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setPrompt("Vælg din måde at spille på");
        spinner.setAdapter(adapter);

    }

    /**
     * This method handle the selection of gameTypes regarding the spinner
     * @param adapterView where the selection has happen
     * @param view that has been clicked on
     * @param position in the spinner
     * @param l row id
     */
    @SuppressLint("StaticFieldLeak")
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        //Gametype 0 = Almindelig, Gametype 1 = Ord fra DR
        gameType = position;

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

    /**
     * This method is called based on the type of game that the user selects
     * @param gameType position in spinner
     */
    private void startGameType(int gameType) {

        if (gameType == 0) {

            galgelogik = almindeligGalgeLogik;
        }

        if (gameType == 1) {

            galgelogik = drGalgeLogik;
        }

    }

    /**
     * This method inflates a dialog, which require the user to input a playername
     * I used the below link as inspiration.
     * https://stackoverflow.com/questions/10903754/input-text-dialog-android
     */
    private void choosePlayerName(){


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Vælg dit spiller navn");

        // Set up the input
        final EditText input = new EditText(this);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setText(player.getName());
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("Vælg navn", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mPlayerName = input.getText().toString();
                player.setName(mPlayerName);

                if (player.getName().equals("")){
                    Toast.makeText(MainActivity.this, R.string.chooseName, Toast.LENGTH_SHORT).show();
                    choosePlayerName();
                }
            }
        });
        builder.show();




    }

    //endregion


}
