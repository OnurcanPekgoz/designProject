package com.hacettepe.designProject;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import com.hacettepe.designProject.entity.User;
import com.hacettepe.designProject.service.UserService;


@SpringBootTest
public class UserServiceTest {

    @Autowired
    UserService userService;

    @Test
    public void test(){
        assertDoesNotThrow(() -> {
        List<User> userList=userService.getUsers();
        for(int i=0;i<userList.size();i++){
            assert userList.get(i).getName() != null;
        }
    });
    }
}
