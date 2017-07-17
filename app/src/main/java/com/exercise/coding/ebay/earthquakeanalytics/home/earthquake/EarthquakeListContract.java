package com.exercise.coding.ebay.earthquakeanalytics.home.earthquake;

import com.exercise.coding.ebay.earthquakeanalytics.BasePresenter;
import com.exercise.coding.ebay.earthquakeanalytics.BaseView;
import com.exercise.coding.ebay.earthquakeanalytics.data.model.Earthquake;
import com.exercise.coding.ebay.earthquakeanalytics.data.model.EarthquakeListData;

import java.util.List;

/**
 * Created by kushahuja on 7/14/17.
 */

public interface EarthquakeListContract {

    public interface View extends BaseView<Presenter> {

        public void showEarthquakeInfoList(List<Earthquake> items);


    }

    public interface Presenter extends BasePresenter{
        public void loadEarthquakeInfo();
    }
}
