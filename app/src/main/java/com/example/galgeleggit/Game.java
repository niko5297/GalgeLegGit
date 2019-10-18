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
    Button tjekBogstav, startNytSpil;
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
        startNytSpil = findViewById(R.id.nytSpil);
        startNytSpil.setOnClickListener(this);
        tjekBogstav.setOnClickListener(this);


        ord.setText("Du skal gætte følgende ord: " + galgelogik.getSynligtOrd());

        gættedeBogstaver.setText("Du har gættet på følgende bogstaver: " + galgelogik.getBrugteBogstaver());





        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

    }

    @Override
    public void onClick(View view) {
        if (view == tjekBogstav && skriveFelt.getText().toString().length() < 1){
            skriveFelt.setError("Du har skrevet for få bogstaver. Skriv et bogstav for at gætte");
        }

        if (view == tjekBogstav && skriveFelt.getText().toString().length() > 1){
            skriveFelt.setError("Du har skrevet for mange bogstaver. Skriv et bogstav for at gætte");
        }

        if (view == tjekBogstav && skriveFelt.getText().toString().length() ==1) {
            System.out.println(skriveFelt.getText());
            opdaterGalge();
            opdaterOrdOgGættedeBogstaver();


        }

        if (view == startNytSpil){
            galgelogik.nulstil();
            antalForkerteGæt = 0;
            billede.setImageDrawable(null);
            opdaterOrdOgGættedeBogstaver();
            AlertDialog.Builder tabtDialog = new AlertDialog.Builder(this);
            tabtDialog.setTitle("Nyt spil");
            tabtDialog.setMessage("Du har startet et nyt spil");
            tabtDialog.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {
                }
            });
            tabtDialog.show();

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
        }
        return super.onOptionsItemSelected(item);
    }
    private void opdaterOrdOgGættedeBogstaver() {
        ord.setText("Gæt ordet: " + galgelogik.getSynligtOrd() + "\n\n");
        if (galgelogik.getBrugteBogstaver().size()>0) {
            if (galgelogik.erSidsteBogstavKorrekt()) {
                gættedeBogstaver.setText("Du gættede rigigt! \n\nDu har gættet på følgende bogstaver: " + galgelogik.getBrugteBogstaver());

            } else {
                gættedeBogstaver.setText("Du gættede desværre forkert... prøv igen \n\nDu har gættet på følgende bogstaver: " + galgelogik.getBrugteBogstaver());
            }

            if (galgelogik.erSpilletVundet()) {
                ord.setText("\nDu har vundet");
                AlertDialog.Builder vindDialog = new AlertDialog.Builder(this);
                vindDialog.setTitle("DU VANDT");
                vindDialog.setMessage("Stort tillykke med sejren!");
                vindDialog.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                    }
                });
                vindDialog.show();
            }
            if (galgelogik.erSpilletTabt()) {
                ord.setText("Du har tabt, ordet var : " + galgelogik.getOrdet());
                AlertDialog.Builder tabtDialog = new AlertDialog.Builder(this);
                tabtDialog.setTitle("Du tabte desværre");
                tabtDialog.setMessage("Bedre held næste gang. Du kan altid starte et nyt spil");
                tabtDialog.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                    }
                });
                tabtDialog.show();
            }
        }
        else {
            gættedeBogstaver.setText(getString(R.string.gættedeBogstaverStart) + " " + galgelogik.getBrugteBogstaver());
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
