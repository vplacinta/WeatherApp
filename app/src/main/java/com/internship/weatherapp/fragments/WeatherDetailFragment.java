package com.internship.weatherapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.internship.weatherapp.R;
import com.internship.weatherapp.WeatherStorage;
import com.internship.weatherapp.models.WeatherItem;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WeatherDetailFragment extends Fragment {

    @BindView(R.id.iv_fr_img)
    ImageView frImage;
    @BindView(R.id.tv_fr_date)
    TextView frDate;
    @BindView(R.id.tv_fr_day)
    TextView frDay;
    @BindView(R.id.tv_fr_night)
    TextView frNight;
    @BindView(R.id.tv_fr_state)
    TextView frState;
    @BindView(R.id.tv_fr_temp_day)
    TextView frTempDay;
    @BindView(R.id.tv_fr_temp_night)
    TextView frTempNight;

    public final static String POSITION_KEY = "position";

    private List<WeatherItem> weatherList = WeatherStorage.getInstance().getList();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_weather_details, container, false);

        ButterKnife.bind(this, view);

        Bundle args = getArguments();
        int position = args.getInt(POSITION_KEY);

        WeatherItem weather = weatherList.get(position);

        String state = weather.getWeather().get(0).getMain();
        frState.setText(state);

        frImage.setImageResource((Integer) weather.getWeather().get(0).getIconPath());

        String day = weather.getDt();
        String substr = day.substring(0, day.indexOf("."));
        long longSubstr = Long.parseLong(substr);

        Date date = new Date(longSubstr * 1000L);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE, MMMM d, yyyy");
        String formattedDate = simpleDateFormat.format(date);
        frDate.setText(formattedDate);

        StringBuilder stringBuilder = new StringBuilder(weather.getTemp().getNight().toString());
        frTempNight.setText(stringBuilder.append(getString(R.string.grade_simbol)));

        stringBuilder = new StringBuilder(weather.getTemp().getMorn().toString());
        frTempDay.setText(stringBuilder.append(getString(R.string.grade_simbol)));

        return view;
    }

}
