package com.example.schoolapp.services.impl;

import com.example.schoolapp.domain.Location;
import com.example.schoolapp.exceptions.ServiceException;
import com.example.schoolapp.repositories.LocationRepository;
import com.example.schoolapp.shared.utils.codes.ErrorCode;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class LocationServiceImpl {
    private final LocationRepository locationRepository;

    public LocationServiceImpl(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public List<Location> findAll(){
        return locationRepository.findAllByDeletedAtIsNull();
    }
    
    public Location findById(Long id) throws ServiceException {
        Optional<Location> locationOptional = locationRepository.findById(id);
        return locationOptional.orElseThrow(() -> ServiceException.builder()
                .errorCode(ErrorCode.RESOURCE_NOT_FOUND)
                .message("object not found")
                .build());
    }

    public Location add(Location location){
        if(location.getId() != null){
            throw ServiceException.builder()
                    .errorCode(ErrorCode.ALREADY_EXISTS)
                    .message("already exists")
                    .build();
        }
        return locationRepository.save(location);
    }

    public Location update(Location location) throws ServiceException {
        if(location.getId() == null){
            throw ServiceException.builder()
                    .errorCode(ErrorCode.SYSTEM_ERROR)
                    .message("id is null")
                    .build();
        }
        return locationRepository.save(location);
    }

    public void deleteById(Long id) throws ServiceException {
        if(id == null){
            throw ServiceException.builder()
                    .errorCode(ErrorCode.SYSTEM_ERROR)
                    .message("id is null")
                    .build();
        }
        Location location = findById(id);
        location.setDeletedAt(new Date());
        locationRepository.save(location);
    }
}
