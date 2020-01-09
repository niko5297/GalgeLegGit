package com.example.galgeleggit.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.galgeleggit.R;
import com.example.galgeleggit.model.Help;
import com.github.jinatonic.confetti.CommonConfetti;
import com.shreyaspatil.MaterialDialog.MaterialDialog;

/**
 *
 * @source https://android-arsenal.com/details/1/7959
 *
 */
public class WinnerActivity extends AppCompatActivity implements View.OnClickListener {

    //region Fields

    Button newGameButton, backButton;
    ViewGroup container;
    TextView winner;
    ImageView trophyImage;

    private MaterialDialog mSimpleDialog;
    private Help help = new Help();

    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winner);
        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        newGameButton = findViewById(R.id.newGameWinner);
        backButton = findViewById(R.id.backWinner);
        winner = findViewById(R.id.winner);
        trophyImage = findViewById(R.id.trophy);
        container = findViewById(R.id.container);

        winner.setText("DU VANDT SPILLET!! \n\nStort tillykke med sejren.\nDin score er nu lokalt gemt i Highscore!\n\n" +
                "Ordet du har gættet er: " + MainActivity.galgelogik.getOrdet() +
                "\nog du fik kun " + MainActivity.galgelogik.getAntalForkerteBogstaver() + " forkerte bogstaver");

        newGameButton.setOnClickListener(this);
        backButton.setOnClickListener(this);

        MainActivity.isGameRunning = false;

        runAnimation();
        runConfetti();
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

    @SuppressLint("RestrictedApi")
    @Override
    public void onClick(View view) {


        if (view == newGameButton) {
            buildDialog();
            mSimpleDialog.show();
        }

        if (view == backButton) {
            onBackPressed();
            finish();
        }

    }

    //region Support methods
    /**
     * This method runs the winning animation
     */
    private void runAnimation() {
        Animation rotate = AnimationUtils.loadAnimation(this, R.anim.rotate_image);
        trophyImage.startAnimation(rotate);
    }

    /**
     * This method runs the confetti from Android Arsenal
     */
    private void runConfetti() {
        container.post(new Runnable() {
            @Override
            public void run() {
                CommonConfetti.rainingConfetti(container, new int[]{Color.RED, Color.BLACK, Color.GREEN, Color.BLUE, Color.YELLOW}).stream(3000);
            }
        });
    }

    /**
     * This method sets the new game if you decide to run a new game from WinnerActivity
     */
    private void setNytspil() {
        MainActivity.isGameRunning = true;
        MainActivity.newGame = false;
        MainActivity.galgelogik.nulstil();
        Game.pointManager.nulstil();
        Game.setNumberOfWrongGuesses(0);
        Intent i = new Intent(this, Game.class);
        startActivity(i);
        finish();
    }

    /**
     * This method builds the material components dialog with animation
     *
     * @source https://android-arsenal.com/details/1/7959*
     */
    private void buildDialog() {
        // Simple Material Dialog
        mSimpleDialog = new MaterialDialog.Builder(this)
                .setTitle("Nyt spil?")
                .setMessage("Vil du gerne starte en nyt spil?")
                .setCancelable(false)
                .setAnimation("newGame.json")
                .setPositiveButton("Helt sikkert", R.drawable.ic_done_black_24dp, new MaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(com.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int i) {
                        Toast.makeText(getApplicationContext(), "Nyt spil!", Toast.LENGTH_SHORT).show();
                        setNytspil();
                        dialogInterface.dismiss();
                    }
                })
                .setNegativeButton("Ellers tak", R.drawable.ic_close_black_24dp, new MaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(com.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {
                        Toast.makeText(getApplicationContext(), "Annulleret!", Toast.LENGTH_SHORT).show();
                        dialogInterface.dismiss();
                    }
                })
                .build();

    }

    //endregion

}
