package com.example.galgeleggit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HighScore extends AppCompatActivity implements AdapterView.OnItemClickListener {

    /**
     * Benyt static variabler til at hente informationer
     */
    public static final String prefsFile = "PrefsFile";
    List<String> arrayList = new ArrayList<>();
    String[] array = new String[5];
    static int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       /* preferences = PreferenceManager.getDefaultSharedPreferences(this);
        stringSet = preferences.getStringSet("highScore",stringSet);

        for(int i = 0; i<stringSet.size(); i++){

        }
        */

        SharedPreferences prefs = getSharedPreferences(prefsFile, MODE_PRIVATE);
        String highscore = prefs.getString("highscore", "Ingen highscore endnu");
        //array[counter] = highscore;
        arrayList.add(highscore);
        arrayList.toArray();
       try {
           //ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, android.R.id.text1, game.getLokalHighscore());
           ArrayAdapter<String> itemsAdapter =
                   new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayList);
           ListView listView = new ListView(this);
           listView.setOnItemClickListener(this);
           listView.setAdapter(itemsAdapter);
           itemsAdapter.notifyDataSetChanged();


           setContentView(listView);
       }catch (NullPointerException e){
           e.printStackTrace();
       }
        //counter++;
    }

    public void onItemClick(AdapterView<?> liste, View v, int position, long id) {
        Toast.makeText(this, "Klik på " + position, Toast.LENGTH_SHORT).show();
    }

}
