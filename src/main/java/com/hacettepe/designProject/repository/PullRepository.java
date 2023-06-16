package com.hacettepe.designProject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hacettepe.designProject.entity.Pull;

@Repository
public interface PullRepository extends JpaRepository<Pull,Integer>{
    Pull findByNumberAndRepoName(int number,String repoName);
    List<Pull> findByRepoName(String repoName);
}
