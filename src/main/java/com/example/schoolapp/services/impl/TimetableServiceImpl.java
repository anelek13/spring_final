package com.example.schoolapp.services.impl;

import com.example.schoolapp.domain.Timetable;
import com.example.schoolapp.exceptions.ServiceException;
import com.example.schoolapp.repositories.TimetableRepository;
import com.example.schoolapp.shared.utils.codes.ErrorCode;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TimetableServiceImpl {
    private final TimetableRepository timetableRepository;

    public TimetableServiceImpl(TimetableRepository timetableRepository) {
        this.timetableRepository = timetableRepository;
    }

    public List<Timetable> findAll(){
        return timetableRepository.findAllByDeletedAtIsNull();
    }
    
    public Timetable findById(Long id) throws ServiceException {
        Optional<Timetable> timetableOptional = timetableRepository.findById(id);
        return timetableOptional.orElseThrow(() -> ServiceException.builder()
                .errorCode(ErrorCode.RESOURCE_NOT_FOUND)
                .message("object not found")
                .build());
    }

    public Timetable add(Timetable timetable){
        if(timetable.getId() != null){
            throw ServiceException.builder()
                    .errorCode(ErrorCode.ALREADY_EXISTS)
                    .message("already exists")
                    .build();
        }
        return timetableRepository.save(timetable);
    }

    public Timetable update(Timetable timetable) throws ServiceException {
        if(timetable.getId() == null){
            throw ServiceException.builder()
                    .errorCode(ErrorCode.SYSTEM_ERROR)
                    .message("id is null")
                    .build();
        }
        return timetableRepository.save(timetable);
    }

    public void deleteById(Long id) throws ServiceException {
        if(id == null){
            throw ServiceException.builder()
                    .errorCode(ErrorCode.SYSTEM_ERROR)
                    .message("id is null")
                    .build();
        }
        Timetable timetable = findById(id);
        timetable.setDeletedAt(new Date());
        timetableRepository.save(timetable);
    }
}
