package com.example.galgeleggit.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.galgeleggit.R;
import com.example.galgeleggit.model.Adapter;
import com.example.galgeleggit.model.Help;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class HighScoreActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    //region Fields

    public static final String prefsFile = "PrefsFile";
    RecyclerView recyclerView;
    private Adapter adapter;
    private Help help = new Help();
    private static List<String> playerName = new ArrayList<>();
    private static List<Integer> highscore = new ArrayList<>();

    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);

        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        SharedPreferences prefs = getSharedPreferences(prefsFile, MODE_PRIVATE);

        try {

            playerName.add(prefs.getString("name",""));
            highscore.add(prefs.getInt("highscore",0));



           // Set<String> highscore = prefs.getStringSet("highscore", null);
            //List<String> list = new ArrayList<>(highscore);
            recyclerView = findViewById(R.id.recyclerview);
            //Collections.sort(list);
            //System.out.println(highscore);

//          Collections.sort(playerName);
            Arrays.sort(highscore.toArray());
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            adapter = new Adapter(this, playerName,highscore);
            recyclerView.setAdapter(adapter);
        } catch (NullPointerException e) {
            e.printStackTrace();
            System.out.println("WARNING: NO HIGHSCORE FOUND!");
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
        switch (id) {
            case R.id.helpMenu:
                help.inflateHelp(this);
                break;
            case R.id.highscore:
                Toast.makeText(this, "Du befinder dig allerede i Highscore", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    public void onItemClick(AdapterView<?> liste, View v, int position, long id) {
        Toast.makeText(this, "Klik på " + position, Toast.LENGTH_SHORT).show();
    }

}
