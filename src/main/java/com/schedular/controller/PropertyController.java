package com.schedular.controller;

import com.schedular.entity.Property;
import com.schedular.service.PropertyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/properties")
public class PropertyController {

    private PropertyService propertyService;

    public PropertyController(PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    @GetMapping("/{locationName}")
    public ResponseEntity<List<Property>> findPropertyByLocation(@PathVariable String locationName) {
        List<Property> properties= propertyService.findPropertyByLocation(locationName);
        return new ResponseEntity<>(properties, HttpStatus.OK);
    }
}
