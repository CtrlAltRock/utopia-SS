package com.smoothstack.ua.services;

import com.smoothstack.ua.models.User;
import com.smoothstack.ua.repos.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    UserRepository userRepository;

    public List<User> getAllUsers() { return (List<User>) userRepository.findAll(); }

    public User getUserByUsername(String username) {
        Optional<User> user = userRepository.findUserByUsername(username);
        if(user.isPresent()) return user.get();
        else return null;
    }

    public User getUserById(Integer userId) {
        Optional<User> user = userRepository.findById(userId.longValue());
        if(user.isPresent()) {
            return user.get();
        }
        else return null;
    }

    public void saveUsers(List<User> users) {
        for (User user: users) {
            user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        }
        userRepository.saveAll(users);
    }

    public User saveUser(User user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        User posted = userRepository.save(user);
        return posted;
    }

    public void deleteUsers(List<User> users) { userRepository.deleteAll(users); }

    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    public void deleteUserById(Long id) { userRepository.deleteById(id); }
}
