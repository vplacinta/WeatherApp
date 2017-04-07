package com.internship.weatherapp.activities;


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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.internship.weatherapp.WeatherStorage;
import com.internship.weatherapp.fragments.VerticalItemFragment;
import com.internship.weatherapp.interfaces.ClickListenerInterface;
import com.internship.weatherapp.R;
import com.internship.weatherapp.listeners.RecyclerTouchListener;
import com.internship.weatherapp.utils.Logger;
import com.internship.weatherapp.views.adapters.WeatherAdapter;
import com.internship.weatherapp.api.ApiClient;
import com.internship.weatherapp.interfaces.ApiInterface;
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
    private static final String THEME_PREFERENCE = "theme_pref";
    private static final String WEATHER_DATA = "Mountain View, CA 94043";

    private int SETTINGS_ACTION = 1;

    @BindView(R.id.toolbar)
    public Toolbar toolbar;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.swipeContainer)
    SwipeRefreshLayout swipeContainer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String themeName = sharedPreferences.getString(THEME_PREFERENCE, getString(R.string.theme_light));

        if (themeName.equals(getString(R.string.theme_dark))) {
            setTheme(R.style.DarkTheme);
            Toast.makeText(this, "Theme has been reset to " + themeName, Toast.LENGTH_SHORT).show();
        } else if (themeName.equals(getString(R.string.theme_light))) {
            setTheme(R.style.LightTheme);
            Toast.makeText(this, "Theme has been reset to " + themeName, Toast.LENGTH_SHORT).show();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerView, new ClickListenerInterface() {
            @Override
            public void onClick(View view, int position) {
//                startActivityForResult(new Intent(getApplicationContext(), DetailsActivity.class), SETTINGS_ACTION);

                Bundle args = new Bundle();
                args.putString("key", String.valueOf(position));

//                FragmentManager fragmentManager = getFragmentManager();
                VerticalItemFragment verticalFragment = new VerticalItemFragment();
                verticalFragment.setArguments(args);

                getFragmentManager().beginTransaction()
                        .replace(android.R.id.content, verticalFragment)
                        .commit();

                // Show DialogFragment
//                verticalFragment.show(fragmentManager, "Dialog Fragment");

            }

            @Override
            public void onLongClick(View view, int position) {
                Toast.makeText(getApplicationContext(), "CLICK 222", Toast.LENGTH_SHORT).show();
            }
        }));

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setIcon(R.mipmap.ic_logo);

        loadItems();

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadItems();
            }
        });

    }

    void loadItems() {
        new MyTask().execute();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadItems();
    }

    void onItemsLoadComplete() {
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
                startActivityForResult(new Intent(this, SettingsActivity.class), SETTINGS_ACTION);
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SETTINGS_ACTION) {
            if (resultCode == SettingsActivity.RESULT_CODE_THEME_UPDATED) {
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

        Call<WeatherResponse> call = apiService.getWeatherData(WEATHER_DATA);

        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                List<WeatherItem> weather = response.body().getWeatherItem();

                Logger.logString(TAG, getString(R.string.str_received_items) + weather.size());
                Logger.logString(TAG, getString(R.string.str_response) + response.code());

                onItemsLoadComplete();
                WeatherStorage.getInstance().setList(weather);

                recyclerView.setAdapter(new WeatherAdapter(weather, getApplicationContext()));
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                Logger.logString(TAG,  getString(R.string.str_received_items) + t.toString());
                Toast.makeText(getApplication(), getString(R.string.msg_failure), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
