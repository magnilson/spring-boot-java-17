package com.magnilsonti.treinamentoudemy.services;

import com.magnilsonti.treinamentoudemy.domain.User;
import com.magnilsonti.treinamentoudemy.domain.dto.UserDTO;

import java.util.List;

public interface UserService {
    User findById(Integer id);
    List<User> findAll();
    User create(UserDTO userDto);
    User update(UserDTO userDto);
    void delete(Integer id);
}
