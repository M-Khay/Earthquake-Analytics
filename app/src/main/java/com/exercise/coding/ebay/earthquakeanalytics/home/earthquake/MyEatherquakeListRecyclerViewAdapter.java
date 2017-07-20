package com.exercise.coding.ebay.earthquakeanalytics.home.earthquake;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.exercise.coding.ebay.earthquakeanalytics.R;
import com.exercise.coding.ebay.earthquakeanalytics.data.model.Earthquake;
import com.exercise.coding.ebay.earthquakeanalytics.data.model.EarthquakeListData;
import com.exercise.coding.ebay.earthquakeanalytics.util.ActivityUtil;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * {@link RecyclerView.Adapter} that can display a {@link EarthquakeListData} for Unit testing and makes a call to the
 * specified {@link EarthquakeListViewFragment.OnListFragmentInteractionListener}.
 */
public class MyEatherquakeListRecyclerViewAdapter extends RecyclerView.Adapter<MyEatherquakeListRecyclerViewAdapter.ViewHolder> {

    private final List<Earthquake> mValues;
    private final EarthquakeListViewFragment.OnListFragmentInteractionListener mListener;
    private final Context mContext;

    public MyEatherquakeListRecyclerViewAdapter(List<Earthquake> items, EarthquakeListViewFragment.OnListFragmentInteractionListener listener, Context context) {
        mValues = items;
        mListener = listener;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_eatherquakelist, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);


        holder.dateTime.setText("Date : " + mValues.get(position).getDatetime().substring(0, 10));
        holder.magnitude.setText("Magnitude : " + mValues.get(position).getMagnitude());
        new convertLatLngToCountryName().execute(holder);

        ColorCode colorCode = ActivityUtil.getColorCode(mValues.get(position).getMagnitude());
        switch (colorCode) {
            case YELLOW:
                holder.mView.setBackgroundColor(mContext.getResources().getColor(R.color.yellow));
                break;
            case RED:
                holder.mView.setBackgroundColor(mContext.getResources().getColor(R.color.red));
                break;
            case ORANGE:
                holder.mView.setBackgroundColor(mContext.getResources().getColor(R.color.orange));
                break;
        }

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView dateTime;
        public final TextView magnitude;
        public final TextView country;

        public Earthquake mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            dateTime = (TextView) view.findViewById(R.id.date_time);
            magnitude = (TextView) view.findViewById(R.id.magnitude);
            country = (TextView) view.findViewById(R.id.country);


        }

        @Override
        public String toString() {
            return super.toString() + " '" + dateTime.getText() + "'" + " '" + magnitude.getText() + "'" + "'";
        }
    }

    private class convertLatLngToCountryName extends AsyncTask<ViewHolder, Void, List<Address>> {
        ViewHolder holder;

        @Override
        protected List<Address> doInBackground(ViewHolder... params) {
            holder = params[0];
            Geocoder geocoder;
            List<Address> addresses = null;
            geocoder = new Geocoder(mContext, Locale.getDefault());

            try {
                addresses = geocoder.getFromLocation(holder.mItem.getLat(), holder.mItem.getLng(), 5); // Here 5 represent max location result to returned, by documents it recommended 1 to 5
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }


            return addresses;
        }

        @Override
        protected void onPostExecute(List<Address> addresses) {

            if (addresses != null && addresses.size() != 0) {
                holder.country.setText("Location : " + addresses.get(0).getCountryName());
            } else {
                holder.country.setText("Location : " + "View it on map");
            }

        }
    }
}
