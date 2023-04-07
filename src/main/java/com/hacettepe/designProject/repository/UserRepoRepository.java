package com.hacettepe.designProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hacettepe.designProject.entity.UserRepo;

@Repository
public interface UserRepoRepository extends JpaRepository<UserRepo,Integer>{
    // TODO duplicate repo name dikkat
    UserRepo findByName(String name);
}
