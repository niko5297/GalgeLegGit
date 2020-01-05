package com.example.galgeleggit.view;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
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

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.galgeleggit.R;
import com.github.jinatonic.confetti.CommonConfetti;
import com.shreyaspatil.MaterialDialog.MaterialDialog;

public class VinderAktivitet extends AppCompatActivity implements View.OnClickListener {

    //region Fields

    Button nytspil, tilbage;
    private MaterialDialog mSimpleDialog;
    ViewGroup container;
    TextView vinder;
    ImageView billede;

    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vinder_aktivitet);
        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        nytspil = findViewById(R.id.nytspil2);
        tilbage = findViewById(R.id.tilStart);
        vinder = findViewById(R.id.vinder);
        billede = findViewById(R.id.trophy);
        container = findViewById(R.id.container);

        vinder.setText("DU VANDT SPILLET!! \n\nStort tillykke med sejren.\nDin score er nu lokalt gemt i Highscore!\n\n" +
                "Ordet du har gættet er: " + MainActivity.galgelogik.getOrdet() +
                "\nog du fik kun " + MainActivity.galgelogik.getAntalForkerteBogstaver() + " forkerte bogstaver");

        nytspil.setOnClickListener(this);
        tilbage.setOnClickListener(this);

        MainActivity.erSpilletIGang = false;

        runAnimation();
        runConfetti();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.game_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


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

    @SuppressLint("RestrictedApi")
    @Override
    public void onClick(View view) {


        if (view == nytspil) {
            buildDialog();
            mSimpleDialog.show();
            /*
            MainActivity.galgelogik.nulstil();
            Game.setAntalForkerteGæt(0);
            Intent i = new Intent(this,MainActivity.class);
            startActivity(i);
            finish();

             */
        }

        if (view == tilbage) {
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
            finish();
        }

    }

    //region Support methods

    private void runAnimation() {
        Animation rotate = AnimationUtils.loadAnimation(this, R.anim.rotate_image);
        billede.startAnimation(rotate);
    }

    private void runConfetti() {
        container.post(new Runnable() {
            @Override
            public void run() {
                CommonConfetti.rainingConfetti(container, new int[]{Color.RED, Color.BLACK, Color.GREEN, Color.BLUE, Color.YELLOW}).stream(3000);
            }
        });
    }

    private void setNytspil() {
        MainActivity.erSpilletIGang = true;
        MainActivity.nytSpil = false;
        MainActivity.galgelogik.nulstil();
        Game.setAntalForkerteGæt(0);
        Intent i = new Intent(this, Game.class);
        startActivity(i);
        finish();
    }

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
