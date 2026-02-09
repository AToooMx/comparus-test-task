package com.rsa.comparus.test.task.service;

import com.rsa.comparus.test.task.dto.UserDto;
import com.rsa.comparus.test.task.mapper.UserMapper;
import com.rsa.comparus.test.task.repository.CustomUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final CustomUserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public List<UserDto> getAllUsers(Map<String, String> filters) {
        var users = userRepository.findAllUsers(filters);
        log.info("Found {} users", users.size());
        return userMapper.toDto(users);
    }
}
