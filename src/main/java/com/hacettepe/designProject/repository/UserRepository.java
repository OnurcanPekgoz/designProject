package com.hacettepe.designProject.repository;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.hacettepe.designProject.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("Select u from User")
    public List<User> getUsers();

}
