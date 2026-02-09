package com.rsa.comparus.test.task.service;

import com.rsa.comparus.test.task.dto.UserDto;

import java.util.List;
import java.util.Map;

public interface UserService {

    List<UserDto> getAllUsers(Map<String, String> filters);

}
