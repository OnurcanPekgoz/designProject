package com.hacettepe.designProject;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.hacettepe.designProject.service.UserService;


@SpringBootTest
public class UserServiceTest {

    @Autowired
    UserService userService;

    @Test
    public void test2() throws JsonMappingException, JsonProcessingException{ 
        userService.getUserRepos("OnurcanPekgoz");
    }
}
