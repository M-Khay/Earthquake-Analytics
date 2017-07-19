package com.exercise.coding.ebay.earthquakeanalytics.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * Created by kushahuja on 7/17/17.
 */

public class Earthquake implements Serializable {

    @SerializedName("datetime")
    @Expose
    private String datetime;
    @SerializedName("depth")
    @Expose
    private Double depth;
    @SerializedName("lng")
    @Expose
    private Double lng;
    @SerializedName("src")
    @Expose
    private String src;
    @SerializedName("eqid")
    @Expose
    private String eqid;
    @SerializedName("magnitude")
    @Expose
    private Double magnitude;
    @SerializedName("lat")
    @Expose
    private Double lat;
    private final static long serialVersionUID = -3840817568252674419L;

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public Double getDepth() {
        return depth;
    }

    public void setDepth(Double depth) {
        this.depth = depth;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getEqid() {
        return eqid;
    }

    public void setEqid(String eqid) {
        this.eqid = eqid;
    }

    public Double getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(Double magnitude) {
        this.magnitude = magnitude;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(datetime).append(depth).append(lng).append(src).append(eqid).append(magnitude).append(lat).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Earthquake) == false) {
            return false;
        }
        Earthquake rhs = ((Earthquake) other);
        return new EqualsBuilder().append(datetime, rhs.datetime).append(depth, rhs.depth).append(lng, rhs.lng).append(src, rhs.src).append(eqid, rhs.eqid).append(magnitude, rhs.magnitude).append(lat, rhs.lat).isEquals();
    }

}