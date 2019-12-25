package com.example.galgeleggit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HighScore extends AppCompatActivity implements AdapterView.OnItemClickListener {

    public static final String prefsFile = "PrefsFile";
    RecyclerView recyclerView;
    private Adapter adapter;

    //TODO: Lav en ny highscore ranking f.eks. tildeling af points efter hvor mange i træk?

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);

        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        SharedPreferences prefs = getSharedPreferences(prefsFile, MODE_PRIVATE);
        Set<String> highscore = prefs.getStringSet("highscore", null);
        List<String> list = new ArrayList<>(highscore);

        System.out.println(highscore);
        System.out.println(list);
       try {
           recyclerView = findViewById(R.id.recyclerview);
           Collections.sort(list);
           System.out.println(highscore);

           recyclerView.setLayoutManager(new LinearLayoutManager(this));
           adapter = new Adapter(this, list);
           recyclerView.setAdapter(adapter);
       }catch (NullPointerException e){
           e.printStackTrace();
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

    public void onItemClick(AdapterView<?> liste, View v, int position, long id) {
        Toast.makeText(this, "Klik på " + position, Toast.LENGTH_SHORT).show();
    }

}
