package com.example.schoolapp.controllers.rest;

import com.example.schoolapp.controllers.BaseController;
import com.example.schoolapp.domain.Role;
import com.example.schoolapp.domain.User;
import com.example.schoolapp.domain.req.UserRoleUpdateRequest;
import com.example.schoolapp.exceptions.ServiceException;
import com.example.schoolapp.services.UserService;
import com.example.schoolapp.shared.utils.codes.ErrorCode;
import com.example.schoolapp.shared.utils.responses.SuccessResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/api/users")
@Api("Точка входа для распознования")
@AllArgsConstructor
public class UserController extends BaseController {

    private UserService userService;

    @GetMapping
    @ApiOperation("Получение всех пользователей в грязном виде")
    public ResponseEntity<?> getAll() {
        return buildResponse(userService.findAll(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    @ApiOperation("Получение по ID")
    public ResponseEntity<?> getOne(@ApiParam("ID элемента") @PathVariable Long id) throws ServiceException {
        return buildResponse(userService.findById(id), HttpStatus.OK);
    }

    @PostMapping
    @ApiOperation("Регистрация пользователей")
    public ResponseEntity<?> add(@RequestBody User user) throws ServiceException {
        if (userService.findByLogin(user.getLogin()) == null) {
            Role role = new Role();
            role.setId(Role.ROLE_STUDENT_ID);
            user.setRole(role);
            user = userService.save(user);
            return buildResponse(user, HttpStatus.OK);
        } else {
            return buildResponse("Login already exists", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/validate")
    @ApiOperation("Валидация логина")
    public ResponseEntity<?> validate(@RequestParam String login) throws ServiceException {
        User user = userService.findByLogin(login);
        if (user != null) {
            throw ServiceException.builder().message("Login exists").errorCode(ErrorCode.ALREADY_EXISTS).build();
        } else {
            return buildResponse(SuccessResponse.builder().message("OK").build(), HttpStatus.OK);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) throws ServiceException {
        userService.deleteById(id);
        return buildResponse(SuccessResponse.builder().message("deleted").build(), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> delete(@RequestBody User user) throws ServiceException {
        userService.delete(user);
        return buildResponse(SuccessResponse.builder().message("deleted").build(), HttpStatus.OK);
    }

    @RequestMapping(method = {RequestMethod.PATCH, RequestMethod.PUT})
    public ResponseEntity<?> update(@RequestBody User user, Authentication authentication)
            throws ServiceException {
        User currentUser = userService.getUserByAuthentication(authentication);
        if (Objects.equals(currentUser.getId(), user.getId()) || currentUser.isAdmin()) {
            return buildResponse(userService.update(user), HttpStatus.OK);
        } else {
            throw new ServiceException("Unauthorized", ErrorCode.ACCESS_DENIED);
        }
    }

    @RequestMapping(method = RequestMethod.PATCH, path = "/partial")
    public ResponseEntity<?> partialUpdate(@RequestBody User user, Authentication authentication)
            throws ServiceException {
        User currentUser = userService.getUserByAuthentication(authentication);
        if (Objects.equals(currentUser.getId(), user.getId()) || currentUser.isAdmin()) {
            return buildResponse(userService.partialUpdate(user, currentUser.isAdmin()), HttpStatus.OK);
        } else {
            throw new ServiceException("Unauthorized", ErrorCode.ACCESS_DENIED);
        }

    }

    @PostMapping("/current")
    public ResponseEntity<?> getCurrentUser(Authentication authentication) throws ServiceException {
        return buildResponse(SuccessResponse.builder()
                .message("found")
                .data(userService.getUserByAuthentication(authentication))
                .build(), HttpStatus.OK);
    }

    @GetMapping("/roles/{roleId}")
    @ApiOperation("По ролям")
    public ResponseEntity<?> getByRole(@PathVariable Long roleId) {
        return buildResponse(userService.findByRole(roleId), HttpStatus.OK);
    }

    @PostMapping("/update/role")
    public ResponseEntity<?> updateRoleOfUser(@RequestBody @Validated UserRoleUpdateRequest userRoleUpdateRequest) {
        return buildSuccessResponse(
                userService.changeUserRole(userRoleUpdateRequest.getUserId(), userRoleUpdateRequest.getRoleId()));
    }
}
