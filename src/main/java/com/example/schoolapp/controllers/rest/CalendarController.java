package com.example.schoolapp.controllers.rest;

import com.example.schoolapp.controllers.BaseController;
import com.example.schoolapp.domain.Calendar;
import com.example.schoolapp.exceptions.ServiceException;
import com.example.schoolapp.services.impl.CalendarServiceImpl;
import com.example.schoolapp.shared.utils.responses.SuccessResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/calendar")
@AllArgsConstructor
public class CalendarController extends BaseController {

    private final CalendarServiceImpl calendarService;

    @GetMapping
    @ApiOperation("Получение в грязном виде")
    public ResponseEntity<?> getAll() {
        return buildResponse(calendarService.findAll(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    @ApiOperation("Получение по ID")
    public ResponseEntity<?> getOne(@ApiParam("ID элемента") @PathVariable Long id) throws ServiceException {
        return buildResponse(calendarService.findById(id), HttpStatus.OK);
    }

    @PostMapping
    @ApiOperation("Добавление объекта")
    public ResponseEntity<?> add(@RequestBody Calendar calendar) throws ServiceException {
        return buildResponse(calendarService.add(calendar), HttpStatus.CREATED);
    }

    @RequestMapping(method = {RequestMethod.PATCH, RequestMethod.PUT})
    public ResponseEntity<?> update(@RequestBody Calendar calendar) throws ServiceException {
        return buildResponse(SuccessResponse.builder()
                .message("updated")
                .data(calendarService.update(calendar))
                .build(), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    @ApiOperation("Удаление объекта по ID")
    public ResponseEntity<?> deleteById(@PathVariable Long id) throws ServiceException{
        calendarService.deleteById(id);
        return buildResponse(SuccessResponse.builder().message("deleted").build(), HttpStatus.OK);
    }
}
