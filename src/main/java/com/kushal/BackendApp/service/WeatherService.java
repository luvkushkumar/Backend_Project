package com.kushal.BackendApp.service;

import com.kushal.BackendApp.AppCache.AppCache;
import com.kushal.BackendApp.apiResponse.WeatherResponse;
import com.kushal.BackendApp.constants.PlaceHolders;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class WeatherService {

    @Value("${weather.api.key}")
    private String apiKey;



    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AppCache appCache;

    @Autowired
    private RedisService redisService;

    public WeatherResponse getWeather(String city) {

        WeatherResponse weatherResponse = redisService.get("weather_of_ " + city, WeatherResponse.class);
        if (weatherResponse != null) {
            return weatherResponse;
        } else {
            String template = appCache.appCache.get(AppCache.keys.WEATHER_API.toString());
            if(template == null)
            {
                log.error("Weather API URL not found in appCache for key: {}",
                        AppCache.keys.WEATHER_API.toString());
                throw new IllegalStateException("Weather API URL not configured");
            }
            String finalAPI = template.replace(PlaceHolders.city, city).replace(PlaceHolders.api_key, apiKey);
            ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalAPI, HttpMethod.GET, null, WeatherResponse.class);
            WeatherResponse body = response.getBody();
            if (body != null) {
                redisService.set("weather_of_" + city, body, 300l);
            }
            return  body;

        }

    }
}
