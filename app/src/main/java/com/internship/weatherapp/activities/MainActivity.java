package com.internship.weatherapp.activities;


import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.internship.weatherapp.WeatherStorage;
import com.internship.weatherapp.fragments.WeatherDetailFragment;
import com.internship.weatherapp.interfaces.OnItemClickListenerInterface;
import com.internship.weatherapp.R;
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

public class MainActivity extends AppCompatActivity implements OnItemClickListenerInterface {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String THEME_PREFERENCE = "theme_pref";
    private static final String WEATHER_DATA = "Mountain View, CA 94043";
    private static final String LIGHT_THEME = "Theme is light";
    private static final String DARK_THEME = "Theme is dark";

    private int SETTINGS_ACTION = 1;

    @BindView(R.id.toolbar)
    public Toolbar toolbar;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.swipeContainer)
    SwipeRefreshLayout swipeContainer;

    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;

    Boolean isPhone;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String themeName = sharedPreferences.getString(THEME_PREFERENCE, getString(R.string.theme_light));
        String themeState = null;

        if (themeName.equals(getString(R.string.theme_dark))) {
            setTheme(R.style.DarkTheme);
            themeState = DARK_THEME;
        } else if (themeName.equals(getString(R.string.theme_light))) {
            setTheme(R.style.LightTheme);
            themeState = LIGHT_THEME;
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        if (getResources().getBoolean(R.bool.portrait_only)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            isPhone = true;
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            isPhone = false;
        }

        if (themeState != null) {
            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, themeState, Snackbar.LENGTH_LONG);
            snackbar.show();
        }

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

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
        new LoadWeatherTask().execute();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadItems();
    }

    void onItemsLoadComplete(List<WeatherItem> weatherList) {

        Logger.logString(TAG, getString(R.string.str_received_items) + weatherList.size());

        WeatherStorage.getInstance().setList(weatherList);

        recyclerView.setAdapter(new WeatherAdapter(getApplicationContext(), this));
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
            case android.R.id.home:
                onBackPressed();
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        recyclerView.setVisibility(View.VISIBLE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
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

    @Override
    public void onItemClick(int position) {
        Bundle args = new Bundle();
        args.putInt(WeatherDetailFragment.POSITION_KEY, position);

        WeatherDetailFragment verticalFragment = new WeatherDetailFragment();
        verticalFragment.setArguments(args);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        if (isPhone) {
            ft.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
            ft.replace(R.id.frameLayout, verticalFragment);
            ft.addToBackStack(null);

            recyclerView.setVisibility(View.INVISIBLE);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        } else {
            ft.setCustomAnimations(R.anim.slide_in, R.anim.slide_out);
            ft.replace(R.id.frameLayout2, verticalFragment);
        }

        ft.commit();

    }


    private class LoadWeatherTask extends AsyncTask<Void, Void, Void> {

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
                onItemsLoadComplete(response.body().getWeatherItem());
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                Logger.logString(TAG, getString(R.string.str_received_items) + t.toString());
                Snackbar snackbar = Snackbar
                        .make(coordinatorLayout, getString(R.string.msg_failure), Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });
    }
}