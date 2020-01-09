package com.example.galgeleggit.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.galgeleggit.R;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * @date 11/11/2019
 *
 * @description This class is the custom written adapter, which defines the layout of RecyclerView
 *
 * @source https://developer.android.com/guide/topics/ui/layout/recyclerview
 *
 */

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    //region Fields

    private List<Highscore> highscoreList;
    private LayoutInflater mInflater;

    //endregion

    //region Constructor

    // data is passed into the constructor
    public Adapter(Context context, List<Highscore> highscoreList) {
        this.mInflater = LayoutInflater.from(context);
        this.highscoreList = highscoreList;
    }

    //endregion

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
            holder.name.setText(highscoreList.get(position).getName()+": ");
            holder.highscore.setText(highscoreList.get(position).getScore()+"");
        }catch (IndexOutOfBoundsException e){
            e.printStackTrace();
        }
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return highscoreList.size();
    }

    //region ViewHolder

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, highscore;


        ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.highscoreName);
            highscore =  itemView.findViewById(R.id.highscorePoints);
        }

    }

    //endregion
}