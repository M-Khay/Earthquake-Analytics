package com.exercise.coding.ebay.earthquakeanalytics.location;

import android.location.Location;

/**
 * Created by kushahuja on 7/16/17.
 */

public interface LocationAccessListener {
    void onSuccess(Location mLocation);

    void onFailure();
}