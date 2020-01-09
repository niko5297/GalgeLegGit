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
import com.example.galgeleggit.model.Highscore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @source https://developer.android.com/guide/topics/ui/layout/recyclerview
 */

public class HighScoreActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    //region Fields

    RecyclerView recyclerView;

    public static final String prefsFile = "PrefsFile";

    private Adapter adapter;
    private Help help = new Help();
    private int counter;
    private Highscore highscore;
    private List<Highscore> highscoreList = new ArrayList<>();

    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);

        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        SharedPreferences prefs = getSharedPreferences(prefsFile, MODE_PRIVATE);

        try {

            counter = prefs.getInt("counter",0);

            for (int i = 1; i<=counter; i++){
                highscore = new Highscore(prefs.getString("name_"+i,""), prefs.getInt("highscore_"+i,0));
                System.out.println(highscore.getName() + highscore.getScore());
                highscoreList.add(highscore);
            }

            Collections.sort(highscoreList);

            recyclerView = findViewById(R.id.recyclerview);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            adapter = new Adapter(this, highscoreList);
            recyclerView.setAdapter(adapter);
        } catch (NullPointerException e) {
            e.printStackTrace();
            System.out.println("WARNING: NO HIGHSCORE FOUND!");
        }
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
