package com.eshop.service;

import com.eshop.model.User;

public interface AuthService {

    User login(String username, String password);

}
