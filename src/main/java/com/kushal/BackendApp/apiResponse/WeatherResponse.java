package com.kushal.BackendApp.apiResponse;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Setter
@Getter
public class WeatherResponse {

    private Current current;

    @Setter
    @Getter
    public class Current
    {
        private int temperature;

        @JsonProperty("weather_descriptions")
        private ArrayList<String> weatherDescriptions;

        private  int feelslike;
    }

}