package com.example.schoolapp.services.impl;

import com.example.schoolapp.domain.Subject;
import com.example.schoolapp.exceptions.ServiceException;
import com.example.schoolapp.repositories.SubjectRepository;
import com.example.schoolapp.shared.utils.codes.ErrorCode;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class SubjectServiceImpl {
    private final SubjectRepository subjectRepository;

    public SubjectServiceImpl(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    public List<Subject> findAll(){
        return subjectRepository.findAllByDeletedAtIsNull();
    }
    
    public Subject findById(Long id) throws ServiceException {
        Optional<Subject> subjectOptional = subjectRepository.findById(id);
        return subjectOptional.orElseThrow(() -> ServiceException.builder()
                .errorCode(ErrorCode.RESOURCE_NOT_FOUND)
                .message("object not found")
                .build());
    }

    public Subject add(Subject subject){
        if(subject.getId() != null){
            throw ServiceException.builder()
                    .errorCode(ErrorCode.ALREADY_EXISTS)
                    .message("already exists")
                    .build();
        }
        return subjectRepository.save(subject);
    }

    public Subject update(Subject subject) throws ServiceException {
        if(subject.getId() == null){
            throw ServiceException.builder()
                    .errorCode(ErrorCode.SYSTEM_ERROR)
                    .message("id is null")
                    .build();
        }
        return subjectRepository.save(subject);
    }

    public void deleteById(Long id) throws ServiceException {
        if(id == null){
            throw ServiceException.builder()
                    .errorCode(ErrorCode.SYSTEM_ERROR)
                    .message("id is null")
                    .build();
        }
        Subject subject = findById(id);
        subject.setDeletedAt(new Date());
        subjectRepository.save(subject);
    }
}
