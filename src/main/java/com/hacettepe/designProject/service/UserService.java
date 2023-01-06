package com.hacettepe.designProject.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hacettepe.designProject.repository.OwnerRepository;
import com.hacettepe.designProject.repository.UserRepoRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hacettepe.designProject.entity.Owner;
import com.hacettepe.designProject.entity.UserRepo;



@Service
public class UserService {

    @Autowired
    OwnerRepository ownerRepository;

    @Autowired
    UserRepoRepository userRepoRepository;
    

    public void saveUser(String result) throws JsonMappingException, JsonProcessingException{
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        Owner user=objectMapper.readValue(result,Owner.class);
        ownerRepository.save(user);
    }

    public List<UserRepo> getUserRepos(String result) throws JsonMappingException, JsonProcessingException{
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        UserRepo[] userRepos=objectMapper.readValue(result,UserRepo[].class);
        List<UserRepo> userReposList= Arrays.asList(userRepos);
        return userReposList;
    }
    public void saveUserReposList(String result) throws JsonMappingException, JsonProcessingException{
        List<UserRepo> userReposList = getUserRepos(result);
        for (UserRepo userRepo : userReposList) {
            userRepoRepository.save(userRepo);
        }
    }
}
    

