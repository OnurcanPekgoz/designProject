package com.hacettepe.designProject.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hacettepe.designProject.repository.CommitRepository;
import com.hacettepe.designProject.repository.PullRepository;
import com.hacettepe.designProject.repository.UserRepoRepository;
import com.hacettepe.designProject.repository.UserRepository;
import com.hacettepe.designProject.result.CommitResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.hacettepe.designProject.entity.Commit;
import com.hacettepe.designProject.entity.Pull;
import com.hacettepe.designProject.entity.User;
import com.hacettepe.designProject.entity.UserRepo;



@Service
public class ApiService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserRepoRepository userRepoRepository;

    @Autowired
    PullRepository pullRepository;

    @Autowired
    CommitRepository commitRepository;

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
            System.out.println(userRepo.getId());
        }
        return userReposList;
    }
    public void saveUserReposList(String result) throws JsonMappingException, JsonProcessingException{
        List<UserRepo> userReposList = getUserRepos(result);
        for (UserRepo userRepo : userReposList) {
            if(userRepository.existsById(userRepo.getOwner().getId())==false){
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
                String ownerJson = ow.writeValueAsString(userRepo.getOwner());
                User owner=objectMapper.readValue(ownerJson, User.class);
                userRepository.save(owner);
            }
            userRepoRepository.save(userRepo);
        }
    }

    public List<Pull> getPulls(String result,String repo) throws JsonMappingException, JsonProcessingException{
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        Pull[] pulls=objectMapper.readValue(result, Pull[].class);
        List<Pull> pullList=Arrays.asList(pulls);
        for (Pull pull : pullList) {
            System.out.println(pull.getTitle());
        }
        return pullList;
    }

    public void savePulls(String result,String repo) throws JsonMappingException, JsonProcessingException{
        List<Pull> pulls=getPulls(result,repo);
        for (Pull pull : pulls) {
            if(userRepoRepository.existsById(pull.getUser().getId())==false){
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
                String userJson = ow.writeValueAsString(pull.getUser());
                User user=objectMapper.readValue(userJson, User.class);
                userRepository.save(user);
            }
            List<User> assignees=pull.getAssignees();
            for (User assignee : assignees) {
                if(userRepoRepository.existsById(assignee.getId())==false){
                    ObjectMapper objectMapper = new ObjectMapper();
                    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                    ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
                    String userJson = ow.writeValueAsString(assignee);
                    User user=objectMapper.readValue(userJson, User.class);
                    userRepository.save(user);
                }
            }
            List<User> reviewers=pull.getRequested_reviewers();
            for (User reviewer : reviewers) {
                if(userRepoRepository.existsById(reviewer.getId())==false){
                    ObjectMapper objectMapper = new ObjectMapper();
                    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                    ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
                    String userJson = ow.writeValueAsString(reviewer);
                    User user=objectMapper.readValue(userJson, User.class);
                    userRepository.save(user);
                }
            }
            pull.setRepo_name(repo);
            pull.setSha(pull.getHead().getSha());
            pullRepository.save(pull);
        }
    }

    public List<CommitResult> getPullCommits(String result) throws JsonMappingException, JsonProcessingException{
        List<CommitResult> commitResultList=getCommits(result);
        return commitResultList;
    }

    public List<CommitResult> getCommits(String result) throws JsonMappingException, JsonProcessingException{
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        CommitResult[] commitResults= objectMapper.readValue(result, CommitResult[].class);
        List<CommitResult> commitResultList= Arrays.asList(commitResults);
        for (CommitResult commitResult : commitResultList) {
            System.out.println(commitResult.getUrl());
        }
        return commitResultList;
    }

    public void saveCommits(String result)  throws JsonMappingException, JsonProcessingException{
        List<CommitResult> commitResults= getCommits(result);
        for (CommitResult commitResult : commitResults) {
            Commit commit = new Commit();
            commit.setSha(commitResult.getSha());
            commit.setAuthor(commitResult.getAuthor());
            commit.setCommitter(commitResult.getCommitter());
            commit.setUrl(commitResult.getUrl());
            commit.setDate(commitResult.getCommit().getCommitter().getDate());
            if(commitResult.getCommit()!=null){
                commit.setMessage(commitResult.getCommit().getMessage());
                commit.setComment_count(commitResult.getCommit().getComment_count());
            }
            // todo if user is deleted from github error
            if(userRepoRepository.existsById(commit.getAuthor().getId())==false){
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
                String userJson = ow.writeValueAsString(commit.getAuthor());
                User user=objectMapper.readValue(userJson, User.class);
                userRepository.save(user);
            }
            if(userRepoRepository.existsById(commit.getCommitter().getId())==false){
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
                String userJson = ow.writeValueAsString(commit.getCommitter());
                User user=objectMapper.readValue(userJson, User.class);
                userRepository.save(user);
            }
           commitRepository.save(commit);
        }
    }
}
    

