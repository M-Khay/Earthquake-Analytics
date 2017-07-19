package com.exercise.coding.ebay.earthquakeanalytics.home.earthquake;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.exercise.coding.ebay.earthquakeanalytics.R;
import com.exercise.coding.ebay.earthquakeanalytics.data.model.EarthquakeListData;
import com.exercise.coding.ebay.earthquakeanalytics.rest.APIClient;
import com.exercise.coding.ebay.earthquakeanalytics.rest.APIInterface;
import com.exercise.coding.ebay.earthquakeanalytics.util.ActivityUtil;
import com.google.android.gms.maps.SupportMapFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EarthquakeListActivity extends AppCompatActivity implements EarthquakeMapViewFragment.OnMapFragmentInteractionListener {

    private EarthquakeListPresenter mEarthquakeListPresenter;
    private EarthquakeListPresenter mEarthquakeMapPresenter;

    EarthquakeMapViewFragment earthquakeMapViewFragment;
    EarthquakeListViewFragment earthquakeListViewFragment;
    public SupportMapFragment supportMapFragment;
    private boolean toggleMapView = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//         set up toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        // create the fragment
        earthquakeListViewFragment = (EarthquakeListViewFragment) getSupportFragmentManager().findFragmentById(R.id.content_frame);
        supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        ViewCompat.setNestedScrollingEnabled(supportMapFragment.getView(), true);


        if (earthquakeListViewFragment == null) {
            earthquakeListViewFragment = EarthquakeListViewFragment.newInstance();
            ActivityUtil.addFragmentToActivity(getSupportFragmentManager(), earthquakeListViewFragment, R.id.content_frame);
        }

        if (supportMapFragment == null || earthquakeMapViewFragment == null) {
            earthquakeMapViewFragment = EarthquakeMapViewFragment.newInstance(this);
        }

        FloatingActionButton fabChangeViewType = (FloatingActionButton) findViewById(R.id.fab);
        fabChangeViewType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (toggleMapView) {
                    mEarthquakeListPresenter.showView(earthquakeListViewFragment);
                    mEarthquakeMapPresenter.hideView(supportMapFragment);
                    toggleMapView =  false;
                } else {
                    mEarthquakeListPresenter.showView(supportMapFragment);
                    mEarthquakeMapPresenter.hideView(earthquakeListViewFragment);
                    toggleMapView = true;
                }

            }
        });
//         Create the presenter
        mEarthquakeListPresenter = new EarthquakeListPresenter(earthquakeListViewFragment);
        mEarthquakeMapPresenter = new EarthquakeListPresenter(earthquakeMapViewFragment);


        mEarthquakeListPresenter.loadEarthquakeInfo();
        mEarthquakeMapPresenter.loadEarthquakeInfo();


    }


    @Override
    protected void onResume() {
        super.onResume();
        mEarthquakeListPresenter.hideView(earthquakeListViewFragment);

    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        if (fragment instanceof EarthquakeListViewFragment) {
//            mEarthquakeListPresenter.hideView(earthquakeListViewFragment);
        }


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
}
