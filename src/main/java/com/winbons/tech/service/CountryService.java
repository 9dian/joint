package com.winbons.tech.service;

import com.winbons.tech.bean.Country;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CountryService
{
  static HashMap<Integer, Country> countryIdMap = getCountryIdMap();

  public CountryService()
  {
    if (countryIdMap == null)
    {
      countryIdMap = new HashMap();

      Country indiaCountry = new Country(1, "India", 10000L);
      Country chinaCountry = new Country(4, "China", 20000L);
      Country nepalCountry = new Country(3, "Nepal", 8000L);
      Country bhutanCountry = new Country(2, "Bhutan", 7000L);

      countryIdMap.put(Integer.valueOf(1), indiaCountry);
      countryIdMap.put(Integer.valueOf(4), chinaCountry);
      countryIdMap.put(Integer.valueOf(3), nepalCountry);
      countryIdMap.put(Integer.valueOf(2), bhutanCountry);
    }
  }

  public List<Country> getAllCountries()
  {
    List countries = new ArrayList(countryIdMap.values());
    return countries;
  }

  public Country getCountry(int id)
  {
    Country country = (Country)countryIdMap.get(Integer.valueOf(id));
    return country;
  }

  public Country addCountry(Country country) {
    country.setId(countryIdMap.size() + 1);
    countryIdMap.put(Integer.valueOf(country.getId()), country);
    return country;
  }

  public Country updateCountry(Country country)
  {
    if (country.getId() <= 0)
      return null;
    countryIdMap.put(Integer.valueOf(country.getId()), country);
    return country;
  }

  public void deleteCountry(int id)
  {
    countryIdMap.remove(Integer.valueOf(id));
  }

  public static HashMap<Integer, Country> getCountryIdMap() {
    return countryIdMap;
  }
}