package com.exercise.coding.ebay.earthquakeanalytics.home.earthquake;

import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.exercise.coding.ebay.earthquakeanalytics.R;
import com.exercise.coding.ebay.earthquakeanalytics.data.model.Earthquake;
import com.exercise.coding.ebay.earthquakeanalytics.location.LocationAccessListener;
import com.exercise.coding.ebay.earthquakeanalytics.location.LocationTracker;
import com.exercise.coding.ebay.earthquakeanalytics.util.ActivityUtil;

/**
 * Created by kushahuja on 7/15/17.
 */

public class EarthquakeListActivity extends AppCompatActivity implements EarthquakeMapViewFragment.OnMapFragmentInteractionListener, EarthquakeListViewFragment.OnListFragmentInteractionListener {

    private EarthquakeListPresenter mEarthquakeListPresenter;
    private EarthquakeListPresenter mEarthquakeMapPresenter;

    EarthquakeMapViewFragment earthquakeMapViewFragment;
    EarthquakeListViewFragment earthquakeListViewFragment;
    private boolean toggleMapView = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // set up toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        // create the fragment
        earthquakeListViewFragment = (EarthquakeListViewFragment) getSupportFragmentManager().findFragmentById(R.id.content_frame);
        earthquakeMapViewFragment = (EarthquakeMapViewFragment) getSupportFragmentManager().findFragmentById(R.id.content_frame_map);

        if (earthquakeListViewFragment == null) {
            earthquakeListViewFragment = EarthquakeListViewFragment.newInstance();
            ActivityUtil.addFragmentToActivity(getSupportFragmentManager(), earthquakeListViewFragment, R.id.content_frame);
        }

        if (earthquakeMapViewFragment == null) {
            earthquakeMapViewFragment = EarthquakeMapViewFragment.newInstance(this);
            ActivityUtil.addFragmentToActivity(getSupportFragmentManager(), earthquakeMapViewFragment, R.id.content_frame_map);
        }

        final FloatingActionButton fabChangeViewType = (FloatingActionButton) findViewById(R.id.fab);
        fabChangeViewType.setImageResource(R.drawable.format_list_bulleted);
        fabChangeViewType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (toggleMapView) {
                    mEarthquakeListPresenter.showView(earthquakeListViewFragment);
                    mEarthquakeMapPresenter.hideView(earthquakeMapViewFragment);

                    fabChangeViewType.setImageResource(R.drawable.google_maps);
                    toggleMapView = false;
                } else {
                    fabChangeViewType.setImageResource(R.drawable.format_list_bulleted);
                    mEarthquakeListPresenter.showView(earthquakeMapViewFragment);
                    mEarthquakeMapPresenter.hideView(earthquakeListViewFragment);
                    toggleMapView = true;
                }

            }
        });

//         Create the presenter
        mEarthquakeListPresenter = new EarthquakeListPresenter(earthquakeListViewFragment);
        mEarthquakeMapPresenter = new EarthquakeListPresenter(earthquakeMapViewFragment);


    }


    @Override
    protected void onResume() {
        super.onResume();

        mEarthquakeListPresenter.hideView(earthquakeListViewFragment);

        mEarthquakeListPresenter.loadEarthquakeInfo(this);
        mEarthquakeMapPresenter.loadEarthquakeInfo(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.about) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMapFragmentInteraction(Uri uri) {

    }

    @Override
    public void onListFragmentInteraction(Earthquake item) {

    }
}
