package com.example.galgeleggit;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
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
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    Button start;
    Spinner spinner;
    private int spilleMaade;
    private AsyncTask asyncTask;
    public static Galgelogik galgelogik = new Galgelogik();

    /**
     * ALLE MANGLENDE RETININGSLINJER TILFØJES HER
     * @param savedInstanceState
     */

    //TODO: Man skal kunne vælge noget ud fra en liste. Vælg hvorvidt det skal være ord fra DR eller andre.
    //TODO: Vis animation -> Logoet der vises ved tab/vind, så hurtigt spinne rundt
    //TODO: Brug 3. parts bibliotek f.eks. konfetti. Kig på Android Arsenal
    //TODO: Se nedenfor
    /**
     * Krav skal være opfyldt på en meningsfuld/god måde:
     * F.eks at en person vælger et ord på listen og så giver telefonen til sin ven, der skal gætte ordet.
     * At gemme data, men aldrig indlæse dem igen er ikke meningsfuldt.
     * Henter man ord fra DR skal brugeren have at vide hvad der sker og spillet skal ikke begynde før ordene er hentet
     */

    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        start = findViewById(R.id.start);
        start.setOnClickListener(this);

        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        addSpinner();

        /*
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
                Toast.makeText(MainActivity.this, "Hentet ord fra DR gennemført", Toast.LENGTH_SHORT).show();
                asyncTask = null;
            }
        };
        asyncTask.execute();
         */
    }

    @Override
    public void onClick(View view) {
        if (view == start){
            startSpilleMaade(spilleMaade);
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


    private void addSpinner() {
        String[] ordType = {"Almindelige ord", "Hent ord fra DR"};
        spinner = findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, android.R.id.text1, ordType);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setPrompt("Vælg din måde at spille på");
        spinner.setAdapter(adapter);

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        spilleMaade = position;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        Toast.makeText(this, "Intet valgt", Toast.LENGTH_SHORT).show();
    }

    private void startSpilleMaade(int spilleMaade){

        if (spilleMaade == 0){
            //TODO: Her skal du sørge for at det kun er de ord som galgeleg allerede har, bliver spillet med
        }

        if (spilleMaade == 1){
            //TODO: Her skal du hente ord fra DR ved brug af asynctask
        }
    }
}
