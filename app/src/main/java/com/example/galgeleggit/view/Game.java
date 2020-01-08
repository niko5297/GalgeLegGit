package com.example.galgeleggit.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.ArraySet;
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
    TextView word, guessedLetters, switchText;
    EditText typeField;
    ImageView hangmanImage;
    Button checkLetter, newGameButton, points;
    private static int numberOfWrongGuesses = 0;
    private boolean usedLetter;
    private MediaPlayer mediaPlayer;
    private Player player = Player.getInstance();
    public static Points pointManager = new Points();
    private Help help = new Help();
    public static Set<String> localHighscoreName = new ArraySet<>();
    public static Set<String> localHighscore = new ArraySet<>();
    public static final String prefsFile = "PrefsFile";

    //endregion

    //region onCreate / onPause
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        galgelogik = MainActivity.galgelogik;

        checkLetter = findViewById(R.id.checkLetter);
        word = findViewById(R.id.word);
        guessedLetters = findViewById(R.id.guessedLetters);
        switchText = findViewById(R.id.switchText);
        typeField = findViewById(R.id.typeField);
        hangmanImage = findViewById(R.id.hangmanImage);
        newGameButton = findViewById(R.id.newGame);
        points = findViewById(R.id.points);
        newGameButton.setOnClickListener(this);
        checkLetter.setOnClickListener(this);

        showHangman();
        if (galgelogik.erSpilletSlut() || MainActivity.newGame) {
            pointManager.nulstil();
            galgelogik.nulstil();
            numberOfWrongGuesses = 0;
            points.setText("Points: " + pointManager.getNumberOfPoints());
            hangmanImage.setImageDrawable(null);
            updateWordAndGuessedLetters();
            showHangman();
        }


        word.setText("Du skal gætte følgende word: " + galgelogik.getSynligtOrd());

        guessedLetters.setText("Du har gættet på følgende bogstaver: " + galgelogik.getBrugteBogstaver());

        points.setText("Points :" + pointManager.getNumberOfPoints());


        System.out.println(galgelogik.getOrdet());
        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

    }

    @Override
    protected void onPause() {
        super.onPause();
        MainActivity.newGame = false;
    }

    //endregion

    //region onClick and Menu

    @Override
    public void onClick(View view) {
        if (view == checkLetter && typeField.getText().toString().length() < 1) {
            typeField.setError("Du har skrevet for få bogstaver. Skriv et bogstav for at gætte");
        }

        if (view == checkLetter && typeField.getText().toString().length() > 1) {
            typeField.setError("Du har skrevet for mange bogstaver. Skriv et bogstav for at gætte");
        }

        if (view == checkLetter && typeField.getText().toString().length() == 1) {
            updateHangman();
            updateWordAndGuessedLetters();


        }

        if (view == newGameButton) {
            switchText.setText("");
            galgelogik.nulstil();
            pointManager.nulstil();
            numberOfWrongGuesses = 0;
            points.setText("Points: " + pointManager.getNumberOfPoints());
            hangmanImage.setImageDrawable(null);
            updateWordAndGuessedLetters();
        }
        typeField.getText().clear();


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
            case R.id.helpMenu:
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

    private void updateWordAndGuessedLetters() {
        word.setText("Du skal gætte følgende word: " + galgelogik.getSynligtOrd());
        if (galgelogik.getBrugteBogstaver().size() > 0) {
            if (galgelogik.erSidsteBogstavKorrekt() && !usedLetter) {
                mediaPlayer = MediaPlayer.create(this, R.raw.points);
                mediaPlayer.start();
                points.setText("Points: " + pointManager.givPoint());
                switchText.setText("Tillykke!! Du gættede rigigt!");
                guessedLetters.setText("Dine brugte bogstaver er: " + galgelogik.getBrugteBogstaver());

            } else {
                switchText.setText("Du gættede desværre forkert... Prøv igen.");
                guessedLetters.setText("Dine brugte bogstaver er: " + galgelogik.getBrugteBogstaver());
            }

            if (galgelogik.erSpilletVundet()) {
                mediaPlayer = MediaPlayer.create(this, R.raw.victory);
                mediaPlayer.setVolume(4F, 4F);
                mediaPlayer.start();
                SharedPreferences.Editor editor = getSharedPreferences(prefsFile, MODE_PRIVATE).edit();
                localHighscoreName.add((player.getName() + ": "));
                localHighscore.add(pointManager.getNumberOfPoints()+"");
                editor.putString("name", player.getName() + ": ");
                editor.putString("highscore", pointManager.getNumberOfPoints()+"");
                editor.apply();
                /*
                localHighscore.add(player.getName() + ": " + pointManager.getNumberOfPoints());
                SharedPreferences.Editor editor = getSharedPreferences(prefsFile, MODE_PRIVATE).edit();
                editor.putStringSet("highscore", localHighscore);
                editor.apply();
                 */
                Intent i = new Intent(this, WinnerActivity.class);
                startActivity(i);
                finish();

            }
            if (galgelogik.erSpilletTabt()) {
                mediaPlayer = MediaPlayer.create(this, R.raw.lost);
                mediaPlayer.start();
                Intent i = new Intent(this, LoserActivity.class);
                startActivity(i);
                finish();
            }
        } else {
            guessedLetters.setText(getString(R.string.gættedeBogstaverStart) + " " + galgelogik.getBrugteBogstaver());
        }

    }

    private void updateHangman() {
        usedLetter = false;
        if (galgelogik.getBrugteBogstaver().contains(typeField.getText().toString())) {
            usedLetter = true;
        }
        galgelogik.gætBogstav(typeField.getText().toString());
        if (!galgelogik.erSidsteBogstavKorrekt() && !usedLetter) {
            points.setText("Points: " + pointManager.tagPoint());
            numberOfWrongGuesses++;

            switch (numberOfWrongGuesses) {
                case 1:
                    hangmanImage.setImageResource(R.drawable.galge);
                    break;
                case 2:
                    hangmanImage.setImageResource(R.drawable.forkert1);
                    break;
                case 3:
                    hangmanImage.setImageResource(R.drawable.forkert2);
                    break;
                case 4:
                    hangmanImage.setImageResource(R.drawable.forkert3);
                    break;
                case 5:
                    hangmanImage.setImageResource(R.drawable.forkert4);
                    break;
                case 6:
                    hangmanImage.setImageResource(R.drawable.forkert5);
                    break;
                case 7:
                    hangmanImage.setImageResource(R.drawable.forkert6);
                    break;
            }

        }
    }

    private void showHangman() {
        switch (numberOfWrongGuesses) {
            case 1:
                hangmanImage.setImageResource(R.drawable.galge);
                break;
            case 2:
                hangmanImage.setImageResource(R.drawable.forkert1);
                break;
            case 3:
                hangmanImage.setImageResource(R.drawable.forkert2);
                break;
            case 4:
                hangmanImage.setImageResource(R.drawable.forkert3);
                break;
            case 5:
                hangmanImage.setImageResource(R.drawable.forkert4);
                break;
            case 6:
                hangmanImage.setImageResource(R.drawable.forkert5);
                break;
            case 7:
                hangmanImage.setImageResource(R.drawable.forkert6);
                break;
        }
    }

    //endregion

    //region Public methods

    public static void setNumberOfWrongGuesses(int numberOfWrongGuesses) {
        Game.numberOfWrongGuesses = numberOfWrongGuesses;
    }

    //endregion


}
