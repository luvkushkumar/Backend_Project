package com.kushal.BackendApp.AppCache;

import com.kushal.BackendApp.Entities.ConfigContent;
import com.kushal.BackendApp.repo.ConfigRepo;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AppCache {

    public enum keys
    {
        WEATHER_API;
    }

    @Autowired
    public ConfigRepo configRepo;

    public Map<String,String> appCache;

    @PostConstruct
    public void init()
    {
        appCache = new HashMap<>();
        List<ConfigContent>  all = configRepo.findAll();
        for(ConfigContent c : all)
        {
            appCache.put(c.getConfig_key(),c.getValue());
        }
    }
}
