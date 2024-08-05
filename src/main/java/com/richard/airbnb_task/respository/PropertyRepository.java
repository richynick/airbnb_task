package com.richard.airbnb_task.respository;

import com.richard.airbnb_task.model.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {
    public List<Property> findByOwnerId(Long userId);
}
