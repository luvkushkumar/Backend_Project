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

    @Autowired
    public ConfigRepo configRepo;

   public Map<String,String> APP_CACHE = new HashMap<>();

    @PostConstruct
    public void init()
    {
        List<ConfigContent>  all = configRepo.findAll();
        for(ConfigContent c : all)
        {
            APP_CACHE.put(c.getKey(),c.getValue());
        }
    }
}
