package com.internship.weatherapp;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.internship.weatherapp.model.WeatherResponse;
import java.text.SimpleDateFormat;
import butterknife.BindView;
import butterknife.ButterKnife;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {
    private SharedPreferences sharedPref ;
    private WeatherResponse weatherList;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.dayName) TextView dayName;
        @BindView(R.id.dayState) TextView dayState;
        @BindView(R.id.maxTemperature) TextView maxTemperature;
        @BindView(R.id.minTemperature) TextView minTemperature;
        @BindView(R.id.imageView)    ImageView imageView;


        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public Adapter(WeatherResponse weatherList, Context context) {
        this.weatherList = weatherList;
        this.context=context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_for_recyclerview, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        sharedPref = PreferenceManager.getDefaultSharedPreferences(context);

       java.util.List<com.internship.weatherapp.model.List> list = weatherList.getList();
        java.util.Date date=new java.util.Date(   list.get(position).getDt().longValue() *1000);
        SimpleDateFormat dt1 = new SimpleDateFormat("EE, dd-MM-yyyy");
        holder.dayName.setText(dt1.format(date));
        holder.dayState.setText(list.get(position).getWeather().get(0).getDescription().toString());
        holder.maxTemperature.setText(list.get(position).getTemp().getMax().intValue()+"°C");
        holder.minTemperature.setText(list.get(position).getTemp().getMin().intValue()+"°C");


        Boolean dayNamePref= sharedPref.getBoolean("dayName",false);
        Boolean dayStatePref= sharedPref.getBoolean("dayState",false);
        Boolean minTemperaturePref= sharedPref.getBoolean("minTemperature",false);
        Boolean maxTemperaturePref= sharedPref.getBoolean("maxTemperature",false);
        Boolean imageViewPref= sharedPref.getBoolean("imageView",false);

        if (dayNamePref) holder.dayName.setVisibility(View.VISIBLE);
        else
            holder.dayName.setVisibility(View.GONE);

        if (dayStatePref) holder.dayState.setVisibility(View.VISIBLE);
        else
            holder.dayState.setVisibility(View.GONE);

        if (minTemperaturePref) holder.minTemperature.setVisibility(View.VISIBLE);
        else
            holder.minTemperature.setVisibility(View.GONE);

        if (maxTemperaturePref) holder.maxTemperature.setVisibility(View.VISIBLE);
        else
            holder.maxTemperature.setVisibility(View.GONE);

        if (imageViewPref) holder.imageView.setVisibility(View.VISIBLE);
        else
            holder.imageView.setVisibility(View.GONE);




    }

    @Override
    public int getItemCount() {
        return  weatherList.getList().size();
    }


}