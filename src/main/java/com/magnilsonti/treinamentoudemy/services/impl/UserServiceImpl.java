package com.magnilsonti.treinamentoudemy.services.impl;

import com.magnilsonti.treinamentoudemy.domain.User;
import com.magnilsonti.treinamentoudemy.repositories.UserRepository;
import com.magnilsonti.treinamentoudemy.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserRepository repository;

    @Override
    public User findById(Integer id) {
        Optional<User> user = repository.findById(id);
        return user.orElse(null);
    }
}
