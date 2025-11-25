package com.starwars.api.service;

import com.starwars.api.domain.Users.User;


public interface UserService
{
    User saveUser(User user) throws Exception;
    User getByUsernameAndPassword(String username, String password);
}
