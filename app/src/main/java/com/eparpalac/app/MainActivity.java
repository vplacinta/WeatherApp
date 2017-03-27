package com.eparpalac.app;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.eparpalac.app.models.WeatherItem;
import com.eparpalac.app.models.WeatherResponse;
import com.internship.weatherapp.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private MyAdapter mAdapter;

    Handler handler;
    Runnable runnable;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

//    @BindView(R.id.btn_test)
//    Button btn;

    @BindView(R.id.loadingPanel)
    RelativeLayout loadingLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        loadingLayout.setVisibility(View.GONE);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        setSupportActionBar(toolbar);
        getSupportActionBar().setIcon(R.mipmap.ic_logo);

//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new MyTask().execute();
//            }
//        });
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
                Toast.makeText(this, R.string.item_text, Toast.LENGTH_SHORT).show();
                break;

            case R.id.item_refresh:

//                int SPLASH_TIME_OUT = 10000;
//                handler = new Handler();
//                runnable = new Runnable() {
//                    @Override
//                    public void run() {
//                        loadingLayout.setVisibility(View.VISIBLE);
//
//                        new MyTask().execute();
//                    }
//                };
//                handler.postDelayed(runnable, SPLASH_TIME_OUT);

                loadingLayout.setVisibility(View.VISIBLE);
                new MyTask().execute();
                loadingLayout.setVisibility(View.GONE);

                break;
            default:
                break;
        }
        return true;
    }


    private class MyTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loadingLayout.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... params) {

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

            Call<WeatherResponse> call = apiService.getWeatherData("Mountain View, CA 94043");

            call.enqueue(new Callback<WeatherResponse>() {
                @Override
                public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                    List<WeatherItem> weather = response.body().getWeatherItem();

                    Log.d(TAG, "Number of items received: " + weather.size());
                    Log.d(TAG, "Response " + response.code());

                    recyclerView.setAdapter(new MyAdapter(weather));
                }

                @Override
                public void onFailure(Call<WeatherResponse> call, Throwable t) {
                    // Log error here since request failed
                    Log.e(TAG, t.toString());

                    Toast.makeText(getApplication(), getString(R.string.msg_failure), Toast.LENGTH_SHORT).show();
                }
            });
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            loadingLayout.setVisibility(View.GONE);
        }
    }
}
