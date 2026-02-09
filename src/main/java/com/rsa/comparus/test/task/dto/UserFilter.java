package com.rsa.comparus.test.task.dto;

import io.micrometer.common.util.StringUtils;
import lombok.Builder;

import java.util.HashMap;
import java.util.Map;

@Builder
public record UserFilter (String username, String name, String surname) {

    public Map<String, String> asMap() {
        Map<String, String> map = new HashMap<>();
        if(!StringUtils.isEmpty(username)) map.put("username", username);
        if(!StringUtils.isEmpty(name)) map.put("name", name);
        if(!StringUtils.isEmpty(surname)) map.put("surname", surname);
        return map;
    }

}
