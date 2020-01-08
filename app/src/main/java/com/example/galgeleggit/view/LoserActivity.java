package com.example.galgeleggit.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.galgeleggit.R;
import com.example.galgeleggit.model.Help;
import com.shreyaspatil.MaterialDialog.MaterialDialog;

public class LoserActivity extends AppCompatActivity implements View.OnClickListener {

    //region Fields

    Button newGameButton, backButton;
    TextView loser;
    ImageView thumbsImage;
    private MaterialDialog mSimpleDialog;
    private Help help = new Help();

    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loser);
        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        newGameButton = findViewById(R.id.newGameLoser);
        backButton = findViewById(R.id.backLoser);
        loser = findViewById(R.id.loser);
        thumbsImage = findViewById(R.id.thumbs);

        loser.setText("Du tabte desværre.\n\nBedre held næste gang.\nDu kan altid starte et nyt spil ved at klikke nedenfor\n\n" +
                "Ordet du skulle have gættet var: " + MainActivity.galgelogik.getOrdet() + "\nDin score er derfor ikke blevet gemt i Highscore");

        newGameButton.setOnClickListener(this);
        backButton.setOnClickListener(this);

        MainActivity.isGameRunning = false;

        runAnimation();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.game_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

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

    private void runAnimation() {
        Animation rotate = AnimationUtils.loadAnimation(this, R.anim.scale_image);
        thumbsImage.startAnimation(rotate);
    }

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
     * https://android-arsenal.com/details/1/7959
     */
    private void buildDialog() {
        // Simple Material Dialog
        mSimpleDialog = new MaterialDialog.Builder(this)
                .setTitle("Nyt spil?")
                .setMessage("Vil du gerne starte en nyt spil?")
                .setCancelable(false)
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
