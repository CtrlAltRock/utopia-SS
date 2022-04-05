package com.smoothstack.ua.services;

import com.smoothstack.ua.models.UserRole;
import com.smoothstack.ua.repos.UserRoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserRoleService {

    Logger logger = LoggerFactory.getLogger(UserRoleService.class);

    @Autowired
    UserRoleRepository userRoleRepository;
    public List<UserRole> getAllUserRoles() { return (List<UserRole>) userRoleRepository.findAll(); }

    public UserRole getUserRoleById(Long userRoleId) {
        Optional<UserRole> userRole = userRoleRepository.findById(userRoleId);
        if(userRole.isPresent()) {
            return userRole.get();
        }
        else return null;
    }

    public UserRole getUserRoleByName(String name) {
        Optional<UserRole> userRole = userRoleRepository.findByName(name);
        if(userRole.isPresent()) {
            return userRole.get();
        }
        else return null;
    }

    public void saveUserRoles(List<UserRole> userRoles) { userRoleRepository.saveAll(userRoles); }

    public UserRole saveUserRole(UserRole userRole) {
        UserRole posted = userRoleRepository.save(userRole);
        return posted;
    }

    public void deleteUserRoles(List<UserRole> userRoles) { userRoleRepository.deleteAll(userRoles); }

    public void deleteUserRoleById(Long id) { userRoleRepository.deleteById(id); }

}
