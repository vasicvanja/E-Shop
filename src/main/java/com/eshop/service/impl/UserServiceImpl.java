package com.eshop.service.impl;

import com.eshop.model.Role;
import com.eshop.model.User;
import com.eshop.model.exceptions.InvalidArgumentsException;
import com.eshop.model.exceptions.PasswordsDoNotMatchException;
import com.eshop.model.exceptions.UsernameExistsException;
import com.eshop.repository.jpa.UserRepository;
import com.eshop.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User register(String username, String password, String repeatedPassword, String name, String surname, Role role) {
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            throw new InvalidArgumentsException();
        }
        if (!password.equals(repeatedPassword)) {
            throw new PasswordsDoNotMatchException();
        }
        if (this.userRepository.findByUsername(username).isPresent()) {
            throw new UsernameExistsException(username);
        }
        User user = new User(username, passwordEncoder.encode(password), name, surname, role);
        return this.userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return this.userRepository.findByUsername(s).orElseThrow(() -> new UsernameNotFoundException(s));
    }
}
