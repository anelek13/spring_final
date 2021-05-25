package com.example.schoolapp.controllers.rest;

import com.example.schoolapp.controllers.BaseController;
import com.example.schoolapp.exceptions.ServiceException;
import com.example.schoolapp.services.impl.RoleServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/roles")
@AllArgsConstructor
public class RoleController extends BaseController {

    private RoleServiceImpl roleService;

    @GetMapping
    public ResponseEntity<?> getAll() {
        return buildResponse(roleService.findAll(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getOne(@PathVariable Long id) throws ServiceException {
        return buildResponse(roleService.findById(id), HttpStatus.OK);
    }

}
