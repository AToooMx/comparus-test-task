package com.rsa.comparus.test.task.controller;

import com.rsa.comparus.test.task.dto.UserDto;
import com.rsa.comparus.test.task.dto.UserFilter;
import com.rsa.comparus.test.task.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @Operation(summary = "Fetch all users from all configured data sources")
    @GetMapping
    public List<UserDto> fetchAllUsers(@ParameterObject UserFilter userFilter) {
        return userService.getAllUsers(userFilter.asMap());
    }

}
