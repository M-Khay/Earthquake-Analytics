package com.exercise.coding.ebay.earthquakeanalytics.home.earthquake;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.exercise.coding.ebay.earthquakeanalytics.R;
import com.exercise.coding.ebay.earthquakeanalytics.data.model.Earthquake;
import com.exercise.coding.ebay.earthquakeanalytics.util.ActivityUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

/**
 * Created by kushahuja on 7/15/17.
 */


/**
 * Activities that contain this fragment must implement the
 * {@link EarthquakeMapViewFragment.OnMapFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EarthquakeMapViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EarthquakeMapViewFragment extends Fragment implements EarthquakeListContract.View, OnMapReadyCallback {

    private OnMapFragmentInteractionListener mListener;
    private static GoogleMap map;
    private static Context mContext;

    public EarthquakeMapViewFragment() {
        // Required empty public constructor
    }

    public static EarthquakeMapViewFragment newInstance(EarthquakeListActivity activity) {

        EarthquakeMapViewFragment fragment = new EarthquakeMapViewFragment();
        mContext = activity;

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_earthquake_map_view, null, false);

        SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(this);


        return view;
    }


    @Override
    public void onAttach(Context mContext) {
        super.onAttach(mContext);
        if (mContext instanceof OnMapFragmentInteractionListener) {
            mListener = (OnMapFragmentInteractionListener) mContext;
        } else {
            throw new RuntimeException(mContext.toString()
                    + " must implement OnMapFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void setPresenter(EarthquakeListContract.Presenter presenter) {

    }

    @Override
    public void showEarthquakeInfoList(List<Earthquake> items) {


        for (Earthquake item : items) {
            Double latitude = item.getLat();
            Double longitude = item.getLng();

            Double magnitude = item.getMagnitude();

            String dateTime = item.getDatetime();
            Double depth = item.getDepth();
            String src = item.getSrc();

            String iconTitle = "Date : " + dateTime + "\n" + "Depth : " + depth + "\n" + "Source :" + src;


            LatLng position = new LatLng(latitude, longitude);

            Bitmap bm = getMarkerBitmapFromView(magnitude, ActivityUtil.getColorCode(magnitude));
            map.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                @Override
                public View getInfoWindow(Marker marker) {
                    return null;
                }

                @Override
                public View getInfoContents(Marker marker) {
                    LinearLayout info = new LinearLayout(mContext);
                    info.setOrientation(LinearLayout.VERTICAL);

                    TextView title = new TextView(mContext);
                    title.setTextColor(Color.BLACK);
                    title.setGravity(Gravity.CENTER);
                    title.setTypeface(null, Typeface.BOLD);
                    title.setText(marker.getTitle());

                    info.addView(title);
//                    info.addView(snippet);

                    return info;
                }
            });
            map.addMarker(new MarkerOptions().position(position)
                    .title(iconTitle))
                    .setIcon(BitmapDescriptorFactory.fromBitmap(bm));
            map.moveCamera(CameraUpdateFactory.newLatLng(position));
        }

        Snackbar mySnackbar = Snackbar.make(getActivity().findViewById(R.id.snackbar_text),
                "Click on the marker to view details about that earthquake.", Snackbar.LENGTH_SHORT);
        mySnackbar.show();
    }

    //    Create a color coded bitmap icon with magnitude value written on it.
    private Bitmap getMarkerBitmapFromView(double magnitude, ColorCode colorCode) {

        View customMarkerView = ((LayoutInflater) mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.google_map_marker, null);
        customMarkerView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        customMarkerView.layout(0, 0, customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight());
        customMarkerView.buildDrawingCache();

        TextView textView = (TextView) customMarkerView.findViewById(R.id.text_view);
        textView.setText(magnitude + "");


        ImageView background = (ImageView) customMarkerView.findViewById(R.id.background_image);
        switch (colorCode) {
            case YELLOW:
                background.setBackgroundColor(mContext.getResources().getColor(R.color.yellow));
                break;
            case RED:
                background.setBackgroundColor(mContext.getResources().getColor(R.color.red));
                break;
            case ORANGE:
                background.setBackgroundColor(mContext.getResources().getColor(R.color.orange));
                break;
        }
        Bitmap returnedBitmap = Bitmap.createBitmap(customMarkerView.getWidth(), customMarkerView.getHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        canvas.drawColor(Color.YELLOW, PorterDuff.Mode.SRC_IN);

        Drawable drawable = customMarkerView.getBackground();
        if (drawable != null)
            drawable.draw(canvas);
        customMarkerView.draw(canvas);
        return returnedBitmap;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnMapFragmentInteractionListener {
        // TODO: Update argument type and name
        void onMapFragmentInteraction(Uri uri);
    }
}
