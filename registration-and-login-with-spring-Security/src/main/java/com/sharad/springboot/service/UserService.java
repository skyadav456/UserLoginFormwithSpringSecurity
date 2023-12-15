package com.sharad.springboot.service;

import com.sharad.springboot.dto.UserDto;
import com.sharad.springboot.entiry.User;

import java.util.List;

public interface UserService {
    void saveUser(UserDto userDto);

    User findUserByEmail(String email);

    List<UserDto> findAllUsers();
}
