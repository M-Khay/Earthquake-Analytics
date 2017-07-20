package com.exercise.coding.ebay.earthquakeanalytics.location;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.exercise.coding.ebay.earthquakeanalytics.constant.Constants;

/**
 * Created by kushahuja on 7/16/17.
 */

public class LocationTracker implements LocationListener {

    private static LocationTracker locationTracker;
    private Context mContext;
    protected LocationManager locationManagerNetwork;
    protected LocationManager locationManagerGPS;
    Location networkLocation;
    Location GPSLocation;
    double latitude;
    double longitude;

    boolean showEnableLocationProviderPromt = true;

    boolean isGPSEnabled = false;

    boolean isNetworkEnabled = false;

    boolean canGetLocation = false;

    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute

    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; //10 meters

    public static final int ENABLE_GPS_ACTIVITY_CODE = 100;

    private LocationTracker() {

    }

    public static LocationTracker getInstance(Context context) {
        if (locationTracker == null) {
            locationTracker = new LocationTracker(context);
        }
        return locationTracker;
    }

    private LocationTracker(Context context) {

        this.mContext = context;
    }

    public interface LocationAccessRequest {
        void onSuccess();

        void onFailure();
    }

    public void getLocation(LocationAccessListener locationAccessListener) {
        try {
            locationManagerNetwork = (LocationManager) mContext.getSystemService(mContext.LOCATION_SERVICE);
            locationManagerGPS = (LocationManager) mContext.getSystemService(mContext.LOCATION_SERVICE);

            isGPSEnabled = locationManagerGPS.isProviderEnabled(LocationManager.GPS_PROVIDER);

            isNetworkEnabled = locationManagerNetwork.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled) {
                if (showEnableLocationProviderPromt) {
                    showEnableLocationProviderPromt = false;
                    enableGpsDialog();
                } else {
                    locationAccessListener.onFailure();
                    return;
                }
            } else {

                this.canGetLocation = true;

                if (isNetworkEnabled) {
                    if (checkLocationAccessPermission())
                        locationManagerNetwork.requestLocationUpdates(
                                LocationManager.NETWORK_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    if (locationManagerNetwork != null) {
                        networkLocation = locationManagerNetwork.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        updateGPSCoordinates();
                    } else {
                        locationAccessListener.onFailure();
                        Log.d("LocationError", "Cannot access  location through network provider");
                        return;
                    }
                }

                //if GPS Enabled get lat/long using GPS Services
                if (isGPSEnabled) {
                    if (checkLocationAccessPermission()) {
                        if (GPSLocation == null) {
                            locationManagerGPS.requestLocationUpdates(
                                    LocationManager.GPS_PROVIDER,
                                    MIN_TIME_BW_UPDATES,
                                    MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        }
                        if (locationManagerGPS != null) {
                            GPSLocation = locationManagerGPS.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            updateGPSCoordinates();
                        } else {
                            locationAccessListener.onFailure();
                            Log.d("LocationError", "Cannot access location");
                            return;
                        }
                    }
                }
            }
        } catch (Exception e) {
            Log.e("LocationError", "Cannot connect to LocationManager", e);
            locationAccessListener.onFailure();
            return;
        }
        if (GPSLocation != null)
            locationAccessListener.onSuccess(GPSLocation);
        else if (networkLocation != null) {
            locationAccessListener.onSuccess(networkLocation);
        } else {
            locationAccessListener.onFailure();
        }

        return;
    }

    public void updateGPSCoordinates() {
        if (GPSLocation != null) {
            latitude = GPSLocation.getLatitude();
            longitude = GPSLocation.getLongitude();
        }
    }

    public boolean checkLocationAccessPermission() {
        boolean locationAccessPermission = (ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED);
        if (locationAccessPermission == false) {
            requestLocationAccessPermission();
            return false;
        }
        return true;
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }


    private void enableGpsDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setMessage("Need to access your location, to show you recent earthquake list near your location.")
                .setCancelable(true)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.dismiss();

                        if (mContext instanceof AppCompatActivity) {
                            AppCompatActivity activity = (AppCompatActivity) mContext;
                            activity.startActivityForResult(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS), ENABLE_GPS_ACTIVITY_CODE);

                        } else {
                            mContext.startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                            dialog.dismiss();
                        }

                    }

                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.dismiss();
                        showEnableLocationProviderPromt = false;

                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }


    private void requestLocationAccessPermission() {
        ActivityCompat.requestPermissions((AppCompatActivity) mContext,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                Constants.MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);

    }

}
