package com.borleone.earthquakecatalog.app;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.borleone.earthquakecatalog.adapter.ListEarthquakesAdapter;
import com.borleone.earthquakecatalog.model.EarthquakesListResponse;
import com.borleone.earthquakecatalog.model.Feature;
import com.borleone.earthquakecatalog.model.Metadata;
import com.borleone.earthquakecatalog.rest.EarthquakesListAPI;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GetListEarthquakes {

    private static final String TAG = GetListEarthquakes.class.getSimpleName();
    private static Retrofit retrofit = null;
    private String BASE_URL = "https://earthquake.usgs.gov/fdsnws/event/1/";
    private List<Feature> listEarthquakes = new ArrayList<>();
    private Context mContext;
    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;
    private ListEarthquakesAdapter listEarthquakesAdapter;

    GetListEarthquakes(Context mContext) {
        this.mContext = mContext;
    }

    public List<Feature> getListEarthquakes() {
        return listEarthquakes;
    }

    public void setRecyclerView(RecyclerView mRecyclerView) {
        this.mRecyclerView = mRecyclerView;
    }

    public void setProgressBar(ProgressBar mProgressBar) {
        this.mProgressBar = mProgressBar;
    }

    void loadEarthquakeList(String timePeriod, String minMagnitude) {

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        final EarthquakesListAPI earthquakesListAPI = retrofit.create(EarthquakesListAPI.class);

        Call<EarthquakesListResponse> call = earthquakesListAPI.getList("geojson", timePeriod, minMagnitude);

        call.enqueue(new Callback<EarthquakesListResponse>() {

            @Override
            public void onResponse(Call<EarthquakesListResponse> call, Response<EarthquakesListResponse> response) {
                try {
                    Metadata metadata = response.body().getMetadata();
                    Log.e(TAG, "count: " + metadata.getCount());
                    listEarthquakes = response.body().getFeatures();
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }

                // close loading bar after response
                mProgressBar.setVisibility(View.GONE);

                listEarthquakesAdapter = new ListEarthquakesAdapter(mContext, listEarthquakes);
                mRecyclerView.setAdapter(listEarthquakesAdapter);
            }


            @Override
            public void onFailure(Call<EarthquakesListResponse> call, Throwable throwable) {
                Log.e(TAG, throwable.toString());
                // close loading bar after response
                mProgressBar.setVisibility(View.GONE);
            }
        });
    }
}
