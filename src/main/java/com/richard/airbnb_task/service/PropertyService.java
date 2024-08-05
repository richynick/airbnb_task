package com.richard.airbnb_task.service;

import com.richard.airbnb_task.model.Property;
import com.richard.airbnb_task.model.User;
import com.richard.airbnb_task.request.PropertRequest;

import java.util.List;

public interface PropertyService {

    Property createProperty(PropertRequest request, User user) throws Exception;
    List<Property> getAllProperties() throws Exception;
    Property getPropertyById(Long propertyId) throws Exception;
    Property updateProperty(Property property, Long propertyId) throws Exception;
    void deleteProperty(Long propertyId) throws Exception;
    List<Property> getUserProperty(Long userId) throws Exception;
}
