package org.example.models;

public class Series {
    int series_id;
    String series_name;

    public Series(int series_id, String series_name) {
        this.series_id = series_id;
        this.series_name = series_name;
    }

    public Series(int series_id) {
        this.series_id = series_id;
    }

    public Series( String series_name) {
        this.series_name = series_name;
    }

    public Series() {}


    public String getSeries_name() {
        return series_name;
    }

    public void setSeries_name(String series_name) {
        this.series_name = series_name;
    }

    public int getSeries_id() {
        return series_id;
    }

    public void setSeries_id(int series_id) {
        this.series_id = series_id;
    }

}
