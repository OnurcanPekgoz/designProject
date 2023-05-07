package com.hacettepe.designProject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
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
    ApiService apiService;
    
    @Autowired
    RestTemplate restTemplate;

    HttpHeaders headers = new HttpHeaders();
    HttpEntity<String> entity = new HttpEntity<>("", headers);

    Controller(){  
        headers.setBearerAuth("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");
    }

    
    @GetMapping("/saveUser/{userName}")
    public void saveUser(@PathVariable("userName") String userName) throws JsonMappingException, JsonProcessingException{
        String url="https://api.github.com/users/{username}".replace("{username}",userName);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        String result=response.getBody();
        apiService.saveUser(result);
    }

    @GetMapping("/getUserRepos/{userName}/{pageNum}")
    public void getUserRepos(@PathVariable("userName") String userName, @PathVariable("pageNum") String pageNum) throws JsonMappingException, JsonProcessingException{
        String url="https://api.github.com/users/{username}/repos?page=pageNum&per_page=100".replace("{username}",userName);
        url=url.replace("pageNum", pageNum);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        String result=response.getBody();
        apiService.getUserRepos(result);
    }
    @GetMapping("/saveUserRepos/{userName}/{pageNum}")
    public void saveUserRepos(@PathVariable("userName") String userName,@PathVariable("pageNum") String pageNum) throws JsonMappingException, JsonProcessingException{
        String url="https://api.github.com/users/{username}/repos?page=pageNum&per_page=100".replace("{username}",userName);
        url=url.replace("pageNum",pageNum);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        String result=response.getBody();
        apiService.saveUserReposList(result);
    }
    @GetMapping("/getPulls/{userName}/{repo}/{pageNum}")
    public void getPulls(@PathVariable("userName") String userName,@PathVariable("repo") String repo, @PathVariable("pageNum") String pageNum) throws JsonMappingException, JsonProcessingException{
        String url="https://api.github.com/repos/{username}/{repo}/pulls?state=all&?page=pageNum&per_page=100".replace("{username}",userName).replace("{repo}",repo);
        url=url.replace("pageNum", pageNum);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        String result=response.getBody();
        apiService.getPulls(result,repo);
    }
    @GetMapping("/savePulls/{userName}/{repo}/{pageNum}")
    public void savePulls(@PathVariable("userName") String userName,@PathVariable("repo") String repo, @PathVariable("pageNum") String pageNum) throws JsonMappingException, JsonProcessingException{
        String url="https://api.github.com/repos/{username}/{repo}/pulls?state=all&?page=pageNum&per_page=100".replace("{username}",userName).replace("{repo}",repo);
        url=url.replace("pageNum", pageNum);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        String result=response.getBody();
        apiService.savePulls(result,repo);
    }

    @GetMapping("/getCommits/{userName}/{repo}/{pageNum}")
    public void getCommits(@PathVariable("userName") String userName,@PathVariable("repo") String repo, @PathVariable("pageNum") String pageNum) throws JsonMappingException, JsonProcessingException{
        String url="https://api.github.com/repos/{username}/{repo}/commits?page=pageNum&per_page=100".replace("{username}",userName).replace("{repo}",repo).replace("pageNum", pageNum);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        String result=response.getBody();
        apiService.getCommits(result);
    }
    @GetMapping("/saveCommits/{userName}/{repo}/{pageNum}")
    public void saveCommits(@PathVariable("userName") String userName,@PathVariable("repo") String repo, @PathVariable("pageNum") String pageNum) throws JsonMappingException, JsonProcessingException{
        String url="https://api.github.com/repos/{username}/{repo}/commits?page=pageNum&per_page=100".replace("{username}",userName).replace("{repo}",repo).replace("pageNum", pageNum);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        String result=response.getBody();
        apiService.saveCommits(result,userName,repo,pageNum);
    }

    @GetMapping("/saveEventLog/{userName}/{repo}/{pullNum}")
    public void saveEventLog(@PathVariable("userName") String userName,@PathVariable("repo") String repo, @PathVariable("pullNum") String pullNum)throws JsonMappingException, JsonProcessingException{
        apiService.saveEventLog(userName,repo,pullNum);
    }

    @GetMapping("/saveEventLogs/{userName}/{repo}")
    public void saveEventLogs(@PathVariable("userName") String userName,@PathVariable("repo") String repo)throws JsonMappingException, JsonProcessingException{
        apiService.saveEventLogs(userName,repo);
    }
}
