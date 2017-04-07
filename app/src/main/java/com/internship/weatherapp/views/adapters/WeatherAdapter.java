package com.internship.weatherapp.views.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.internship.weatherapp.R;
import com.internship.weatherapp.models.WeatherItem;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ViewHolder> {

    private static final String DATE_PREFERENCE = "pref_date";
    private static final String TEMP_NIGHT_PREFERENCE = "pref_temp_night";
    private static final String TEMP_DAY_PREFERENCE = "pref_temp_day";

    private List<WeatherItem> weatherList;

    private SharedPreferences sharedPreferences;
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
        ImageView weatherImage;

        ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }

    public WeatherAdapter(List<WeatherItem> weatherList, Context context) {
        this.weatherList = weatherList;
        this.context = context;
    }

    @Override
    public WeatherAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        WeatherItem weather = weatherList.get(position);

        String state = weather.getWeather().get(0).getMain();
        holder.state.setText(state);

        Boolean date_pref = sharedPreferences.getBoolean(DATE_PREFERENCE, true);
        Boolean night_temp_pref = sharedPreferences.getBoolean(TEMP_NIGHT_PREFERENCE, true);
        Boolean day_temp_pref = sharedPreferences.getBoolean(TEMP_DAY_PREFERENCE, true);

        holder.weatherImage.setImageResource((Integer) weather.getWeather().get(0).getIconPath());

        if (date_pref.equals(false)) {
            holder.day.setVisibility(View.INVISIBLE);
        } else {
            String day = weather.getDt();
            String substr = day.substring(0, day.indexOf("."));
            long longSubstr = Long.parseLong(substr);

            Date date = new Date(longSubstr * 1000L);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, MMM d, ''yy");
            String formattedDate = simpleDateFormat.format(date);
            System.out.println(formattedDate);

            holder.day.setText(formattedDate);
        }

        if (night_temp_pref.equals(false)) {
            holder.tempNight.setVisibility(View.INVISIBLE);
        } else {
            String tempNight = weather.getTemp().getNight().toString();
            holder.tempNight.setText(tempNight + context.getString(R.string.grade_simbol));
        }

        if (day_temp_pref.equals(false)) {
            holder.tempDay.setVisibility(View.INVISIBLE);
        } else {
            String tempday = weather.getTemp().getMorn().toString();
            holder.tempDay.setText(tempday + context.getString(R.string.grade_simbol));
        }

    }

    @Override
    public int getItemCount() {
        return weatherList.size();
    }

}