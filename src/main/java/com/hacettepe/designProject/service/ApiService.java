package com.hacettepe.designProject.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hacettepe.designProject.repository.OwnerRepository;
import com.hacettepe.designProject.repository.PullRepository;
import com.hacettepe.designProject.repository.UserRepoRepository;
import com.hacettepe.designProject.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.hacettepe.designProject.entity.Owner;
import com.hacettepe.designProject.entity.Pull;
import com.hacettepe.designProject.entity.User;
import com.hacettepe.designProject.entity.UserRepo;



@Service
public class ApiService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    OwnerRepository ownerRepository;

    @Autowired
    UserRepoRepository userRepoRepository;

    @Autowired
    PullRepository pullRepository;

    

    public void saveUser(String result) throws JsonMappingException, JsonProcessingException{
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        User user=objectMapper.readValue(result,User.class);
        userRepository.save(user);
    }

    public List<UserRepo> getUserRepos(String result) throws JsonMappingException, JsonProcessingException{
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        UserRepo[] userRepos=objectMapper.readValue(result,UserRepo[].class);
        List<UserRepo> userReposList= Arrays.asList(userRepos);
        for (UserRepo userRepo : userReposList) {
            System.out.println(userRepo.getName());
        }
        return userReposList;
    }
    public void saveUserReposList(String result) throws JsonMappingException, JsonProcessingException{
        List<UserRepo> userReposList = getUserRepos(result);
        for (UserRepo userRepo : userReposList) {
            if(ownerRepository.existsById(userRepo.getOwner().getId())==false){
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
                String ownerJson = ow.writeValueAsString(userRepo.getOwner());
                Owner owner=objectMapper.readValue(ownerJson, Owner.class);
                ownerRepository.save(owner);
            }
            userRepoRepository.save(userRepo);
        }
    }

    public List<Pull> getPulls(String result) throws JsonMappingException, JsonProcessingException{
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        Pull[] pulls=objectMapper.readValue(result, Pull[].class);
        List<Pull> pullList=Arrays.asList(pulls);
        for (Pull pull : pullList) {
            System.out.println(pull.getTitle());
        }
        return pullList;
    }

    public void savePulls(String result) throws JsonMappingException, JsonProcessingException{
        List<Pull> pulls=getPulls(result);
        for (Pull pull : pulls) {
            if(userRepoRepository.existsById(pull.getUser().getId())==false){
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
                String userJson = ow.writeValueAsString(pull.getUser());
                User user=objectMapper.readValue(userJson, User.class);
                userRepository.save(user);
            }
            pullRepository.save(pull);
        }
    }
}
    

