package com.hacettepe.designProject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hacettepe.designProject.entity.Commit;

@Repository
public interface CommitRepository extends JpaRepository<Commit,Integer> {
    List<Commit> findByPullNum(int pullNum);
}
