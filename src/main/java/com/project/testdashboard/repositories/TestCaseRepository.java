package com.project.testdashboard.repositories;

import com.project.testdashboard.entities.Bug;
import com.project.testdashboard.entities.TestCase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TestCaseRepository extends JpaRepository<TestCase, Long> {
    List<TestCase> findAllByOrderById();
    List<TestCase> findAllByOrderByName();
}
