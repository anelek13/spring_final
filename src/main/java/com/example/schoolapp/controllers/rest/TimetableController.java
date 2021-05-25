package com.example.schoolapp.controllers.rest;

import com.example.schoolapp.controllers.BaseController;
import com.example.schoolapp.domain.Timetable;
import com.example.schoolapp.exceptions.ServiceException;
import com.example.schoolapp.services.impl.TimetableServiceImpl;
import com.example.schoolapp.shared.utils.responses.SuccessResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/timetable")
@AllArgsConstructor
public class TimetableController extends BaseController {
    private final TimetableServiceImpl timetableService;

    @GetMapping
    @ApiOperation("Получение в грязном виде")
    public ResponseEntity<?> getAll() {
        return buildResponse(timetableService.findAll(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    @ApiOperation("Получение по ID")
    public ResponseEntity<?> getOne(@ApiParam("ID элемента") @PathVariable Long id) throws ServiceException {
        return buildResponse(timetableService.findById(id), HttpStatus.OK);
    }

    @PostMapping
    @ApiOperation("Добавление объекта")
    public ResponseEntity<?> add(@RequestBody Timetable timetable) throws ServiceException {
        return buildResponse(timetableService.add(timetable), HttpStatus.CREATED);
    }

    @RequestMapping(method = {RequestMethod.PATCH, RequestMethod.PUT})
    public ResponseEntity<?> update(@RequestBody Timetable timetable) throws ServiceException {
        return buildResponse(SuccessResponse.builder()
                .message("updated")
                .data(timetableService.update(timetable))
                .build(), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    @ApiOperation("Удаление объекта по ID")
    public ResponseEntity<?> deleteById(@PathVariable Long id) throws ServiceException{
        timetableService.deleteById(id);
        return buildResponse(SuccessResponse.builder().message("deleted").build(), HttpStatus.OK);
    }
}
