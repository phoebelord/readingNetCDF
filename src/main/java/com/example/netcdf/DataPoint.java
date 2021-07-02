package com.example.netcdf;

import java.io.Serializable;

public class DataPoint implements Serializable {

  private double lat;
  private double lon;
  private double time;
  private float temp;

  public DataPoint(double lat, double lon, double time, float temp) {
    this.lat = lat;
    this.lon = lon;
    this.time = time;
    this.temp = temp;
  }

  public double getLat() {
    return lat;
  }

  public double getLon() {
    return lon;
  }

  public double getTime() {
    return time;
  }

  public float getTemp() {
    return temp;
  }

}
