package com.schedular.service;

import com.schedular.entity.Property;
import com.schedular.repository.PropertyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropertyService {

    private PropertyRepository propertyRepository;

    public PropertyService(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }


    public List<Property> findPropertyByLocation(String locationName) {
        List<Property> properties = propertyRepository.findPropertyByLocation(locationName);
        return properties;
    }
}
