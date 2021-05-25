package com.example.schoolapp.services.impl;

import com.example.schoolapp.domain.Calendar;
import com.example.schoolapp.exceptions.ServiceException;
import com.example.schoolapp.repositories.CalendarRepository;
import com.example.schoolapp.shared.utils.codes.ErrorCode;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CalendarServiceImpl {
    private final CalendarRepository calendarRepository;

    public CalendarServiceImpl(CalendarRepository calendarRepository) {
        this.calendarRepository = calendarRepository;
    }

    public List<Calendar> findAll(){
        return calendarRepository.findAllByDeletedAtIsNull();
    }

    public Calendar findById(Long id) throws ServiceException {
        Optional<Calendar> courseOptional = calendarRepository.findById(id);
        return courseOptional.orElseThrow(() -> ServiceException.builder()
                .errorCode(ErrorCode.RESOURCE_NOT_FOUND)
                .message("object not found")
                .build());
    }

    public Calendar add(Calendar calendar){
        if(calendar.getId() != null){
            throw ServiceException.builder()
                    .errorCode(ErrorCode.ALREADY_EXISTS)
                    .message("already exists")
                    .build();
        }
        return calendarRepository.save(calendar);
    }

    public Calendar update(Calendar calendar) throws ServiceException {
        if(calendar.getId() == null){
            throw ServiceException.builder()
                    .errorCode(ErrorCode.SYSTEM_ERROR)
                    .message("id is null")
                    .build();
        }
        return calendarRepository.save(calendar);
    }

    public void deleteById(Long id) throws ServiceException {
        if(id == null){
            throw ServiceException.builder()
                    .errorCode(ErrorCode.SYSTEM_ERROR)
                    .message("id is null")
                    .build();
        }
        Calendar calendar = findById(id);
        calendar.setDeletedAt(new Date());
        calendarRepository.save(calendar);
    }
}
