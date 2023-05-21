package com.project.testdashboard.repositories;

import com.project.testdashboard.entities.Bug;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BugRepository extends JpaRepository<Bug, Long> {

    int countByStatus(String status);
    List<Bug> findAllByOrderById();
    List<Bug> findAllByOrderByName();

}
