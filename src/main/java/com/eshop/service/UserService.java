package com.eshop.service;

import com.eshop.model.Role;
import com.eshop.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    User register(String username, String password, String repeatedPassword, String name, String surname, Role role);

}
