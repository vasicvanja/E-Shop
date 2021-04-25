package com.eshop.service.impl;

import com.eshop.model.User;
import com.eshop.model.exceptions.InvalidArgumentsException;
import com.eshop.model.exceptions.InvalidUserCredentialsException;
import com.eshop.model.exceptions.PasswordsDoNotMatchException;
import com.eshop.model.exceptions.UsernameExistsException;
import com.eshop.repository.jpa.UserRepository;
import com.eshop.service.AuthService;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    public AuthServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User login(String username, String password) {
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            throw new InvalidArgumentsException();
        }
        return this.userRepository.findByUsernameAndPassword(username, password)
                .orElseThrow(InvalidUserCredentialsException::new);
    }
}
