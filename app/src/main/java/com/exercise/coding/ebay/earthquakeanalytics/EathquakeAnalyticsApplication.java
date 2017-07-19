package com.exercise.coding.ebay.earthquakeanalytics;

import android.app.Application;

/**
 * Created by kushahuja on 7/17/17.
 */

public class EathquakeAnalyticsApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
    }

    // This is called when the overall system is running low on memory,
    // and would like actively running processes to tighten their belts.
    // Overriding this method is totally optional!
    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }
}
