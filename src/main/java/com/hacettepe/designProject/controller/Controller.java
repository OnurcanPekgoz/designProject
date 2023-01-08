package com.hacettepe.designProject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.hacettepe.designProject.service.ApiService;

@RestController
public class Controller {
    @Autowired
    ApiService userService;
    
    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/saveUser/{userName}")
    public void saveUser(@PathVariable("userName") String userName) throws JsonMappingException, JsonProcessingException{
        String url="https://api.github.com/users/{username}".replace("{username}",userName);
        String result=restTemplate.getForObject(url,String.class);
        userService.saveUser(result);
    }

    @GetMapping("/getUserRepos/{userName}/{pageNum}")
    public void getUserRepos(@PathVariable("userName") String userName, @PathVariable("pageNum") String pageNum) throws JsonMappingException, JsonProcessingException{
        String url="https://api.github.com/users/{username}/repos?page=pageNum&per_page=100".replace("{username}",userName);
        url=url.replace("pageNum", pageNum);
        String result=restTemplate.getForObject(url,String.class);
        userService.getUserRepos(result);
    }
    @GetMapping("/saveUserRepos/{userName}/{pageNum}")
    public void saveUserRepos(@PathVariable("userName") String userName,@PathVariable("pageNum") String pageNum) throws JsonMappingException, JsonProcessingException{
        String url="https://api.github.com/users/{username}/repos?page=pageNum&per_page=100".replace("{username}",userName);
        url=url.replace("pageNum",pageNum);
        String result=restTemplate.getForObject(url,String.class);
        userService.saveUserReposList(result);
    }
    @GetMapping("/getPulls/{userName}/{repo}/{pageNum}")
    public void getPulls(@PathVariable("userName") String userName,@PathVariable("repo") String repo, @PathVariable("pageNum") String pageNum) throws JsonMappingException, JsonProcessingException{
        String url="https://api.github.com/repos/{username}/{repo}/pulls?state=all&?page=pageNum&per_page=100".replace("{username}",userName).replace("{repo}",repo);
        url=url.replace("pageNum", pageNum);
        String result=restTemplate.getForObject(url,String.class);
        userService.getPulls(result);
    }
    @GetMapping("/savePulls/{userName}/{repo}/{pageNum}")
    public void savePulls(@PathVariable("userName") String userName,@PathVariable("repo") String repo, @PathVariable("pageNum") String pageNum) throws JsonMappingException, JsonProcessingException{
        String url="https://api.github.com/repos/{username}/{repo}/pulls?state=all&?page=pageNum&per_page=100".replace("{username}",userName).replace("{repo}",repo);
        url=url.replace("pageNum", pageNum);
        String result=restTemplate.getForObject(url,String.class);
        userService.savePulls(result);
    }
}
