package com.exercise.coding.ebay.earthquakeanalytics.home.earthquake;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.exercise.coding.ebay.earthquakeanalytics.R;
import com.exercise.coding.ebay.earthquakeanalytics.util.ActivityUtil;

public class EarthquakeListActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // set up toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//      unused.
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        // create the fragment
        EarthquakeListFragment earthquakeListFragment = (EarthquakeListFragment) getSupportFragmentManager().findFragmentById(R.id.content_frame);

        if (earthquakeListFragment == null) {
            earthquakeListFragment = EarthquakeListFragment.newInstance();
            ActivityUtil.addFragmentToActivity(getSupportFragmentManager(), earthquakeListFragment, R.id.content_frame);
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

        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
