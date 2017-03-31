package com.internship.weatherapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.internship.weatherapp.models.WeatherItem;
import com.internship.weatherapp.models.WeatherResponse;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private int SETTINGS_ACTION = 1;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.swipeContainer)
    SwipeRefreshLayout swipeContainer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        // ------------------------------ Set Theme ------------------------------
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        String themeName = pref.getString("theme_pref", "Light");

        if (themeName.equals("Dark")) {
            setTheme(R.style.AppTheme2);
        } else if (themeName.equals("Light")) {
            Toast.makeText(this, "set theme", Toast.LENGTH_SHORT).show();
            setTheme(R.style.AppTheme);
        }

        Toast.makeText(this, "Theme has been reset to " + themeName, Toast.LENGTH_SHORT).show();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        // ------------------------------ RecyclerView ------------------------------
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        // --------------------------------- Toolbar ---------------------------------
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setIcon(R.mipmap.ic_logo);

        new MyTask().execute();

        // --------------------------------- Swipe ---------------------------------
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                refreshItems();
            }
        });

        // -------------------------------- Logger ---------------------------------
        new Logger(true);
    }

    void refreshItems() {
        new MyTask().execute();
        // Load complete
        onItemsLoadComplete();
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshItems();
    }

    void onItemsLoadComplete() {
        // Stop refresh animation
        swipeContainer.setRefreshing(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.item_newactivity:
                startActivityForResult(new Intent(this, MyPreferenceActivity.class), SETTINGS_ACTION);
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SETTINGS_ACTION) {
            if (resultCode == MyPreferenceActivity.RESULT_CODE_THEME_UPDATED) {
                finish();
                startActivity(getIntent());
                return;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    private class MyTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            getData();
            return null;
        }

    }

    public void getData() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<WeatherResponse> call = apiService.getWeatherData("Mountain View, CA 94043");

        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                List<WeatherItem> weather = response.body().getWeatherItem();

                Logger.logString(MainActivity.this, "Number of items received: " + weather.size());
                Logger.logString(MainActivity.this, "Response " + response.code());

                recyclerView.setAdapter(new MyAdapter(weather, getApplicationContext()));
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
                Toast.makeText(getApplication(), getString(R.string.msg_failure), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
