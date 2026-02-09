package com.rsa.comparus.test.task.mapper;

import com.rsa.comparus.test.task.dto.UserDto;
import com.rsa.comparus.test.task.model.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    List<UserDto> toDto(List<User> users);

}
