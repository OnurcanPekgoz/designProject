package com.hacettepe.designProject.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.hacettepe.designProject.repository.CommitRepository;
import com.hacettepe.designProject.repository.EventLogRepository;
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
import com.hacettepe.designProject.entity.EventLog;
import com.hacettepe.designProject.entity.Pull;
import com.hacettepe.designProject.entity.User;
import com.hacettepe.designProject.entity.UserRepo;



@Service
public class ApiService {
    @Autowired
    RestTemplate restTemplate;
    
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserRepoRepository userRepoRepository;

    @Autowired
    PullRepository pullRepository;

    @Autowired
    CommitRepository commitRepository;

    @Autowired
    EventLogRepository eventLogRepository;

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
        /*for (UserRepo userRepo : userReposList) {
            System.out.println(userRepo.getId());
        }*/
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
        /*for (Pull pull : pullList) {
            System.out.println(pull.getTitle());
        }*/
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
            pull.setRepoName(repo);
            pull.setSha(pull.getHead().getSha());
            pullRepository.save(pull);
        }
    }

    public List<CommitResult> getCommits(String result) throws JsonMappingException, JsonProcessingException{
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        CommitResult[] commitResults= objectMapper.readValue(result, CommitResult[].class);
        List<CommitResult> commitResultList= Arrays.asList(commitResults);
        /*for (CommitResult commitResult : commitResultList) {
            System.out.println(commitResult.getUrl());
        }*/
        return commitResultList;
    }

    public void saveCommits(String result,String userName,String repoName,String pageNum)  throws JsonMappingException, JsonProcessingException{
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
    
            if(commit.getAuthor() != null){
                if(userRepoRepository.existsById(commit.getAuthor().getId())==false){
                    ObjectMapper objectMapper = new ObjectMapper();
                    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                    ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
                    String userJson = ow.writeValueAsString(commit.getAuthor());
                    User user=objectMapper.readValue(userJson, User.class);
                    userRepository.save(user);
                }  
            }
            if(commit.getCommitter() != null){
                if(userRepoRepository.existsById(commit.getCommitter().getId())==false){
                    ObjectMapper objectMapper = new ObjectMapper();
                    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                    ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
                    String userJson = ow.writeValueAsString(commit.getCommitter());
                    User user=objectMapper.readValue(userJson, User.class);
                    userRepository.save(user);
                }
            }
            
            String url="https://api.github.com/repos/userName/repoName/commits/sha/pulls?page=pageNum&per_page=100";
            url=url.replace("userName", userName);
            url=url.replace("repoName", repoName);
            url=url.replace("sha", commit.getSha());
            url=url.replace("pageNum", pageNum)        ;    
            String pullJson=restTemplate.getForObject(url,String.class);
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            Pull[] pulls= objectMapper.readValue(pullJson, Pull[].class);
        
            for(Pull pull: pulls){
                commit.setPullNum(pull.getNumber());
            }
            commit.setRepoName(repoName);
            commit.setOwner(userName);

            commitRepository.save(commit);
        }
    }

    public void saveEventLog(String userName, String repo, String pullnum){
        UserRepo userRepo = userRepoRepository.findByName(repo);
        Pull pull = pullRepository.findByNumberAndRepoName(Integer.parseInt(pullnum), repo);
        List<Commit> commitList = commitRepository.findByPullNum(Integer.parseInt(pullnum));

        for (Commit commit : commitList) {
            
            EventLog eventlog = new EventLog();
            eventlog.setCaseId("Pull Request " + pullnum);
            eventlog.setActivity("push commit");
            eventlog.setTimestamp(commit.getDate());
            if(commit.getCommitter() != null){
                eventlog.setUser(commit.getCommitter().getLogin());
            }
            eventlog.setTitle(commit.getMessage());
            eventlog.setCommentCount(commit.getComment_count());
            eventLogRepository.save(eventlog);
            
        }
        EventLog eventlog1 = new EventLog("Pull Request " + pullnum, "Created pull request", pull.getCreated_at(), pull.getUser().getLogin(), pull.getTitle(), -1);
        eventLogRepository.save(eventlog1);
        EventLog eventlog2 = new EventLog("Pull Request " + pullnum, "Updated pull request", pull.getUpdated_at(), pull.getUser().getLogin(), pull.getTitle(), -1);
        eventLogRepository.save(eventlog2);
        EventLog eventlog3 = new EventLog("Pull Request " + pullnum, "Merged pull request", pull.getMerged_at(), pull.getUser().getLogin(), pull.getTitle(), -1);
        eventLogRepository.save(eventlog3);
        EventLog eventlog4 = new EventLog("Pull Request " + pullnum, "Closed pull request", pull.getClosed_at(), pull.getUser().getLogin(), pull.getTitle(), -1);
        eventLogRepository.save(eventlog4);
    }

    public void saveEventLogs(String userName, String repo){
        List<Pull> pullList=pullRepository.findByRepoName(repo);
        for (Pull pull : pullList) {
            saveEventLog(userName, repo, Integer.toString(pull.getNumber()));
        }
    }
    

}
    

