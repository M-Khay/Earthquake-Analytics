package com.exercise.coding.ebay.earthquakeanalytics.home.earthquake;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.exercise.coding.ebay.earthquakeanalytics.R;
import com.exercise.coding.ebay.earthquakeanalytics.data.model.Earthquake;
import com.exercise.coding.ebay.earthquakeanalytics.data.model.EarthquakeListData;
import com.exercise.coding.ebay.earthquakeanalytics.dummy.DummyContent.DummyItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link EarthquakeListData} for Unit testing and makes a call to the
 * specified {@link EarthquakeListViewFragment.OnListFragmentInteractionListener}.
 */
public class MyEatherquakeListRecyclerViewAdapter extends RecyclerView.Adapter<MyEatherquakeListRecyclerViewAdapter.ViewHolder> {

    private final List<Earthquake> mValues;
    private final EarthquakeListViewFragment.OnListFragmentInteractionListener mListener;

    public MyEatherquakeListRecyclerViewAdapter(List<Earthquake> items, EarthquakeListViewFragment.OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
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

        holder.dateTime.setText(mValues.get(position).getDatetime());
        holder.magnitude.setText(mValues.get(position).getMagnitude().toString());
        holder.depth.setText(mValues.get(position).getDepth().toString());
        holder.src.setText(mValues.get(position).getSrc().toString());


        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
//                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView dateTime;
        public final TextView magnitude;
        public final TextView src;
        public final TextView depth;
        public Earthquake mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            dateTime= (TextView) view.findViewById(R.id.date_time);
            magnitude= (TextView) view.findViewById(R.id.magnitude);
            src= (TextView) view.findViewById(R.id.src);
            depth= (TextView) view.findViewById(R.id.depth);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + dateTime.getText() + "'"+ " '" + magnitude.getText() + "'"+ " '" + src.getText() + "'"+ " '" + depth.getText() + "'";
        }
    }
}
