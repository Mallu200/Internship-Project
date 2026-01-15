package org.xworkz.prodex.entity;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CountryEntity {

    private int id;
    private String name;
    private String iso2;
    private List<StateEntity> states;
}