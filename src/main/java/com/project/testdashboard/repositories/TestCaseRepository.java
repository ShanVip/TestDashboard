package com.project.testdashboard.repositories;

import com.project.testdashboard.entities.TestCase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestCaseRepository extends JpaRepository<TestCase, Long> {

}
