package com.example.schoolapp.controllers.rest;

import com.example.schoolapp.controllers.BaseController;
import com.example.schoolapp.domain.Subject;
import com.example.schoolapp.exceptions.ServiceException;
import com.example.schoolapp.services.impl.SubjectServiceImpl;
import com.example.schoolapp.shared.utils.responses.SuccessResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/subject")
@AllArgsConstructor
public class SubjectController extends BaseController {
    private final SubjectServiceImpl subjectService;

    @GetMapping
    @ApiOperation("Получение в грязном виде")
    public ResponseEntity<?> getAll() {
        return buildResponse(subjectService.findAll(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    @ApiOperation("Получение по ID")
    public ResponseEntity<?> getOne(@ApiParam("ID элемента") @PathVariable Long id) throws ServiceException {
        return buildResponse(subjectService.findById(id), HttpStatus.OK);
    }

    @PostMapping
    @ApiOperation("Добавление объекта")
    public ResponseEntity<?> add(@RequestBody Subject subject) throws ServiceException {
        return buildResponse(subjectService.add(subject), HttpStatus.CREATED);
    }

    @RequestMapping(method = {RequestMethod.PATCH, RequestMethod.PUT})
    public ResponseEntity<?> update(@RequestBody Subject subject) throws ServiceException {
        return buildResponse(SuccessResponse.builder()
                .message("updated")
                .data(subjectService.update(subject))
                .build(), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    @ApiOperation("Удаление объекта по ID")
    public ResponseEntity<?> deleteById(@PathVariable Long id) throws ServiceException{
        subjectService.deleteById(id);
        return buildResponse(SuccessResponse.builder().message("deleted").build(), HttpStatus.OK);
    }
}
