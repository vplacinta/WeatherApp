package com.internship.weatherapp;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import com.internship.weatherapp.model.WeatherResponse;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swiperefresh;

    private WeatherResponse weatherList;
    private Adapter mAdapter;
    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ButterKnife.bind(this);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        makeRequest();

        swiperefresh.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        weatherList.getList().clear();
                        mAdapter.notifyDataSetChanged();
                       makeRequest();

                    }
                }
        );
    }

    public void makeRequest() {
        ApiFactory.createAPI().getData("Mountain View", "json", "metric", 5).enqueue(new Callback<WeatherResponse>() {

            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                weatherList = response.body();
                Toast.makeText(MainActivity.this, "Data has updated ", Toast.LENGTH_SHORT).show();
                mAdapter = new Adapter(weatherList);
                recyclerView.setAdapter(mAdapter);
                swiperefresh.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Network problem", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item:
                Toast.makeText(this, "ActivityTwo", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
}



