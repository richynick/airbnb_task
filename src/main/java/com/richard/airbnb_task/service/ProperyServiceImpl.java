package com.richard.airbnb_task.service;

import com.richard.airbnb_task.model.Property;
import com.richard.airbnb_task.model.User;
import com.richard.airbnb_task.request.PropertRequest;
import com.richard.airbnb_task.respository.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProperyServiceImpl implements PropertyService{

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private UserService userService;

    @Override
    public Property createProperty(PropertRequest request, User user) throws Exception {
        Property createdProperty = new Property();
        createdProperty.setOwner(user);
        createdProperty.setAvailable(request.isAvailable());
        createdProperty.setAddress(request.getAddress());
        createdProperty.setDescription(request.getDescription());
        createdProperty.setName(request.getName());
        createdProperty.setDrinkAllowed(request.isDrinkAllowed());
        createdProperty.setExtraCharges(request.getExtraCharges());
        createdProperty.setMaxCheckoutTimeInNights(request.getMaxCheckoutTimeInNights());
        createdProperty.setNumberOfBathrooms(request.getNumberOfBathrooms());
        createdProperty.setPetAllowed(request.isPetAllowed());
        createdProperty.setNumberOfBedrooms(request.getNumberOfBedrooms());
        createdProperty.setPricePerNight(request.getPricePerNight());

        Property savedProperty = propertyRepository.save(createdProperty);
        return savedProperty;
    }

    @Override
    public List<Property> getAllProperties() throws Exception {
        return propertyRepository.findAll();
    }

    @Override
    public Property getPropertyById(Long propertyId) throws Exception {
        Optional<Property> optionalProperty = propertyRepository.findById(propertyId);
        if(optionalProperty.isEmpty()){
            throw new Exception("Property not found with id" + propertyId);
        }
        return optionalProperty.get();
    }

    @Override
    public Property updateProperty(Property property, Long propertyId) throws Exception {

        Property property1 = getPropertyById(propertyId);
            if(property1 == null){
                throw new Exception("Property not found");
            }
        property1.setPetAllowed(property.isPetAllowed());
        property1.setAvailable(property.isAvailable());
        property1.setName(property.getName());
        property1.setDescription(property.getDescription());
        property1.setDrinkAllowed(property.isDrinkAllowed());
        property1.setExtraCharges(property.getExtraCharges());
        property1.setMaxCheckoutTimeInNights(property.getMaxCheckoutTimeInNights());
        property1.setNumberOfBathrooms(property.getNumberOfBathrooms());
        property1.setNumberOfBedrooms(property.getNumberOfBedrooms());
        property1.setPricePerNight(property.getPricePerNight());
        return null;
    }

    @Override
    public void deleteProperty(Long propertyId) throws Exception {
        Property property = getPropertyById(propertyId);
        propertyRepository.delete(property);
    }

    @Override
    public List<Property> getUserProperty(Long userId) throws Exception {
        return propertyRepository.findByOwnerId(userId);
    }
}
