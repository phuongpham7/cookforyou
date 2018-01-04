package com.pp.cookforyou.controllers;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pp.cookforyou.models.Location;
import com.pp.cookforyou.repositories.LocationRepository;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class LocationController {
	
	@Autowired
    LocationRepository locationRepository;

    @GetMapping("/locations")
    public List<Location> getAllLocations() {
        Sort sortByNameAtDesc = new Sort(Sort.Direction.DESC, "name");
        return locationRepository.findAll(sortByNameAtDesc);
    }

    @PostMapping("/locations")
    public Location createLocation(@Valid @RequestBody Location location) {
        location.setDateCreated(LocalDateTime.now());
        return locationRepository.save(location);
    }

    @GetMapping(value="/locations/{id}")
    public ResponseEntity<Location> getLocationById(@PathVariable("id") String id) {
        Location location = locationRepository.findOne(id);
        if(location == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(location, HttpStatus.OK);
        }
    }

    @PutMapping(value="/locations/{id}")
    public ResponseEntity<Location> updateLocation(@PathVariable("id") String id,
                                           @Valid @RequestBody Location location) {
        Location locationData = locationRepository.findOne(id);
        if(locationData == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        locationData.setName(location.getName());
        locationData.setDateUpdated(LocalDateTime.now());
        Location updatedLocation = locationRepository.save(locationData);
        return new ResponseEntity<>(updatedLocation, HttpStatus.OK);
    }

    @DeleteMapping(value="/locations/{id}")
    public void deleteLocation(@PathVariable("id") String id) {
    	Location locationData = locationRepository.findOne(id);
        if(locationData == null) {
            return;
        }
        locationData.setDeleted(true);
        locationData.setDateDeleted(LocalDateTime.now());
        locationRepository.delete(id);
    }

}
