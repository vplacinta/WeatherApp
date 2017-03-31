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

import com.internship.weatherapp.models.WeatherItem;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<WeatherItem> mDataset;

    private SharedPreferences sp;
    private Context context;

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_day)
        TextView day;
        @BindView(R.id.tv_state)
        TextView state;
        @BindView(R.id.tv_temp_day)
        TextView tempDay;
        @BindView(R.id.tv_temp_night)
        TextView tempNight;

        @BindView(R.id.iv_item)
        ImageView image;

        ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }

    }

    // Provide a suitable constructor
    MyAdapter(List<WeatherItem> myDataset, Context context) {
        mDataset = myDataset;
        this.context = context;
    }

    // Create new views
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        sp = PreferenceManager.getDefaultSharedPreferences(context);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        WeatherItem weather = mDataset.get(position);

        String state = weather.getWeather().get(0).getMain();
        holder.state.setText(state);

        Boolean date_pref = sp.getBoolean("pref_date", true);
        Boolean night_temp_pref = sp.getBoolean("pref_temp_night", true);
        Boolean day_temp_pref = sp.getBoolean("pref_temp_day", true);

        holder.image.setImageResource((Integer) weather.getWeather().get(0).getIconPath());

        if (date_pref.equals(false)) {
            holder.day.setVisibility(View.INVISIBLE);
        } else {

            String day = weather.getDt(); // Get date in unix-format
            String substr = day.substring(0, day.indexOf(".")); // Get substring drom string
            long longSubstr = Long.parseLong(substr);

            Date date = new Date(longSubstr * 1000L); // *1000 is to convert seconds to milliseconds
            SimpleDateFormat sdf = new SimpleDateFormat("EEE, MMM d, ''yy"); // the format of your date
            String formattedDate = sdf.format(date);
            System.out.println(formattedDate);

            holder.day.setText(formattedDate);
        }

        if (night_temp_pref.equals(false)) {
            holder.tempNight.setVisibility(View.INVISIBLE);
        } else {
            String tempNight = weather.getTemp().getNight().toString();
            holder.tempNight.setText(tempNight + " \u2103");
        }

        if (day_temp_pref.equals(false)) {
            holder.tempDay.setVisibility(View.INVISIBLE);
        } else {
            String tempday = weather.getTemp().getMorn().toString();
            holder.tempDay.setText(tempday + " \u2103");
        }

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}