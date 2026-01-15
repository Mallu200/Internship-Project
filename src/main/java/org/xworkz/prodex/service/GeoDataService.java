package org.xworkz.prodex.service;

import org.xworkz.prodex.entity.CityEntity;
import org.xworkz.prodex.entity.CountryEntity;
import org.xworkz.prodex.entity.StateEntity;

import java.util.List;

public interface GeoDataService {

    List<CountryEntity> getAllCountries();

    List<StateEntity> getStatesByCountryCode(String iso2);

    List<CityEntity> getCitiesByStateCode(String stateCode);
}
