//package com.smoothstack.ua.auth;
//
//import com.smoothstack.ua.models.User;
//import com.smoothstack.ua.repos.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//@Service
//public class AdminUserDetailsService implements UserDetailsService {
//
//    @Autowired
//    UserRepository userRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) {
//        User user = userRepository.findUserByUsername(username).get();
//        if (user == null) {
//            throw new UsernameNotFoundException(username);
//        }
//        return new MyUserPrincipal(user);
//    }
//
//}
