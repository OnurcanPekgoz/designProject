package com.hacettepe.designProject.repository;

import org.springframework.stereotype.Repository;

import com.hacettepe.designProject.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
