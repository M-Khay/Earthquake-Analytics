package com.exercise.coding.ebay.earthquakeanalytics.home.earthquake;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.exercise.coding.ebay.earthquakeanalytics.R;
import com.exercise.coding.ebay.earthquakeanalytics.data.model.EarthquakeListData;
import com.exercise.coding.ebay.earthquakeanalytics.rest.APIClient;
import com.exercise.coding.ebay.earthquakeanalytics.rest.APIInterface;
import com.exercise.coding.ebay.earthquakeanalytics.util.ActivityUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EarthquakeListActivity extends AppCompatActivity {

    private EarthquakeListPresenter mEarthListPresenter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // set up toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // create the fragment
        EarthquakeListFragment earthquakeListFragment = (EarthquakeListFragment) getSupportFragmentManager().findFragmentById(R.id.content_frame);

        if (earthquakeListFragment == null) {
            earthquakeListFragment = EarthquakeListFragment.newInstance();
            ActivityUtil.addFragmentToActivity(getSupportFragmentManager(), earthquakeListFragment, R.id.content_frame);
        }

        // Create the presenter
        mEarthListPresenter = new EarthquakeListPresenter(earthquakeListFragment);
//        mEarthListPresenter  = new EarthquakeListPresenter(
//                Injection.provideTasksRepository(getApplicationContext()), tasksFragment);

        mEarthListPresenter.loadEarthquakeInfo();




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
}
