package com.example.JavaWithDocker.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import com.example.JavaWithDocker.model.*;
import com.example.JavaWithDocker.service.*;
import com.example.JavaWithDocker.support.Util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@RestController
@CacheConfig(cacheNames = "targets")
public class TargetController {
    private static final Logger log = LoggerFactory.getLogger(TargetController.class);
    // DB access
    @Autowired
    private TargetRepository repository;

    @GetMapping("/load")
    @CacheEvict(value="target", allEntries=true)
    public List<Target> getTargetsFromAPI(){
        return apiGetTargets();
    }

    @GetMapping("/targets/{deviceId}")
    @ResponseBody
    @CachePut(value="target", key = "#deviceId")
    public Target findTargets(@PathVariable String deviceId){
        Target target =repository.findFirstByDeviceId(deviceId);
        log.info("Found target deviceId - "+ target.getDeviceId());
        return target;
    }

    @GetMapping("/targets")
    @CacheEvict(value="target", allEntries=true)
    public List<Target> getAllTargets() {
        // fetch all the targets from the db and return them
        List<Target> targets = (List<Target>) repository.findAll();
        log.info("Found all targets");
        return targets;
    }

    @PostMapping("/targets")
    @ResponseBody
    public Target newTarget(@RequestBody Map<String, String> payload) {
        Target newTarget = new Target();
        newTarget.setDeviceId(payload.get("deviceId")); 
        newTarget.setDeviceStatus("healthy"); // default to healthy. a broken device shouldn't be created before is fixed
        newTarget.setTargetNumber(payload.get("targetNumber"));
        newTarget.setLastLocation(new Location("0", "0")); // initial location should be the location of the targets
        // not sure what's the best error capture method that Java has
        // error handling could be impoved here
        try { 
            log.info("create a target");
            return repository.save(newTarget);
        }catch (Exception e) {
            log.info(e.toString());
            throw (e);
        }
    }

    @PutMapping("/targets/{id}")
    @ResponseBody
    public Target updateTarget(@PathVariable UUID id, @RequestBody Map<String, String> payload) {
        // not sure what's the best error capture method that Java has
        // error handling could be impoved here
        try {
            return repository.findById(id).map(target -> {
                target.setDeviceId(payload.get("deviceId"));
                target.setTargetNumber(payload.get("targetNumber"));
                return repository.save(target);
            }).orElseGet(() -> {
                Target target = new Target();
                    target.setDeviceId(payload.get("deviceId"));
                    target.setDeviceStatus("healthy");  // default to healthy. a broken device shouldn't be created before is fixed
                    target.setTargetNumber(payload.get("targetNumber"));
                    target.setLastLocation(new Location("0", "0")); // initial location should be the location of the targets
                log.info("Update a target");
                return repository.save(target);
            });
        } catch (Exception e) {
            log.info(e.toString());
            throw(e);
        }
    }
    private List<Target> apiGetTargets() {
        // api from JavaWithDocker
        final String uri = "https://5d96585ca824b400141d26b2.mockapi.io/halter/device/1/status";
        RestTemplate restTemplate = new RestTemplate();
        
        RawData[] result = restTemplate.getForObject(uri, RawData[].class);
        
        List<Target> targets = new ArrayList<Target>();

        Random random = new Random();

        RawData mostRecent = result[0]; 
        // find the most recent target. timestamp format - 2020-10-19T09:04:20.842Z"
        for (RawData item : result) {
            String timestamp = item.getTimeStamp();
            if (Util.parseTimestamp(mostRecent.getTimeStamp()).getTime() < Util.parseTimestamp(timestamp).getTime()) {
                mostRecent = item;
            };
        };
        // mechanism to prevent getting the same deviceId or target number from the api
        Target existingTarget = repository.findFirstByDeviceId(mostRecent.getId());
        if (existingTarget != null) {
            
        } else {
            Target target = new Target();
            target.setDeviceId(mostRecent.getId()); 
            target.setDeviceStatus(mostRecent.getHealthy() ? "healthy" : "broken");
            target.setTargetNumber(String.format("%05d", random.nextInt(10000)));
            target.setLastLocation(new Location(mostRecent.getLat(), mostRecent.getLng()));
                // append to the list and return the list
            targets.add(target);
                // insert the most recent target 
            repository.save(target);
            log.info("Get the device detail from the api and store in the database");
        }

        return targets;
    }
}
