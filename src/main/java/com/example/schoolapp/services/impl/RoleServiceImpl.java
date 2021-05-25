package com.example.schoolapp.services.impl;

import com.example.schoolapp.domain.Role;
import com.example.schoolapp.exceptions.ServiceException;
import com.example.schoolapp.repositories.RoleRepository;
import com.example.schoolapp.shared.utils.codes.ErrorCode;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
public class RoleServiceImpl {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role findById(Long id) throws ServiceException {
        Optional<Role> roleOptional = roleRepository.findById(id);
        return roleOptional.orElseThrow(() -> ServiceException.builder()
                .errorCode(ErrorCode.RESOURCE_NOT_FOUND)
                .message("role not found")
                .build());
    }

    
    public List<Role> findAll() {
        return roleRepository.findAllByDeletedAtIsNull();
    }

    
    public List<Role> findAllWithDeleted() {
        return roleRepository.findAll();
    }

    
    public Role update(Role role) throws ServiceException {
        if(role.getId() == null){
            throw ServiceException.builder()
                    .errorCode(ErrorCode.SYSTEM_ERROR)
                    .message("role is null")
                    .build();
        }
        return  roleRepository.save(role);
    }

    
    public Role save(Role role) throws ServiceException {
        if(role.getId() != null){
            throw ServiceException.builder()
                    .errorCode(ErrorCode.ALREADY_EXISTS)
                    .message("role already exists")
                    .build();
        }
        return  roleRepository.save(role);
    }

    
    public void delete(Role role) throws ServiceException {
        if(role.getId() == null){
            throw ServiceException.builder()
                    .errorCode(ErrorCode.SYSTEM_ERROR)
                    .message("role is null")
                    .build();
        }
        role = findById(role.getId());
        role.setDeletedAt(new Date());
        roleRepository.save(role);
    }

    
    public void deleteById(Long id) throws ServiceException {
        if(id == null){
            throw ServiceException.builder()
                    .errorCode(ErrorCode.SYSTEM_ERROR)
                    .message("id is null")
                    .build();
        }
        Role role = findById(id);
        role.setDeletedAt(new Date());
        roleRepository.save(role);
    }
}
