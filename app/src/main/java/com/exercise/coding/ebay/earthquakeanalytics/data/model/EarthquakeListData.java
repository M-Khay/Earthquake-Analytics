
package com.exercise.coding.ebay.earthquakeanalytics.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


import java.io.Serializable;

public class EarthquakeListData implements Serializable {

    @SerializedName("earthquakes")
    @Expose
    private List<Earthquake> earthquakes = null;
    private final static long serialVersionUID = -5881542602339646019L;

    public List<Earthquake> getEarthquakes() {
        return earthquakes;
    }

    public void setEarthquakes(List<Earthquake> earthquakes) {
        this.earthquakes = earthquakes;
    }


    }




