package com.hacettepe.designProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hacettepe.designProject.entity.EventLog;

@Repository
public interface EventLogRepository extends JpaRepository<EventLog,Integer> {
    
}