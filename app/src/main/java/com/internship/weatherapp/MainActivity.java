package com.internship.weatherapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
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
    private static String LOCATION="Chisinau";
    private static String MODE="json";
    private static String UNITS="metric";
    private static int DAYS_COUNT=5;
    private SharedPreferences sharedPref ;

    @Override
    protected void onResume() {
        makeRequest();
        Boolean themePref= sharedPref.getBoolean("DarkTheme",true);
        Boolean locationPref= sharedPref.getBoolean("Location",true);
        if (locationPref)
   setTitle(getResources().getString(R.string.Weather)+" "+ LOCATION);
        else
setTitle(getResources().getString(R.string.Weather));

        super.onResume();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ButterKnife.bind(this);
        unbinder = ButterKnife.bind(this);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
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
        ApiFactory apiFactory= new ApiFactory();
        apiFactory.createAPI(getBaseContext()).getData(LOCATION, MODE, UNITS, DAYS_COUNT).enqueue(new Callback<WeatherResponse>() {

            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                weatherList = response.body();
                Toast.makeText(MainActivity.this, getResources().getText(R.string.on_response_text), Toast.LENGTH_SHORT).show();
                mAdapter = new Adapter(weatherList,getBaseContext());
                recyclerView.setAdapter(mAdapter);
                swiperefresh.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, getResources().getText(R.string.on_failure_text), Toast.LENGTH_SHORT).show();
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
                startActivity(new Intent(this,SettingsActivity.class));
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



