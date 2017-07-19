package com.exercise.coding.ebay.earthquakeanalytics.home.earthquake;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.SupportActivity;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.exercise.coding.ebay.earthquakeanalytics.R;
import com.exercise.coding.ebay.earthquakeanalytics.data.model.Earthquake;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;
import java.util.zip.Inflater;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EarthquakeMapViewFragment.OnMapFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EarthquakeMapViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EarthquakeMapViewFragment extends Fragment implements EarthquakeListContract.View {
    private OnMapFragmentInteractionListener mListener;
    private static GoogleMap map;
    private static Context context;
    public EarthquakeMapViewFragment() {
        // Required empty public constructor
    }

    public static EarthquakeMapViewFragment newInstance(EarthquakeListActivity activity) {
        EarthquakeMapViewFragment fragment = new EarthquakeMapViewFragment();

        SupportMapFragment supportMapFragment = (SupportMapFragment) activity.getSupportFragmentManager().findFragmentById(R.id.map);

        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;

            }
        });
        context= activity;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_earthquake_map_view, container, false);
//        SupportMapFragment supportMapFragment  = (SupportMapFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.map);

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onMapFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnMapFragmentInteractionListener) {
            mListener = (OnMapFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
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

            String iconTitle =  "Date : "+ dateTime + "\n" + "Depth : "+ depth+ "\n"+ "Source :" + src;


            LatLng position = new LatLng(latitude, longitude);
            Bitmap bm =getMarkerBitmapFromView(magnitude);
            map.addMarker(new MarkerOptions().position(position)
                    .title(iconTitle))
                    .setIcon(BitmapDescriptorFactory.fromBitmap(bm));
            map.moveCamera(CameraUpdateFactory.newLatLng(position));
        }
    }

//    Create a color coded bitmap icon with magnitude value written on it.
    private Bitmap getMarkerBitmapFromView(double magnitude) {

        View customMarkerView = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.google_map_marker, null);
        ImageView markerImageView = (ImageView) customMarkerView.findViewById(R.id.background_image);
        customMarkerView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        customMarkerView.layout(0, 0, customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight());
        customMarkerView.buildDrawingCache();

        TextView textView =  (TextView) customMarkerView.findViewById(R.id.text_view);
        textView.setText(magnitude+"");
        Bitmap returnedBitmap = Bitmap.createBitmap(customMarkerView.getWidth(), customMarkerView.getHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        canvas.drawColor(Color.WHITE, PorterDuff.Mode.SRC_IN);
        Drawable drawable = customMarkerView.getBackground();
        if (drawable != null)
            drawable.draw(canvas);
        customMarkerView.draw(canvas);
        return returnedBitmap;
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
