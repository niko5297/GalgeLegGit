package com.example.galgeleggit;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button start;
    private AsyncTask asyncTask;
    public static Galgelogik galgelogik = new Galgelogik();

    /**
     * ALLE MANGLENDE RETININGSLINJER TILFØJES HER
     * @param savedInstanceState
     */

    //TODO: Man skal kunne vælge noget ud fra en liste. Vælg hvorvidt det skal være ord fra DR eller andre.
    //TODO: Afspil lyd når du vinder og taber
    //TODO: Vis animation
    //TODO: Brug 3. parts bibliotek f.eks. konfetti. Kig på Android Arsenal
    //TODO: Se nedenfor
    /**
     * Krav skal være opfyldt på en meningsfuld/god måde:
     * F.eks at en person vælger et ord på listen og så giver telefonen til sin ven, der skal gætte ordet.
     * At gemme data, men aldrig indlæse dem igen er ikke meningsfuldt.
     * Henter man ord fra DR skal brugeren have at vide hvad der sker og spillet skal ikke begynde før ordene er hentet
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        start = findViewById(R.id.start);
        start.setOnClickListener(this);

        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        asyncTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                try {
                    galgelogik.hentOrdFraDr();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return "Hentet ord fra DR gennemført";
            }

            @Override
            protected void onPostExecute(Object o) {
                asyncTask = null;
            }
        };
        asyncTask.execute();
    }

    @Override
    public void onClick(View view) {
        if (view == start){
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
}
