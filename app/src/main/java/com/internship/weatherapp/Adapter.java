package com.internship.weatherapp;

/**
 * Created by apersin on 21-Mar-17.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {

    private ArrayList<Wheather> wheatherList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.dayName) TextView dayName;
        @BindView(R.id.dayState) TextView dayState;
        @BindView(R.id.maxTemperature) TextView maxTemperature;
        @BindView(R.id.minTemperature) TextView minTemperature;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


    public Adapter(ArrayList<Wheather> wheatherList) {
        this.wheatherList = wheatherList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_for_recyclerview, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Wheather wheather = wheatherList.get(position);
        holder.dayName.setText(wheather.getDayName());
        holder.dayState.setText(wheather.getDayState());
        holder.maxTemperature.setText(wheather.getMaxTemperature());
        holder.minTemperature.setText(wheather.getMinTemperature());

    }

    @Override
    public int getItemCount() {
        return wheatherList.size();
    }


}