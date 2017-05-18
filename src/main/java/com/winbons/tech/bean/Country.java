package com.winbons.tech.bean;

public class Country {
    int id;
    String countryName;
    long population;

    public Country() {
    }

    public Country(int i, String countryName, long population) {
        this.id = i;
        this.countryName = countryName;
        this.population = population;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCountryName() {
        return this.countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public long getPopulation() {
        return this.population;
    }

    public void setPopulation(long population) {
        this.population = population;
    }
}