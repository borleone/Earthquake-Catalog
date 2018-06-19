package com.borleone.earthquakecatalog.app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.borleone.earthquakecatalog.R;
import com.borleone.earthquakecatalog.model.EarthquakesListResponse;
import com.borleone.earthquakecatalog.model.Metadata;
import com.borleone.earthquakecatalog.rest.EarthquakesListAPI;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private Spinner mSpinnerTime, mSpinnerMinMagnitude;
    private Button mButtonSearch;
    private String minMagnitude, timePeriod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize UI references
        mSpinnerTime = findViewById(R.id.spinner_time);
        mSpinnerMinMagnitude = findViewById(R.id.spinner_min_magnitude);
        mButtonSearch = findViewById(R.id.button_search);

        // Creating adapter for spinner - min magnitude
        ArrayAdapter<CharSequence> magnitudeAdapter = ArrayAdapter.createFromResource(this, R.array.magnitude_array, android.R.layout.simple_spinner_item);

        magnitudeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        mSpinnerMinMagnitude.setAdapter(magnitudeAdapter);

        // Creating adapter for spinner - time
        ArrayAdapter<CharSequence> startTimeAdapter = ArrayAdapter.createFromResource(this, R.array.time_array, android.R.layout.simple_spinner_item);

        startTimeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        mSpinnerTime.setAdapter(startTimeAdapter);

        //if(timePeriod!=null)mSpinnerTime

        mButtonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePeriod = mSpinnerTime.getSelectedItem().toString();
                minMagnitude = mSpinnerMinMagnitude.getSelectedItem().toString();
                //loadEarthquakeList();
                Intent intent = new Intent(MainActivity.this, ShowListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("time_period", timePeriod);
                intent.putExtra("min_magnitude", minMagnitude);
                startActivity(intent);
            }
        });
    }

}
