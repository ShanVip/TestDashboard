package com.project.testdashboard.services;

import com.project.testdashboard.entities.TestCase;
import com.project.testdashboard.repositories.TestCaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TestCaseService {

    private final TestCaseRepository testCaseRepository;

    @Autowired
    public TestCaseService(TestCaseRepository testCaseRepository) {
        this.testCaseRepository = testCaseRepository;
    }

    public void deleteTestCase(TestCase testCase) {
        testCaseRepository.delete(testCase);
    }

    public TestCase saveTestCase(TestCase testCase) {
        return testCaseRepository.save(testCase);
    }

    public TestCase getTestCaseById(Long testCaseId) {
        return testCaseRepository.findById(testCaseId).orElse(null);
    }
    public List<TestCase> getAllEntitiesSortedById() {
        return testCaseRepository.findAllByOrderById();
    }
    public List<TestCase> getAllEntitiesSortedByName() {
        return testCaseRepository.findAllByOrderByName();
    }
    public List<TestCase> getAllEntities() {
        return testCaseRepository.findAll();
    }

    public TestCase getBugById(Long testCaseId) {
        return testCaseRepository.findById(testCaseId).orElse(null);
    }

    public int getTotalTestCase() {
        return testCaseRepository.findAll().size();
    }
}
