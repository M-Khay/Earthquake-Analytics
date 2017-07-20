package com.exercise.coding.ebay.earthquakeanalytics;

import com.exercise.coding.ebay.earthquakeanalytics.data.model.Earthquake;

import java.util.List;

/**
 * Created by kushahuja on 7/14/17.
 */

public interface BaseView<T> {

    void setPresenter(T presenter);
    public void showEarthquakeInfoList(List<Earthquake> items);

}
