package com.borleone.earthquakecatalog.app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.borleone.earthquakecatalog.R;
import com.borleone.earthquakecatalog.adapter.ListEarthquakesAdapter;

import java.text.DateFormat;
import java.util.Date;
import java.util.TimeZone;

public class ShowListActivity extends AppCompatActivity {

    private static final String TAG = ShowListActivity.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;
    private String minMagnitude, timePeriod;
    GetListEarthquakes getListEarthquakes = new GetListEarthquakes(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_list);

        Intent intent = getIntent();
        timePeriod = intent.getStringExtra("time_period");
        minMagnitude = intent.getStringExtra("min_magnitude");

        timePeriod = setTimeParam(timePeriod);

        getListEarthquakes.loadEarthquakeList(timePeriod, minMagnitude);

        mProgressBar = findViewById(R.id.progressBar);
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        getListEarthquakes.setRecyclerView(mRecyclerView);
        getListEarthquakes.setProgressBar(mProgressBar);
    }

    private String setTimeParam(String timePeriod) {
        Date date;

        switch (timePeriod) {
            case "1 hour":
                date = new Date(System.currentTimeMillis() - 3600 * 1000);
                break;
            case "1 day":
                date = new Date(System.currentTimeMillis() - 3600 * 1000 * 24);
                break;
            case "7 days":
                date = new Date(System.currentTimeMillis() - 3600 * 1000 * 24 * 7);
                break;
            default:
                return "";
        }
        String time = date.toString();
        Log.e(TAG, time);
        return time;
    }
}