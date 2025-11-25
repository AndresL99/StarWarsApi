package com.starwars.api.service.impl;

import com.starwars.api.domain.Users.User;
import com.starwars.api.repository.UserRepository;
import com.starwars.api.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;


    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User saveUser(User user) throws Exception {
        if (!userRepository.existsById(user.getId())) {
            return userRepository.save(user);
        } else {
            throw new Exception("User already exist...");
        }
    }


    @Override
    public User getByUsernameAndPassword(String username, String password) {
        return userRepository.getByUsernameAndPassword(username,password);
    }
}
