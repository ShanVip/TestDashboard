package com.project.testdashboard.services;

import com.project.testdashboard.entities.Bug;
import com.project.testdashboard.entities.TestCase;
import com.project.testdashboard.repositories.BugRepository;
import com.project.testdashboard.repositories.TestCaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestCaseService {
    private final TestCaseRepository testCaseRepository;

    @Autowired
    public TestCaseService(TestCaseRepository testCaseRepository) {
        this.testCaseRepository = testCaseRepository;
    }

    public TestCase saveTestCase(TestCase testCase) {
        return testCaseRepository.save(testCase);
    }
}
