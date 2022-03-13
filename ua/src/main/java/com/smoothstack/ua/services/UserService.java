package com.smoothstack.ua.services;

import com.smoothstack.ua.models.User;
import com.smoothstack.ua.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public User getUserById(Integer userId) {
        Optional<User> user = userRepository.findById(userId);
        return user.get();
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }
}
