package com.example.galgeleggit;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


public class Game extends AppCompatActivity implements View.OnClickListener {

    Galgelogik galgelogik = new Galgelogik();
    TextView ord, gættedeBogstaver;
    EditText skriveFelt;
    ImageView billede;
    Button tjekBogstav;
    static int antalForkerteGæt = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        tjekBogstav = findViewById(R.id.tjekBogstav);
        ord = findViewById(R.id.ord);
        gættedeBogstaver = findViewById(R.id.gættedeBogstaver);
        skriveFelt = findViewById(R.id.skriveFelt);
        billede = findViewById(R.id.galge);
        tjekBogstav.setOnClickListener(this);


        ord.setText("Du skal gætte følgende ord: " + galgelogik.getSynligtOrd());

        gættedeBogstaver.setText("Du har gættet på følgende bogstaver: " + galgelogik.getBrugteBogstaver());





        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

    }

    @Override
    public void onClick(View view) {
        if (view == tjekBogstav && skriveFelt.getText().toString().length() ==1) {
            System.out.println(skriveFelt.getText());
            opdaterGalge();
            opdaterOrdOgGættedeBogstaver();

        }
        else {
            skriveFelt.setError("Du har skrevet for mange bogstaver. Skriv et bogstav for at gætte");
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
                dialog.setMessage("Spillet her går ud på....");
                dialog.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                    }
                });
                dialog.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    private void opdaterOrdOgGættedeBogstaver() {
        ord.setText("Gæt ordet: " + galgelogik.getSynligtOrd());
        gættedeBogstaver.setText("Du har gættet på følgende bogstaver: " + galgelogik.getBrugteBogstaver());

        if (galgelogik.erSpilletVundet()) {
            ord.setText("\nDu har vundet");
        }
        if (galgelogik.erSpilletTabt()) {
            ord.setText("Du har tabt, ordet var : " + galgelogik.getOrdet());
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
}
