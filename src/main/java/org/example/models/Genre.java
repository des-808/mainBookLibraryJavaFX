package org.example.models;
//наделал фигни
public class Genre {
    private int id;
    private String name;

    public Genre(String name) {
        this.name = name;
    }
    public Genre(int id,String name) {
        this.id = id;
        this.name = name;
    }

    public Genre() {}
    public Genre(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Genre{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}