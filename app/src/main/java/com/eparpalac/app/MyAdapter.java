package com.eparpalac.app;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.eparpalac.app.models.WeatherItem;
import com.internship.weatherapp.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<WeatherItem> mDataset;

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
    MyAdapter(List<WeatherItem> myDataset) {
        mDataset = myDataset;
    }

    // Create new views
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        WeatherItem weather = mDataset.get(position);

        String day = weather.getDt(); // Get date in unix-format
        String substr = day.substring(0, day.indexOf(".")); // Get substring drom string
        long longSubstr = Long.parseLong(substr);

        Date date = new Date(longSubstr * 1000L); // *1000 is to convert seconds to milliseconds
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, MMM d, ''yy"); // the format of your date
        String formattedDate = sdf.format(date);
        System.out.println(formattedDate);

        String state = weather.getWeather().get(0).getMain();
        String tempday = weather.getTemp().getMorn().toString();
        String tempNight = weather.getTemp().getNight().toString();

        holder.day.setText(formattedDate);
        holder.state.setText(state);
        holder.tempDay.setText(tempday + " \u2103");
        holder.tempNight.setText(tempNight + " \u2103");
        holder.image.setImageResource((Integer) weather.getWeather().get(0).getIconPath());

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}