package com.exercise.coding.ebay.earthquakeanalytics.home.earthquake;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

import com.exercise.coding.ebay.earthquakeanalytics.data.model.Earthquake;
import com.exercise.coding.ebay.earthquakeanalytics.data.model.EarthquakeListData;
import com.exercise.coding.ebay.earthquakeanalytics.rest.APIClient;
import com.exercise.coding.ebay.earthquakeanalytics.rest.APIInterface;
import com.google.android.gms.maps.SupportMapFragment;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by kushahuja on 7/14/17.
 */

public class EarthquakeListPresenter implements EarthquakeListContract.Presenter {

    EarthquakeListContract.View mEarthquakeListView;

    public EarthquakeListPresenter(@NonNull EarthquakeListContract.View earthQuakeListView) {

        mEarthquakeListView = checkNotNull(earthQuakeListView, "Earthquake List view cannot be null");
        earthQuakeListView.setPresenter(this);

    }

    @Override
    public void start() {

    }

    @Override
    public void loadEarthquakeInfo() {
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);


        Call<EarthquakeListData> data = apiInterface.getRecentEarthquakeList("44", "-9", "22", "55", "khay");

        data.enqueue(new Callback<EarthquakeListData>() {
            @Override
            public void onResponse(Call<EarthquakeListData> call, Response<EarthquakeListData> response) {
                Log.d("Earthquake list ", response.toString());

                List<Earthquake> responeEarthquakeList = response.body().getEarthquakes();
                if (responeEarthquakeList != null)
                    mEarthquakeListView.showEarthquakeInfoList(responeEarthquakeList);
            }

            @Override
            public void onFailure(Call<EarthquakeListData> call, Throwable t) {

            }
        });

    }

    @Override
    public void showView(Fragment fragment) {
        if (fragment instanceof EarthquakeListViewFragment)
            ((EarthquakeListViewFragment) fragment).getView().setVisibility(View.VISIBLE);
        else if (fragment instanceof SupportMapFragment) {
            ((SupportMapFragment) fragment).getView().setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void hideView(Fragment fragment) {
        if (fragment instanceof EarthquakeListViewFragment)
            ((EarthquakeListViewFragment) fragment).getView().setVisibility(View.INVISIBLE);
        else if (fragment instanceof SupportMapFragment)
            ((SupportMapFragment) fragment).getView().setVisibility(View.INVISIBLE);

    }

}



