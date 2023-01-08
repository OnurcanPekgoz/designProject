package com.hacettepe.designProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hacettepe.designProject.entity.Pull;

@Repository
public interface PullRepository extends JpaRepository<Pull,Integer>{

}
