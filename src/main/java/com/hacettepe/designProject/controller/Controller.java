package com.hacettepe.designProject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.hacettepe.designProject.service.UserService;

@RestController
public class Controller {
    @Autowired
    UserService userService;
    
    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/saveUser/{userName}")
    public void saveUser(@PathVariable("userName") String userName) throws JsonMappingException, JsonProcessingException{
        String url="https://api.github.com/users/{username}".replace("{username}",userName);
        String result=restTemplate.getForObject(url,String.class);
        userService.saveUser(result);
    }

    @GetMapping("/getUserRepos/{userName}")
    public void getUserRepos(@PathVariable("userName") String userName) throws JsonMappingException, JsonProcessingException{
        String url="https://api.github.com/users/{username}/repos".replace("{username}",userName);
        String result=restTemplate.getForObject(url,String.class);
        userService.getUserRepos(result);
    }
    @GetMapping("/saveUserRepos/{userName}")
    public void saveUserRepos(@PathVariable("userName") String userName) throws JsonMappingException, JsonProcessingException{
        String url="https://api.github.com/users/{username}/repos".replace("{username}",userName);
        String result=restTemplate.getForObject(url,String.class);
        userService.saveUserReposList(result);
    }
}
