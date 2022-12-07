package com.hacettepe.designProject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hacettepe.designProject.repository.UserRepository;

import com.hacettepe.designProject.entity.User;

@Service
public class UserService {
    
    @Autowired
    UserRepository userRepository;

    public List<User> getUsers(){
        List<User> userList=userRepository.getUsers();
        return userList;
    }
    
}
