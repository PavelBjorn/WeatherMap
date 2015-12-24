package com.fedor.pavel.weathermap.models;

/**
 * Created by Pavel on 23.12.2015.
 */
public class CityModel {

    private long id;

    private String name;

    public static final String SAVE_ID_KEY = "id";

    public static final String SAVE_NAME_KEY = "name";

    public CityModel() {

    }

    public CityModel(long id) {
        this.id = id;
    }

    public CityModel(String name, long id) {
        this.name = name;
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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
        return name;
    }

    @Override
    public boolean equals(Object o) {

        return (o instanceof CityModel) && id == ((CityModel) o).getId();

    }
}
