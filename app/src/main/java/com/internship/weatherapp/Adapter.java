package com.internship.weatherapp;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.internship.weatherapp.model.WeatherResponse;
import java.text.SimpleDateFormat;
import butterknife.BindView;
import butterknife.ButterKnife;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {

    private WeatherResponse weatherList;

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

    public Adapter(WeatherResponse weatherList) {
        this.weatherList = weatherList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_for_recyclerview, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

       java.util.List<com.internship.weatherapp.model.List> list = weatherList.getList();
        java.util.Date date=new java.util.Date(   list.get(position).getDt().longValue() *1000);
        SimpleDateFormat dt1 = new SimpleDateFormat("EE, dd-MM-yyyy");
        holder.dayName.setText(dt1.format(date));

        holder.dayState.setText(list.get(position).getWeather().get(0).getDescription().toString());
        holder.maxTemperature.setText(list.get(position).getTemp().getMax().intValue()+"°C");
        holder.minTemperature.setText(list.get(position).getTemp().getMin().intValue()+"°C");
    }

    @Override
    public int getItemCount() {
        return  weatherList.getList().size();
    }


}