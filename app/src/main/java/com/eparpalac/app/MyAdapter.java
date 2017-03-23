package com.eparpalac.app;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.internship.weatherapp.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<Weather> mDataset;

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_day)
        TextView day;
        @BindView(R.id.tv_state)
        TextView state;
        @BindView(R.id.tv_temp_day)
        TextView tempDay;
        @BindView(R.id.tv_temp_night)
        TextView tempNight;

        ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }

    // Provide a suitable constructor
    MyAdapter(List<Weather> myDataset) {
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

        Weather weather = mDataset.get(position);

        holder.day.setText(weather.getDay());
        holder.state.setText(weather.getState());
        holder.tempDay.setText(weather.getTempDay());
        holder.tempNight.setText(weather.getTempNight());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}