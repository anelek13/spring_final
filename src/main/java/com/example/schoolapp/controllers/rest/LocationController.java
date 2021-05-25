package com.example.schoolapp.controllers.rest;

import com.example.schoolapp.controllers.BaseController;
import com.example.schoolapp.domain.Location;
import com.example.schoolapp.exceptions.ServiceException;
import com.example.schoolapp.services.impl.LocationServiceImpl;
import com.example.schoolapp.shared.utils.responses.SuccessResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/location")
@AllArgsConstructor
public class LocationController extends BaseController {

    private final LocationServiceImpl locationService;

    @GetMapping
    @ApiOperation("Получение в грязном виде")
    public ResponseEntity<?> getAll() {
        return buildResponse(locationService.findAll(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    @ApiOperation("Получение по ID")
    public ResponseEntity<?> getOne(@ApiParam("ID элемента") @PathVariable Long id) throws ServiceException {
        return buildResponse(locationService.findById(id), HttpStatus.OK);
    }

    @PostMapping
    @ApiOperation("Добавление объекта")
    public ResponseEntity<?> add(@RequestBody Location location) throws ServiceException {
        return buildResponse(locationService.add(location), HttpStatus.CREATED);
    }

    @RequestMapping(method = {RequestMethod.PATCH, RequestMethod.PUT})
    public ResponseEntity<?> update(@RequestBody Location location) throws ServiceException {
        return buildResponse(SuccessResponse.builder()
                .message("updated")
                .data(locationService.update(location))
                .build(), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    @ApiOperation("Удаление объекта по ID")
    public ResponseEntity<?> deleteById(@PathVariable Long id) throws ServiceException{
        locationService.deleteById(id);
        return buildResponse(SuccessResponse.builder().message("deleted").build(), HttpStatus.OK);
    }
}
