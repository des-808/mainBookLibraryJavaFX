package org.example.models;

public class Cover {

    private int Cover_id;
    private String Cover_name;
    private String Cover_path;

    public Cover(int cover_id, String cover_name, String cover_path) {
        Cover_id = cover_id;
        Cover_name = cover_name;
        Cover_path = cover_path;
    }

    public Cover(int cover_id) {
        Cover_id = cover_id;
    }

    public Cover(String cover_path, String cover_name) {
        Cover_path = cover_path;
        Cover_name = cover_name;
    }
    public Cover() {}

    public Cover(String text) {
        Cover_name = text;
    }


    public String getCover_path() {
        return Cover_path;
    }

    public void setCover_path(String cover_path) {
        Cover_path = cover_path;
    }


    public String getCover_name() {
        return Cover_name;
    }

    public void setCover_name(String cover_name) {
        Cover_name = cover_name;
    }

    public int getCover_id() {
        return Cover_id;
    }

    public void setCover_id(int cover_id) {
        Cover_id = cover_id;
    }

}
