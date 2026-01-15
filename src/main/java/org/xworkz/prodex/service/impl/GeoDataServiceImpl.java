package org.xworkz.prodex.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.xworkz.prodex.entity.CityEntity;
import org.xworkz.prodex.entity.CountryEntity;
import org.xworkz.prodex.entity.StateEntity;
import org.xworkz.prodex.service.GeoDataService;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Service
public class GeoDataServiceImpl implements GeoDataService {

    private List<CountryEntity> allGeoData = Collections.emptyList();
    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final String JSON_FILE_PATH = "countries+states+cities.json";

    @PostConstruct
    public void init() {
        try {
            ClassPathResource resource = new ClassPathResource(JSON_FILE_PATH);

            this.allGeoData = objectMapper.readValue(
                    resource.getInputStream(),
                    objectMapper.getTypeFactory().constructCollectionType(List.class, CountryEntity.class)
            );

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<CountryEntity> getAllCountries() {
        return Collections.unmodifiableList(allGeoData);
    }

    @Override
    public List<StateEntity> getStatesByCountryCode(String iso2) {
        return allGeoData.stream()
                .filter(c -> c.getIso2() != null && c.getIso2().equalsIgnoreCase(iso2))
                .findFirst()
                .map(CountryEntity::getStates)
                .orElse(Collections.emptyList());
    }


    @Override
    public List<CityEntity> getCitiesByStateCode(String stateName) {
        return allGeoData.stream()
                .flatMap(c -> c.getStates().stream())
                .filter(s -> s.getName() != null && s.getName().equalsIgnoreCase(stateName))
                .findFirst()
                .map(StateEntity::getCities)
                .orElse(Collections.emptyList());
    }
}