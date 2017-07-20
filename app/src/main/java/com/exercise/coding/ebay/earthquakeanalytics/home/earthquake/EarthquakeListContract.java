package com.exercise.coding.ebay.earthquakeanalytics.home.earthquake;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.exercise.coding.ebay.earthquakeanalytics.BasePresenter;
import com.exercise.coding.ebay.earthquakeanalytics.BaseView;
import com.exercise.coding.ebay.earthquakeanalytics.data.model.Earthquake;

import java.util.List;

/**
 * Created by kushahuja on 7/14/17.
 */

public interface EarthquakeListContract {

    // Add methods here to add additional functionalities to the view.

    public interface View extends BaseView<Presenter> {

        public void showEarthquakeInfoList(List<Earthquake> items);

        public void changeMapStyle(int mapView);
    }

    // Add methods here to add additional functionalities to the present.

    public interface Presenter extends BasePresenter{
        public void loadEarthquakeInfo(Context mContext);

        public void showView(Fragment fragment);

        public void hideView(Fragment fragment);
    }
}
