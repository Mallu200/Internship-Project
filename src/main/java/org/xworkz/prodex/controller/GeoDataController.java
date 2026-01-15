package org.xworkz.prodex.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.xworkz.prodex.service.GeoDataService;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/")
public class GeoDataController {

    @Autowired
    private GeoDataService geoDataService;

    @GetMapping("getCountries")
    @ResponseBody
    public Map<String, Object> getCountries() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<String> countryNames = geoDataService.getAllCountries().stream()
                    .map(c -> c.getName())
                    .collect(Collectors.toList());

            response.put("status", "SUCCESS");
            response.put("data", countryNames);
        } catch (Exception e) {
            System.err.println("ERROR: Failed to fetch countries: " + e.getMessage());
            response.put("status", "ERROR");
            response.put("message", "Internal server error fetching countries.");
        }
        return response;
    }

    @GetMapping("getStates")
    @ResponseBody
    public Map<String, Object> getStates(@RequestParam("countryCode") String countryCode) {
        Map<String, Object> response = new HashMap<>();
        if (countryCode == null || countryCode.trim().isEmpty()) {
            response.put("status", "FAILURE");
            response.put("message", "Country Code parameter is required.");
            return response;
        }

        try {
            List<String> stateNames = geoDataService.getStatesByCountryCode(countryCode.trim()).stream()
                    .map(s -> s.getName())
                    .collect(Collectors.toList());

            response.put("status", "SUCCESS");
            response.put("data", stateNames);
        } catch (Exception e) {
            System.err.println("ERROR: Failed to fetch states: " + e.getMessage());
            response.put("status", "ERROR");
            response.put("message", "Internal server error fetching states.");
        }
        return response;
    }

    @GetMapping("getCities")
    @ResponseBody
    public Map<String, Object> getCities(@RequestParam("stateName") String stateName) {
        Map<String, Object> response = new HashMap<>();
        if (stateName == null || stateName.trim().isEmpty()) {
            response.put("status", "FAILURE");
            response.put("message", "State parameter is required.");
            response.put("data", Collections.emptyList());
            return response;
        }

        try {
            List<String> cityNames = geoDataService.getCitiesByStateCode(stateName.trim()).stream()
                    .map(c -> c.getName())
                    .collect(Collectors.toList());

            response.put("status", "SUCCESS");
            response.put("data", cityNames);
        } catch (Exception e) {
            System.err.println("ERROR: Failed to fetch cities for " + stateName + ": " + e.getMessage());
            response.put("status", "ERROR");
            response.put("message", "Internal server error fetching cities.");
        }
        return response;
    }
}