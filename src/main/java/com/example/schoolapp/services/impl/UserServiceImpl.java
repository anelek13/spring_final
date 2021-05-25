package com.example.schoolapp.services.impl;

import com.example.schoolapp.domain.Role;
import com.example.schoolapp.domain.User;
import com.example.schoolapp.exceptions.ServiceException;
import com.example.schoolapp.repositories.UserRepository;
import com.example.schoolapp.services.UserService;
import com.example.schoolapp.shared.utils.codes.ErrorCode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleServiceImpl roleService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleServiceImpl roleService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public User findById(Long id) throws ServiceException {
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.orElseThrow(() -> ServiceException.builder()
                .errorCode(ErrorCode.RESOURCE_NOT_FOUND)
                .message("user not found")
                .build());
    }

    
    public List<User> findAll() {
        return userRepository.findAllByDeletedAtIsNull();
    }

    
    public List<User> findAllByRole(Long id) {
        return userRepository.findAllByDeletedAtIsNullAndRole_Id(id);
    }

    
    public List<User> findAllWithDeleted() {
        return userRepository.findAll();
    }

    
    public User update(User user) throws ServiceException {
        if (Objects.isNull(user.getId())) {
            throw ServiceException.builder()
                    .errorCode(ErrorCode.SYSTEM_ERROR)
                    .message("user is null")
                    .build();
        }
        return userRepository.save(user);
    }

    
    public User save(User user) throws ServiceException {
        if (Objects.nonNull(user.getId())) {
            throw ServiceException.builder()
                    .errorCode(ErrorCode.ALREADY_EXISTS)
                    .message("user already exists")
                    .build();
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    
    public void delete(User user) throws ServiceException {
        if (Objects.isNull(user.getId())) {
            throw ServiceException.builder()
                    .errorCode(ErrorCode.SYSTEM_ERROR)
                    .message("user is null")
                    .build();
        }
        user = findById(user.getId());
        user.setDeletedAt(new Date());
        userRepository.save(user);
    }

    
    public void deleteById(Long id) throws ServiceException {
        if (Objects.isNull(id)) {
            throw ServiceException.builder()
                    .errorCode(ErrorCode.SYSTEM_ERROR)
                    .message("id is null")
                    .build();
        }
        User user = findById(id);
        user.setDeletedAt(new Date());
        userRepository.save(user);
    }

    
    public User findByLogin(String login) {
        return userRepository.findByLoginAndDeletedAtIsNull(login).orElse(null);
    }

    
    public Set getAuthority(User user) {
        return Collections.singleton(new SimpleGrantedAuthority(user.getRole().getName()));
    }

    
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = findByLogin(login);
        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(), getAuthority(user));
    }

    
    public List<User> findByRole(Long roleId) {
        return userRepository.findAllByDeletedAtIsNullAndRole_Id(roleId);
    }

    
    public User changeUserRole(Long userId, Long roleId) {
        User user = findById(userId);
        Role role = roleService.findById(roleId);
        if (Objects.isNull(user)) {
            throw new ServiceException("No user with such id", ErrorCode.RESOURCE_NOT_FOUND);
        }
        if (Objects.isNull(role)) {
            throw new ServiceException("No role with such id", ErrorCode.RESOURCE_NOT_FOUND);
        }
        user.setRole(role);
        return save(user);
    }

    
    public User getUserByAuthentication(Authentication authentication) {
        return findByLogin(authentication.getName());
    }

    
    public User partialUpdate(User user, boolean isAdmin) throws ServiceException {
        User userToUpdate = findById(user.getId());

        if (isAdmin) {
            userToUpdate.setRole(user.getRole());
        }
        userToUpdate.setFirstName(user.getFirstName());
        userToUpdate.setLastName(user.getLastName());

        return userRepository.save(userToUpdate);
    }
}
