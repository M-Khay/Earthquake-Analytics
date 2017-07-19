package com.exercise.coding.ebay.earthquakeanalytics.home.earthquake;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.exercise.coding.ebay.earthquakeanalytics.R;
import com.exercise.coding.ebay.earthquakeanalytics.data.model.Earthquake;
import com.exercise.coding.ebay.earthquakeanalytics.data.model.EarthquakeListData;
import com.exercise.coding.ebay.earthquakeanalytics.util.ActivityUtil;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link EarthquakeListData} for Unit testing and makes a call to the
 * specified {@link EarthquakeListViewFragment.OnListFragmentInteractionListener}.
 */
public class MyEatherquakeListRecyclerViewAdapter extends RecyclerView.Adapter<MyEatherquakeListRecyclerViewAdapter.ViewHolder> {

    private final List<Earthquake> mValues;
    private final EarthquakeListViewFragment.OnListFragmentInteractionListener mListener;
    private final Context mContext;
    public MyEatherquakeListRecyclerViewAdapter(List<Earthquake> items, EarthquakeListViewFragment.OnListFragmentInteractionListener listener,Context context) {
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

        holder.dateTime.setText("Date : "+ mValues.get(position).getDatetime());
        holder.magnitude.setText("Magnitude : "+mValues.get(position).getMagnitude().toString());
        holder.depth.setText("Depth : "+mValues.get(position).getDepth().toString());
        holder.src.setText("Source : "+mValues.get(position).getSrc().toString());
        ColorCode colorCode = ActivityUtil.getColorCode(mValues.get(position).getMagnitude());
        switch (colorCode){
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
