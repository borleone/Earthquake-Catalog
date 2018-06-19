package com.borleone.earthquakecatalog.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.borleone.earthquakecatalog.R;
import com.borleone.earthquakecatalog.model.Feature;

import java.util.List;
import java.util.Locale;

public class ListEarthquakesAdapter extends RecyclerView.Adapter<ListEarthquakesAdapter.MyViewHolder> {

    private List<Feature> earthquakeList;
    private Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mTextViewMagnitude, mTextViewLocation;

        MyViewHolder(View view) {
            super(view);
            mTextViewMagnitude = view.findViewById(R.id.textView_magnitude);
            mTextViewLocation = view.findViewById(R.id.textView_location);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(final View v) {
            // get position
            int pos = getAdapterPosition();

            // check if item still exists
            if (pos != RecyclerView.NO_POSITION) {
                final Feature clickedEarthquake = earthquakeList.get(pos);

                openWebURL(clickedEarthquake.getProperties().getUrl());

            }
        }
    }

    public ListEarthquakesAdapter(Context context, List<Feature> earthquakeList) {
        this.mContext = context;
        this.earthquakeList = earthquakeList;
    }

    @Override
    public ListEarthquakesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_earthquake, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ListEarthquakesAdapter.MyViewHolder holder, int position) {
        Feature earthquake = earthquakeList.get(position);
        holder.mTextViewMagnitude.setText(String.format(Locale.US,"%,.2f",earthquake.getProperties().getMag()));
        holder.mTextViewLocation.setText(earthquake.getProperties().getPlace());
    }

    @Override
    public int getItemCount() {
        return earthquakeList.size();
    }

    private void openWebURL(String inURL) {
        Intent browse = new Intent(Intent.ACTION_VIEW, Uri.parse(inURL));

        mContext.startActivity(browse);
    }
}
