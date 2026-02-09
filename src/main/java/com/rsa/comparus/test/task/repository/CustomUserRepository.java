package com.rsa.comparus.test.task.repository;

import com.rsa.comparus.test.task.model.User;

import java.util.List;
import java.util.Map;

public interface CustomUserRepository {

    List<User> findAllUsers(Map<String, String> filters);

}
