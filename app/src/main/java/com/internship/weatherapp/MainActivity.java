package com.internship.weatherapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
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

    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;

    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swiperefresh;

    private WeatherResponse weatherList;
    private Adapter mAdapter;
    private Unbinder unbinder;
    private static String LOCATION="Chisinau";
    private static String MODE="json";
    private static String UNITS="metric";
    private static int DAYS_COUNT=5;
    private  static int themeVersion=0;
    private SharedPreferences sharedPref ;
    private  Snackbar snackbar;

    @Override
    protected void onResume() {
        if(themeVersion!=Integer.parseInt(sharedPref.getString("Theme_list", "-1"))) {
            ThemeUtils.changeToTheme(this, Integer.parseInt(sharedPref.getString("Theme_list", "-1")));
        themeVersion=Integer.parseInt(sharedPref.getString("Theme_list", "-1"));
        }
        makeRequest();
        LOCATION=sharedPref.getString("location"," ");
        setTitle(getResources().getString(R.string.Weather) + " "+LOCATION);

        super.onResume();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ThemeUtils.onActivityCreateSetTheme(this);
        setContentView(R.layout.main);
        ButterKnife.bind(this);

        snackbar = Snackbar.make(coordinatorLayout,  getResources().getText(R.string.on_response_text), Snackbar.LENGTH_LONG);

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
                mAdapter = new Adapter(weatherList,getBaseContext());
                recyclerView.setAdapter(mAdapter);
                snackbar.show();
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
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item:

             //  startActivity(new Intent(this,SettingsActivity.class));  //Explicit
                startActivity(new Intent( "android.intent.action_send")); //implicit
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



