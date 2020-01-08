package com.example.galgeleggit.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.galgeleggit.R;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    /**
     * Kilde:
     */

    private List<String> name;
    private List<Integer> highscore;
    private LayoutInflater mInflater;

    // data is passed into the constructor
    public Adapter(Context context, List<String> name, List<Integer> highscore) {
        this.mInflater = LayoutInflater.from(context);
        this.name = name;
        this.highscore = highscore;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recycler_template, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        try {
           // if (name.get(position))
            holder.name.setText(name.get(position));
            holder.highscore.setText(highscore.get(position)+"");
        }catch (IndexOutOfBoundsException e){
            e.printStackTrace();
        }
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return highscore.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, highscore;


        ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.highscoreName);
            highscore =  itemView.findViewById(R.id.highscorePoints);
        }

    }
}